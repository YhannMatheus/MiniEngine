package minieditor.Windows;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import minieditor.Utils.EditorCamera;
import miniengine.Core.Game;
import miniengine.Core.GameWindow;
import miniengine.Math.Vector2;

public class MainEditor extends Application {

    // --- Componentes Globais ---
    private GameWindow engine;
    private BorderPane rootLayout;
    private Button btnMaximize;

    // Variáveis de Controle (Arrasto e Mouse)
    private double xOffset = 0;
    private double yOffset = 0;
    private double lastMouseX = 0;
    private double lastMouseY = 0;

    @Override
    public void start(Stage primaryStage) {
        // O Maestro: Apenas delega as tarefas
        setupWindowStyle(primaryStage);
        createMainLayout();
        createTitleBar(primaryStage);
        createGameViewport();
        finalizeScene(primaryStage);

        launchTools(primaryStage);

        // Inicia o loop da engine
        engine.start();
    }

    // ---------------------------------------------------------
    // 1. CONFIGURAÇÃO DA JANELA
    // ---------------------------------------------------------
    private void setupWindowStyle(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);
    }

    private void createMainLayout() {
        rootLayout = new BorderPane();
        // Define a borda e fundo base do editor
        rootLayout.setStyle("-fx-border-color: #333; -fx-border-width: 1; -fx-background-color: #1e1e1e;");
    }

    private void finalizeScene(Stage stage) {
        // Cria a cena com tamanho inicial
        Scene scene = new Scene(rootLayout, 1280, 768);

        // Carrega o CSS
        try {
            scene.getStylesheets().add(getClass().getResource("/minieditor/theme.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Erro ao carregar CSS: " + e.getMessage());
        }

        stage.setScene(scene);
        stage.show();
    }

    // ---------------------------------------------------------
    // 2. BARRA DE TÍTULO (Top Bar)
    // ---------------------------------------------------------
    private void createTitleBar(Stage stage) {
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setStyle("-fx-background-color: #2d2d30; -fx-padding: 0;");

        // Título e Ícone
        Label titleLabel = new Label("  MiniEngine Editor");
        titleLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 12px; -fx-font-family: 'Segoe UI';");

        // Espaçador
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Botões de Controle (Min, Max, Close)
        HBox windowControls = createControlButtons(stage);

        titleBar.getChildren().addAll(titleLabel, spacer, windowControls);

        // Adiciona lógica de arrastar a janela
        enableWindowDragging(stage, titleBar);

        // Adiciona ao topo do layout principal
        rootLayout.setTop(titleBar);
    }

    private HBox createControlButtons(Stage stage) {
        HBox controls = new HBox(0);

        Button btnMinimize = createWindowButton("—");
        btnMinimize.setOnAction(e -> stage.setIconified(true));

        btnMaximize = createWindowButton("▢");
        btnMaximize.setOnAction(e -> toggleMaximize(stage));

        Button btnClose = createCloseButton();
        btnClose.setOnAction(e -> System.exit(0));

        controls.getChildren().addAll(btnMinimize, btnMaximize, btnClose);
        return controls;
    }

    private void enableWindowDragging(Stage stage, HBox titleBar) {
        titleBar.setOnMousePressed(event -> {
            if (!stage.isMaximized()) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        titleBar.setOnMouseDragged(event -> {
            if (!stage.isMaximized()) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        titleBar.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) toggleMaximize(stage);
        });
    }

    // ---------------------------------------------------------
    // 3. VIEWPORT DO JOGO (Grid e Câmera)
    // ---------------------------------------------------------
    private void createGameViewport() {
        // Inicializa a Engine
        Vector2 gameResolution = new Vector2(1280, 720);
        engine = new GameWindow(gameResolution);

        // Container do Jogo
        StackPane gameViewport = new StackPane();
        gameViewport.setStyle("-fx-background-color: #1e1e1e;");
        gameViewport.getChildren().add(engine.getCanvas());

        // Adiciona os controles de Zoom e Pan
        enableViewportNavigation(gameViewport);

        // Adiciona ao centro do layout
        rootLayout.setCenter(gameViewport);
    }

    private void enableViewportNavigation(StackPane viewport) {
        // Zoom (Scroll)
        viewport.setOnScroll(event -> {
            EditorCamera cam = Game.getInstance().editorCamera;
            double zoomFactor = 1.1;

            if (event.getDeltaY() > 0) cam.zoom *= zoomFactor;
            else cam.zoom /= zoomFactor;

            // Clamp (Limites de Zoom)
            if (cam.zoom < 0.1) cam.zoom = 0.1;
            if (cam.zoom > 5.0) cam.zoom = 5.0;
        });

        // Pan (Arrastar - Clique Inicial)
        viewport.setOnMousePressed(event -> {
            if (event.isMiddleButtonDown() || event.isPrimaryButtonDown()) {
                lastMouseX = event.getX();
                lastMouseY = event.getY();
            }
        });

        // Pan (Arrastar - Movimento)
        viewport.setOnMouseDragged(event -> {
            if (event.isMiddleButtonDown() || event.isPrimaryButtonDown()) {
                EditorCamera cam = Game.getInstance().editorCamera;

                double deltaX = event.getX() - lastMouseX;
                double deltaY = event.getY() - lastMouseY;

                cam.position.x += deltaX;
                cam.position.y += deltaY;

                lastMouseX = event.getX();
                lastMouseY = event.getY();
            }
        });
    }

    // ---------------------------------------------------------
    // 4. FERRAMENTAS E UTILITÁRIOS
    // ---------------------------------------------------------
    private void launchTools(Stage primaryStage) {
        HierarchyWindow hierarchy = new HierarchyWindow(primaryStage);
        hierarchy.setX(primaryStage.getX() + 50);
        hierarchy.setY(primaryStage.getY() + 100);
        hierarchy.show();
    }

    private void toggleMaximize(Stage stage) {
        boolean max = !stage.isMaximized();
        stage.setMaximized(max);
        btnMaximize.setText(max ? "❐" : "▢");
    }

    // Fábricas de Botões (Usando CSS)
    private Button createWindowButton(String icon) {
        Button btn = new Button(icon);
        btn.getStyleClass().add("window-control-button");
        return btn;
    }

    private Button createCloseButton() {
        Button btn = new Button("✕");
        btn.getStyleClass().add("window-close-button-main");
        return btn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}