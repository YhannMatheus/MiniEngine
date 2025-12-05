package minieditor.Windows;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;;

public abstract class EditorWindow extends Stage {

    protected VBox content;
    private double xOffset = 0;
    private double yOffset = 0;

    public EditorWindow(String title, Stage owner, double width, double height) {
        initOwner(owner);
        initStyle(StageStyle.TRANSPARENT); // Essencial para arredondar cantos
        initModality(Modality.NONE);

        BorderPane windowLayout = new BorderPane();
        windowLayout.getStyleClass().add("custom-window-border");

        // --- CABEÇALHO (StackPane para centralizar título) ---
        StackPane titleContainer = new StackPane();
        titleContainer.getStyleClass().add("window-title-bar");

        // 1. Título no Centro
        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("window-title-label");

        // 2. Botão na Direita
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        Button hideBtn = new Button("−"); // Traço
        hideBtn.getStyleClass().add("window-action-button");
        hideBtn.setOnAction(e -> this.close());

        buttonBox.getChildren().add(hideBtn);
        titleContainer.getChildren().addAll(lblTitle, buttonBox);

        // --- ARRASTAR JANELA ---
        titleContainer.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        titleContainer.setOnMouseDragged(event -> {
            this.setX(event.getScreenX() - xOffset);
            this.setY(event.getScreenY() - yOffset);
        });

        // --- CONTEÚDO ---
        content = new VBox(10);
        content.setStyle("-fx-padding: 10; -fx-background-color: transparent;");

        windowLayout.setTop(titleContainer);
        windowLayout.setCenter(content);

        // --- CENA ---
        Scene scene = new Scene(windowLayout, width, height);
        scene.setFill(null); // Fundo nulo para transparência funcionar

        try {
            scene.getStylesheets().add(getClass().getResource("/minieditor/theme.css").toExternalForm());
        } catch (Exception e) { /* Ignorar */ }

        setScene(scene);
        drawInterface();
        setAlwaysOnTop(true);
    }

    public abstract void drawInterface();
}
