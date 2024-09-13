package view;

import javafx.scene.control.ListView;
import model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionView {
    // Method to format and return transactions for display
    public List<String> formatTransactionDetails(List<Transaction> transactions) {
        List<String> transactionDetails = new ArrayList<>();

        for (Transaction transaction : transactions) {
            String formattedTransaction = "Project: " + transaction.getProjectName() +
                    " | Address: " + transaction.getAddress() +
                    " | Size: " + transaction.getSize() +
                    " | Price: $" + transaction.getPrice();
            transactionDetails.add(formattedTransaction);
        }

        return transactionDetails;
    }

    // Displays transactions in a ListView (used in the main GUI)
    public ListView<String> displayTransactions(List<String> formattedTransactions) {
        ListView<String> transactionList = new ListView<>();
        transactionList.getItems().addAll(formattedTransactions);
        return transactionList;
    }
}