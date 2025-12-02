package miniengine.components;

import miniengine.GameComponent;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import miniengine.bases.Vector2;

public class SpriteRender extends GameComponent {
    private Image sprite;

    public Vector2 size;
    public Vector2 offset;

    public SpriteRender(String imagePath, Vector2 size){
        this.size = size;
        this.offset = Vector2.zero();

        if(!imagePath.startsWith("/")) imagePath = "/" + imagePath;

        try {
            var stream = getClass().getResourceAsStream((imagePath));

            if(stream == null){
                System.err.println("ERROR: Imagem não encontrada: " + imagePath);
            }
            else
            {
                sprite = new Image(stream);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void draw(GraphicsContext gc){
        if( sprite == null) return;

        Transform t = gameObject.getComponent(Transform.class);

        gc.save();

        gc.translate(t.position.x, t.position.y);
        gc.rotate(t.rotation);
        gc.scale(t.scale.x, t.scale.y);

        double drawX = (-size.x / 2) + offset.x;
        double drawY = (-size.y / 2) + offset.y;

        gc.drawImage(sprite,drawX,drawY, size.x, size.y);

        gc.restore();
    }
}
