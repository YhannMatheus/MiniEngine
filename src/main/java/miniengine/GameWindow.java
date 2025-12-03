package miniengine;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import miniengine.components.Camera;
import miniengine.components.Transform;

public class GameWindow extends Application {

    private Game game;
    private double width;
    private double height;

    @Override
    public void start(Stage stage) {
        this.game = Game.getInstance();
        this.width = game.getWidth();
        this.height = game.getHeight();

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        Scene scene = createScene(canvas);
        setupInputs(scene, canvas);

        stage.setTitle(game.getTitle());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        startGameLoop(gc);
    }

    private Scene createScene(Canvas canvas) {
        StackPane root = new StackPane(canvas);
        root.setStyle("-fx-background-color: black;");
        return new Scene(root, width, height);
    }

    private void setupInputs(Scene scene, Canvas canvas) {
        scene.setOnKeyPressed(e -> Input._onKeyPressed(e.getCode()));
        scene.setOnKeyReleased(e -> Input._onKeyReleased(e.getCode()));

        scene.setOnMousePressed(e -> Input._onMousePressed(e.getButton()));
        scene.setOnMouseReleased(e -> Input._onMouseReleased(e.getButton()));

        scene.setOnMouseMoved(e -> Input._onMouseMoved(e.getX(), e.getY()));
        scene.setOnMouseDragged(e -> Input._onMouseMoved(e.getX(), e.getY()));

        canvas.setFocusTraversable(true);
        canvas.requestFocus();
    }

    private void startGameLoop(GraphicsContext gc) {
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, width, height);

                gc.save();

                Camera cam = game.getMainCamera();
                if (cam != null) {
                    gc.translate(width / 2, height / 2);

                    double totalZoom = game.getScale() * cam.zoom;
                    gc.scale(totalZoom, totalZoom);

                    Transform camT = cam.gameObject.transform;
                    gc.translate(-camT.position.x, -camT.position.y);
                } else {
                    gc.scale(game.getScale(), game.getScale());
                }

                game.processNewObjects();
                game.updateAll();
                game.renderAll(gc);
                game.processDeadObjects();

                gc.restore();

                Input._endFrame();
            }
        }.start();
    }
}