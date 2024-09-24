package model;

public class Property extends PropertyBase {
    private boolean isApproved; 
    
    public Property(double size, double price, String facilities, String projectName, String address) {
        this.size = size;
        this.price = price;
        this.facilities = facilities;
        this.projectName = projectName;
        this.address = address;
        this.isApproved = false;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public boolean isApproved() {
        return isApproved;
    }

    @Override
    public void displayPropertyDetails() {
        System.out.println("Project: " + projectName);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: $" + price);
        System.out.println("Facilities: " + facilities);
        System.out.println("Address: " + address);
        System.out.println("--------------------------------------");
    }
}
