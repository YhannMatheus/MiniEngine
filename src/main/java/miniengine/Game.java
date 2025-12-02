package miniengine;

import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;

public class Game {

    private static Game instance;

    // Configurações Globais
    private final String title;
    private final int width;
    private final int height;
    private final double scale;

    // REFERÊNCIA PARA O MUNDO ATUAL
    private World currentWorld;

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

    // --- SISTEMA DE TROCA DE MUNDOS ---

    public void loadWorld(World newWorld) {
        // 1. Se já tem um mundo rodando, avisa que ele vai fechar
        if (currentWorld != null) {
            currentWorld.onExit();
        }

        // 2. Troca a referência
        currentWorld = newWorld;

        // 3. Inicia o novo mundo
        if (currentWorld != null) {
            currentWorld.onEnter();
        }
    }

    // Atalho para adicionar objetos no mundo atual sem precisar pegar a instância do mundo
    public void addObject(GameObject obj) {
        if (currentWorld != null) {
            currentWorld.addObject(obj);
        }
    }

    // --- DELEGAÇÃO DO LOOP (GameWindow chama isso, Game repassa pro World) ---

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

    // Getters
    public static Game getInstance() { return instance; }
    public String getTitle() { return title; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public double getScale() { return scale; }
}