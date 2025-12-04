package miniengine.Components.UIcollection;

import miniengine.Graphics.GameColor;
import miniengine.Graphics.Painter;
import miniengine.Math.Vector2;
import miniengine.Structure.GameComponent;
import miniengine.Structure.Transform;

public class UIText extends GameComponent {
    public String text;
    public GameColor color = GameColor.WHITE;
    public double fontSize = 20;

    public boolean centerAlign = false;

    public UIText(String text) {
        this.text = text;
    }

    @Override
    public void draw(Painter p) {
        Transform t = gameObject.transform;

        p.setColor(color);
        p.setFont("Verdana", fontSize);

        p.drawText(text, t.position.x, t.position.y);
    }

    public void drawAt(Painter p, Vector2 position){
        p.setColor(color);
        p.setFont("Verdana", fontSize);

        double drawX = position.x;
        double drawY = position.y;

        p.drawText(text, drawX, drawY);
    }
}
