package miniengine.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import miniengine.GameComponent;
import miniengine.bases.Vector2;

public class Collider extends GameComponent {

    public Vector2 size;
    public Vector2 offset;

    public boolean isTrigger = false;
    private boolean isDebug = true;
    public boolean inColliding = false;

    public Collider(Vector2 size, Vector2 offset) {
        this.size = size;
        this.offset = offset;
    }

    public Collider(Vector2 Size){
        this.size = Size;
        this.offset = Vector2.zero();
    }


    public boolean overlaps(Collider other){
        Transform tA = this.gameObject.transform;

        if(tA == null) return false;

        // Arestas de A
        double aLeft = tA.position.x + this.offset.x - (this.size.x/2);
        double aRight = tA.position.x + this.offset.x + (this.size.x/2);
        double aTop = tA.position.y + this.offset.y - (this.size.y/2);
        double aBottom = tA.position.y + this.offset.y + (this.size.y/2);


        Transform tB = other.gameObject.transform;
        // Arestas de B
        double bLeft = tB.position.x + other.offset.x - (other.size.x/2);
        double bRight = tB.position.x + other.offset.x + (other.size.x/2);
        double bTop = tB.position.y + other.offset.y - (other.size.y/2);
        double bBottom = tB.position.y + other.offset.y + (other.size.y/2);


        // regras
        if (aRight < bLeft) return false;
        if (aLeft > bRight) return false;
        if (aBottom < bTop) return false;
        if (aTop > bBottom) return false;

        return true;
    }

    @Override
    public void draw(GraphicsContext gc){
        if(!isDebug) return;

        Transform t = gameObject.transform;

        gc.setStroke(isTrigger ? Color.YELLOW : Color.LIMEGREEN);

        gc.setLineWidth(1);
        double drawX = t.position.x + offset.x - (size.x/2);
        double drawY = t.position.y + offset.y - (size.y/2);
        gc.strokeRect(drawX,drawY,size.x,size.y);
    }
}
