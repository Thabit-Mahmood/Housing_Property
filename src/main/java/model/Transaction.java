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
    public String getProjectName() { return projectName; }
    public String getAddress() { return address; }
    public String getSize() { return size; }
    public double getPrice() { return price; }

    public void displayTransactionDetails() {
        System.out.println("Project: " + projectName);
        System.out.println("Address: " + address);
        System.out.println("Size: " + size);
        System.out.println("Price: $" + price);
    }
}
