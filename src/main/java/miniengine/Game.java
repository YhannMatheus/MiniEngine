package miniengine;

import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import miniengine.components.Camera;
import miniengine.components.SoundListener;

public class Game {

    private static Game instance;

    private final String title;
    private final int width;
    private final int height;
    private final double scale;

    private World currentWorld;
    private Camera mainCamera;
    private SoundListener soundListener;

    public Game(String title, int width, int height, double scale) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    public Game(String title, int width, int height) {
        this(title, width, height, 1.0);
    }

    public void start() {
        instance = this;
        Application.launch(GameWindow.class);
    }

    public void loadWorld(World newWorld) {
        if (currentWorld != null) {
            currentWorld.onExit();
        }
        currentWorld = newWorld;

        if (currentWorld != null) {
            currentWorld.onEnter();
        }
    }

    public void addObject(GameObject obj) {
        if (currentWorld != null) {
            currentWorld.addObject(obj);
        }
    }

    public void processNewObjects() {
        if (currentWorld != null) currentWorld.processNewObjects();
    }

    public void processDeadObjects() {
        if (currentWorld != null) currentWorld.processDeadObjects();
    }

    public void updateAll() {
        if (currentWorld != null) currentWorld.updateWorld();
    }

    public void renderAll(GraphicsContext gc) {
        if (currentWorld != null) currentWorld.renderWorld(gc);
    }

    // ____ Seters _____
    public void setMainCamera(Camera camera) {this.mainCamera = camera;}
    public void setListener(SoundListener soundListener) {this.soundListener = soundListener;}

    // ____ Geters _____
    public Camera getMainCamera() {return this.mainCamera;}
    public static Game getInstance() { return instance; }
    public String getTitle() { return title; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public double getScale() { return scale; }
    public SoundListener getListener() { return soundListener; }

}