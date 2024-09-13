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
        this.projects = projects;
    }

    // Updated searchProperties method to return a list of Property
    public List<Property> searchProperties(double minSize, double maxSize, double minPrice, double maxPrice, String facilities, String projectName) {
        List<Property> matchedProperties = new ArrayList<>();

        // Iterate over each project and its properties
        for (Project project : projects) {
            if (project.getProjectName().equalsIgnoreCase(projectName)) {
                for (Property property : project.getProperties()) {
                    // Check if the property matches the criteria
                    double size = property.getSize();
                    double price = property.getPrice();
                    String propertyFacilities = property.getFacilities();

                    boolean matchesSize = size >= minSize && size <= maxSize;
                    boolean matchesPrice = price >= minPrice && price <= maxPrice;

                    // Split the facilities string and match individually
                    String[] propertyFacilitiesArray = propertyFacilities.toLowerCase().split(",\\s*");
                    boolean matchesFacilities = false;
                    for (String facility : propertyFacilitiesArray) {
                        if (facility.contains(facilities.toLowerCase())) {
                            matchesFacilities = true;
                            break;
                        }
                    }

                    // If all criteria match, add the property to the list
                    if (matchesSize && matchesPrice && matchesFacilities) {
                        matchedProperties.add(property);
                    }
                }
            }
        }

        // If no properties match the criteria, return an empty list
        return matchedProperties;
    }

    // New method to get a project by its name
    public Project getProjectByName(String projectName) {
        return projects.stream()
                .filter(project -> project.getProjectName().equalsIgnoreCase(projectName))
                .findFirst()
                .orElse(null); // Return null if no project is found
    }
}
