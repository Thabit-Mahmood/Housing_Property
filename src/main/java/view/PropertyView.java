package main.java.view;

import java.util.List;
import main.java.model.Property;

public class PropertyView {
    public void displayProperties(List<Property> properties) {
        if (properties.isEmpty()) {
            System.out.println("No properties found matching the criteria.");
        } else {
            System.out.println("Matching Properties:");
            for (Property property : properties) {
                property.displayPropertyDetails();
                System.out.println("-----");
            }
        }
    }

    public void displayPropertyDetail(Property property) {
        property.displayPropertyDetails();
    }
}