package main.java.controller;

import java.util.List;

import main.java.model.Property;
import main.java.services.PropertySearchService;
import main.java.view.PropertyView;

public class PropertyController {
    private PropertySearchService propertySearchService;
    private PropertyView propertyView;

    public PropertyController(PropertySearchService propertySearchService, PropertyView propertyView) {
        this.propertySearchService = propertySearchService;
        this.propertyView = propertyView;
    }

    public void searchProperties(String size, double minPrice, double maxPrice) {
        List<Property> result = propertySearchService.searchProperties(size, minPrice, maxPrice);
        propertyView.displaySearchResults(result);
    }
}
