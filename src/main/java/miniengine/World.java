package miniengine;

import javafx.scene.canvas.GraphicsContext;
import miniengine.bases.Vector2;
import miniengine.components.Collider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class World {

    protected List<GameObject> activeObjects = new ArrayList<>();
    private final List<GameObject> objectsToAdd = new ArrayList<>();
    private final int CELL_SIZE = 150;

    public void onEnter() {}
    public void onExit() {}

    public void addObject(GameObject obj) {
        objectsToAdd.add(obj);
    }

    public void processNewObjects() {
        if (!objectsToAdd.isEmpty()) {
            for (GameObject obj : objectsToAdd) {
                obj.initialize();
                activeObjects.add(obj);
            }
            objectsToAdd.clear();
        }
    }

    public void processDeadObjects() {
        activeObjects.removeIf(obj -> {
            if (obj.isDestroyed) {
                obj.dispose();
                return true;
            }
            return false;
        });
    }

    public void updateWorld() {
        for (int i = 0; i < activeObjects.size(); i++) {
            activeObjects.get(i).runUpdate();
        }
        resolveCollisions();
    }

    public void renderWorld(GraphicsContext gc) {
        for (int i = 0; i < activeObjects.size(); i++) {
            activeObjects.get(i).runDraw(gc);
        }
    }

    private void resolveCollisions() {
        for (GameObject obj : activeObjects) {
            Collider col = obj.getComponent(Collider.class);
            if (col != null) col.inColliding = false;
        }

        Map<String, List<GameObject>> grid = new HashMap<>();

        for (GameObject obj : activeObjects) {
            Collider col = obj.getComponent(Collider.class);
            if (col == null) continue;

            int cellX = (int) (obj.transform.position.x / CELL_SIZE);
            int cellY = (int) (obj.transform.position.y / CELL_SIZE);
            String key = cellX + "," + cellY;

            grid.computeIfAbsent(key, k -> new ArrayList<>()).add(obj);
        }

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
                    }
                }
            }
        }
    }
}