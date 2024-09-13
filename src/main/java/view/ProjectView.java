package view;

import javafx.scene.control.ListView;
import model.Project;
import model.Property;

public class ProjectView {
    public ListView<String> displayProjectDetails(Project project) {
        ListView<String> projectDetailsList = new ListView<>();
        projectDetailsList.getItems().add("Project: " + project.getProjectName());

        for (Property property : project.getProperties()) {
            projectDetailsList.getItems().add("Property: " + property.getAddress() +
                    " | Size: " + property.getSize() + " | Price: " + property.getPrice());
        }

        return projectDetailsList;
    }
}
