package miniengine.Graphics;

public class GameColor {
    public double r;
    public double g;
    public double b;
    public double a;

    public GameColor(double r, double g, double b, double a) {
        this.r = Math.max(0,Math.min(255,r)) / 255.0;
        this.g = Math.max(0,Math.min(255,g)) / 255.0;
        this.b = Math.max(0,Math.min(255,b)) / 255.0;
        this.a = Math.max(0,Math.min(255,a)) / 255.0;
    }

    public GameColor(double r, double g, double b) {
        this(r,g,b,255);
    }

    private double clamp(double value){
        return Math.max(0,Math.min(1.0,value));
    }

    public static GameColor random(){
        return new GameColor(
                (int)(Math.random() * 255),
                (int)(Math.random() * 255),
                (int)(Math.random() * 255)
        );
    }

    // ____ PREDEFINIÇÔES ____
    public static final GameColor WHITE       = new GameColor(255, 255, 255);
    public static final GameColor BLACK       = new GameColor(0, 0, 0);
    public static final GameColor RED         = new GameColor(255, 0, 0);
    public static final GameColor GREEN       = new GameColor(0, 255, 0);
    public static final GameColor BLUE        = new GameColor(0, 0, 255);
    public static final GameColor YELLOW      = new GameColor(255, 255, 0);
    public static final GameColor CYAN        = new GameColor(0, 255, 255);
    public static final GameColor MAGENTA     = new GameColor(255, 0, 255);
    public static final GameColor GRAY        = new GameColor(128, 128, 128);
    public static final GameColor TRANSPARENT = new GameColor(0, 0, 0, 0);
}
