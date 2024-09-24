package controller;

import model.Transaction;
import services.FileHandler;
import view.TransactionView;

import java.util.List;

public class TransactionController {

    @SuppressWarnings("unused")
    private TransactionView transactionView;
    private FileHandler fileHandler; // Singleton FileHandler

    // Updated constructor to accept TransactionView
    public TransactionController(TransactionView transactionView) {
        this.transactionView = transactionView;
        this.fileHandler = FileHandler.getInstance(); // Initialize the FileHandler
    }

    // Fetch transactions for a specific project from the file
    public List<Transaction> fetchTransactions(String projectName) {
        List<Transaction> allTransactions = fileHandler.loadTransactionsFromCSV();
        List<Transaction> projectTransactions = allTransactions.stream()
                // Normalize both the input and project name from the file for case-insensitive comparison
                .filter(transaction -> transaction.getProjectName().trim().equalsIgnoreCase(projectName.trim()))
                .toList();

        // Debugging: Check how many transactions were loaded and matched
        System.out.println("Loaded " + allTransactions.size() + " transactions from CSV.");
        System.out.println("Found " + projectTransactions.size() + " transactions for project: " + projectName);

        return projectTransactions.size() > 5 ? projectTransactions.subList(0, 5) : projectTransactions;
    }

}