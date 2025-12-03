package miniengine.components;

import javafx.scene.paint.Color;
import miniengine.Game;
import miniengine.GameComponent;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import miniengine.bases.Vector2;

import java.io.InputStream;

public class SpriteRender extends GameComponent {
    private Image sprite;
    public Vector2 size;
    public Vector2 offset;

    public boolean flipX = false;
    public boolean flipY = false;
    public  double alpha;

    public SpriteRender(String fileName, Vector2 size){
        this.size = size;
        this.offset = Vector2.zero();

        String path = fileName.startsWith("/") ? fileName : "/images/" + fileName;

        try {
            InputStream stream = getClass().getResourceAsStream(path);
            if(stream == null){
                System.err.println("ERRO [" + gameObject.name + "] - " + path + " nao encontrado");
            } else {
                this.sprite = new Image(stream);
            }
        }catch (Exception e){
            System.err.println("ERRO [" + gameObject.name + "] - " + e.getMessage());
        }
    }

    @Override
    public void draw(GraphicsContext gc){
        Transform t = gameObject.transform;

        Camera cam = Game.getInstance().getMainCamera();

        if(cam != null & !cam.isVisible(t.position, this.size)) return;

        gc.save();

        gc.translate(t.position.x, t.position.y);
        gc.rotate(t.rotation);
        gc.scale(t.scale.x, t.scale.y);

        double scaleX = flipX ? -1 : 1;
        double scaleY = flipY ? -1 : 1;

        gc.setGlobalAlpha(alpha);

        double drawY = (-size.x/2) + offset.x;
        double drawX = (-size.x/2) + offset.y;

        if(sprite != null){
            gc.drawImage(sprite,drawX,drawY,size.x,size.y);
        }

        gc.restore();
    }
}
