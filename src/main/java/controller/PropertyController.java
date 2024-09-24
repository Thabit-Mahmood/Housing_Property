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
        initializePropertiesFromCSV();  // Now using CSV method
        this.approvalService = PropertyApprovalService.getInstance();

        // Log loaded properties
        System.out.println("Properties loaded: ");
        for (Property property : properties) {
            System.out.println(property.getAddress() + " - " + property.getPrice() + " - " + property.getSize());
        }
    }

    public void addPropertyToSeller(Seller seller, Property property) {
        seller.addProperty(property);
    }

    public void submitPropertyForApproval(Seller seller, Property property) {
        seller.submitPropertyForApproval(property, approvalService);
    }

    public List<Property> getSellerProperties(Seller seller) {
        return seller.getOwnedProperties();
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

    // Method to load properties from the CSV file and initialize them into projects
    private void initializePropertiesFromCSV() {
        this.properties = fileHandler.loadPropertiesFromCSV();  // Fetch properties from CSV
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
        fileHandler.savePropertiesToFile(allProperties);
    }

    // Method to filter properties based on criteria, now including facilities
    public List<Property> searchPropertiesByCriteria(double minPrice, double maxPrice, double minSize, double maxSize,
            String location, String projectName, String facilities) {
        return properties.stream()
                .filter(property -> property.getPrice() >= minPrice && property.getPrice() <= maxPrice)
                .filter(property -> property.getSize() >= minSize && property.getSize() <= maxSize)
                .filter(property -> location.isEmpty() || property.getAddress().contains(location))
                .filter(property -> projectName == null || property.getProjectName().equalsIgnoreCase(projectName))
                .filter(property -> facilities.isEmpty() || property.getFacilities().toLowerCase().contains(facilities.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Method to search properties by project name
    public List<Property> searchProperties(String projectName) {
        List<Property> results = new ArrayList<>();
        Project project = getProjectByName(projectName);
        if (project != null) {
            results.addAll(project.getProperties());
        }
        return results;
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
    public List<Property> searchPropertiesByCriteria(double minPrice, double maxPrice, double minSize, double maxSize, String location, String projectName) {
        return properties.stream()
                .filter(property -> property.getPrice() >= minPrice && property.getPrice() <= maxPrice)
                .filter(property -> property.getSize() >= minSize && property.getSize() <= maxSize)
                .filter(property -> location.isEmpty() || property.getAddress().contains(location))
                .filter(property -> projectName == null || property.getProjectName().equalsIgnoreCase(projectName))
                .collect(Collectors.toList());
    }
}
