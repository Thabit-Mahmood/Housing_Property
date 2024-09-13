package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import controller.PropertyController;
import controller.TransactionController;
import model.Project;
import model.Property;
import java.util.ArrayList;
import java.util.List;

public class MainView extends Application {

    // Define projects as an instance variable
    private List<Project> projects;
    private PropertyController propertyController;
    private TransactionController transactionController;
    private TableView<Property> table;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Property Search System");

        // Initialize the projects list
        projects = new ArrayList<>();
        initializeProjects(); // Populate the projects list

        // Initialize the controllers
        propertyController = new PropertyController(projects, new PropertyView());
        transactionController = new TransactionController(new TransactionView());

        // Create the form for search criteria
        Label projectLabel = new Label("Project Name:");
        ComboBox<String> projectDropdown = new ComboBox<>();
        projectDropdown.getItems().addAll(getProjectNames()); // Populate dropdown from project list

        Label minSizeLabel = new Label("Minimum Size:");
        TextField minSizeInput = new TextField();

        Label maxSizeLabel = new Label("Maximum Size:");
        TextField maxSizeInput = new TextField();

        Label minPriceLabel = new Label("Minimum Price:");
        TextField minPriceInput = new TextField();

        Label maxPriceLabel = new Label("Maximum Price:");
        TextField maxPriceInput = new TextField();

        Label facilitiesLabel = new Label("Facilities:");
        TextField facilitiesInput = new TextField();

        Button searchButton = new Button("Search Properties");

        // TableView to display search results
        table = new TableView<>();
        initializeTableView();

        // Label to display property details when a property is selected in TableView
        Label propertyDetailsLabel = new Label();
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Property selectedProperty = (Property) newSelection;
                propertyDetailsLabel.setText("Project: " + selectedProperty.getProjectName() +
                        "\nSize: " + selectedProperty.getSize() + " sq ft" +
                        "\nPrice: $" + selectedProperty.getPrice() +
                        "\nFacilities: " + selectedProperty.getFacilities() +
                        "\nAddress: " + selectedProperty.getAddress());
            }
        });

        // Event handling for search button
        searchButton.setOnAction(e -> {
            String projectName = projectDropdown.getValue();
            double minSize = Double.parseDouble(minSizeInput.getText());
            double maxSize = Double.parseDouble(maxSizeInput.getText());
            double minPrice = Double.parseDouble(minPriceInput.getText());
            double maxPrice = Double.parseDouble(maxPriceInput.getText());
            String facilities = facilitiesInput.getText();

            // Perform search and update table
            List<Property> results = propertyController.searchProperties(minSize, maxSize, minPrice, maxPrice,
                    facilities, projectName);
            updateTable(results);
        });

        // Transaction Section: View transactions based on project selection
        Label transactionLabel = new Label("View Transactions for Project:");
        ComboBox<String> transactionProjectDropdown = new ComboBox<>();
        transactionProjectDropdown.getItems().addAll(getProjectNames()); // Populate project dropdown

        Button viewTransactionsButton = new Button("View Transactions");
        ListView<String> transactionListView = new ListView<>();

        // Event handling for viewing transactions
        viewTransactionsButton.setOnAction(e -> {
            String selectedProjectName = transactionProjectDropdown.getValue();
            if (selectedProjectName != null) {
                // Fetch transactions based on project name
                List<String> transactions = transactionController.fetchTransactions("C:/Users/thabi/housing_property_project/src/resources/transactions.txt",
                        selectedProjectName);
                transactionListView.getItems().clear(); // Clear previous transactions
                transactionListView.getItems().addAll(transactions); // Display new transactions
            } else {
                transactionListView.getItems().clear();
                transactionListView.getItems().add("Please select a project to view transactions.");
            }
        });

        // Layout for viewing transactions
        VBox transactionLayout = new VBox(10, transactionLabel, transactionProjectDropdown, viewTransactionsButton,
                transactionListView);

        // Add a section for viewing project details
        Label projectDetailsLabel = new Label("View Project Details:");
        ComboBox<String> projectDetailsDropdown = new ComboBox<>();
        projectDetailsDropdown.getItems().addAll(getProjectNames()); // Populate dropdown

        Button viewProjectDetailsButton = new Button("View Project Details");
        ListView<String> projectDetailsListView = new ListView<>();

        // Event handling for viewing project details
        viewProjectDetailsButton.setOnAction(e -> {
            String selectedProjectName = projectDetailsDropdown.getValue();
            Project selectedProject = propertyController.getProjectByName(selectedProjectName);
            projectDetailsListView.getItems().clear(); // Clear previous details
            if (selectedProject != null) {
                projectDetailsListView.getItems().add("Project: " + selectedProject.getProjectName());
                for (Property property : selectedProject.getProperties()) {
                    projectDetailsListView.getItems().add("Property: " + property.getAddress() +
                            " | Size: " + property.getSize() + " | Price: " + property.getPrice());
                }
            } else {
                projectDetailsListView.getItems().add("No project details available.");
            }
        });

        // Layout for viewing project details
        VBox projectDetailsLayout = new VBox(10, projectDetailsLabel, projectDetailsDropdown, viewProjectDetailsButton,
                projectDetailsListView);

        // Layout for search
        GridPane formLayout = new GridPane();
        formLayout.setPadding(new Insets(10));
        formLayout.setHgap(10);
        formLayout.setVgap(10);

        formLayout.add(projectLabel, 0, 0);
        formLayout.add(projectDropdown, 1, 0);
        formLayout.add(minSizeLabel, 0, 1);
        formLayout.add(minSizeInput, 1, 1);
        formLayout.add(maxSizeLabel, 0, 2);
        formLayout.add(maxSizeInput, 1, 2);
        formLayout.add(minPriceLabel, 0, 3);
        formLayout.add(minPriceInput, 1, 3);
        formLayout.add(maxPriceLabel, 0, 4);
        formLayout.add(maxPriceInput, 1, 4);
        formLayout.add(facilitiesLabel, 0, 5);
        formLayout.add(facilitiesInput, 1, 5);
        formLayout.add(searchButton, 1, 6);

        // Main layout with the new sections added
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(formLayout, table, transactionLayout, projectDetailsLayout);

        Scene scene = new Scene(mainLayout, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to initialize projects list with sample data
    private void initializeProjects() {
        Project project1 = new Project("Project Alpha");
        Project project2 = new Project("Project Beta");

        Property property1 = new Property(1200, 250000, "Pool, Gym", "Project Alpha", "123 Alpha St");
        Property property2 = new Property(800, 150000, "Gym", "Project Alpha", "124 Alpha St");
        Property property3 = new Property(1000, 200000, "Pool", "Project Beta", "200 Beta St");
        Property property4 = new Property(1500, 300000, "Pool, Gym, Park", "Project Beta", "201 Beta St");

        project1.addProperty(property1);
        project1.addProperty(property2);
        project2.addProperty(property3);
        project2.addProperty(property4);

        projects.add(project1);
        projects.add(project2);
    }

    // Helper method to get project names
    private List<String> getProjectNames() {
        List<String> projectNames = new ArrayList<>();
        for (Project project : projects) {
            projectNames.add(project.getProjectName());
        }
        return projectNames;
    }

    @SuppressWarnings("unchecked")
    private void initializeTableView() {
        TableColumn<Property, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Property, Double> sizeColumn = new TableColumn<>("Size (sq ft)");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<Property, Double> priceColumn = new TableColumn<>("Price ($)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Property, String> facilitiesColumn = new TableColumn<>("Facilities");
        facilitiesColumn.setCellValueFactory(new PropertyValueFactory<>("facilities"));

        table.getColumns().addAll(addressColumn, sizeColumn, priceColumn, facilitiesColumn);
    }

    private void updateTable(List<Property> searchResults) {
        table.getItems().clear(); // Clear previous results
        table.getItems().addAll(searchResults); // Add new results
    }

    public static void main(String[] args) {
        launch(args);
    }
}
