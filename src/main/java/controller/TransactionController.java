package controller;

import model.Transaction;
import services.FileHandler;
import view.TransactionView;
import model.Property;

import java.util.List;
import java.util.stream.Collectors;

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
                .filter(transaction -> transaction.getProjectName().trim().equalsIgnoreCase(projectName.trim()))
                .collect(Collectors.toList());

        // Debugging: Check how many transactions were loaded and matched
        System.out.println("Loaded " + allTransactions.size() + " transactions from CSV.");
        System.out.println("Found " + projectTransactions.size() + " transactions for project: " + projectName);

        return projectTransactions.size() > 0 ? projectTransactions : null; // return null if no transaction found
    }

    // Fetch the last N transactions for a specific project (new feature)
    public List<Transaction> fetchRecentTransactions(String projectName, int limit) {
        List<Transaction> allTransactions = fileHandler.loadTransactionsFromCSV();

        // Debugging: Check if transactions are being loaded
        System.out.println("Loaded " + allTransactions.size() + " transactions from CSV.");

        List<Transaction> projectTransactions = allTransactions.stream()
                .filter(transaction -> transaction.getProjectName().trim().equalsIgnoreCase(projectName.trim()))
                .collect(Collectors.toList());

        // Debugging: Check if the transactions are filtered properly
        System.out.println("Found " + projectTransactions.size() + " transactions for project: " + projectName);

        // Return the last 'limit' number of transactions
        int transactionCount = projectTransactions.size();
        return projectTransactions.subList(Math.max(transactionCount - limit, 0), transactionCount);
    }

    // Add a new transaction when a customer buys a property
    public void addTransaction(Property property) {
        Transaction transaction = new Transaction(
                property.getProjectName(),
                "\"" + property.getAddress() + "\"",  // Add quotes around address
                String.valueOf(property.getSize()),    // Convert size to string
                property.getPrice()
        );

        fileHandler.addTransactionToCSV(transaction);  // Add the new transaction to the file
    }

}