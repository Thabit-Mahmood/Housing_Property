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
            .filter(property -> property.getSize() >= minSize && property.getSize() <= maxSize)
            .filter(property -> property.getPrice() >= minPrice && property.getPrice() <= maxPrice)
            .filter(property -> property.getFacilities().contains(facilities))
            .filter(property -> property.getProjectName().equalsIgnoreCase(projectName))
            .collect(Collectors.toList());

        propertyView.displayProperties(filteredProperties);
    }
}