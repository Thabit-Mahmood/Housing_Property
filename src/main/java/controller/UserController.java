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
    User user = userService.authenticateUser(username, password);
    if (user != null) {
      user.displayUserDashboard(); // Should display the admin dashboard
    } else {
      loginView.showError("Invalid username or password!");
    }
    return user;
  }

  // Handles user sign-up
  public void signUpUser(String username, String password, String email, String role) {
    if ("Admin".equalsIgnoreCase(role)) {
      loginView.showError("You cannot sign up as an Admin!");
      return;
    }
    if (!role.equalsIgnoreCase("Customer") && !role.equalsIgnoreCase("Seller")) {
      loginView.showError("Invalid role! You can only sign up as a Customer or Seller.");
      return;
    }
    userService.registerUser(username, password, email, role);
    loginView.showSuccess("Sign-up successful! Please log in.");
  }

}