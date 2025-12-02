package miniengine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// Esta classe herda de Application, mas o usuário final nem precisa saber que ela existe.
public class GameWindow extends Application {

    @Override
    public void start(Stage stage) {
        // 1. Recupera a instância do Jogo que o usuário criou
        Game game = Game.getInstance();

        if (game == null) {
            throw new IllegalStateException("O jogo não foi inicializado corretamente via Game.start()");
        }

        // 2. Configura a janela usando os dados do objeto Game
        stage.setTitle(game.getTitle());
        stage.setResizable(false);

        // 3. Cria o Canvas (Tela de Pintura) com o tamanho configurado
        Canvas canvas = new Canvas(game.getWidth(), game.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 4. Monta a cena
        StackPane root = new StackPane(canvas);
        root.setStyle("-fx-background-color: black;"); // Fundo padrão preto
        Scene scene = new Scene(root, game.getWidth(), game.getHeight());

        stage.setScene(scene);
        stage.show();

        // Aqui iniciaremos o Game Loop depois
        System.out.println("Janela iniciada com sucesso: " + game.getTitle());
    }
}