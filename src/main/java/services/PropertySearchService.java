package main.java.services;

import java.util.ArrayList;
import java.util.List;

import main.java.model.Property;

public class PropertySearchService {
    private static PropertySearchService instance;
    private List<Property> properties; // Assume this list is populated elsewhere

    private PropertySearchService() {
        properties = new ArrayList<>();
        // Initialize with sample data or retrieve from a source
    }

    public static synchronized PropertySearchService getInstance() {
        if (instance == null) {
            instance = new PropertySearchService();
        }
        return instance;
    }

    public List<Property> searchProperties(double minSize, double maxSize, double minPrice, double maxPrice) {
        List<Property> matchedProperties = new ArrayList<>();
        for (Property property : properties) {
            double size = Double.parseDouble(property.getSize()); // Convert size from String to double
            if (size >= minSize && size <= maxSize &&
                property.getPrice() >= minPrice && property.getPrice() <= maxPrice) {
                matchedProperties.add(property);
            }
        }
        return matchedProperties;
    }

    // Method to set properties (for testing or initialization purposes)
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}