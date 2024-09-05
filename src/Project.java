import java.util.ArrayList;
import java.util.List;

public class Project {
    private String projectName;
    private List<Property> properties;
    private List<Transaction> transactions;

    // Constructor
    public Project(String projectName) {
        this.projectName = projectName;
        this.properties = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    // Get project name
    public String getProjectName() {
        return projectName;
    }

    // Add property to the project
    public void addProperty(Property property) {
        properties.add(property);
    }

    // Get properties based on search criteria
    public List<Property> searchProperties(String size, double minPrice, double maxPrice, String facilities) {
        List<Property> results = new ArrayList<>();
        for (Property property : properties) {
            boolean matches = true;
            if (size != null && !property.getSize().equals(size)) {
                matches = false;
            }
            if (property.getPrice() < minPrice || property.getPrice() > maxPrice) {
                matches = false;
            }
            if (facilities != null && !property.getFacilities().contains(facilities)) {
                matches = false;
            }
            if (matches) {
                results.add(property);
            }
        }
        return results;
    }

    // Display all properties in the project
    public void displayProperties() {
        for (Property property : properties) {
            property.displayDetails();
        }
    }

    // Fetch historical transactions for the project
    public void loadHistoricalTransactions(String filename) {
        FileHandler fileHandler = new FileHandler();
        transactions = fileHandler.readTransactions(filename);
    }

    // Get historical transactions (last 5 transactions)
    public List<Transaction> getHistoricalTransactions() {
        int size = transactions.size();
        List<Transaction> recentTransactions = new ArrayList<>();
        for (int i = Math.max(0, size - 5); i < size; i++) {
            recentTransactions.add(transactions.get(i));
        }
        return recentTransactions;
    }
}
