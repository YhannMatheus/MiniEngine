package miniengine.components;

import miniengine.GameComponent;
import miniengine.bases.Vector2;

public class Transform extends GameComponent {
    public Vector2 position;
    public Vector2 scale;
    public double rotation;

    //------ constructors -------
    public Transform(){
        this.position = Vector2.zero();
        this.scale = Vector2.one();
        this.rotation = 0;
    }
    public Transform(Vector2 position,Vector2 scale,double rotation){
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    // ---- Helpers -----
    public void translate(Vector2 direction){
        this.position.x += direction.x;
        this.position.y += direction.y;
    }

    public void rotate(double angle){
        this.rotation += angle;
    }
}
