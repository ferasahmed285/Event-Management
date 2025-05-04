import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class allEventsTable{
    private Stage primaryStage;

    private Scene createEventTableScene() {
        TableView<Event> tableView = new TableView<>();

        // Title column
        TableColumn<Event, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Description column
        TableColumn<Event, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Category column
        TableColumn<Event, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        // DateTime column
        TableColumn<Event, String> dateTimeCol = new TableColumn<>("Date & Time");
        dateTimeCol.setCellValueFactory(cellData -> {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return new SimpleStringProperty(cellData.getValue().getDateTime().format(fmt));
        });

        // Price column
        TableColumn<Event, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Attendees count column
        TableColumn<Event, Integer> attendeesCol = new TableColumn<>("Attendees");
        attendeesCol.setCellValueFactory(cellData ->
            new SimpleIntegerProperty(cellData.getValue().getAttendees().size()).asObject()
        );

        // Room column
        TableColumn<Event, String> roomCol = new TableColumn<>("Room");
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));

        // Organizer column
        TableColumn<Event, String> organizerCol = new TableColumn<>("Organizer");
        organizerCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().organizer.getUsername())
        );

        tableView.getColumns().addAll(
            titleCol, descCol, categoryCol,
            dateTimeCol, priceCol, attendeesCol,
            roomCol, organizerCol
        );

        // Load data from the Database
        List<Event> events = Database.events;
        ObservableList<Event> data = FXCollections.observableArrayList(events);
        tableView.setItems(data);

        // Back button to return to start page
        Button backButton = new Button("Back");
        //backButton.setOnAction(e -> primaryStage.setScene(createStartScene()));

        VBox layout = new VBox(10, backButton, tableView);
        layout.setPadding(new Insets(20));

        return new Scene(layout, 800, 600);
    }

}