package miniengine.bases;

public class Vector2 {

    public double x;
    public double y;

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static Vector2 zero() {
        return new Vector2(0, 0);
    }

    public static Vector2 one() {
        return new Vector2(1, 1);
    }

    public void normalize() {
        double length = Math.sqrt(x * x + y * y); // Pitágoras

        if (length != 0) {
            this.x = this.x / length;
            this.y = this.y / length;
        }
    }
}
