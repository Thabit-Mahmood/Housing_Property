package view;

import controller.AdminController;
import controller.PropertyController;
import controller.TransactionController;
import controller.UserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class MainView extends Application {

    private UserController userController = new UserController();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login or Sign Up");

        VBox layout = new VBox(10);
        
        // Create login UI components
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");

        Button loginButton = new Button("Login");
        Button signUpButton = new Button("Sign Up");  // New sign-up button

        // Add components to layout
        layout.getChildren().addAll(new Label("Login"), usernameInput, passwordInput, loginButton, signUpButton);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Handle login logic
        loginButton.setOnAction(e -> handleLogin(primaryStage, usernameInput.getText(), passwordInput.getText()));

        // Handle sign-up button click
        signUpButton.setOnAction(e -> displaySignUpForm(primaryStage));
    }

    private void handleLogin(Stage primaryStage, String username, String password) {
        User loggedInUser = userController.loginUser(username, password);

        if (loggedInUser == null) {
            System.out.println("Invalid login details. Please try again.");
            return;
        }

        // Based on the user role, display the appropriate dashboard
        if (loggedInUser.getRole().equals("Admin")) {
            AdminDashboardView adminView = new AdminDashboardView(
                new AdminController(null),  // Remove the Admin object
                new TransactionController(new TransactionView())
            );
            adminView.displayAdminDashboard(primaryStage);
        }
         else if (loggedInUser.getRole().equals("Seller")) {
            SellerDashboardView sellerView = new SellerDashboardView(new PropertyController(), (model.Seller) loggedInUser);
            sellerView.displaySellerDashboard(primaryStage);
        } else {
            CustomerDashboardView customerView = new CustomerDashboardView(new PropertyController());
            customerView.displayCustomerDashboard(primaryStage);
        }
    }

    private void displaySignUpForm(Stage primaryStage) {
        primaryStage.setTitle("Sign Up");

        VBox layout = new VBox(10);

        // Sign-up form components
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");

        TextField emailInput = new TextField();
        emailInput.setPromptText("Email");

        TextField roleInput = new TextField();
        roleInput.setPromptText("Role (Customer/Seller)");  // Allow only Customer or Seller

        Button submitSignUpButton = new Button("Submit");

        // Add components to layout
        layout.getChildren().addAll(new Label("Sign Up"), usernameInput, passwordInput, emailInput, roleInput, submitSignUpButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Handle sign-up logic when the button is clicked
        submitSignUpButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            String email = emailInput.getText();
            String role = roleInput.getText();

            userController.signUpUser(username, password, email, role);  // Calls the sign-up method from UserController

            // Redirect back to login after successful sign-up
            start(primaryStage);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
