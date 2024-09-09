public abstract class PropertyBase {
  protected String size;
  protected double price;
  protected String facilities;
  protected String projectName;
  protected String address;

  // Abstract method
  public abstract void displayPropertyDetails();

  // Getters and Setters
  public String getSize() { return size; }
  public void setSize(String size) { this.size = size; }

  public double getPrice() { return price; }
  public void setPrice(double price) { this.price = price; }

  public String getFacilities() { return facilities; }
  public void setFacilities(String facilities) { this.facilities = facilities; }

  public String getProjectName() { return projectName; }
  public void setProjectName(String projectName) { this.projectName = projectName; }

  public String getAddress() { return address; }
  public void setAddress(String address) { this.address = address; }
}
