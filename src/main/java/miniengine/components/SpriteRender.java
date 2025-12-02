package miniengine.components;

import javafx.scene.paint.Color;
import miniengine.GameComponent;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import miniengine.bases.Vector2;

import java.io.InputStream;

public class SpriteRender extends GameComponent {
    private Image sprite;

    public Vector2 size;
    public Vector2 offset;

    public SpriteRender(String fileName, Vector2 size){
        this.size = size;
        this.offset = Vector2.zero();

        String path;

        if (fileName.startsWith("/")){
            path = fileName;
        }else{
            path = "/images/" + fileName;
        }

        try{
            InputStream stream = getClass().getResourceAsStream(path);

            if (stream == null){
                System.err.println("ERRO GRAFICO: Imagem não encontrada '" + fileName + "'");
                System.err.println("    ↳ procura realizada em: src/main/resources" + path);
            }
        }catch (Exception e){
            InputStream stream = getClass().getResourceAsStream(path);

            if (stream == null) {
                System.err.println("--- ERRO DE CARREGAMENTO ---");
                System.err.println("Tentando carregar: " + path);
                System.err.println("Onde o Java está procurando recursos: " + getClass().getResource("/"));
                System.err.println("----------------------------");
            }

            e.printStackTrace();
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        Transform t = gameObject.getComponent(Transform.class);

        // Se não tem Transform, não temos onde desenhar, então abortamos silenciosamente.
        if (t == null) return;

        gc.save();
        gc.translate(t.position.x, t.position.y);
        gc.rotate(t.rotation);
        gc.scale(t.scale.x, t.scale.y);

        double drawX = (-size.x / 2) + offset.x;
        double drawY = (-size.y / 2) + offset.y;

        if (sprite != null) {
            gc.drawImage(sprite, drawX, drawY, size.x, size.y);
        } else {
            // Fallback visual silencioso (Quadrado Rosa)
            gc.setFill(Color.HOTPINK);
            gc.fillRect(drawX, drawY, size.x, size.y);
        }

        gc.restore();
    }
}
