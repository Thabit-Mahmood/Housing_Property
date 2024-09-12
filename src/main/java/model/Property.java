package model;

public class Property {
    private String size; // Size should be a String
    private double price;
    private String facilities;
    private String projectName;
    private String address;

    public Property(String size, double price, String facilities, String projectName, String address) {
        this.size = size; // Store size as String
        this.price = price;
        this.facilities = facilities;
        this.projectName = projectName;
        this.address = address;
    }

    public String getSize() {
        return size; // Return size as String
    }

    public double getPrice() {
        return price;
    }

    public String getFacilities() {
        return facilities;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getAddress() {
        return address;
    }

    public void displayPropertyDetails() {
        System.out.println("Project: " + projectName);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: $" + price);
        System.out.println("Facilities: " + facilities);
        System.out.println("Address: " + address);
    }
}
