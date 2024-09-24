package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.AdminController;
import controller.TransactionController;
import model.Property;

public class AdminDashboardView {

    private AdminController adminController;
    @SuppressWarnings("unused")
    private TransactionController transactionController;

    public AdminDashboardView(AdminController adminController, TransactionController transactionController) {
        this.adminController = adminController;
        this.transactionController = transactionController;
    }

    public void displayAdminDashboard(Stage primaryStage) {
        primaryStage.setTitle("Admin Dashboard");

        // Section for project management
        Label projectManagementLabel = new Label("Project Management");

        // Create project
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

        // Delete project
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

        // List all projects
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
        Label nextPendingPropertyLabel = new Label("Next Pending Property: None");
        
        // Get the next pending property for review
        Property nextPendingProperty = adminController.getPendingProperty();
        if (nextPendingProperty != null) {
            nextPendingPropertyLabel.setText("Next Pending Property: " + nextPendingProperty.getAddress());
        }

        // Approve the next pending property
        approvePropertyButton.setOnAction(e -> {
            if (nextPendingProperty != null) {
                adminController.approveProperty(nextPendingProperty);
                nextPendingPropertyLabel.setText("Next Pending Property: " + adminController.getPendingProperty());
            } else {
                showError("No pending properties.");
            }
        });

        // Reject the next pending property
        rejectPropertyButton.setOnAction(e -> {
            if (nextPendingProperty != null) {
                adminController.rejectProperty(nextPendingProperty);
                nextPendingPropertyLabel.setText("Next Pending Property: " + adminController.getPendingProperty());
            } else {
                showError("No pending properties.");
            }
        });

        // Layout for property approval management
        VBox propertyApprovalLayout = new VBox(10, propertyApprovalLabel, nextPendingPropertyLabel, approvePropertyButton, rejectPropertyButton);

        // Layout for entire admin dashboard
        VBox layout = new VBox(20);
        layout.getChildren().addAll(new Label("Welcome to the Admin Dashboard"), projectManagementLabel, projectNameInput, createProjectButton, deleteProjectInput, deleteProjectButton, listProjectsButton, projectListView, propertyApprovalLayout);

        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Show error message
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}