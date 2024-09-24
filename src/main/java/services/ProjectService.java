package services;

import model.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectService {
    private static ProjectService instance;
    private List<Project> projects;  // Store the list of projects

    private ProjectService() {
        this.projects = new ArrayList<>();
    }

    public static synchronized ProjectService getInstance() {
        if (instance == null) {
            instance = new ProjectService();
        }
        return instance;
    }

    // Add a new project
    public void addProject(Project project) {
        projects.add(project);
        System.out.println("Project added: " + project.getProjectName());
    }

    // Remove a project by name
    public void removeProject(String projectName) {
        projects.removeIf(project -> project.getProjectName().equalsIgnoreCase(projectName));
        System.out.println("Project removed: " + projectName);
    }

    // Get all projects
    public List<Project> getAllProjects() {
        return projects;
    }

    // Get all project names
    public List<String> getAllProjectNames() {
        List<String> projectNames = new ArrayList<>();
        for (Project project : projects) {
            projectNames.add(project.getProjectName());
        }
        return projectNames;
    }
}