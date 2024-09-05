// Project.java
import java.util.ArrayList;
import java.util.List;

public class Project {
    private String projectName;
    private List<Property> properties;

    // Constructor
    public Project(String projectName) {
        this.projectName = projectName;
        this.properties = new ArrayList<>();
    }

    // Getters
    public String getProjectName() {
        return projectName;
    }

    // Method to add a property to the project
    public void addProperty(Property property) {
        properties.add(property);
    }

    // Method to search for properties based on size, price range, and facilities
    public List<Property> searchProperties(String size, double minPrice, double maxPrice, String facilities) {
        List<Property> filteredProperties = new ArrayList<>();
        for (Property property : properties) {
            if ((size == null || property.getSize().equals(size)) &&
                (property.getPrice() >= minPrice && property.getPrice() <= maxPrice) &&
                (facilities == null || property.getFacilities().equalsIgnoreCase(facilities))) {
                filteredProperties.add(property);
            }
        }
        return filteredProperties;
    }

    // Method to display all properties in the project
    public void displayProperties() {
        for (Property property : properties) {
            property.displayDetails();
        }
    }
    
    // Method to get historical transactions (placeholder, will be updated later)
    public List<Transaction> getHistoricalTransactions() {
        // This will retrieve transactions in the future
        return new ArrayList<>();
    }
}
