package miniengine;

import javafx.scene.canvas.GraphicsContext;
import miniengine.components.Collider;

import java.util.ArrayList;
import java.util.List;

public abstract class World {
    protected List<GameObject> activeObjects = new ArrayList<>();
    private List<GameObject> objectsToAdd = new ArrayList<>();
    private List<GameObject> objectsToRemove = new ArrayList<>();

    // --- CICLO DE VIDA DO MUNDO ---

    /**
     * Chamado automaticamente quando o Game carrega este mundo.
     * É aqui que você cria o Player, o Mapa, os Inimigos, etc.
     */
    public abstract void onEnter();

    /**
     * Chamado quando trocamos para outro mundo.
     * Bom para parar músicas ou salvar dados.
     */
    public void onExit() {
        // Opcional
    }

    // --- GERENCIAMENTO DE OBJETOS ---

    public void addObject(GameObject obj) {
        objectsToAdd.add(obj);
    }

    public void removeObject(GameObject obj) {
        objectsToRemove.add(obj);
    }

    // --- MÉTODOS INTERNOS (Chamados pelo Game Loop) ---

    public void processNewObjects() {
        for (GameObject obj : objectsToAdd) {
            obj.initialize();
            activeObjects.add(obj);
        }
        objectsToAdd.clear();
    }

    public void processDeadObjects() {
        for (GameObject obj : objectsToRemove) {
            obj.dispose();
            activeObjects.remove(obj);
        }
        objectsToRemove.clear();
    }

    public void updateWorld() {
        for (GameObject obj : activeObjects) {
            obj.runUpdate();
        }

        resolveCollisions();
    }

    public void renderWorld(GraphicsContext gc) {
        for (GameObject obj : activeObjects) {
            obj.runDraw(gc);
        }
    }

    public void resolveCollisions(){
        for (GameObject obj : activeObjects) {
            Collider col = obj.getComponent(Collider.class);

            if(col != null){
                col.inColliding = false;
            }
        }
        for( int i = 0; i < activeObjects.size(); i++){
            GameObject objA = activeObjects.get(i);
            Collider colA = objA.getComponent(Collider.class);

            if(colA == null) continue;

            for( int j = i +1; j < activeObjects.size(); j++){
                GameObject objB = activeObjects.get(j);
                Collider colB = objB.getComponent(Collider.class);

                if(colB == null) continue;

                if (colA.overlaps(colB)) {
                    colA.inColliding = true;
                    colB.inColliding = true;
                }
            }
        }
    }
}
