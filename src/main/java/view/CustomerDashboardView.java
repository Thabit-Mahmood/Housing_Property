package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Property;
import model.Transaction;
import controller.PropertyController;
import controller.TransactionController;

import java.util.List;

public class CustomerDashboardView {

    private PropertyController propertyController;
    private TransactionController transactionController;

    public CustomerDashboardView(PropertyController propertyController) {
        this.propertyController = propertyController;
        this.transactionController = new TransactionController(new TransactionView()); // Initialize TransactionController
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

        // Add columns to TableView
        propertyTableView.getColumns().addAll(addressColumn, sizeColumn, priceColumn);

        // Search button action
        searchButton.setOnAction(e -> {
            try {
                // Validate and parse input fields
                double minPrice = minPriceInput.getText().isEmpty() ? 0 : Double.parseDouble(minPriceInput.getText());
                double maxPrice = maxPriceInput.getText().isEmpty() ? Double.MAX_VALUE
                        : Double.parseDouble(maxPriceInput.getText());
                double minSize = minSizeInput.getText().isEmpty() ? 0 : Double.parseDouble(minSizeInput.getText());
                double maxSize = maxSizeInput.getText().isEmpty() ? Double.MAX_VALUE
                        : Double.parseDouble(maxSizeInput.getText());
                String location = locationInput.getText();

                // Fetch properties based on the criteria
                List<Property> results = propertyController.searchPropertiesByCriteria(minPrice, maxPrice, minSize,
                        maxSize, location);

                propertyTableView.getItems().clear();
                propertyTableView.getItems().addAll(results);

            } catch (NumberFormatException ex) {
                // Handle invalid input
                showError("Please enter valid numeric values for price and size.");
            }
        });

        // Button to view transaction history
        Button viewTransactionHistoryButton = new Button("View Transaction History");

        // Transaction history view (ListView)
        ListView<String> transactionListView = new ListView<>();

        // View transaction history button action
        viewTransactionHistoryButton.setOnAction(e -> {
            Property selectedProperty = propertyTableView.getSelectionModel().getSelectedItem();
            if (selectedProperty != null) {
                String projectName = selectedProperty.getProjectName();
                List<Transaction> transactions = transactionController.fetchTransactions(projectName); // Fetch transactions for the selected project
                transactionListView.getItems().clear();
                transactions.forEach(transaction -> transactionListView.getItems().add(transaction.toString())); // Display the transactions
            } else {
                transactionListView.getItems().clear();
                transactionListView.getItems().add("No property selected.");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label("Search Properties"),
                minPriceInput, maxPriceInput, minSizeInput, maxSizeInput, locationInput,
                searchButton, propertyTableView, viewTransactionHistoryButton, transactionListView);

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Show error message
    private void showError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}