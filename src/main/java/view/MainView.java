package main.java.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.java.controller.PropertyController;
import main.java.controller.TransactionController;
import main.java.model.Project;
import main.java.model.Property;
import main.java.util.DropdownAutoSuggest;

public class MainView {
    private List<Project> projects;
    private PropertyController propertyController;
    private TransactionController transactionController;
    private DropdownAutoSuggest dropdownAutoSuggest;

    public MainView(PropertyController propertyController, TransactionController transactionController) {
        projects = new ArrayList<>();
        this.propertyController = propertyController;
        this.transactionController = transactionController;
        initializeData();
        initializeAutoSuggest();
        displayMenu();
    }

    private void viewProjects() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a project:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getProjectName());
        }
        int projectChoice = scanner.nextInt();
        Project selectedProject = projects.get(projectChoice - 1);
        ProjectView projectView = new ProjectView();
        projectView.displayProjectDetails(selectedProject);
    }

    private void initializeData() {
        // Initialize sample data (e.g., add projects and properties)
        // Example:
        // projects.add(new Project("Project A"));
        // projects.add(new Project("Project B"));
    }

    private void initializeAutoSuggest() {
        List<String> projectNames = new ArrayList<>();
        for (Project project : projects) {
            projectNames.add(project.getProjectName());
        }
        dropdownAutoSuggest = new DropdownAutoSuggest(projectNames);
    }

    private void viewPropertyDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a property to view details:");
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            System.out.println((i + 1) + ". Project: " + project.getProjectName());
            List<Property> properties = project.getProperties();
            for (int j = 0; j < properties.size(); j++) {
                System.out.println("   " + (j + 1) + ". Address: " + properties.get(j).getAddress());
            }
        }
        int projectChoice = scanner.nextInt();
        Project selectedProject = projects.get(projectChoice - 1);
        List<Property> properties = selectedProject.getProperties();
        int propertyChoice = scanner.nextInt();
        Property selectedProperty = properties.get(propertyChoice - 1);
        PropertyView propertyView = new PropertyView();
        propertyView.displayPropertyDetail(selectedProperty);
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Property Search System.");
        System.out.println("1. Search Properties");
        System.out.println("2. View Transactions");
        System.out.println("3. View Projects");
        System.out.println("4. View Property Details");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                searchProperties();
                break;
            case 2:
                viewTransactions();
                break;
            case 3:
                viewProjects();
                break;
            case 4:
                viewPropertyDetails();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void searchProperties() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter minimum size: ");
        double minSize = scanner.nextDouble();
        System.out.print("Enter maximum size: ");
        double maxSize = scanner.nextDouble();
        System.out.print("Enter minimum price: ");
        double minPrice = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double maxPrice = scanner.nextDouble();
        System.out.print("Enter desired facilities: ");
        String facilities = scanner.next();
        System.out.print("Enter project name: ");
        String projectName = scanner.next();

        propertyController.searchProperties(minSize, maxSize, minPrice, maxPrice, facilities, projectName);
    }

    // Inside MainView.java, `viewTransactions` method
    private void viewTransactions() {
        System.out.println("Select a project:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getProjectName());
        }
        Scanner scanner = new Scanner(System.in);
        int projectChoice = scanner.nextInt();
        Project selectedProject = projects.get(projectChoice - 1);
        transactionController.displayTransactions("transactions.txt", selectedProject.getProjectName());
    }

    private void selectProject() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter project name to search: ");
        String input = scanner.next();

        List<String> suggestions = dropdownAutoSuggest.getSuggestions(input);
        if (suggestions.isEmpty()) {
            System.out.println("No matching projects found.");
        } else {
            System.out.println("Suggested Projects:");
            suggestions.forEach(System.out::println);
        }
    }
}