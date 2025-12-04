package miniengine.Physics;

import miniengine.Components.Collider;
import miniengine.Math.Vector2;
import miniengine.Structure.Transform;

public class Physics {
    public static Vector2 gravity = new Vector2(0, 980);

    public static Vector2 checkCollision(Collider colA, Collider colB) {
        Transform tA = colA.gameObject.transform;
        Transform tB = colB.gameObject.transform;

        double centerAx = tA.position.x + colA.offset.x;
        double centerAy = tA.position.y + colA.offset.y;
        double centerBx = tB.position.x + colB.offset.x;
        double centerBy = tB.position.y + colB.offset.y;

        double dx = centerAx - centerBx;
        double dy = centerAy - centerBy;

        double combinedHalfWidths = (colA.size.x / 2) + (colB.size.x / 2);
        double combinedHalfHeights = (colA.size.y / 2) + (colB.size.y / 2);

        if (Math.abs(dx) < combinedHalfWidths && Math.abs(dy) < combinedHalfHeights) {

            double overlapX = combinedHalfWidths - Math.abs(dx);
            double overlapY = combinedHalfHeights - Math.abs(dy);

            if (overlapX < overlapY) {
                if (dx > 0) {
                    return new Vector2(overlapX, 0);
                } else {
                    return new Vector2(-overlapX, 0);
                }
            } else {
                if (dy > 0) {
                    return new Vector2(0, overlapY);
                } else {
                    return new Vector2(0, -overlapY);
                }
            }
        }

        return Vector2.zero();
    }
}