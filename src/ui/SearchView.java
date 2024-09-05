package ui;
// SearchView.java
import Project;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SearchView extends VBox {

    public SearchView(Project project) {
        Label searchLabel = new Label("Search Properties");
        TextField sizeField = new TextField();
        sizeField.setPromptText("Size");
        TextField minPriceField = new TextField();
        minPriceField.setPromptText("Min Price");
        TextField maxPriceField = new TextField();
        maxPriceField.setPromptText("Max Price");
        TextField facilitiesField = new TextField();
        facilitiesField.setPromptText("Facilities");
        Button searchButton = new Button("Search");

        searchButton.setOnAction(e -> {
            String size = sizeField.getText();
            double minPrice = Double.parseDouble(minPriceField.getText());
            double maxPrice = Double.parseDouble(maxPriceField.getText());
            String facilities = facilitiesField.getText();

            // Perform search and update UI
            List<Property> searchResults = project.searchProperties(size, minPrice, maxPrice, facilities);
            getChildren().clear();
            getChildren().add(searchLabel);
            for (Property p : searchResults) {
                Label propertyDetails = new Label(p.getDetails());
                getChildren().add(propertyDetails);
            }
        });

        getChildren().addAll(searchLabel, sizeField, minPriceField, maxPriceField, facilitiesField, searchButton);
    }
}
