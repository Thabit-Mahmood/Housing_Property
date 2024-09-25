package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import controller.PropertyController;
import model.Property;
import model.Seller;
import services.ProjectService;

public class SellerDashboardView {

  private PropertyController propertyController;
  private Seller seller;
  private ProjectService projectService; // Service to get existing projects
  @SuppressWarnings("unused")
  private ObservableList<String> projectNames; // Dropdown for existing projects
  private ComboBox<String> projectNameDropdown;
  private TextField addressField;
  private TextField sizeField;
  private TextField priceField;
  private TextField facilitiesField;

  public SellerDashboardView(PropertyController propertyController, Seller seller) {
    this.propertyController = propertyController;
    this.seller = seller;
    this.projectService = ProjectService.getInstance(); // Initialize ProjectService
    initializeUIComponents(); // Initialize the UI components
  }

  // Initialize UI components
  private void initializeUIComponents() {
    projectNameDropdown = new ComboBox<>(); // ComboBox for project names
    projectNameDropdown.setPromptText("Select Project");

    // Add property form fields
    addressField = new TextField();
    addressField.setPromptText("Address");
    sizeField = new TextField();
    sizeField.setPromptText("Size (sq ft)");
    priceField = new TextField();
    priceField.setPromptText("Price");
    facilitiesField = new TextField();
    facilitiesField.setPromptText("Facilities");
  }

  // Display the seller dashboard
  public void displaySellerDashboard(Stage primaryStage) {
    primaryStage.setTitle("Seller Dashboard");

    // List for displaying seller's approved properties
    ListView<String> propertyListView = new ListView<>();
    Button refreshButton = new Button("View Approved Properties");
    refreshButton.setOnAction(e -> {
      propertyListView.getItems().clear();
      List<Property> sellerProperties = propertyController.getSellerProperties(seller);
      if (sellerProperties.isEmpty()) {
        propertyListView.getItems().add("No approved properties found.");
      } else {
        for (Property property : sellerProperties) {
          propertyListView.getItems().add(property.getAddress() + " | Price: $" + property.getPrice());
        }
      }
    });

    // Populate project names dropdown with projects from the CSV file
    // Populate project names dropdown with unique projects from the CSV file
    List<String> projectNames = projectService.getAllProjectNames();
// Use a Set to filter out duplicates
    Set<String> uniqueProjectNames = new HashSet<>(projectNames);
    ObservableList<String> projectOptions = FXCollections.observableArrayList(uniqueProjectNames);
    projectNameDropdown.setItems(projectOptions);
    projectNameDropdown.setPromptText("Select Project");


    // Submit for approval button action
    Button submitForApprovalButton = new Button("Submit for Approval");

    submitForApprovalButton.setOnAction(e -> {
      String projectName = projectNameDropdown.getValue();
      String address = addressField.getText();
      String sizeInput = sizeField.getText();
      String priceInput = priceField.getText();
      String facilities = facilitiesField.getText();

      // Validate inputs
      validateInputs(sizeInput, priceInput);

      // Check if validation passed
      if (isNumeric(sizeInput) && isNumeric(priceInput)) {
        double size = Double.parseDouble(sizeInput);
        double price = Double.parseDouble(priceInput);

        Property newProperty = new Property(size, price, facilities, projectName, address);
        propertyController.submitPropertyForApproval(seller, newProperty); // Submit for admin approval
        System.out.println("Property submitted for approval: " + newProperty.getAddress());

        // Clear all fields after successful submission
        clearFields();
      }
    });

    VBox layout = new VBox(10);
    layout.getChildren().addAll(
            new Label("Welcome to the Seller Dashboard"),
            new Label("Submit New Property for Approval:"),
            projectNameDropdown, addressField, sizeField, priceField, facilitiesField,
            submitForApprovalButton, refreshButton, propertyListView);

    Scene scene = new Scene(layout, 600, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  // Show error message
  @SuppressWarnings("unused")
  private void showError(String errorMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(errorMessage);
    alert.showAndWait();
  }

  // Helper method to get the selected property from the ListView
  @SuppressWarnings("unused")
  private Property getSelectedProperty(ListView<String> propertyListView) {
    String selectedPropertyAddress = propertyListView.getSelectionModel().getSelectedItem();
    if (selectedPropertyAddress != null) {
      for (Property property : propertyController.getSellerProperties(seller)) {
        if (property.getAddress().equals(selectedPropertyAddress)) {
          return property;
        }
      }
    }
    return null;
  }

  private void validateInputs(String sizeInput, String priceInput) {
    if (!isNumeric(sizeInput) || !isNumeric(priceInput)) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Input Error");
      alert.setHeaderText("Invalid Input");
      alert.setContentText("Please enter valid numbers for size and price.");
      alert.showAndWait();
    }
  }

  // Method to check if a string is numeric
  private boolean isNumeric(String str) {
    if (str == null || str.isEmpty()) {
      return false;
    }
    try {
      Double.parseDouble(str);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  private void clearFields() {
    addressField.clear();
    sizeField.clear();
    priceField.clear();
    facilitiesField.clear();
  }
}