package main.java.services;

import java.util.ArrayList;
import java.util.List;

import main.java.model.Property;

public class PropertySearchService {
    private static PropertySearchService instance;

    private PropertySearchService() {}

    public static synchronized PropertySearchService getInstance() {
        if (instance == null) {
            instance = new PropertySearchService();
        }
        return instance;
    }

    public List<Property> searchProperties(String size, double minPrice, double maxPrice) {
        List<Property> matchedProperties = new ArrayList<>();
        // Iterate through all properties and filter by criteria
        return matchedProperties;
    }
}