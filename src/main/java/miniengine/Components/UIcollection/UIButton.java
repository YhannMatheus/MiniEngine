package miniengine.Components.UIcollection;

import miniengine.Graphics.GameColor;
import miniengine.Graphics.Painter;
import miniengine.Input.Input;
import miniengine.Math.Vector2;
import miniengine.Structure.Transform;

public class UIButton extends InterfaceComponent {
    public Vector2 size;
    public UIText label;
    public Runnable onClick;
    public boolean interactable = true;

    public GameColor normalColor = new GameColor(100, 100, 255);
    public GameColor hoverColor = new GameColor(150, 150, 255);
    public GameColor pressedColor = new GameColor(50, 50, 200);
    public GameColor disabledColor = new GameColor(100, 100, 100);

    public boolean useBorder = false;
    public double borderWidth = 2.0;
    public GameColor borderColor = GameColor.WHITE;

    public boolean useGradient = false;
    public GameColor gradientTop = new GameColor(100, 100, 255);
    public GameColor gradientBottom = new GameColor(0, 0, 150);

    public boolean useDropShadow = false;
    public Vector2 shadowOffset = new Vector2(5, 5);
    public GameColor shadowColor = new GameColor(0, 0, 0, 100);

    private boolean isHovering = false;
    private boolean isPressed = false;

    public UIButton(Vector2 size, String text, Runnable onClick) {
        this.size = size;
        this.onClick = onClick;

        this.label = new UIText(text);
        this.label.color = GameColor.WHITE;
        this.label.fontSize = 20;
    }

    public void setBaseColor(GameColor color) {
        this.normalColor = color;
        this.hoverColor = adjustColor(color, 40);
        this.pressedColor = adjustColor(color, -50);

        this.gradientTop = adjustColor(color, 20);
        this.gradientBottom = adjustColor(color, -40);
    }

    @Override
    protected void onUpdateUI() {
        if (!interactable) {
            isHovering = false;
            isPressed = false;
            return;
        }

        Transform t = gameObject.transform;
        Vector2 mouse = Input.mousePosition;

        if (mouse.x >= t.position.x && mouse.x <= t.position.x + size.x &&
                mouse.y >= t.position.y && mouse.y <= t.position.y + size.y) {

            isHovering = true;

            if (Input.getMouseButton(0)) {
                isPressed = true;
            } else if (isPressed) {
                isPressed = false;
                if (onClick != null) onClick.run();
            }
        } else {
            isHovering = false;
            isPressed = false;
        }
    }

    @Override
    public void draw(Painter p) {
        Transform t = gameObject.transform;

        double drawX = t.position.x;
        double drawY = t.position.y;

        if (useDropShadow && !isPressed) {
            p.setColor(shadowColor);
            p.fillRect(drawX + shadowOffset.x, drawY + shadowOffset.y, size.x, size.y);
        }

        if (!interactable) {
            p.setColor(disabledColor);
            p.fillRect(drawX, drawY, size.x, size.y);
        }
        else if (useGradient && !isPressed && !isHovering) {
            p.fillGradientRect(drawX, drawY, size.x, size.y, gradientTop, gradientBottom);
        }
        else {
            GameColor drawColor;
            if (isPressed) drawColor = pressedColor;
            else if (isHovering) drawColor = hoverColor;
            else drawColor = normalColor;

            p.setColor(drawColor);
            p.fillRect(drawX, drawY, size.x, size.y);
        }

        if (useBorder) {
            p.setColor(borderColor);
            p.setLineWidth(borderWidth);
            p.drawRect(drawX, drawY, size.x, size.y);
        }

        double centerX = drawX + 15;
        double centerY = drawY + (size.y / 1.7);

        label.drawAt(p, new Vector2(centerX, centerY));
    }

    private GameColor adjustColor(GameColor original, int offset) {
        int r = clamp((int)(original.r + offset));
        int g = clamp((int)(original.g + offset));
        int b = clamp((int)(original.b + offset));
        return new GameColor(r, g, b, original.a);
    }

    private int clamp(int val) {
        return Math.max(0, Math.min(255, val));
    }

}
