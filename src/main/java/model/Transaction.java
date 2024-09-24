package model;

public class Transaction {
    private String projectName;
    private String address;
    private String size;
    private double price;

    public Transaction(String projectName, String address, String size, double price) {
        this.projectName = projectName;
        this.address = address;
        this.size = size;
        this.price = price;
    }

    // Getters and setters
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

    // Properly formatted toString() method
    @Override
    public String toString() {
        return "Transaction [Project: " + projectName +
               ", Address: " + address +
               ", Size: " + size + " sq ft" +
               ", Price: $" + String.format("%,.2f", price) + "]";
    }
}