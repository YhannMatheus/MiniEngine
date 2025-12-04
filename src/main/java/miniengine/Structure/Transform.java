package miniengine.Structure;

import miniengine.Math.Vector2;

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

    public void rotate(double degrees){
        this.rotation += degrees;
    }

    public void lookAt(Transform target){
        double dy = target.position.y - this.position.y;
        double dx = target.position.x - this.position.x;

        double angleInRadians = Math.atan2(dx, dy);

        this.rotation += Math.toDegrees(angleInRadians);
    }

    public Vector2 forward() {
        double radians = Math.toRadians(rotation);
        return new Vector2(Math.cos(radians), Math.sin(radians));
    }

    public Vector2 backward(){
        Vector2 fwd = forward();
        return new Vector2(-fwd.x, -fwd.y);
    }

    public Vector2 right() {
        double radians = Math.toRadians(rotation + 90);
        return new Vector2(Math.cos(radians), Math.sin(radians));
    }
    public Vector2 left() {
        Vector2 r = right();
        return new Vector2(-r.x, -r.y);
    }
}
