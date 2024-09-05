package Modules;
// Transaction.java
public class Transaction {
  private String projectName;
  private String address;
  private String size;
  private double price;

  // Constructor
  public Transaction(String projectName, String address, String size, double price) {
      this.projectName = projectName;
      this.address = address;
      this.size = size;
      this.price = price;
  }

  // Getters
  public String getProjectName() {
      return projectName;
  }

  public String getAddress() {
      return address;
  }

  public String getSize() {
      return size;
  }

  public double getPrice() {
      return price;
  }

  // Setters
  public void setProjectName(String projectName) {
      this.projectName = projectName;
  }

  public void setAddress(String address) {
      this.address = address;
  }

  public void setSize(String size) {
      this.size = size;
  }

  public void setPrice(double price) {
      this.price = price;
  }

  // Method to display transaction details
  public void displayTransactionDetails() {
      System.out.println("Transaction Details:");
      System.out.println("Project Name: " + projectName);
      System.out.println("Address: " + address);
      System.out.println("Size: " + size);
      System.out.println("Price: $" + price);
      System.out.println("----------------------------");
  }

  public String getDetails() {
    return String.format("Project: %s, Address: %s, Size: %s, Price: %.2f", projectName, address, size, price);
  }
}