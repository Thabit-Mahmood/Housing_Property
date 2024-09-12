package view;

import java.util.List;
import model.Project;
import model.Property;

public class ProjectView {
    public void displayProjectDetails(Project project) {
        System.out.println("Project: " + project.getProjectName());
        List<Property> properties = project.getProperties();
        if (properties.isEmpty()) {
            System.out.println("No properties available for this project.");
        } else {
            System.out.println("Properties:");
            for (Property property : properties) {
                property.displayPropertyDetails();
                System.out.println("-----");
            }
        }
    }
}