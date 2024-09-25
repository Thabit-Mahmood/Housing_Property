package controller;

import model.Project;
import model.Property;
import model.Seller;
import services.FileHandler;
import services.PropertyApprovalService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyController {
    private List<Property> properties;
    private List<Project> projects;
    private FileHandler fileHandler;
    private PropertyApprovalService approvalService;

    public PropertyController() {
        this.properties = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.fileHandler = FileHandler.getInstance();
        this.approvalService = PropertyApprovalService.getInstance();
        this.properties = fileHandler.loadPropertiesFromCSV(); // Load approved properties from CSV

        System.out.println("Properties loaded: " + properties.size());

        // Log loaded properties for debugging purposes
        System.out.println("Properties loaded: ");
        for (Property property : properties) {
            System.out.println(property.getAddress() + " - " + property.getPrice() + " - " + property.getSize());
        }
    }

    // Method to remove a property and update the CSV file
    public void removeProperty(Property property) {
        // Load all properties from the file
        List<Property> properties = fileHandler.loadPropertiesFromCSV();

        // Remove the selected property from the list
        properties.removeIf(p -> p.getAddress().equals(property.getAddress())
                && p.getProjectName().equals(property.getProjectName())
                && p.getPrice() == property.getPrice());

        // Save the updated list back to the CSV file
        fileHandler.savePropertiesToFile(properties);
    }

    // Method for adding a new property (used by sellers)
    public void addProperty(Property property) {
        Project project = getProjectByName(property.getProjectName());
        if (project != null) {
            project.addProperty(property);
        } else {
            System.out.println("Project not found!");
        }
        saveAllProperties();
    }

    // Associate property with seller after approval
    public void addPropertyToSeller(Seller seller, Property property) {
        seller.addProperty(property); // Add property to seller's list after admin approval
        saveAllProperties(); // Save all approved properties
    }

    // Method for submitting a property for approval by a seller
    public void submitPropertyForApproval(Seller seller, Property property) {
        property.setSellerUsername(seller.getUsername()); // Associate property with the seller

        // Check if the property is already in pending properties
        if (!isPropertyInPendingCSV(property)) {
            approvalService.submitForApproval(property); // Submit property for admin approval
            fileHandler.savePendingPropertyToCSV(property); // Save to pending properties CSV file
            System.out.println("Property submitted for approval: " + property.getAddress());
        } else {
            System.out.println("Property is already pending: " + property.getAddress());
        }
    }

    // Check if property is already in pending_properties.csv
    private boolean isPropertyInPendingCSV(Property property) {
        List<Property> pendingProperties = approvalService.getPendingProperties();
        for (Property pendingProperty : pendingProperties) {
            // Compare all relevant fields
            if (pendingProperty.getAddress().equals(property.getAddress()) &&
                    pendingProperty.getProjectName().equals(property.getProjectName()) &&
                    pendingProperty.getPrice() == property.getPrice() &&
                    pendingProperty.getFacilities().equals(property.getFacilities())) {
                return true; // Duplicate found
            }
        }
        return false; // No duplicates
    }

    // Method to get the list of pending properties
    public List<Property> getPendingProperties() {
        return approvalService.getPendingProperties(); // Get all pending properties
    }

    // Get all properties owned by a specific seller
    public List<Property> getSellerProperties(Seller seller) {
        // Load the approved properties from the CSV file
        this.properties = fileHandler.loadPropertiesFromCSV(); // Load approved properties from CSV
        return properties.stream()
                .filter(property -> property.getSellerUsername().equals(seller.getUsername()))
                .collect(Collectors.toList());
    }

    // Initialize properties from CSV
    @SuppressWarnings("unused")
    private void initializePropertiesFromCSV() {
        this.properties = fileHandler.loadPropertiesFromCSV(); // Load approved properties from CSV
        for (Property property : properties) {
            Project project = getProjectByName(property.getProjectName());
            if (project != null) {
                project.addProperty(property);
            } else {
                // Create a new project if it doesn't exist
                Project newProject = new Project(property.getProjectName());
                newProject.addProperty(property);
                projects.add(newProject);
            }
        }
    }

    // Save all properties to file
    private void saveAllProperties() {
        List<Property> allProperties = new ArrayList<>();
        for (Project project : projects) {
            allProperties.addAll(project.getProperties());
        }
        fileHandler.savePropertiesToFile(allProperties); // Save approved properties
    }

    // Method to filter properties based on criteria, including facilities
    public List<Property> searchPropertiesByCriteria(double minPrice, double maxPrice, double minSize, double maxSize,
            String location, String projectName, String facilities) {
        // Debugging the input criteria
        System.out.println("Search Criteria - Min Price: " + minPrice + ", Max Price: " + maxPrice +
                ", Min Size: " + minSize + ", Max Size: " + maxSize +
                ", Location: " + location + ", Project Name: " + projectName + ", Facilities: " + facilities);

        // Filter properties based on input criteria
        List<Property> filteredProperties = properties.stream()
                .filter(property -> property.getPrice() >= minPrice && property.getPrice() <= maxPrice)
                .filter(property -> property.getSize() >= minSize && property.getSize() <= maxSize)
                .filter(property -> location.isEmpty()
                        || property.getAddress().toLowerCase().contains(location.toLowerCase()))
                .filter(property -> projectName == null || projectName.isEmpty()
                        || property.getProjectName().equalsIgnoreCase(projectName))
                .filter(property -> facilities.isEmpty()
                        || property.getFacilities().toLowerCase().contains(facilities.toLowerCase())) // Facilities
                                                                                                      // filtering
                .collect(Collectors.toList());

        System.out.println("Filtered Properties: " + filteredProperties.size());
        for (Property property : filteredProperties) {
            System.out.println(property.getAddress() + " - " + property.getPrice() + " - " + property.getSize());
        }

        return filteredProperties;
    }

    // Method to get a project by name
    public Project getProjectByName(String name) {
        return projects.stream()
                .filter(project -> project.getProjectName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    // Method to get all project names for the dropdown/auto-suggestion
    public List<String> getAllProjectNames() {
        return properties.stream()
                .map(Property::getProjectName)
                .distinct()
                .collect(Collectors.toList());
    }

    // Modify search to include project name as criteria
    public List<Property> searchPropertiesByCriteria(double minPrice, double maxPrice, double minSize, double maxSize,
            String location, String projectName) {
        return properties.stream()
                .filter(property -> property.getPrice() >= minPrice && property.getPrice() <= maxPrice)
                .filter(property -> property.getSize() >= minSize && property.getSize() <= maxSize)
                .filter(property -> location.isEmpty() || property.getAddress().contains(location))
                .filter(property -> projectName == null || property.getProjectName().equalsIgnoreCase(projectName))
                .collect(Collectors.toList());
    }
}