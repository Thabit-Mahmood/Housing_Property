package view;

import javafx.scene.control.ListView;
import model.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionView {
    public List<String> displayTransactions(List<Transaction> transactions) {
        return transactions.stream()
            .map(transaction -> "Project: " + transaction.getProjectName() +
                    " | Address: " + transaction.getAddress() +
                    " | Size: " + transaction.getSize() +
                    " | Price: $" + transaction.getPrice())
            .collect(Collectors.toList());
    }
}
