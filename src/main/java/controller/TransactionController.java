package controller;

import model.Transaction;
import services.FileHandler;
import view.TransactionView;

import java.util.List;

public class TransactionController {
    private TransactionView transactionView;
    private FileHandler fileHandler;  // Singleton FileHandler

    public TransactionController(TransactionView transactionView) {
        this.transactionView = transactionView;
        this.fileHandler = FileHandler.getInstance();  // Initialize the FileHandler
    }

    // Fetch transactions for a specific project from the file
    public List<String> fetchTransactions(String filePath, String projectName) {
        List<Transaction> transactions = fileHandler.getRecentTransactions(filePath, projectName);

        // If transactions are found, return formatted strings for the view
        if (transactions.isEmpty()) {
            return List.of("No transactions found for " + projectName);
        }

        return transactionView.formatTransactionDetails(transactions);  // Return formatted transactions
    }
}