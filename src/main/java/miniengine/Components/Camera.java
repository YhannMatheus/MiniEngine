package miniengine.Components;

import miniengine.Core.Game;
import miniengine.Math.*;
import miniengine.Structure.GameComponent;
import miniengine.Structure.GameObject;
import miniengine.Structure.Transform;


public class Camera extends GameComponent {
    public static int instances;
    private final String name;
    public Vector2 viewportSize;
    public double zoom = 1.0;

    public Camera(Vector2 viewportSize){
        instances++;
        name = "Camera_" + instances;
        this.viewportSize = viewportSize;
    }

    @Override
    public void start(){
        Game.getInstance().setMainCamera(this);
    }

    public void switGameObject(GameObject gameObject){
        this.gameObject = gameObject;
    }

    public boolean isVisible(Vector2 targetPos, Vector2 targetSize) {
        Transform myT = gameObject.transform;

        double left   = myT.position.x - (viewportSize.x / 2) / zoom;
        double right  = myT.position.x + (viewportSize.x / 2) / zoom;
        double top    = myT.position.y - (viewportSize.y / 2) / zoom;
        double bottom = myT.position.y + (viewportSize.y / 2) / zoom;

        double targetLeft   = targetPos.x - targetSize.x / 2;
        double targetRight  = targetPos.x + targetSize.x / 2;
        double targetTop    = targetPos.y - targetSize.y / 2;
        double targetBottom = targetPos.y + targetSize.y / 2;

        if (targetRight < left) return false;
        if (targetLeft > right) return false;
        if (targetBottom < top) return false;
        if (targetTop > bottom) return false;

        return true;
    }
    public String getName() {return name;}
}
