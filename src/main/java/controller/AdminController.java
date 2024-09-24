package controller;

import model.Admin;
import model.Property;
import services.ProjectService;
import java.util.List;

public class AdminController {

    private ProjectService projectService;

    public AdminController(Admin admin) {
        this.projectService = ProjectService.getInstance();
    }

    // Approves a property submitted by a seller
    public void approveProperty(Property property) {
        projectService.approveProperty(property);
    }

    // Rejects a property submitted by a seller
    public void rejectProperty(Property property) {
        projectService.rejectProperty(property);
    }

    // Creates a new project
    public void createProject(String projectName) {
        projectService.createProject(projectName);
    }

    // Deletes a project
    public void deleteProject(String projectName) {
        projectService.deleteProject(projectName);
    }

    // List all projects
    public List<String> listProjects() {
        return projectService.getAllProjectNames();
    }

    // Get the next pending property for approval
    public Property getPendingProperty() {
        return projectService.getNextPendingProperty();
    }
}