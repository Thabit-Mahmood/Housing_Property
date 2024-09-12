package controller;

import java.util.List;
import services.FileHandler;
import view.TransactionView;
import model.Transaction;

public class TransactionController {
    private TransactionView transactionView;

    public TransactionController(TransactionView transactionView) {
        this.transactionView = transactionView;
    }

    public void fetchTransactions(String filePath, String projectName) {
        List<Transaction> transactions = FileHandler.getInstance().getRecentTransactions(filePath, projectName);
        transactionView.displayTransactions(transactions);
    }
}