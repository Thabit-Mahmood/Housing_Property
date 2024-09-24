package view;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.PropertyController;
import model.Property;
import model.Seller;
import services.ProjectService;

public class SellerDashboardView {

  private PropertyController propertyController;
  private Seller seller;
  @SuppressWarnings("unused")
  private ProjectService projectService; // Service to get existing projects
  private ObservableList<String> projectNames; // Dropdown for existing projects

  public SellerDashboardView(PropertyController propertyController, Seller seller) {
    this.propertyController = propertyController;
    this.seller = seller;
  }

  // Display the seller dashboard
  public void displaySellerDashboard(Stage primaryStage) {
    primaryStage.setTitle("Seller Dashboard");

    // List for displaying seller's approved properties
    ListView<String> propertyListView = new ListView<>();
    Button refreshButton = new Button("View Approved Properties");
    refreshButton.setOnAction(e -> {
      propertyListView.getItems().clear();
      for (Property property : propertyController.getSellerProperties(seller)) {
        propertyListView.getItems().add(property.getAddress() + " | Price: $" + property.getPrice());
      }
    });

    // Add a property (selecting from existing projects)
    ComboBox<String> projectNameDropdown = new ComboBox<>(projectNames);
    projectNameDropdown.setPromptText("Select Project");

    // Add property form fields
    TextField addressField = new TextField();
    addressField.setPromptText("Address");
    TextField sizeField = new TextField();
    sizeField.setPromptText("Size (sq ft)");
    TextField priceField = new TextField();
    priceField.setPromptText("Price");
    TextField facilitiesField = new TextField();
    facilitiesField.setPromptText("Facilities");
    Button submitForApprovalButton = new Button("Submit for Approval");

    // Submit for approval button action
    submitForApprovalButton.setOnAction(e -> {
      try {
        String projectName = projectNameDropdown.getValue();
        String address = addressField.getText();
        double size = Double.parseDouble(sizeField.getText());
        double price = Double.parseDouble(priceField.getText());
        String facilities = facilitiesField.getText();

        Property newProperty = new Property(size, price, facilities, projectName, address);
        propertyController.submitPropertyForApproval(seller, newProperty); // Submit for admin approval
        System.out.println("Property submitted for approval: " + newProperty.getAddress());
      } catch (NumberFormatException ex) {
        showError("Please enter valid numbers for size and price.");
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
}