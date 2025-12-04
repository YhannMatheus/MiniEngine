package miniengine.Graphics;

import javafx.geometry.VPos;
import javafx.scene.text.TextAlignment;
import miniengine.Math.Vector2;
import miniengine.Physics.Collider;
import miniengine.Structure.GameComponent;
import miniengine.Structure.Transform;

public class GameText extends GameComponent {
    public enum Anchor {
        CENTER,
        TOP_LEFT, TOP_RIGHT, TOP_CENTER,
        BOTTOM_LEFT, BOTTOM_RIGHT, BOTTOM_CENTER,
        CENTER_LEFT, CENTER_RIGHT
    }

    public String text;
    public String fontName = "Verdana";
    public double fontSize = 20;
    public GameColor color = GameColor.WHITE;

    public Anchor anchor = Anchor.CENTER;
    public Vector2 offset = new Vector2(0, 0);

    private final Vector2 parentSize = new Vector2(0, 0);

    public GameText(String text) {
        this.text = text;
    }

    public GameText(String text, double size, GameColor color) {
        this.text = text;
        this.fontSize = size;
        this.color = color;
    }

    @Override
    public void draw(Painter p) {
        if (text == null || text.isEmpty()) return;

        p.save();
        p.setColor(color);
        p.setFont(fontName, fontSize);

        calculateParentSize();

        Transform t = gameObject.transform;
        double x = t.position.x;
        double y = t.position.y;
        double w = parentSize.x;
        double h = parentSize.y;

        TextAlignment alignX = TextAlignment.CENTER;
        VPos alignY = VPos.CENTER;

        double finalX = x;
        double finalY = y;

        switch (anchor) {
            case CENTER:
                alignX = TextAlignment.CENTER;
                alignY = VPos.CENTER;
                finalX = x;
                finalY = y;
                break;
            case TOP_LEFT:
                alignX = TextAlignment.LEFT;
                alignY = VPos.TOP;
                finalX = x - (w / 2);
                finalY = y - (h / 2);
                break;
            case TOP_RIGHT:
                alignX = TextAlignment.RIGHT;
                alignY = VPos.TOP;
                finalX = x + (w / 2);
                finalY = y - (h / 2);
                break;
            case TOP_CENTER:
                alignX = TextAlignment.CENTER;
                alignY = VPos.TOP;
                finalX = x;
                finalY = y - (h / 2);
                break;
            case BOTTOM_LEFT:
                alignX = TextAlignment.LEFT;
                alignY = VPos.BOTTOM;
                finalX = x - (w / 2);
                finalY = y + (h / 2);
                break;
            case BOTTOM_RIGHT:
                alignX = TextAlignment.RIGHT;
                alignY = VPos.BOTTOM;
                finalX = x + (w / 2);
                finalY = y + (h / 2);
                break;
            case BOTTOM_CENTER:
                alignX = TextAlignment.CENTER;
                alignY = VPos.BOTTOM;
                finalX = x;
                finalY = y + (h / 2);
                break;
            case CENTER_LEFT:
                alignX = TextAlignment.LEFT;
                alignY = VPos.CENTER;
                finalX = x - (w / 2);
                finalY = y;
                break;
            case CENTER_RIGHT:
                alignX = TextAlignment.RIGHT;
                alignY = VPos.CENTER;
                finalX = x + (w / 2);
                finalY = y;
                break;
        }

        p.setTextAlignment(alignX);
        p.setTextBaseline(alignY);
        p.drawText(text, finalX + offset.x, finalY + offset.y);

        p.restore();
    }

    private void calculateParentSize() {
        Collider col = gameObject.getComponent(Collider.class);
        if (col != null) {
            parentSize.x = col.size.x;
            parentSize.y = col.size.y;
            return;
        }

        SpriteRenderer spr = gameObject.getComponent(SpriteRenderer.class);
        if (spr != null) {
            parentSize.x = spr.size.x;
            parentSize.y = spr.size.y;
            return;
        }

        parentSize.x = 0;
        parentSize.y = 0;
    }
}
