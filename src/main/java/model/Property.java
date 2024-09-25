package model;

public class Property extends PropertyBase {
    private boolean isApproved; 
    private String sellerUsername;  // Field to associate the property with the seller

    // Add new fields for the missing attributes
    private String dateOfValuation;
    private String noOfFloors;
    private String year;
    private String pricePerSqFt;
    
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

    // Getter for project name
    public String getProjectName() {
        return projectName;
    }

    // Getter for address
    public String getAddress() {
        return address;
    }

    // Getter for size
    public double getSize() {
        return size;
    }

    // Getter for price
    public double getPrice() {
        return price;
    }

    // Add new getters and setters for DateOfValuation, NoOfFloors, Year, PricePerSqFt
    public String getDateOfValuation() {
        return dateOfValuation;
    }

    public void setDateOfValuation(String dateOfValuation) {
        this.dateOfValuation = dateOfValuation;
    }

    public String getNoOfFloors() {
        return noOfFloors;
    }

    public void setNoOfFloors(String noOfFloors) {
        this.noOfFloors = noOfFloors;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPricePerSqFt() {
        return pricePerSqFt;
    }

    public void setPricePerSqFt(String pricePerSqFt) {
        this.pricePerSqFt = pricePerSqFt;
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