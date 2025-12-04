package miniengine.Graphics;

import miniengine.Core.Game;
import miniengine.Structure.GameComponent;
import javafx.scene.image.Image;
import miniengine.Math.Vector2;
import miniengine.Structure.Transform;

import java.io.InputStream;

public class SpriteRenderer extends GameComponent {
    // Imagem
    private Image sprite;

    // Dimensões
    public Vector2 size;
    public Vector2 offset;

    // Efeitos
    public boolean flipX = false;
    public boolean flipY = false;
    public double alpha = 1.0;

    public SpriteRenderer(String fileName, Vector2 size) {
        this.size = size;
        this.offset = Vector2.zero();

        String path = fileName.startsWith("/") ? fileName : "/images/" + fileName;

        try {
            InputStream stream = getClass().getResourceAsStream(path);
            if (stream == null) {
                System.err.println("ERRO [Sprite] - Imagem " + path + " nao encontrada");
            } else {
                this.sprite = new Image(stream);
            }
        } catch (Exception e) {
            System.err.println("ERRO [Sprite] - " + e.getMessage());
        }
    }

    @Override
    public void draw(Painter p) {
        if (sprite == null) return;

        Transform t = gameObject.transform;
        Camera cam = Game.getInstance().getMainCamera();

        // Culling (Só desenha se a câmera estiver vendo)
        if (cam != null && !cam.isVisible(t.position, this.size)) return;

        p.save();

        p.translate(t.position.x, t.position.y);

        p.rotate(t.rotation);

        double finalScaleX = t.scale.x * (flipX ? -1 : 1);
        double finalScaleY = t.scale.y * (flipY ? -1 : 1);
        p.scale(finalScaleX, finalScaleY);

        p.setAlpha(alpha);

        double drawX = (-size.x / 2) + offset.x;
        double drawY = (-size.y / 2) + offset.y;

        p.drawImage(sprite, drawX, drawY, size.x, size.y);

        p.restore();
    }

    public Image getImage() {
        return sprite;
    }
}
