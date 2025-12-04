package miniengine.Graphics;

import javafx.scene.image.Image;
import miniengine.Core.*;
import miniengine.Structure.*;
import miniengine.Math.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Animator extends GameComponent {
    private Image spriteSheet;
    private Map<String,AnimationClip> clips = new HashMap<>();
    private AnimationClip currentClip;

    public Vector2 frameSize;
    public Vector2 offset = Vector2.zero();

    private double timer = 0.0;
    private int currentFrameIndex = 0;

    public boolean flipX = false;
    public boolean flipY = false;
    public double alpha = 1.0;

    private int columsInSheet;

    public Animator (String fileName, Vector2 frameSize) {
        this.frameSize = frameSize;

        String path = fileName.startsWith("/") ? fileName : "/images/" + fileName;

        try {
            InputStream stream = getClass().getResourceAsStream(path);
            if(stream != null){
                this.spriteSheet = new Image(stream);
                this.columsInSheet = (int)(spriteSheet.getWidth()/frameSize.x);
            }
        }catch (Exception e){
            System.err.println("Error [Animator] - " + e.getMessage());
        }
    }

    public void addClip(AnimationClip clip){
        clips.put(clip.name, clip);
    }

    public void play(String clipName){
        if(currentClip != null && currentClip.name.equals(clipName)) return;

        AnimationClip clip = clips.get(clipName);
        if(clip != null){
            currentClip = clip;
            currentFrameIndex = clip.startFrame;
            timer = 0.0;
        }else{
            System.err.println("Aviso: Animação '" + clipName + "' não encontrada.");
        }
    }

    @Override
    public void update(){
        if(currentClip == null) return;

        timer += Time.deltaTime;

        double timePerFrame = 1.0 / currentClip.fps;

        if(timer >= timePerFrame){
            timer = 0.0;
            currentFrameIndex++;
            if(currentFrameIndex >= currentClip.endFrame){
                if(currentClip.loop){
                    currentFrameIndex = currentClip.startFrame;
                } else{
                    currentFrameIndex = currentClip.endFrame;
                }
            }
        }
    }

    @Override
    public void draw(Painter p){
        if(spriteSheet == null || currentClip == null) return;

        Transform t = gameObject.transform;
        Camera cam = Game.getInstance().getMainCamera();

        if(cam != null && !cam.isVisible(t.position, frameSize)) return;

        int col = currentFrameIndex % columsInSheet;
        int row = currentFrameIndex / columsInSheet;

        double srcX = col * frameSize.x;
        double srcY = row * frameSize.y;

        p.save();

        p.translate(t.position.x, t.position.y);
        p.rotate(t.rotation);

        double finalScaleX = t.scale.x * (flipX ? -1 : 1);
        double finalScaleY = t.scale.y * (flipY ? -1 : 1);

        p.setAlpha(alpha);

        double drawX = (-frameSize.x * finalScaleX) + offset.x;
        double drawY = (-frameSize.y * finalScaleY) + offset.y;

        p.drawImage(spriteSheet,srcX,srcY, frameSize.x,frameSize.y,
                    drawX, drawY, frameSize.x, frameSize.y);

        p.restore();
    }
}
