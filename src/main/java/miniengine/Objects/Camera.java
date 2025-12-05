package miniengine.Objects;

import miniengine.Math.*;
import miniengine.Structure.GameObject;


public class Camera extends GameObject {
    public static int instances;

    private String name;
    public Vector2 viewPortSize;
    public double zoom = 1.0;

    public Camera(Vector2 viewportSize){
        instances++;
        super("Camera_" + instances);
        this.viewPortSize = viewportSize;
    }

    public boolean isVisible(Vector2 objPos, Vector2 objSize) {
        // Margem de segurança (Culling Margin)
        double margin = 100;

        // A câmera foca no CENTRO do viewPortSize
        double camX = transform.position.x;
        double camY = transform.position.y;

        // Calcula as bordas da visão baseada no ViewPortSize e Zoom
        double halfWidth = (viewPortSize.x / 2) / zoom;
        double halfHeight = (viewPortSize.y / 2) / zoom;

        double camLeft = camX - halfWidth - margin;
        double camRight = camX + halfWidth + margin;
        double camTop = camY - halfHeight - margin;
        double camBottom = camY + halfHeight + margin;

        // Calcula as bordas do objeto
        double objLeft = objPos.x;
        double objRight = objPos.x + objSize.x;
        double objTop = objPos.y;
        double objBottom = objPos.y + objSize.y;

        // Verifica intersecção AABB
        boolean outsideHorizontally = (objRight < camLeft || objLeft > camRight);
        boolean outsideVertically = (objBottom < camTop || objTop > camBottom);

        return !outsideHorizontally && !outsideVertically;
    }

    public Vector2 worldToScreen(Vector2 worldPos) {
        double screenX = (worldPos.x - transform.position.x) * zoom + (viewPortSize.x / 2);
        double screenY = (worldPos.y - transform.position.y) * zoom + (viewPortSize.y / 2);
        return new Vector2(screenX, screenY);
    }

    public String getName() {return name;}
}
