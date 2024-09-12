package main.java.controller;

import java.util.List;
import main.java.model.Transaction;
import main.java.services.FileHandler;
import main.java.view.TransactionView;

public class TransactionController {
    private FileHandler fileHandler;
    private TransactionView transactionView;

    public TransactionController(FileHandler fileHandler, TransactionView transactionView) {
        this.fileHandler = fileHandler;
        this.transactionView = transactionView;
    }

    public void displayTransactions(String filename, String projectName) {
        List<Transaction> transactions = fileHandler.readTransactions(filename);
        List<Transaction> recentTransactions = fileHandler.getRecentTransactions(transactions, projectName);
        transactionView.displayTransactions(recentTransactions);
    }
}