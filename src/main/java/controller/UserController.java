package controller;

import model.User;
import services.UserService;
import view.LoginView;

public class UserController {
  private UserService userService;
  private LoginView loginView;

  public UserController() {
    this.userService = UserService.getInstance();
    this.loginView = new LoginView();
  }

  // Handles user login
  public User loginUser(String username, String password) {
    // Authenticate user
    User user = userService.authenticateUser(username, hashPassword(password));  // Use hashed password for comparison
    if (user != null) {
      user.displayUserDashboard();  // Redirect to the correct dashboard based on role
    } else {
      loginView.showError("Invalid username or password!");  // Error message for invalid credentials
    }
    return user;
  }

  // Handles user sign-up
  public void signUpUser(String username, String password, String email, String role) {
    if ("Admin".equalsIgnoreCase(role)) {
      loginView.showError("You cannot sign up as an Admin!");  // Prevent sign-up as Admin
      return;
    }
    if (!role.equalsIgnoreCase("Customer") && !role.equalsIgnoreCase("Seller")) {
      loginView.showError("Invalid role! You can only sign up as a Customer or Seller.");
      return;
    }

    // Validate password strength
    if (!isValidPassword(password)) {
      loginView.showError("Password is too weak! It must be at least 8 characters long.");
      return;
    }

    // Register user with hashed password
    userService.registerUser(username, hashPassword(password), email, role);
    loginView.showSuccess("Sign-up successful! Please log in.");
  }

  // Password hashing method (simple hashing for now, can be improved)
  private String hashPassword(String password) {
    return Integer.toString(password.hashCode());  // Simple hash, can replace with stronger hash like bcrypt
  }

  // Password strength validation
  private boolean isValidPassword(String password) {
    return password.length() >= 8;  // Ensure password has at least 8 characters
  }
}