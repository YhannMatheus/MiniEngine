package miniengine.Graphics;

public class AnimationClip {
    public String name;

    public int startFrame;
    public int endFrame;

    public double fps = 24;
    public boolean loop = false;

    public AnimationClip(String name, int startFrame, int endFrame, double fps, boolean loop) {
        this.name = name;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
        this.fps = fps;
        this.loop = loop;
    }

}
