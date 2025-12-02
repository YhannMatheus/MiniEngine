package miniengine;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import miniengine.bases.Vector2;

public class GameWindow extends Application {

    @Override
    public void start(Stage stage) {
        Game game = Game.getInstance();

        double finalWidth = game.getWidth();
        double finalHeight = game.getHeight();

        Canvas canvas = new Canvas(finalWidth, finalHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setImageSmoothing(false);

        StackPane root = new StackPane(canvas);
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, finalWidth, finalHeight);
        stage.setTitle(game.getTitle());
        stage.setScene(scene);

        stage.setResizable(true);
        stage.show();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {

                // Limpa a tela
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, finalWidth, finalHeight);

                gc.save();
                gc.scale(game.getScale(), game.getScale());

                // Roda o ciclo da Engine
                game.processNewObjects();    // INIT
                game.updateAll();            // UPDATE
                game.renderAll(gc);          // DRAW
                game.processDeadObjects();   // DISPOSE

                gc.restore();
            }
        }.start();
    }
}