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

    private List<Project> projects;
    private PropertyController propertyController;
    private TransactionController transactionController;
    private TableView<Property> table;
    private TextArea transactionArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Property Search System");

        projects = new ArrayList<>();
        initializeProjects(); 

        propertyController = new PropertyController(projects, new PropertyView());
        transactionController = new TransactionController(new TransactionView());

        Label projectLabel = new Label("Project Name:");
        ComboBox<String> projectDropdown = new ComboBox<>();
        projectDropdown.getItems().addAll(getProjectNames());

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

        table = new TableView<>();
        initializeTableView();

        Label propertyDetailsLabel = new Label();
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Property selectedProperty = newSelection;
                propertyDetailsLabel.setText("Project: " + selectedProperty.getProjectName() +
                        "\nSize: " + selectedProperty.getSize() + " sq ft" +
                        "\nPrice: $" + selectedProperty.getPrice() +
                        "\nFacilities: " + selectedProperty.getFacilities() +
                        "\nAddress: " + selectedProperty.getAddress());
            }
        });

        searchButton.setOnAction(e -> {
            String projectName = projectDropdown.getValue();
            double minSize = Double.parseDouble(minSizeInput.getText());
            double maxSize = Double.parseDouble(maxSizeInput.getText());
            double minPrice = Double.parseDouble(minPriceInput.getText());
            double maxPrice = Double.parseDouble(maxPriceInput.getText());
            String facilities = facilitiesInput.getText();

            List<Property> results = propertyController.searchProperties(minSize, maxSize, minPrice, maxPrice,
                    facilities, projectName);
            updateTable(results);
        });

        Button viewTransactionsButton = new Button("View Transactions");
        viewTransactionsButton.setOnAction(e -> {
            String projectName = projectDropdown.getValue();
            List<String> transactions = transactionController.fetchTransactions("src/resources/transactions.txt", projectName);
            transactionArea.setText(String.join("\n", transactions));
        });

        transactionArea = new TextArea();
        transactionArea.setEditable(false);

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
        formLayout.add(viewTransactionsButton, 1, 7);

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(formLayout, table, transactionArea);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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
        table.getItems().clear();
        table.getItems().addAll(searchResults);
    }

    public static void main(String[] args) {
        launch(args);
    }
}