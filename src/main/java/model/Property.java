package model;

public class Property extends PropertyBase {
    private boolean isApproved; 
    private String sellerUsername;  // New field to associate the property with the seller
    
    public Property(double size, double price, String facilities, String projectName, String address) {
        this.size = size;
        this.price = price;
        this.facilities = facilities;
        this.projectName = projectName;
        this.address = address;
        this.isApproved = false;
        this.sellerUsername = "";  // Default empty seller username
    }   

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public String getSellerUsername() {
        return sellerUsername;  // Getter for the seller username
    }

    @Override
    public void displayPropertyDetails() {
        System.out.println("Project: " + projectName);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: $" + price);
        System.out.println("Facilities: " + facilities);
        System.out.println("Address: " + address);
        System.out.println("Seller: " + sellerUsername);  // Display seller username
        System.out.println("--------------------------------------");
    }
}
