package Modules;
// MainUI.java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.PropertyView;
import ui.SearchView;
import ui.TransactionView;

public class MainUI extends Application {

    private Project project;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Housing Property Management");

        // Initialize the project and load data
        project = new Project("ProjectA");
        project.loadHistoricalTransactions("transactions.txt");

        // Create the main layout
        BorderPane mainLayout = new BorderPane();

        // Create a tab pane and add tabs
        TabPane tabPane = new TabPane();

        Tab propertyTab = new Tab("Properties", new PropertyView(project));
        Tab searchTab = new Tab("Search", new SearchView(project));
        Tab transactionTab = new Tab("Transactions", new TransactionView(project));

        tabPane.getTabs().addAll(propertyTab, searchTab, transactionTab);

        mainLayout.setCenter(tabPane);

        // Create and set the scene
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
