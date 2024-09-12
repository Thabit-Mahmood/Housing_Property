package main.java.view;

import java.util.ArrayList;
import java.util.List;

import main.java.model.Project;
import main.java.model.Property;

public class MainView {
    private List<Project> projects;

    public MainView() {
        projects = new ArrayList<>();
        initializeData();
        displayMenu();
    }

    private void initializeData() {
        // Creating sample projects and properties
        Project projectA = new Project("Project A");
        projectA.addProperty(new Property("1500 sq ft", 300000, "3 bedrooms, 2 bathrooms", "Project A", "123 Street"));
        projectA.addProperty(new Property("1600 sq ft", 320000, "4 bedrooms, 3 bathrooms", "Project A", "456 Avenue"));

        Project projectB = new Project("Project B");
        projectB.addProperty(new Property("1200 sq ft", 250000, "2 bedrooms, 2 bathrooms", "Project B", "789 Boulevard"));

        projects.add(projectA);
        projects.add(projectB);
    }

    public void displayMenu() {
        // Display search options and results here
    }

    public List<Project> getProjects() {
        return projects;
    }
}