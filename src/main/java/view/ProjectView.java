package main.java.view;

import java.util.List;
import main.java.model.Project;

public class ProjectView {

    public void displayProjects(List<Project> projects) {
        if (projects.isEmpty()) {
            System.out.println("No projects available.");
        } else {
            System.out.println("Available Projects:");
            for (Project project : projects) {
                System.out.println("Project Name: " + project.getProjectName());
                // Additional project details can be added here
            }
        }
    }

    public void displayProjectDetails(Project project) {
        System.out.println("Project Name: " + project.getProjectName());
        System.out.println("Properties:");
        project.getProperties().forEach(property -> {
            System.out.println("Address: " + property.getAddress());
            System.out.println("Size: " + property.getSize());
            System.out.println("Price: " + property.getPrice());
            System.out.println("Facilities: " + property.getFacilities());
            System.out.println("-----");
        });
    }
}