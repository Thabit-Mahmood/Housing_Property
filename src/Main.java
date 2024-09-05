// Main.java
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a project
        Project projectA = new Project("ProjectA");

        // Create some properties
        Property property1 = new Property("1500 sq ft", 300000, "3 bedrooms, 2 bathrooms", "ProjectA", "123 Street");
        Property property2 = new Property("1600 sq ft", 350000, "4 bedrooms, 3 bathrooms", "ProjectA", "456 Avenue");

        // Add properties to the project
        projectA.addProperty(property1);
        projectA.addProperty(property2);

        // Display all properties in the project
        System.out.println("All properties in " + projectA.getProjectName() + ":");
        projectA.displayProperties();

        // Search for properties based on criteria
        System.out.println("\nSearch Results for properties with size '1500 sq ft' and price between $200,000 and $400,000:");
        List<Property> searchResults = projectA.searchProperties("1500 sq ft", 200000, 400000, null);
        for (Property property : searchResults) {
            property.displayDetails();
        }
    }
}
