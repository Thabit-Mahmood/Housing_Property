package services;

import model.Property;

import java.util.LinkedList;
import java.util.Queue;

public class PropertyApprovalService {
    private static PropertyApprovalService instance;
    private Queue<Property> pendingProperties;

    private PropertyApprovalService() {
        pendingProperties = new LinkedList<>();
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
        System.out.println("Property submitted for approval: " + property.getAddress());
    }

    // Approves a property
    public void approveProperty(Property property) {
        property.setApproved(true);
        pendingProperties.remove(property);
        FileHandler.getInstance().savePropertiesToFile(FileHandler.getInstance().loadPropertiesFromCSV());  // Save approved properties
        System.out.println("Property approved: " + property.getAddress());
    }

    // Rejects a property
    public void rejectProperty(Property property) {
        pendingProperties.remove(property);
        System.out.println("Property rejected: " + property.getAddress());
    }

    // Get the next pending property for approval
    public Property getPendingProperty() {
        return pendingProperties.peek();
    }

    // Check if there are pending properties
    public boolean hasPendingProperties() {
        return !pendingProperties.isEmpty();
    }
}