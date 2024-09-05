// Main.java
public class Main {
  public static void main(String[] args) {
      // Create a sample property
      Property property1 = new Property("1500 sq ft", 300000, "3 bedrooms, 2 bathrooms", "ProjectA", "123 Street");

      // Display property details
      property1.displayDetails();

      // Modify some attributes and display the details again
      property1.setPrice(320000);  // Update the price
      property1.setFacilities("4 bedrooms, 2 bathrooms");  // Update the facilities

      System.out.println("Updated Property Details:");
      property1.displayDetails();
  }
}