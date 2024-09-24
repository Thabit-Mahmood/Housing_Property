package model;

public class Admin extends User {

    public Admin(String username, String password, String email) {
        super(username, password, email, "Admin");
    }

    @Override
    public void displayUserDashboard() {
        System.out.println("Welcome to the Admin Dashboard!");
        // Admin functionalities to be added later
    }

    // Additional admin-specific methods for managing users, properties, and transactions
}
