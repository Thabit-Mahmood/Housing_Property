package view;

import java.util.List;
import model.Transaction;

public class TransactionView {
    public void displayTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            System.out.println("Last 5 Transactions:");
            for (Transaction transaction : transactions) {
                transaction.displayTransactionDetails();
                System.out.println("-----");
            }
        }
    }
}