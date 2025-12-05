package minieditor.Utils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EditorGrid {
    private static final int BASE_SIZE = 50;
    private static final Color LINE_COLOR = Color.rgb(60, 60, 60,0.5);
    private static final Color AXIS_COLOR = Color.rgb(20,20,20);

    public static void draw(GraphicsContext gc, double width, double height, EditorCamera cam) {
        double zoom = cam.zoom;
        double gridSize = BASE_SIZE * zoom;

        if (gridSize < 10 ) return;

        double offsetX = cam.position.x % gridSize;
        double offsetY = cam.position.y % gridSize;

        gc.setLineWidth(1);
        gc.setStroke(LINE_COLOR);

        for (double x = offsetX; x < offsetX + gridSize; x += gridSize) {
            gc.strokeLine(x, 0, x, height);
        }

        for (double y = offsetY; y < offsetY + gridSize; y += gridSize) {
            gc.strokeLine(0, y, width, y);
        }

        double worldOriginX = cam.position.x;
        double worldOriginY = cam.position.y;

        if(worldOriginX >= 0 && worldOriginX <= width){
            gc.strokeLine(worldOriginX, 0, worldOriginX, height);
        }

        if (worldOriginY >= 0 && worldOriginY <= height) {
            gc.strokeLine(0, worldOriginY, width, worldOriginY);
        }
    }
}
