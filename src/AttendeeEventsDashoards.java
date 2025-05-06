import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendeeEventsDashoards {
    private Attendee attendee; // The logged-in attendee
    private Runnable goBack; // To go back to dashboard

    // Constructor: Takes the attendee and a way to go back
    public AttendeeEventsDashoards(Attendee attendee, Runnable goBack) {
        this.attendee = attendee;
        this.goBack = goBack;
    }

    // Creates the JavaFX page (Scene)
    public Scene getScene() {
        // Create a vertical box to hold everything
        VBox layout = new VBox(10); // 10 is spacing between items
        layout.setStyle("-fx-padding: 20;"); // Add some padding

        // Add a title
        Label titleLabel = new Label("Available Events");

        // Create a ListView to show events
        ListView<String> eventsList = new ListView<>();

        // Get and sort events
        List<Event> sortedEvents = getSortedEvents();

        // Add each event to the ListView
        for (Event event : sortedEvents) {
            // Format event details
            String eventInfo = "Title: " + event.getTitle() +
                    ", Category: " + event.getCategory() +
                    ", Date: " + event.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                    ", Price: $" + event.getPrice();

            // Add event info to ListView
            eventsList.getItems().add(eventInfo);

            // Create a Buy button for this event
            Button buyButton = new Button("Buy");
            buyButton.setOnAction(e -> {
                // Check if attendee already bought a ticket
                if (event.getAttendees().contains(attendee)) {
                    showAlert("Error", "You already bought a ticket for this event.");
                    return;
                }

                // Check if attendee has enough money
                if (attendee.wallet.getBalance() < event.getPrice()) {
                    showAlert("Error", "You don't have enough money.");
                    return;
                }

                // Try to buy the ticket
                attendee.buyTickets(event);
                showAlert("Success", "Ticket bought for " + event.getTitle() + "!");

                // Refresh the list
                eventsList.getItems().clear();
                for (Event ev : getSortedEvents()) {
                    eventsList.getItems().add("Title: " + ev.getTitle() +
                            ", Category: " + ev.getCategory() +
                            ", Date: " + ev.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                            ", Price: $" + ev.getPrice());
                }
            });

            // Add Buy button next to event (in a horizontal box if needed, but keeping simple)
            layout.getChildren().add(buyButton);
        }

        // Add a Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> goBack.run());

        // Add everything to the layout
        layout.getChildren().add(0, titleLabel); // Add title at the top
        layout.getChildren().add(1, eventsList); // Add list after title

        // Add Back button at the bottom
        layout.getChildren().add(backButton);

        // Create and return the Scene
        return new Scene(layout, 600, 400); // Window size: 600x400
    }

    // Gets events, prioritizes by interests, and sorts by date
    private List<Event> getSortedEvents() {
        List<Event> events = new ArrayList<>(Database.events);

        // Create two lists: matching interests and others
        List<Event> matchingInterests = new ArrayList<>();
        List<Event> otherEvents = new ArrayList<>();

        // Split events based on interests
        if (attendee.interests != null && !attendee.interests.isEmpty()) {
            for (Event event : events) {
                if (attendee.interests.contains(event.getCategory())) {
                    matchingInterests.add(event);
                } else {
                    otherEvents.add(event);
                }
            }
        } else {
            otherEvents = events; // If no interests, show all events
        }

        // Sorting each list by date
        matchingInterests.sort((e1, e2) -> e1.getDateTime().compareTo(e2.getDateTime()));
        otherEvents.sort((e1, e2) -> e1.getDateTime().compareTo(e2.getDateTime()));

        // Combining lists with matching interests first
        List<Event> sortedEvents = new ArrayList<>();
        sortedEvents.addAll(matchingInterests);
        sortedEvents.addAll(otherEvents);

        return sortedEvents;
    }

    // Showing a pop-up message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}