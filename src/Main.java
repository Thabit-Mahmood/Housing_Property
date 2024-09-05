// Main.java
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create instances of FileHandler and Project
        FileHandler fileHandler = new FileHandler();
        Project project = new Project("ProjectA");

        // Add properties to the project
        Property property1 = new Property("1500 sq ft", 300000, "3 bedrooms, pool", "ProjectA", "123 Street");
        Property property2 = new Property("1600 sq ft", 320000, "4 bedrooms, gym", "ProjectA", "456 Avenue");
        Property property3 = new Property("1200 sq ft", 250000, "2 bedrooms, garden", "ProjectA", "789 Boulevard");

        project.addProperty(property1);
        project.addProperty(property2);
        project.addProperty(property3);

        // Display all properties
        System.out.println("All Properties:");
        project.displayProperties();

        // Search for properties with size "1500 sq ft" and price range 250000 to 350000
        List<Property> searchResults = project.searchProperties("1500 sq ft", 250000, 350000, null);
        System.out.println("\nSearch Results:");
        for (Property p : searchResults) {
            p.displayDetails();
        }

        // Load historical transactions from a file
        project.loadHistoricalTransactions("transactions.txt");

        // Display historical transactions (last 5 transactions)
        System.out.println("\nHistorical Transactions:");
        List<Transaction> transactions = project.getHistoricalTransactions();
        for (Transaction transaction : transactions) {
            transaction.displayTransactionDetails();
        }
    }
}
