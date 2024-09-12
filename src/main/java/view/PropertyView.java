package main.java.view;

import java.util.List;

import main.java.model.Property;

public class PropertyView {
    public void displaySearchResults(List<Property> properties) {
        if (properties.isEmpty()) {
            System.out.println("No properties found for the given criteria.");
        } else {
            for (Property property : properties) {
                property.displayPropertyDetails();
                System.out.println("-----");
            }
        }
    }
}