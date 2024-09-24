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
        Label nextPendingPropertyLabel = new Label("Next Pending Property: None");

        updateNextPendingPropertyLabel(nextPendingPropertyLabel);

        // Approve the next pending property
        approvePropertyButton.setOnAction(e -> {
            Property nextPendingProperty = adminController.approveNextProperty();
            if (nextPendingProperty != null) {
                showSuccess("Approved Property: " + nextPendingProperty.getAddress());
                updateNextPendingPropertyLabel(nextPendingPropertyLabel); // Refresh pending property
            } else {
                showError("No pending properties to approve.");
            }
        });

        // Reject the next pending property
        rejectPropertyButton.setOnAction(e -> {
            Property nextPendingProperty = adminController.rejectNextProperty();
            if (nextPendingProperty != null) {
                showSuccess("Rejected Property: " + nextPendingProperty.getAddress());
                updateNextPendingPropertyLabel(nextPendingPropertyLabel); // Refresh pending property
            } else {
                showError("No pending properties to reject.");
            }
        });

        VBox propertyApprovalLayout = new VBox(10, propertyApprovalLabel, nextPendingPropertyLabel, approvePropertyButton, rejectPropertyButton);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(new Label("Welcome to the Admin Dashboard"), projectManagementLabel, projectNameInput, createProjectButton, deleteProjectInput, deleteProjectButton, listProjectsButton, projectListView, propertyApprovalLayout);

        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Update the label to show the next pending property
    private void updateNextPendingPropertyLabel(Label label) {
        Property nextPendingProperty = adminController.getPendingProperty();
        if (nextPendingProperty != null) {
            label.setText("Next Pending Property: " + nextPendingProperty.getAddress());
        } else {
            label.setText("Next Pending Property: None");
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
