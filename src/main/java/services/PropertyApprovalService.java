package services;

import model.Property;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PropertyApprovalService {
    private static PropertyApprovalService instance;
    private Queue<Property> pendingProperties;
    private final String pendingPropertiesFile = "src/resources/pending_properties.csv"; // Path to pending properties CSV
    private final String approvedPropertiesFile = "src/resources/properties.csv"; // Path to approved properties CSV

    private PropertyApprovalService() {
        pendingProperties = new LinkedList<>();
        loadPendingPropertiesFromFile(); // Load pending properties from file on service initialization
    }

    public static synchronized PropertyApprovalService getInstance() {
        if (instance == null) {
            instance = new PropertyApprovalService();
        }
        return instance;
    }

    // Method to add a property for approval (called when a seller submits a property)
    public void submitForApproval(Property property) {
        pendingProperties.add(property);
        savePendingPropertiesToFile(); // Persist pending properties to file
        System.out.println("Property submitted for approval: " + property.getAddress());
    }

    // Method to approve the next property (removes it from the queue and moves it to approved properties)
    public Property approveNextProperty() {
        Property property = pendingProperties.poll(); // Retrieve and remove the first property in the queue
        if (property != null) {
            property.setApproved(true);  // Set the property as approved
            saveApprovedPropertyToFile(property); // Save approved property to storage
            savePendingPropertiesToFile(); // Update pending properties file after approval
            System.out.println("Property approved: " + property.getAddress());
        }
        return property;
    }

    // Method to reject the next pending property (removes it from the queue)
    public Property rejectNextProperty() {
        Property property = pendingProperties.poll(); // Retrieve and remove the first property in the queue
        if (property != null) {
            savePendingPropertiesToFile(); // Update pending properties file after rejection
            System.out.println("Property rejected: " + property.getAddress());
        }
        return property;
    }

    // Method to view the next pending property without removing it
    public Property getNextPendingProperty() {
        return pendingProperties.peek(); // Only returns the next property without removing it
    }

    // Method to get all pending properties as a list
    public List<Property> getPendingProperties() {
        return new ArrayList<>(pendingProperties); // Return as a list
    }

    // Approve a specific property (used when directly approving a specific property)
    public void approveProperty(Property property) {
        property.setApproved(true);
        pendingProperties.remove(property);
        saveApprovedPropertyToFile(property); // Save approved property to file
        savePendingPropertiesToFile(); // Save updated pending properties
        System.out.println("Property approved: " + property.getAddress());
    }

    // Reject a specific property (used when directly rejecting a specific property)
    public void rejectProperty(Property property) {
        pendingProperties.remove(property);
        savePendingPropertiesToFile(); // Save updated pending properties
        System.out.println("Property rejected: " + property.getAddress());
    }

    // Load pending properties from a file (CSV)
    private void loadPendingPropertiesFromFile() {
        File file = new File(pendingPropertiesFile);
        if (!file.exists()) {
            System.out.println("Pending properties file does not exist.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) { // Ensure all fields are available
                    double size = Double.parseDouble(data[0]);
                    double price = Double.parseDouble(data[1]);
                    String facilities = data[2];
                    String projectName = data[3];
                    String address = data[4];
                    String sellerUsername = data[5];

                    Property property = new Property(size, price, facilities, projectName, address);
                    property.setSellerUsername(sellerUsername);
                    pendingProperties.add(property); // Add to the pending properties queue
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading pending properties file: " + e.getMessage());
        }
    }

    // Save pending properties to a file (CSV)
    private void savePendingPropertiesToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pendingPropertiesFile, false))) {
            for (Property property : pendingProperties) {
                bw.write(property.getSize() + "," + property.getPrice() + "," + property.getFacilities() + "," +
                        property.getProjectName() + "," + property.getAddress() + "," + property.getSellerUsername());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving pending properties to file: " + e.getMessage());
        }
    }

    // Save an approved property to a file (CSV)
    private void saveApprovedPropertyToFile(Property property) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(approvedPropertiesFile, true))) {
            String dateOfValuation = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            double sizeSqM = property.getSize() / 10.7639; // Convert SqFt to SqM
            double pricePerSqFt = property.getPrice() / property.getSize();
            String noOfFloors = "N/A"; // Placeholder for now

            bw.write(String.format("%s,%.2f,%.0f,%s,%s,\"%s\",%s,%.0f,%s,%.2f,%s", 
                    dateOfValuation, sizeSqM, property.getSize(), property.getFacilities(), noOfFloors, 
                    property.getAddress(), property.getProjectName(), property.getPrice(), 
                    Calendar.getInstance().get(Calendar.YEAR), pricePerSqFt, property.getSellerUsername()));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving approved property to file: " + e.getMessage());
        }
    }

    // Check if there are pending properties
    public boolean hasPendingProperties() {
        return !pendingProperties.isEmpty();
    }
}