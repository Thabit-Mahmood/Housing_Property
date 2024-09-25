package model;

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

    @Override
    public String toString() {
        return projectName + ",\"" + address + "\"," + size + "," + price;
    }
}