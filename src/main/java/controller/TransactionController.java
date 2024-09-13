package controller;

import model.Transaction;
import services.FileHandler;
import view.TransactionView;

import java.util.List;

public class TransactionController {
    private TransactionView transactionView;
    private FileHandler fileHandler;

    public TransactionController(TransactionView transactionView) {
        this.transactionView = transactionView;
        this.fileHandler = new FileHandler();
    }

    public List<String> fetchTransactions(String filePath, String projectName) {
        List<Transaction> transactions = fileHandler.getRecentTransactions(filePath, projectName);
        return transactionView.displayTransactions(transactions);
    }
}
