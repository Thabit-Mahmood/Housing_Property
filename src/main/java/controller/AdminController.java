package controller;

import model.Admin;
import model.Property;
import services.ProjectService;
import services.PropertyApprovalService;

import java.util.List;

public class AdminController {

    private PropertyApprovalService propertyApprovalService;
    private ProjectService projectService;

    // Constructor ensures both services are initialized
    public AdminController(Admin admin) {
        this.projectService = ProjectService.getInstance();
        this.propertyApprovalService = PropertyApprovalService.getInstance(); // Initialize PropertyApprovalService
    }

    // Approves the next property in the queue
    public Property approveNextProperty() {
        Property nextProperty = propertyApprovalService.getNextPendingProperty();
        if (nextProperty != null) {
            propertyApprovalService.approveProperty(nextProperty); // Approve and remove the next property
            return nextProperty;
        }
        return null;
    }

    // Rejects the next property in the queue
    public Property rejectNextProperty() {
        Property nextProperty = propertyApprovalService.getNextPendingProperty();
        if (nextProperty != null) {
            propertyApprovalService.rejectProperty(nextProperty); // Reject and remove the next property
            return nextProperty;
        }
        return null;
    }

    // Approves a specific property submitted by a seller
    public void approveProperty(Property property) {
        projectService.approveProperty(property); // Directly approve the property in the project
    }

    // Rejects a specific property submitted by a seller
    public void rejectProperty(Property property) {
        projectService.rejectProperty(property); // Directly reject the property in the project
    }

    // Creates a new project
    public void createProject(String projectName) {
        projectService.createProject(projectName); // Create a new project
    }

    // Deletes a project
    public void deleteProject(String projectName) {
        projectService.deleteProject(projectName); // Delete a specific project
    }

    // List all projects
    public List<String> listProjects() {
        return projectService.getAllProjectNames(); // Return the list of all project names
    }

    // Get the next pending property for approval without removing it
    public Property getPendingProperty() {
        return propertyApprovalService.getNextPendingProperty(); // View the next pending property
    }

    // Check if there are any pending properties for approval
    public boolean hasPendingProperties() {
        return propertyApprovalService.hasPendingProperties(); // Check if there are pending properties
    }
}