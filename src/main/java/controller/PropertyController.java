package controller;

import java.util.ArrayList;
import java.util.List;
import model.Project;
import model.Property;
import view.PropertyView;

public class PropertyController {
    private List<Project> projects;
    @SuppressWarnings("unused")
    private PropertyView propertyView;

    public PropertyController(List<Project> projects, PropertyView propertyView) {
        this.projects = projects;
        this.propertyView = propertyView;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;  // Set the project list after initialization
    }

    // Return the search results as a List of Properties
    public List<Property> searchProperties(double minSize, double maxSize, double minPrice, double maxPrice, String facilities, String projectName) {
        List<Property> matchedProperties = new ArrayList<>();

        for (Project project : projects) {
            if (project.getProjectName().equalsIgnoreCase(projectName)) {
                for (Property property : project.getProperties()) {

                    double size = property.getSize();
                    double price = property.getPrice();
                    String propertyFacilities = property.getFacilities();

                    boolean matchesSize = size >= minSize && size <= maxSize;
                    boolean matchesPrice = price >= minPrice && price <= maxPrice;

                    String[] propertyFacilitiesArray = propertyFacilities.toLowerCase().split(",\\s*");
                    boolean matchesFacilities = false;
                    for (String facility : propertyFacilitiesArray) {
                        if (facility.contains(facilities.toLowerCase())) {
                            matchesFacilities = true;
                            break;
                        }
                    }

                    if (matchesSize && matchesPrice && matchesFacilities) {
                        matchedProperties.add(property);
                    }
                }
            }
        }

        return matchedProperties;
    }
}