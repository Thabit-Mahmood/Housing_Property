package controller;

import model.Admin;
import model.Project;
import services.ProjectService;

public class AdminController {
    private ProjectService projectService;  // Add a project service to handle projects

    public AdminController(Admin admin) {
        this.projectService = ProjectService.getInstance();  // Initialize the project service
    }

    // Method to create a new project
    public void createProject(String projectName) {
        projectService.addProject(new Project(projectName));  // Admin creates a new project
        System.out.println("Project created: " + projectName);
    }

    // Method to delete a project
    public void deleteProject(String projectName) {
        projectService.removeProject(projectName);  // Admin deletes a project
        System.out.println("Project deleted: " + projectName);
    }

    // Method to list all projects
    public void listProjects() {
        System.out.println("All Projects:");
        projectService.getAllProjects().forEach(project -> 
            System.out.println("- " + project.getProjectName()));
    }
}
