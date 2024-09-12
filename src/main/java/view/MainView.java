package main.java.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.java.controller.PropertyController;
import main.java.controller.TransactionController;
import main.java.model.Project;

public class MainView {
    private List<Project> projects;
    private PropertyController propertyController;
    private TransactionController transactionController;

    public MainView(PropertyController propertyController, TransactionController transactionController) {
        projects = new ArrayList<>();
        this.propertyController = propertyController;
        this.transactionController = transactionController;
        initializeData();
        displayMenu();
    }

    private void initializeData() {
        // Initialize sample data
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Property Search System.");
        System.out.println("1. Search Properties");
        System.out.println("2. View Transactions");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                searchProperties();
                break;
            case 2:
                viewTransactions();
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
}