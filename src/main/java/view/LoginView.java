package view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Scanner;

public class LoginView {

  // Collects login details
  public String[] getLoginDetails() {
    try (Scanner sc = new Scanner(System.in)) {
      System.out.print("Username: ");
      String username = sc.nextLine();
      System.out.print("Password: ");
      String password = sc.nextLine();
      return new String[] { username, password };
    }
  }

  // Collects sign-up details
  public String[] getSignUpDetails() {
    try (Scanner sc = new Scanner(System.in)) {
      System.out.print("Username: ");
      String username = sc.nextLine();
      System.out.print("Password: ");
      String password = sc.nextLine();
      System.out.print("Email: ");
      String email = sc.nextLine();
      System.out.print("Role (Customer/Seller): ");
      String role = sc.nextLine();
      return new String[] { username, password, email, role };
    }
  }

  // Displays an error message in an alert box
  public void showError(String errorMessage) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(errorMessage);
    alert.showAndWait();
  }

  // Displays a success message
  public void showSuccess(String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}