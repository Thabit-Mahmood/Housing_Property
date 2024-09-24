package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import controller.AdminController;
import controller.TransactionController;

public class AdminDashboardView {

    private TransactionController transactionController;
    @SuppressWarnings("unused")
    private AdminController adminController;

    // Update the constructor to accept both AdminController and TransactionController
    public AdminDashboardView(AdminController adminController, TransactionController transactionController) {
        this.transactionController = transactionController;
        this.adminController = adminController;
    }

    // Display the admin dashboard
    public void displayAdminDashboard(Stage primaryStage) {
        primaryStage.setTitle("Admin Dashboard");

        // Transaction viewing section
        Label transactionLabel = new Label("View Transactions for Project:");
        TextField projectNameInput = new TextField();
        projectNameInput.setPromptText("Enter project name");

        Button viewTransactionsButton = new Button("View Transactions");
        ListView<String> transactionListView = new ListView<>();

        // Event handling for viewing transactions
        viewTransactionsButton.setOnAction(e -> {
            String projectName = projectNameInput.getText();
            if (!projectName.isEmpty()) {
                List<String> transactions = transactionController.fetchTransactions("src/resources/transactions.txt", projectName);
                transactionListView.getItems().clear();
                transactionListView.getItems().addAll(transactions);
            } else {
                transactionListView.getItems().clear();
                transactionListView.getItems().add("Please enter a project name.");
            }
        });

        VBox transactionLayout = new VBox(10, transactionLabel, projectNameInput, viewTransactionsButton, transactionListView);

        // Layout for entire admin dashboard
        VBox layout = new VBox(10);
        layout.getChildren().addAll(new Label("Welcome to the Admin Dashboard"), transactionLayout);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
