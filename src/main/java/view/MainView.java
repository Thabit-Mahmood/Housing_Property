package main.java.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.java.controller.TransactionController;
import main.java.model.Project;

public class MainView {
    private List<Project> projects;
    private TransactionController transactionController;

    public MainView(TransactionController transactionController) {
        this.projects = new ArrayList<>();
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
                // Search Properties functionality (to be implemented)
                break;
            case 2:
                System.out.println("Select a project:");
                for (int i = 0; i < projects.size(); i++) {
                    System.out.println((i + 1) + ". " + projects.get(i).getProjectName());
                }
                int projectChoice = scanner.nextInt();
                Project selectedProject = projects.get(projectChoice - 1);
                transactionController.displayTransactions("transactions.txt", selectedProject.getProjectName());
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}