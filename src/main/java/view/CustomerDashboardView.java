package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Property;
import controller.PropertyController;
import java.util.List;

public class CustomerDashboardView {

    private PropertyController propertyController;

    public CustomerDashboardView(PropertyController propertyController) {
        this.propertyController = propertyController;
    }

    // Display the customer dashboard
    @SuppressWarnings("unchecked")
    public void displayCustomerDashboard(Stage primaryStage) {
        primaryStage.setTitle("Customer Dashboard");

        // Search properties
        TextField minPriceInput = new TextField();
        minPriceInput.setPromptText("Min Price");

        TextField maxPriceInput = new TextField();
        maxPriceInput.setPromptText("Max Price");

        TextField minSizeInput = new TextField();
        minSizeInput.setPromptText("Min Size (SqFt)");

        TextField maxSizeInput = new TextField();
        maxSizeInput.setPromptText("Max Size (SqFt)");

        TextField locationInput = new TextField();
        locationInput.setPromptText("Location");

        Button searchButton = new Button("Search Properties");

        TableView<Property> propertyTableView = new TableView<>();

        // Create Table Columns
        TableColumn<Property, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Property, Double> sizeColumn = new TableColumn<>("Size (SqFt)");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<Property, Double> priceColumn = new TableColumn<>("Price ($)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        propertyTableView.getColumns().addAll(addressColumn, sizeColumn, priceColumn);

        // Search button action
        searchButton.setOnAction(e -> {
            try {
                double minPrice = minPriceInput.getText().isEmpty() ? 0 : Double.parseDouble(minPriceInput.getText());
                double maxPrice = maxPriceInput.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceInput.getText());
                double minSize = minSizeInput.getText().isEmpty() ? 0 : Double.parseDouble(minSizeInput.getText());
                double maxSize = maxSizeInput.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxSizeInput.getText());
                String location = locationInput.getText();

                List<Property> results = propertyController.searchPropertiesByCriteria(minPrice, maxPrice, minSize, maxSize, location);

                propertyTableView.getItems().clear();
                propertyTableView.getItems().addAll(results);
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter valid numerical values for price and size.");
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
            new Label("Search Properties"),
            minPriceInput, maxPriceInput, minSizeInput, maxSizeInput, locationInput,
            searchButton, propertyTableView
        );

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}