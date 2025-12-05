package miniengine.Core;

import miniengine.Objects.Camera;
import miniengine.Graphics.GameColor;
import miniengine.Graphics.Painter;
import miniengine.Structure.GameObject;
import miniengine.Math.Vector2;
import miniengine.Physics.Physics;
import miniengine.Components.Collider;

import java.util.*;

public class World {

    public String name;
    private int index;
    public Vector2 size;

    // Bounds (Limites do Mundo)
    public Vector2 minPoints;
    public Vector2 maxPoints;

    // Câmera do Mundo (Isso faltava para o Game não dar erro)
    public Camera camera;

    private static int instances;
    public boolean showBounds = false;

    protected List<GameObject> activeObjects = new ArrayList<>();
    private final List<GameObject> objectsToAdd = new ArrayList<>();

    private final int CELL_SIZE = 150;

    public World(Vector2 size){
        instances++;
        index = instances;
        this.name = "World_" + index;
        this.size = size;
        this.minPoints = new Vector2(0, 0);
        this.maxPoints = new Vector2(0, 0);

        // Inicializa a câmera no centro (ou 0,0)
        Vector2 defaultResolution = Game.getInstance().getScreenSize();
        if (defaultResolution.x == 0) defaultResolution = new Vector2(1280, 720);
        this.camera = new Camera(defaultResolution);
        this.addObject(this.camera);

        calculateBounds();
    }

    private void calculateBounds(){
        minPoints.y = -(size.y/2);
        maxPoints.y = +(size.y/2);
        minPoints.x = -(size.x/2);
        maxPoints.x = +(size.x/2);
    }

    // ____ Funções Livres ____
    public void onEnter() {}
    public void onExit() {}

    // ____ API Padrão da Engine (Correção dos Erros) ____

    public void start() {
        onEnter();
    }

    // Renomeado de updateWorld para update
    public void update() {
        processNewObjects();

        for (int i = 0; i < activeObjects.size(); i++) {
            activeObjects.get(i).runUpdate();
        }

        resolveCollisions();
        processDeadObjects(); // Processa mortos depois da física

        activeObjects.sort((a,b) -> Integer.compare(a.layer, b.layer));
    }

    // Renomeado de renerScene para draw
    public void draw(Painter painter) {
        for(int i = 0; i < activeObjects.size(); i++) {
            GameObject obj = activeObjects.get(i);
            if(!obj.isUI){
                obj.runDraw(painter);
            }
        }

        if(showBounds) drawWorldBounds(painter);

        for(int i = 0; i < activeObjects.size(); i++) {
            GameObject obj = activeObjects.get(i);
            if (obj.isUI) {
                obj.runDraw(painter);
            }
        }
    }

    // ____ Controle de Objetos ____
    public void addObject(GameObject obj) {
        objectsToAdd.add(obj);
    }

    //__ Funções Internas ___

    private void processNewObjects() {
        if (!objectsToAdd.isEmpty()) {
            activeObjects.addAll(objectsToAdd);
            for (GameObject obj : objectsToAdd) {
                obj.initialize();
            }
            objectsToAdd.clear();
        }
    }

    private void processDeadObjects() {
        activeObjects.removeIf(obj -> {
            if (obj.isDestroyed) {
                obj.dispose();
                return true;
            }
            return false;
        });
    }

    private void drawWorldBounds(Painter p) {
        p.save();
        p.setColor(GameColor.BLUE);
        p.setLineWidth(5);
        p.drawRect(minPoints.x, minPoints.y, size.x, size.y);
        p.setLineWidth(2);
        p.drawRect(-10, -10, 20, 20);
        p.restore();
    }

    public List<GameObject> getObjects() {
        return activeObjects;
    }

    public Vector2 clampPosition(Vector2 pos) {
        double clampedX = Math.max(minPoints.x, Math.min(maxPoints.x, pos.x));
        double clampedY = Math.max(minPoints.y, Math.min(maxPoints.y, pos.y));
        return new Vector2(clampedX, clampedY);
    }

    // --- FÍSICA E COLISÃO ---

    private void resolveCollisions() {
        // Reseta estado de colisão
        for (GameObject obj : activeObjects) {
            Collider col = obj.getComponent(Collider.class);
            if (col != null) col.inColliding = false;
        }

        // Grid Espacial (Otimização)
        Map<String, List<GameObject>> grid = new HashMap<>();

        for (GameObject obj : activeObjects) {
            Collider col = obj.getComponent(Collider.class);
            if (col == null) continue;

            int cellX = (int) (obj.transform.position.x / CELL_SIZE);
            int cellY = (int) (obj.transform.position.y / CELL_SIZE);
            String key = cellX + "," + cellY;

            grid.computeIfAbsent(key, k -> new ArrayList<>()).add(obj);
        }

        // Verifica colisão nas células vizinhas
        for (String key : grid.keySet()) {
            List<GameObject> objectsInCell = grid.get(key);
            String[] parts = key.split(",");
            int cx = Integer.parseInt(parts[0]);
            int cy = Integer.parseInt(parts[1]);

            checkCollisionBetweenLists(objectsInCell, objectsInCell);
            checkCollisionBetweenLists(objectsInCell, grid.get((cx + 1) + "," + cy));
            checkCollisionBetweenLists(objectsInCell, grid.get(cx + "," + (cy + 1)));
            checkCollisionBetweenLists(objectsInCell, grid.get((cx + 1) + "," + (cy + 1)));
            checkCollisionBetweenLists(objectsInCell, grid.get((cx - 1) + "," + (cy + 1)));
        }
    }

    private void checkCollisionBetweenLists(List<GameObject> listA, List<GameObject> listB) {
        if (listA == null || listB == null || listA.isEmpty() || listB.isEmpty()) return;

        for (GameObject objA : listA) {
            Collider colA = objA.getComponent(Collider.class);

            for (GameObject objB : listB) {
                if (objA == objB) continue;

                Collider colB = objB.getComponent(Collider.class);
                if (colB == null) continue;

                Vector2 push = Physics.checkCollision(colA, colB);

                if (push.x != 0 || push.y != 0) {
                    colA.inColliding = true;
                    colB.inColliding = true;

                    objA.runOnCollision(objB);
                    objB.runOnCollision(objA);

                    if (!colA.isTrigger && !colB.isTrigger) {

                        objA.transform.translate(new Vector2(push.x, push.y));

                        objA.runPhysicsResolved(push);
                    }
                }
            }
        }
    }
}