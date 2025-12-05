package minieditor.Utils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import miniengine.Math.Vector2;

import java.util.function.Consumer;

public class InspectorFields {

    public static Label createSectionTitle(String title){
        Label lbl = new Label(title);
        lbl.getStyleClass().add("inspector-section-title");
        lbl.setMaxWidth(Double.MAX_VALUE);
        return lbl;
    }

    public static HBox createTextField(String text, String initialValue, Consumer<String> onUpdate) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label lbl = new Label(text);
        lbl.getStyleClass().add("inspector-prop-label");

        TextField tf = new TextField(initialValue);
        tf.getStyleClass().add("inspector-text-field");
        HBox.setHgrow(tf, Priority.ALWAYS);

        tf.setOnKeyTyped(event -> {onUpdate.accept(tf.getText());});

        row.getChildren().addAll(lbl, tf);
        return row;
    }

    public static HBox createVector2Field(String label, Vector2 vec){
        HBox row = new HBox(5);
        row.setAlignment(Pos.CENTER_LEFT);

        Label lbl = new Label(label);
        lbl.getStyleClass().add("inspector-prop-label");

        TextField fieldX = createNumberInput(vec.x, val -> vec.x = val);
        Label lblX = new Label("X");
        lbl.setStyle("-fx-text-fill: #ff5555; -fx-font-size: 9px; -fx-font-weight:bold;");

        TextField fieldY = createNumberInput(vec.y, val -> vec.y = val);
        Label lblY = new Label("Y");
        lbl.setStyle("-fx-text-fill: #ff5555; -fx-font-size: 9px; -fx-font-weight:bold;");

        row.getChildren().addAll(lbl ,lblX ,fieldX, lblY, fieldY);
        return row;
    }

    public static HBox createDoubleField(String label, double initialValue, Consumer<Double> onUpdate) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label lbl = new Label(label);
        lbl.getStyleClass().add("inspector-prop-label");

        // Usa o método auxiliar seguro
        TextField tf = createNumberInput(initialValue, onUpdate);
        HBox.setHgrow(tf, Priority.ALWAYS);

        row.getChildren().addAll(lbl, tf);
        return row;
    }

    private static TextField createNumberInput(double initialValue, Consumer<Double> onUpdate) {
        TextField tf = new TextField(String.valueOf(initialValue));
        tf.getStyleClass().add("inspector-number-field");

        tf.setOnKeyTyped(e -> {
            try {
                String text = tf.getText().replace(",", ".");

                if (text.equals("-") || text.equals(".") || text.isEmpty()) return;

                double val = Double.parseDouble(text);
                onUpdate.accept(val);

                tf.setStyle("-fx-text-fill: white;");
            } catch (NumberFormatException ex) {
                tf.setStyle("-fx-text-fill: #ff5555;");
            }
        });
        return tf;
    }
}
