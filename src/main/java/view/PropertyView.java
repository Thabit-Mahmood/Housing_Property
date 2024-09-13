package view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Property;
import java.util.List;

public class PropertyView {

    public void displayPropertyDetail(Property property) {
        System.out.println("Project: " + property.getProjectName());
        System.out.println("Size: " + property.getSize() + " sq ft");
        System.out.println("Price: $" + property.getPrice());
        System.out.println("Facilities: " + property.getFacilities());
        System.out.println("Address: " + property.getAddress());
        System.out.println("--------------------------------------");
    }

    @SuppressWarnings("unchecked")
    public TableView<Property> displayProperties(List<Property> properties) {
        TableView<Property> table = new TableView<>();

        TableColumn<Property, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Property, Double> sizeColumn = new TableColumn<>("Size (sq ft)");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<Property, Double> priceColumn = new TableColumn<>("Price ($)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Property, String> facilitiesColumn = new TableColumn<>("Facilities");
        facilitiesColumn.setCellValueFactory(new PropertyValueFactory<>("facilities"));

        table.getColumns().addAll(addressColumn, sizeColumn, priceColumn, facilitiesColumn);
        table.getItems().addAll(properties);

        return table;
    }
}