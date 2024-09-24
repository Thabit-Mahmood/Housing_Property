package model;

import java.util.ArrayList;
import java.util.List;

import services.PropertyApprovalService;

public class Seller extends User {
    private List<Property> ownedProperties;

    public Seller(String username, String password, String email) {
        super(username, password, email, "Seller");
        this.ownedProperties = new ArrayList<>();  // Initialize the list of owned properties
    }

    @Override
    public void displayUserDashboard() {
        System.out.println("Welcome to the Seller Dashboard!");
        // Seller-specific actions could be managed here, or in the view
    }

    // Method to add a property to the seller's list of owned properties
    public void addProperty(Property property) {
        ownedProperties.add(property);
        System.out.println("Property added: " + property.getAddress());
    }

    // Method to get all owned properties
    public List<Property> getOwnedProperties() {
        return ownedProperties;
    }

    // Method to submit property for approval
    public void submitPropertyForApproval(Property property, PropertyApprovalService approvalService) {
        approvalService.submitForApproval(property);
        System.out.println("Submitted property for approval: " + property.getAddress());
    }
}
