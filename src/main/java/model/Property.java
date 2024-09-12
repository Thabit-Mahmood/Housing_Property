package main.java.model;

public class Property extends PropertyBase {
  public Property(String size, double price, String facilities, String projectName, String address) {
      this.size = size;
      this.price = price;
      this.facilities = facilities;
      this.projectName = projectName;
      this.address = address;
  }

  @Override
  public void displayPropertyDetails() {
      System.out.println("Project Name: " + projectName);
      System.out.println("Address: " + address);
      System.out.println("Size: " + size);
      System.out.println("Price: $" + price);
      System.out.println("Facilities: " + facilities);
  }
}