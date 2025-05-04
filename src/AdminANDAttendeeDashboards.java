import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminANDAttendeeDashboards {

    private BorderPane mainLayout;

    private VBox AdminDashboard() {
        VBox adminPane = new VBox(10);
        adminPane.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label adminLabel = new Label("Admin Dashboard");
        Button categoryButton = new Button("Manage Categories");
        Button roomButton = new Button("Manage Rooms");
        Button eventButton = new Button("Manage Events");
        Button usersButton = new Button("View All Users");
        Button logoutButton = new Button("Logout");
//        categoryButton.setOnAction(e -> mainLayout.setCenter(createListPane("Categories", categories)));
//        roomButton.setOnAction(e -> mainLayout.setCenter(createListPane("Rooms", rooms)));
//        eventButton.setOnAction(e -> mainLayout.setCenter(createListPane("Events", events)));
//        usersButton.setOnAction(e -> mainLayout.setCenter(createListPane("Users", users)));
//        logoutButton.setOnAction(e -> mainLayout.setCenter(createLoginPane()));
        adminPane.getChildren().addAll(adminLabel, categoryButton, roomButton, eventButton, usersButton, logoutButton);
        return adminPane;
    }

    private VBox AttendeeDashboard() {
        VBox attendeePane = new VBox(10);
        attendeePane.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label attendeeLabel = new Label("Attendee Dashboard");
        Button viewEventsButton = new Button("View Events");
        Button myTicketsButton = new Button("See My Tickets");
        Button profileButton = new Button("View Profile");
        Button logoutButton = new Button("Logout");
//        viewEventsButton.setOnAction(e -> mainLayout.setCenter(createListPane("Available Events", events)));
//        myTicketsButton.setOnAction(e -> mainLayout.setCenter(createListPane("My Tickets", myTickets)));
//        profileButton.setOnAction(e -> mainLayout.setCenter(createProfilePane()));
//        logoutButton.setOnAction(e -> mainLayout.setCenter(createLoginPane()));
        attendeePane.getChildren().addAll(attendeeLabel, viewEventsButton, myTicketsButton, profileButton, logoutButton);
        return attendeePane;
    }


    private VBox AttendeePurchasedEvents(String title, ObservableList<String> items) {
        VBox listPane = new VBox(10);
        listPane.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label titleLabel = new Label(title);
        ListView<String> listView = new ListView<>(items);
        Button backButton = new Button("Back");
        if (title.equals("Available Events") || title.equals("My Tickets") || title.equals("Attendee Profile")) {
            backButton.setOnAction(e -> mainLayout.setCenter(AttendeeDashboard()));
        } else {
            backButton.setOnAction(e -> mainLayout.setCenter(AdminDashboard()));
        }
        listPane.getChildren().addAll(titleLabel, listView, backButton);
        return listPane;
    }
}