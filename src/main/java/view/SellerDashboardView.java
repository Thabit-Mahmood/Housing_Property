package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.PropertyController;
import model.Property;
import model.Seller;

public class SellerDashboardView {

  private PropertyController propertyController;
  private Seller seller;

  public SellerDashboardView(PropertyController propertyController, Seller seller) {
    this.propertyController = propertyController;
    this.seller = seller;
  }

  // Display the seller dashboard
  public void displaySellerDashboard(Stage primaryStage) {
    primaryStage.setTitle("Seller Dashboard");

    // View owned properties
    ListView<String> propertyListView = new ListView<>();
    Button refreshButton = new Button("View Owned Properties");
    refreshButton.setOnAction(e -> {
      propertyListView.getItems().clear();
      for (Property property : propertyController.getSellerProperties(seller)) {
        propertyListView.getItems().add(property.getAddress());
      }
    });

    // Add a property
    TextField projectNameField = new TextField();
    projectNameField.setPromptText("Project Name");
    TextField addressField = new TextField();
    addressField.setPromptText("Address");
    TextField sizeField = new TextField();
    sizeField.setPromptText("Size");
    TextField priceField = new TextField();
    priceField.setPromptText("Price");
    TextField facilitiesField = new TextField();
    facilitiesField.setPromptText("Facilities");
    Button addPropertyButton = new Button("Add Property");

    addPropertyButton.setOnAction(e -> {
      String projectName = projectNameField.getText();
      String address = addressField.getText();
      double size = Double.parseDouble(sizeField.getText());
      double price = Double.parseDouble(priceField.getText());
      String facilities = facilitiesField.getText();

      Property newProperty = new Property(size, price, facilities, projectName, address);
      propertyController.addPropertyToSeller(seller, newProperty);
      System.out.println("Property added to owned properties.");
    });

    // Submit property for approval
    Button submitForApprovalButton = new Button("Submit for Approval");
    submitForApprovalButton.setOnAction(e -> {
      Property selectedProperty = getSelectedProperty(propertyListView);
      if (selectedProperty != null) {
        propertyController.submitPropertyForApproval(seller, selectedProperty);
        System.out.println("Submitted for approval: " + selectedProperty.getAddress());
      }
    });

    VBox layout = new VBox(10);
    layout.getChildren().addAll(
        new Label("Welcome to the Seller Dashboard"),
        projectNameField, addressField, sizeField, priceField, facilitiesField,
        addPropertyButton, refreshButton, propertyListView, submitForApprovalButton);

    Scene scene = new Scene(layout, 600, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  // Helper method to get the selected property from the ListView
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
