package ui;
// TransactionView.java
import Project;
import Transaction;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TransactionView extends VBox {

    public TransactionView(Project project) {
        Label transactionLabel = new Label("Historical Transactions:");
        getChildren().add(transactionLabel);

        // Display historical transactions from the project
        for (Transaction transaction : project.getHistoricalTransactions()) {
            Label transactionDetails = new Label(transaction.getDetails());
            getChildren().add(transactionDetails);
        }
    }
}
