package minieditor.Utils;

import miniengine.Math.Vector2;

public class EditorCamera {
    public Vector2 position;
    public double zoom;

    public EditorCamera() {
        this.position = new Vector2();
        this.zoom = 1;
    }
}
