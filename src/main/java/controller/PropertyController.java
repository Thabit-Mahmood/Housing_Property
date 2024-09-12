package controller;

import java.util.List;
import java.util.stream.Collectors;
import model.Project;
import model.Property;
import view.PropertyView;

public class PropertyController {
    private List<Project> projects;
    private PropertyView propertyView;

    public PropertyController(List<Project> projects, PropertyView propertyView) {
        this.projects = projects;
        this.propertyView = propertyView;
    }

    public void displayPropertyDetails(Property property) {
        propertyView.displayPropertyDetail(property);
    }

    public void searchProperties(double minSize, double maxSize, double minPrice, double maxPrice, String facilities, String projectName) {
        List<Property> filteredProperties = projects.stream()
            .flatMap(project -> project.getProperties().stream())
            .filter(property -> {
                try {
                    double size = property.getSize();  // Now stored as double, no need for conversion
                    return size >= minSize && size <= maxSize &&
                           property.getPrice() >= minPrice && property.getPrice() <= maxPrice &&
                           property.getFacilities().contains(facilities) &&
                           property.getProjectName().equalsIgnoreCase(projectName);
                } catch (Exception e) {
                    System.out.println("Error filtering properties: " + e.getMessage());
                    return false;
                }
            })
            .collect(Collectors.toList());

        propertyView.displayProperties(filteredProperties);
    }
}