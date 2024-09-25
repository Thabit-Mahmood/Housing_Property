package services;

import model.Property;
import model.Project;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class ProjectService {
    private static ProjectService instance;
    private List<Project> projects;
    private Queue<Property> pendingProperties;
    private final String projectsCSVPath = "src/resources/projects.csv"; // Path to the projects CSV file
    private final String pendingPropertiesCSVPath = "src/resources/pending_properties.csv"; // Pending properties file
                                                                                            // path
    private final String approvedPropertiesCSVPath = "src/resources/properties.csv"; // Approved properties file path
    private static final Logger logger = Logger.getLogger(ProjectService.class.getName());

    private ProjectService() {
        this.projects = new ArrayList<>();
        this.pendingProperties = new LinkedList<>();
        loadProjectsFromCSV(); // Load projects when the service is initialized
        loadPendingPropertiesFromCSV(); // Load pending properties on initialization
    }

    public static synchronized ProjectService getInstance() {
        if (instance == null) {
            instance = new ProjectService();
        }
        return instance;
    }

    // Create a new project and save to file
    public void createProject(String projectName) {
        Project newProject = new Project(projectName);
        projects.add(newProject);
        saveProjectsToCSV(); // Save the updated list to file
        System.out.println("Project created: " + projectName);
    }

    // Delete a project by name and save the updated list
    public void deleteProject(String projectName) {
        projects.removeIf(project -> project.getProjectName().equalsIgnoreCase(projectName));
        saveProjectsToCSV(); // Save the updated list to file
        System.out.println("Project deleted: " + projectName);
    }

    // Save projects to the CSV file
    private void saveProjectsToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(projectsCSVPath, false))) {
            for (Project project : projects) {
                bw.write(project.getProjectName());
                bw.newLine();
            }
        } catch (IOException e) {
            logger.severe("Error saving projects: " + e.getMessage());
        }
    }

    // Load projects from the CSV file
    private void loadProjectsFromCSV() {
        File file = new File(projectsCSVPath);
        if (!file.exists()) {
            logger.severe("Projects CSV file does not exist.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String projectName = line.trim();
                if (!projectName.isEmpty()) {
                    projects.add(new Project(projectName));
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading projects CSV file: " + e.getMessage());
        }
    }

    // Approve a property (move it from the pending queue to the approved list in
    // the relevant project)
    public void approveProperty(Property property) {
        Project project = getProjectByName(property.getProjectName());
        if (project != null) {
            project.addProperty(property); // Add the property to the project's approved properties list
            pendingProperties.remove(property); // Remove from pending queue
            savePendingPropertiesToCSV(); // Save updated pending properties list
            saveApprovedPropertyToCSV(property); // Save the approved property in the desired format
            System.out.println("Property approved: " + property.getAddress());
        } else {
            System.out.println("Project not found: " + property.getProjectName());
        }
    }

    // Reject a property (remove it from the pending queue)
    public void rejectProperty(Property property) {
        pendingProperties.remove(property);
        savePendingPropertiesToCSV(); // Save updated pending properties list
        System.out.println("Property rejected: " + property.getAddress());
    }

    // Submit a property for approval
    public void submitPropertyForApproval(Property property) {
        pendingProperties.add(property);
        savePendingPropertiesToCSV(); // Save to the pending properties file
        System.out.println("Property submitted for approval: " + property.getAddress());
    }

    // Load pending properties from the CSV file
    private void loadPendingPropertiesFromCSV() {
        File file = new File(pendingPropertiesCSVPath);
        if (!file.exists()) {
            logger.severe("Pending properties CSV file does not exist.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) { // Adjusted to 6 fields (including seller username)
                    double size = Double.parseDouble(data[0].trim());
                    double price = Double.parseDouble(data[1].trim());
                    String facilities = data[2].trim();
                    String projectName = data[3].trim();
                    String address = data[4].trim();
                    String sellerUsername = data[5].trim();

                    Property pendingProperty = new Property(size, price, facilities, projectName, address);
                    pendingProperty.setSellerUsername(sellerUsername); // Set seller username
                    pendingProperties.add(pendingProperty); // Add to pending queue
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading pending properties CSV file: " + e.getMessage());
        }
    }

    // Save pending properties to the CSV file
    private void savePendingPropertiesToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pendingPropertiesCSVPath, false))) {
            for (Property property : pendingProperties) {
                bw.write(property.getSize() + "," + property.getPrice() + "," + property.getFacilities() + "," +
                        property.getProjectName() + "," + property.getAddress() + "," + property.getSellerUsername());
                bw.newLine();
            }
        } catch (IOException e) {
            logger.severe("Error saving pending properties: " + e.getMessage());
        }
    }

    // Save approved property to the properties CSV file
    private void saveApprovedPropertyToCSV(Property property) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(approvedPropertiesCSVPath, true))) {
            // Prepare the format for writing the approved property to the file
            String dateOfValuation = new SimpleDateFormat("dd/MM/yyyy").format(new Date()); // Current date
            String sizeSqM = String.format("%.2f", property.getSize() / 10.7639); // Convert SqFt to SqM
            String sqFt = String.format("%.2f", property.getSize()); // Keep size in SqFt
            String propertyType = property.getFacilities(); // Use facilities as property type (can be updated based on
                                                            // requirements)
            String noOfFloors = "N/A"; // Placeholder if noOfFloors is not provided
            String address = property.getAddress();
            String scheme = property.getProjectName();
            String price = String.valueOf(property.getPrice());
            String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)); // Current year
            String pricePerSqFt = String.format("%.2f", property.getPrice() / property.getSize()); // Price per SqFt

            // Write the property details in the correct format
            bw.write(dateOfValuation + "," + sizeSqM + "," + sqFt + "," + propertyType + "," + noOfFloors + "," +
                    "\"" + address + "\"," + scheme + "," + price + "," + year + "," + pricePerSqFt);
            bw.newLine();
        } catch (IOException e) {
            logger.severe("Error saving approved property: " + e.getMessage());
        }
    }

    // Get the next pending property for review
    public Property getNextPendingProperty() {
        return pendingProperties.peek(); // Returns the next property in the queue
    }

    // List all projects
    public List<String> getAllProjectNames() {
        List<String> projectNames = new ArrayList<>();
        for (Project project : projects) {
            projectNames.add(project.getProjectName());
        }
        return FileHandler.getInstance().loadProjectsFromCSV();
    }

    // Get a project by name
    private Project getProjectByName(String projectName) {
        return projects.stream()
                .filter(project -> project.getProjectName().equalsIgnoreCase(projectName))
                .findFirst()
                .orElse(null);
    }
}
