package controller;

import java.util.List;
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

    public void setProjects(List<Project> projects) {
        this.projects = projects;  // Set the project list after initialization
    }

    public void searchProperties(double minSize, double maxSize, double minPrice, double maxPrice, String facilities, String projectName) {
        System.out.println("Searching for properties with the following criteria:");
        System.out.println("Size: " + minSize + " to " + maxSize);
        System.out.println("Price: " + minPrice + " to " + maxPrice);
        System.out.println("Facilities: " + facilities);
        System.out.println("Project Name: " + projectName);
        System.out.println("--------------------------------------");

        boolean foundMatch = false;

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


                    if (matchesSize && matchesPrice && matchesFacilities) {
                        foundMatch = true;
                        propertyView.displayPropertyDetail(property); // Display matching property
                    }
                }
            }
        }

        if (!foundMatch) {
            System.out.println("No properties found matching the criteria.");
        }
    }
}