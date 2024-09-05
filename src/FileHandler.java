// FileHandler.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    // Method to read transactions from a file and return a list of Transaction objects
    public List<Transaction> readTransactions(String filename) {
        List<Transaction> transactions = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader("../data/" + filename))) {
            while ((line = br.readLine()) != null) {
                // Assuming the file format is: ProjectName, Address, Size, Price
                String[] transactionData = line.split(", ");
                if (transactionData.length == 4) {
                    String projectName = transactionData[0];
                    String address = transactionData[1];
                    String size = transactionData[2];
                    double price = Double.parseDouble(transactionData[3]);

                    Transaction transaction = new Transaction(projectName, address, size, price);
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return transactions;
    }
}