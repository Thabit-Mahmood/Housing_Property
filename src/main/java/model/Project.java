package main.java.model;

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
            if (property.getSize() >= minSize && property.getSize() <= maxSize &&
                property.getPrice() >= minPrice && property.getPrice() <= maxPrice) {
                result.add(property);
            }
        }
        return result;
    }

    public String getProjectName() {
        return projectName;
    }
}