package model;

public class Customer extends User {

    public Customer(String username, String password, String email) {
        super(username, password, email, "Customer");
    }

    @Override
    public void displayUserDashboard() {
        System.out.println("Welcome to the Customer Dashboard!");
        // Add additional functionality as needed
    }

    // Additional customer-specific methods can be added here
}