package ui;
// PropertyView.java
import Project;
import Property;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PropertyView extends VBox {

    public PropertyView(Project project) {
        Label propertyLabel = new Label("Property Details:");
        // Display properties from the project
        for (Property property : project.getProperties()) {
            Label propertyDetails = new Label(property.getDetails());
            getChildren().add(propertyDetails);
        }
    }
}
