package miniengine.Core;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import miniengine.Core.Game;
import miniengine.Input.Input;
import miniengine.Math.Vector2;

public class GameWindow {

    private Canvas canvas;
    private GraphicsContext gc;
    private AnimationTimer loop;
    private Vector2 resolution;

    public GameWindow(Vector2 resolution) {
        this.resolution = resolution;

        // Inicializa o Canvas (Superfície de desenho do JavaFX)
        this.canvas = new Canvas(resolution.x, resolution.y);
        this.gc = canvas.getGraphicsContext2D();

        // Configura Input e Singleton
        configureInput();
        configureGameInstance();

        // Prepara o loop (mas não inicia ainda, espera o start())
        createLoop();
    }

    // --- API PÚBLICA ---

    /**
     * Retorna o Canvas para ser inserido na interface do Editor.
     */
    public Canvas getCanvas() {
        return this.canvas;
    }

    /**
     * Inicia o loop do jogo e foca no canvas para receber input.
     */
    public void start() {
        if (loop != null) loop.start();
        canvas.requestFocus();
    }

    /**
     * Para o loop (útil para pausar o editor).
     */
    public void stop() {
        if (loop != null) loop.stop();
    }

    // --- CONFIGURAÇÕES INTERNAS ---

    private void configureGameInstance() {
        Game game = Game.getInstance();
        game.setGraphicsContext(gc);
        game.setScreenSize(resolution);
    }

    private void configureInput() {
        // Permite que o Canvas receba foco do teclado
        canvas.setFocusTraversable(true);

        // --- TECLADO ---
        canvas.setOnKeyPressed(e -> Input._onKeyPressed(e.getCode()));
        canvas.setOnKeyReleased(e -> Input._onKeyReleased(e.getCode()));

        // --- MOUSE ---
        canvas.setOnMousePressed(e -> Input._onMousePressed(e.getButton()));
        canvas.setOnMouseReleased(e -> Input._onMouseReleased(e.getButton()));

        // Movimento (Mouse Move normal + Dragged quando botão está segurado)
        canvas.setOnMouseMoved(e -> Input._onMouseMoved(e.getX(), e.getY()));
        canvas.setOnMouseDragged(e -> Input._onMouseMoved(e.getX(), e.getY()));

        // Hack de usabilidade: Clicar no jogo foca nele (para o teclado funcionar)
        canvas.setOnMouseClicked(e -> canvas.requestFocus());
    }

    private void createLoop() {
        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Delega toda a lógica para o Singleton Game
                Game.getInstance().runFrame();
            }
        };
    }
}