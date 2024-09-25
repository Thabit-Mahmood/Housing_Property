package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.AdminController;
import controller.TransactionController;
import model.Property;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class AdminDashboardView {

    private AdminController adminController;
    @SuppressWarnings("unused")
    private TransactionController transactionController;

    public AdminDashboardView(AdminController adminController, TransactionController transactionController) {
        this.adminController = adminController;
        this.transactionController = transactionController;
    }

    @SuppressWarnings("unchecked")
    public void displayAdminDashboard(Stage primaryStage) {
        primaryStage.setTitle("Admin Dashboard");

        // Section for project management
        Label projectManagementLabel = new Label("Project Management");

        TextField projectNameInput = new TextField();
        projectNameInput.setPromptText("Enter new project name");
        Button createProjectButton = new Button("Create Project");

        createProjectButton.setOnAction(e -> {
            String projectName = projectNameInput.getText();
            if (!projectName.isEmpty()) {
                adminController.createProject(projectName);
                projectNameInput.clear();
            } else {
                showError("Please enter a valid project name.");
            }
        });

        TextField deleteProjectInput = new TextField();
        deleteProjectInput.setPromptText("Enter project name to delete");
        Button deleteProjectButton = new Button("Delete Project");

        deleteProjectButton.setOnAction(e -> {
            String projectName = deleteProjectInput.getText();
            if (!projectName.isEmpty()) {
                adminController.deleteProject(projectName);
                deleteProjectInput.clear();
            } else {
                showError("Please enter a valid project name.");
            }
        });

        Button listProjectsButton = new Button("List All Projects");
        ListView<String> projectListView = new ListView<>();
        listProjectsButton.setOnAction(e -> {
            projectListView.getItems().clear();
            projectListView.getItems().addAll(adminController.listProjects());
        });

        // Section for property approval
        Label propertyApprovalLabel = new Label("Property Approval");
        Button approvePropertyButton = new Button("Approve Property");
        Button rejectPropertyButton = new Button("Reject Property");

// Create a TableView for the next pending property details
        TableView<Property> propertyTableView = new TableView<>();
// Set a fixed cell height (e.g., 40 pixels)
        propertyTableView.setFixedCellSize(40);
        propertyTableView.setPrefHeight(1500); // You can adjust the preferred height as needed

// Define columns
        TableColumn<Property, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Property, Double> sizeColumn = new TableColumn<>("Size (SqFt)");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<Property, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Property, String> facilitiesColumn = new TableColumn<>("Facilities");
        facilitiesColumn.setCellValueFactory(new PropertyValueFactory<>("facilities"));

        TableColumn<Property, String> sellerColumn = new TableColumn<>("Seller");
        sellerColumn.setCellValueFactory(new PropertyValueFactory<>("sellerUsername"));

// Add columns to the table
        propertyTableView.getColumns().addAll(addressColumn, sizeColumn, priceColumn, facilitiesColumn, sellerColumn);

// Update the table with the next pending property
        updatePropertyTableView(propertyTableView);

// Approve the next pending property
        approvePropertyButton.setOnAction(e -> {
            Property nextPendingProperty = adminController.approveNextProperty();
            if (nextPendingProperty != null) {
                showSuccess("Approved Property: " + nextPendingProperty.getAddress());
                updatePropertyTableView(propertyTableView); // Refresh pending property
            } else {
                showError("No pending properties to approve.");
            }
        });

// Reject the next pending property
        rejectPropertyButton.setOnAction(e -> {
            Property nextPendingProperty = adminController.rejectNextProperty();
            if (nextPendingProperty != null) {
                showSuccess("Rejected Property: " + nextPendingProperty.getAddress());
                updatePropertyTableView(propertyTableView); // Refresh pending property
            } else {
                showError("No pending properties to reject.");
            }
        });

        VBox propertyApprovalLayout = new VBox(10, propertyApprovalLabel, propertyTableView, approvePropertyButton, rejectPropertyButton);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(new Label("Welcome to the Admin Dashboard"), projectManagementLabel, projectNameInput, createProjectButton, deleteProjectInput, deleteProjectButton, listProjectsButton, projectListView, propertyApprovalLayout);

        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Update the label to show the next pending property
    // Update the label to show the next pending property
    // Update the TableView with the next pending property
    private void updatePropertyTableView(TableView<Property> tableView) {
        tableView.getItems().clear(); // Clear existing items
        Property nextPendingProperty = adminController.getPendingProperty();
        if (nextPendingProperty != null) {
            tableView.getItems().add(nextPendingProperty); // Add the next pending property to the table
        }
    }




    // Show error message
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Show success message
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}