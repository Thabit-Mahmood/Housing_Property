package model;

public class Property extends PropertyBase {
    private boolean isApproved; 
    private String sellerUsername;  // Field to associate the property with the seller
    
    // Constructor
    public Property(double size, double price, String facilities, String projectName, String address) {
        this.size = size;
        this.price = price;
        this.facilities = facilities;
        this.projectName = projectName;
        this.address = address;
        this.isApproved = false;
        this.sellerUsername = "";  // Default empty seller username
    }

    // Set the seller's username for this property
    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    // Get the seller's username
    public String getSellerUsername() {
        return sellerUsername;
    }

    // Set the approval status
    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    // Check if the property is approved
    public boolean isApproved() {
        return isApproved;
    }

    // Display property details
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