package main.java.controller;

import java.util.List;
import java.util.stream.Collectors;
import main.java.model.Project;
import main.java.model.Property;
import main.java.view.PropertyView;

public class PropertyController {
    private List<Project> projects;
    private PropertyView propertyView;

    public PropertyController(List<Project> projects, PropertyView propertyView) {
        this.projects = projects;
        this.propertyView = propertyView;
    }

    public void searchProperties(double minSize, double maxSize, double minPrice, double maxPrice, String facilities, String projectName) {
        List<Property> filteredProperties = projects.stream()
            .flatMap(project -> project.getProperties().stream())
            .filter(property -> {
                try {
                    double size = Double.parseDouble(property.getSize()); // Convert size from String to double
                    return size >= minSize && size <= maxSize &&
                           property.getPrice() >= minPrice && property.getPrice() <= maxPrice &&
                           property.getFacilities().contains(facilities) &&
                           property.getProjectName().equalsIgnoreCase(projectName);
                } catch (NumberFormatException e) {
                    // Handle case where size cannot be parsed as double
                    return false;
                }
            })
            .collect(Collectors.toList());

        propertyView.displayProperties(filteredProperties);
    }
}
