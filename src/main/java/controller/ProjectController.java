package main.java.controller;

import main.java.model.Project;
import java.util.List;

public class ProjectController {
    private List<Project> projects;

    public ProjectController(List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getAllProjects() {
        return projects;
    }

    public Project getProjectByName(String name) {
        return projects.stream()
                        .filter(project -> project.getProjectName().equalsIgnoreCase(name))
                        .findFirst()
                        .orElse(null);
    }
}