

public class Transaction {
  private String projectName;
  private String address;
  private String size;
  private double price;

  // Getters and Setters
  public String getProjectName() { return projectName; }
  public void setProjectName(String projectName) { this.projectName = projectName; }

  public String getAddress() { return address; }
  public void setAddress(String address) { this.address = address; }

  public String getSize() { return size; }
  public void setSize(String size) { this.size = size; }

  public double getPrice() { return price; }
  public void setPrice(double price) { this.price = price; }
}
