package miniengine.Graphics;


import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class Painter {
    private final GraphicsContext gc;

    public Painter(GraphicsContext gc) {
        this.gc = gc;
    }

    // --- CONFIGURAÇÃO ---

    public void setColor(GameColor color) {
        Color fxColor = new Color(color.r, color.g, color.b, color.a);
        gc.setFill(fxColor);
        gc.setStroke(fxColor);
    }

    public void setLineWidth(double width) {
        gc.setLineWidth(width);
    }

    public void setAlpha(double alpha) {
        gc.setGlobalAlpha(alpha);
    }

    // --- FORMAS ---

    public void fillRect(double x, double y, double width, double height) {
        gc.fillRect(x, y, width, height);
    }

    public void drawRect(double x, double y, double width, double height) {
        gc.strokeRect(x, y, width, height);
    }

    public void fillOval(double x, double y, double width, double height) {
        gc.fillOval(x, y, width, height);
    }

    // --- IMAGENS ---

    public void drawImage(Image img, double x, double y, double width, double height) {
        gc.drawImage(img, x, y, width, height);
    }

    public void drawImage(Image img, double sx, double sy, double sw, double sh,
                          double dx, double dy, double dw, double dh) {
        gc.drawImage(img, sx, sy, sw, sh, dx, dy, dw, dh);
    }

    // --- TEXTO ---

    public void setFont(String fontName, double size) {
        gc.setFont(new Font(fontName, size));
    }

    public void drawText(String text, double x, double y) {
        gc.fillText(text, x, y);
    }

    public void setTextAlignment(TextAlignment align) {
        gc.setTextAlign(align);
    }

    public void setTextBaseline(VPos vpos) {
        gc.setTextBaseline(vpos);
    }

    // --- TRANSFORMAÇÕES ---

    public void translate(double x, double y) {
        gc.translate(x, y);
    }

    public void rotate(double degrees) {
        gc.rotate(degrees);
    }

    public void scale(double x, double y) {
        gc.scale(x, y);
    }

    public void save() {
        gc.save();
    }

    public void restore() {
        gc.restore();
    }

    public GraphicsContext getRawContext() {
        return gc;
    }


}
