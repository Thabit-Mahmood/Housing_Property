package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import controller.PropertyController;
import controller.TransactionController;
import model.Project;
import model.Property;
import util.DropdownAutoSuggest;

public class MainView {
    private List<Project> projects;
    private PropertyController propertyController;
    private TransactionController transactionController;
    private DropdownAutoSuggest dropdownAutoSuggest;
    private Scanner scanner;

    public MainView(PropertyController propertyController, TransactionController transactionController) {
        projects = new ArrayList<>();
        this.propertyController = propertyController;
        this.transactionController = transactionController;
        this.scanner = new Scanner(System.in);
        initializeData();
        initializeAutoSuggest();
        displayMenu();
    }

    private void viewProjects() {
        ProjectView projectView = new ProjectView();
        for (Project project : projects) {
            projectView.displayProjectDetails(project);
        }
    }

    private void initializeData() {
        Project project1 = new Project("Project Alpha");
        Project project2 = new Project("Project Beta");

        Property property1 = new Property(1200, 250000, "Pool, Gym", "Project Alpha", "123 Alpha St");
        Property property2 = new Property(800, 150000, "Gym", "Project Alpha", "124 Alpha St");
        Property property3 = new Property(1000, 200000, "Pool", "Project Beta", "200 Beta St");
        Property property4 = new Property(1500, 300000, "Pool, Gym, Park", "Project Beta", "201 Beta St");

        project1.addProperty(property1);
        project1.addProperty(property2);
        project2.addProperty(property3);
        project2.addProperty(property4);

        projects.add(project1);
        projects.add(project2);
    }

    private void initializeAutoSuggest() {
        List<String> projectNames = new ArrayList<>();
        for (Project project : projects) {
            projectNames.add(project.getProjectName());
        }
        dropdownAutoSuggest = new DropdownAutoSuggest(projectNames);
    }

    private void viewPropertyDetails() {
        PropertyView propertyView = new PropertyView();
        for (Project project : projects) {
            for (Property property : project.getProperties()) {
                propertyView.displayPropertyDetail(property);
            }
        }
    }

    public void displayMenu() {
        boolean running = true;

        while (running) {
            System.out.println("Welcome to the Property Search System.");
            System.out.println("1. View Projects");
            System.out.println("2. View Property Details");
            System.out.println("3. View Transactions");
            System.out.println("4. Search Properties");
            System.out.println("5. Select Project");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        viewProjects();
                        break;
                    case 2:
                        viewPropertyDetails();
                        break;
                    case 3:
                        viewTransactions();
                        break;
                    case 4:
                        searchProperties();
                        break;
                    case 5:
                        selectProject();
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
            }
        }
        scanner.close();
    }

    private void searchProperties() {
        try (Scanner scanner = new Scanner(System.in)) {
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
    }

    // Inside MainView.java, `viewTransactions` method
    private void viewTransactions() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter project name: ");
            String projectName = scanner.nextLine();
            transactionController.fetchTransactions("src/resources/transactions.txt", projectName);
        }
    }

    private void selectProject() {
        try (Scanner scanner = new Scanner(System.in)) {
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

    public static void main(String[] args) {
        // Initialize sample data
        List<Project> projects = new ArrayList<>();
        PropertyView propertyView = new PropertyView();
        PropertyController propertyController = new PropertyController(projects, propertyView);
        TransactionView transactionView = new TransactionView();
        TransactionController transactionController = new TransactionController(transactionView);

        // Create an instance of MainView
        new MainView(propertyController, transactionController);
    }
}