// Main.java
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create an instance of FileHandler
        FileHandler fileHandler = new FileHandler();

        // Read transactions from the file in the 'data' directory
        List<Transaction> transactions = fileHandler.readTransactions("transactions.txt");

        // Display all transactions
        System.out.println("Historical Transactions:");
        for (Transaction transaction : transactions) {
            transaction.displayTransactionDetails();
        }
    }
}
