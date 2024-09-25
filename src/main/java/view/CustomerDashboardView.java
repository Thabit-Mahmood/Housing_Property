package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ObservableList<String> projectNames; // For dropdown/auto-suggest feature

    public CustomerDashboardView(PropertyController propertyController) {
        this.propertyController = propertyController;
        this.transactionController = new TransactionController(new TransactionView());
        this.projectNames = FXCollections.observableArrayList(propertyController.getAllProjectNames()); // Load project
                                                                                                        // names for
                                                                                                        // dropdown
    }

    // Display the customer dashboard
    @SuppressWarnings("unchecked")
    public void displayCustomerDashboard(Stage primaryStage) {
        primaryStage.setTitle("Customer Dashboard");

        // Search fields
        TextField minPriceInput = new TextField();
        minPriceInput.setPromptText("Min Price");

        TextField maxPriceInput = new TextField();
        maxPriceInput.setPromptText("Max Price");

        TextField minSizeInput = new TextField();
        minSizeInput.setPromptText("Min Size (SqFt)");

        TextField maxSizeInput = new TextField();
        maxSizeInput.setPromptText("Max Size (SqFt)");

        // Project name dropdown/auto-suggestion
        ComboBox<String> projectNameDropdown = new ComboBox<>(projectNames);
        projectNameDropdown.setPromptText("Project Name");

        TextField locationInput = new TextField();
        locationInput.setPromptText("Location");

        TextField facilitiesInput = new TextField();
        facilitiesInput.setPromptText("Facilities");

        Button searchButton = new Button("Search Properties");

        TableView<Property> propertyTableView = new TableView<>();

        // Create Table Columns for properties
        TableColumn<Property, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Property, Double> sizeColumn = new TableColumn<>("Size (SqFt)");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<Property, Double> priceColumn = new TableColumn<>("Price ($)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // New Columns for Project Name and Facilities
        TableColumn<Property, String> projectNameColumn = new TableColumn<>("Project Name");
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));

        TableColumn<Property, String> facilitiesColumn = new TableColumn<>("Facilities");
        facilitiesColumn.setCellValueFactory(new PropertyValueFactory<>("facilities"));

        // Add columns to TableView
        propertyTableView.getColumns().addAll(addressColumn, sizeColumn, priceColumn, projectNameColumn,
                facilitiesColumn);

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
                String facilities = facilitiesInput.getText();
                String selectedProjectName = projectNameDropdown.getSelectionModel().getSelectedItem();

                // Fetch properties based on the criteria
                List<Property> results = propertyController.searchPropertiesByCriteria(minPrice, maxPrice, minSize,
                        maxSize, location, selectedProjectName, facilities);

                propertyTableView.getItems().clear();
                propertyTableView.getItems().addAll(results);

            } catch (NumberFormatException ex) {
                // Handle invalid input
                showError("Please enter valid numeric values for price and size.");
            }
        });

        // View transaction history button
        Button viewTransactionHistoryButton = new Button("View Transaction History");
        ListView<String> transactionListView = new ListView<>();

        viewTransactionHistoryButton.setOnAction(e -> {
            Property selectedProperty = propertyTableView.getSelectionModel().getSelectedItem();
            if (selectedProperty != null) {
                String projectName = selectedProperty.getProjectName();
                System.out.println("Fetching transactions for project: " + projectName); // Debugging

                List<Transaction> transactions = transactionController.fetchRecentTransactions(projectName, 5);
                transactionListView.getItems().clear();
                if (!transactions.isEmpty()) {
                    for (Transaction transaction : transactions) {
                        transactionListView.getItems().add(transaction.toString());
                    }
                } else {
                    System.out.println("No transactions found for project: " + projectName); // Debugging
                    transactionListView.getItems().add("No transactions found for the selected project.");
                }
            } else {
                showError("Please select a property to view its transaction history.");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(minPriceInput, maxPriceInput, minSizeInput, maxSizeInput, projectNameDropdown,
                locationInput, facilitiesInput, searchButton, propertyTableView, viewTransactionHistoryButton,
                transactionListView);

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}
