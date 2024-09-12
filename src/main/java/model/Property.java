package main.java.model;

public class Property {
    private double size;
    private double price;
    private String facilities;
    private String projectName;
    private String address;

    public Property(double size, double price, String facilities, String projectName, String address) {
        this.size = size;
        this.price = price;
        this.facilities = facilities;
        this.projectName = projectName;
        this.address = address;
    }

    public double getSize() {
        return size;
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