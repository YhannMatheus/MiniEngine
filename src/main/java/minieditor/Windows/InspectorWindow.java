package minieditor.Windows;

import javafx.stage.Stage;
import minieditor.Utils.InspectorFields;
import miniengine.Structure.GameObject;

public class InspectorWindow extends EditorWindow {

    public InspectorWindow(Stage ower) {
        super("Inspector", ower,280,400);
    }

    @Override
    public void drawInterface(){}

    public void inspect(GameObject obj){
        content.getChildren().clear();

        if(obj == null) return;

        content.getChildren().add(InspectorFields.createSectionTitle("Object Settings"));

        if(obj.transform != null) createTransformInspector(new GameObject());


    }

    private void createTransformInspector(GameObject obj){
        content.getChildren().add(
                InspectorFields.createTextField("Name", obj.name, (val) -> obj.name = val)
        );
        content.getChildren().add(
                InspectorFields.createVector2Field("Position", obj.transform.position)
        );
        content.getChildren().add(
                InspectorFields.createVector2Field("Scale", obj.transform.scale)
        );
        content.getChildren().add(
                InspectorFields.createDoubleField("Rotation", obj.transform.rotation, val -> obj.transform.rotation = val)
        );
    }
}
