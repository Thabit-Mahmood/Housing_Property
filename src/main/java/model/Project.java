package model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String projectName;
    private List<Property> properties;

    public Project(String projectName) {
        this.projectName = projectName;
        this.properties = new ArrayList<>();
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public List<Property> getProperties() {
        return properties;
    }

    public List<Property> getPropertiesByCriteria(double minSize, double maxSize, double minPrice, double maxPrice) {
        List<Property> result = new ArrayList<>();
        for (Property property : properties) {
            try {
                double size = property.getSize();
                if (size >= minSize && size <= maxSize &&
                    property.getPrice() >= minPrice && property.getPrice() <= maxPrice) {
                    result.add(property);
                }
            } catch (NumberFormatException e) {
                // Handle case where size cannot be parsed as double
                System.out.println("Invalid size format: " + property.getSize());
            }
        }
        return result;
    }

    public String getProjectName() {
        return projectName;
    }
}