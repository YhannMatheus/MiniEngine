package minieditor.Windows;


import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class HierarchyWindow extends EditorWindow {

    public HierarchyWindow(Stage ower) {
        super("Hierarchy", ower, 250,400);
    }


    @Override
    public void drawInterface() {
        Label title = new Label("Scene Objects");
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: white");

        ListView<String> list = new ListView<>();
        list.getItems().add("Main Camera");

        content.getChildren().addAll(title,list);
    }
}
