package miniengine.Core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import miniengine.Components.Audio.SoundListener;
import miniengine.Objects.Camera;
import miniengine.Graphics.Painter;
import miniengine.Input.Input;
import miniengine.Math.Vector2;

import minieditor.Utils.EditorCamera;
import minieditor.Utils.EditorGrid;

public class Game {

    private static Game instance;

    // --- ESTADO GLOBAL ---
    private Vector2 screenSize = new Vector2(0,0);
    private GraphicsContext gc;
    private World currentWorld;
    private Painter painter;

    // --- SISTEMA DE ÁUDIO ---
    private SoundListener listener;

    // ___ REFERENCIAS AO EDITOR ____
    public EditorCamera editorCamera;

    private Game(){
        this.editorCamera = new EditorCamera();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    // --- CONFIGURAÇÃO ---

    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
        this.painter = new Painter(gc);
    }

    public void setScreenSize(Vector2 size) {
        this.screenSize = size;
    }

    public Vector2 getScreenSize() {
        return screenSize;
    }

    public double getWidth() {
        return screenSize.x;
    }

    public double getHeight() {
        return screenSize.y;
    }

    public double getScale() {
        return 1.0;
    }

    // --- MÉTODOS DE ÁUDIO (O que estava faltando) ---

    public void setListener(SoundListener listener) {
        this.listener = listener;
    }

    public SoundListener getListener() {
        return this.listener;
    }

    // --- LOOP DO JOGO ---

    public void runFrame() {
        if (gc == null || screenSize == null) return;

        // Limpa a tela
        gc.clearRect(0, 0, screenSize.x, screenSize.y);
        gc.setFill(Color.web("#1e1e1e"));
        gc.fillRect(0, 0, screenSize.x, screenSize.y);

        EditorGrid.draw(gc, screenSize.x, screenSize.y, editorCamera);

        // Atualiza e Renderiza o Mundo
        if (currentWorld != null) {
            currentWorld.update();
            if (painter != null) {
                currentWorld.draw(painter);
            }
        }

        //Finaliza Input
        Input._endFrame();
    }

    // --- GERENCIAMENTO DE CENAS ---

    public void setWorld(World world) {
        this.currentWorld = world;
        if (world != null) {
            world.start();
        }
    }

    public World getWorld() {
        return currentWorld;
    }

    public Camera getMainCamera() {
        if (currentWorld != null) return currentWorld.camera;
        return null;
    }

}