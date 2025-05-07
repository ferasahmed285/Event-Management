//handle null error
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Attendee extends User {
    public List<String> interests;
    public Wallet wallet;
    private Runnable goBack; // To go back to dashboard


    public Attendee(String username, String password, LocalDate dateOfBirth, String address, Gender gender) {
        super(username, password, dateOfBirth, address, gender);
        this.wallet = new Wallet(0);
        this.interests = List.of();
    }

    public void addInterests(List<String> interests) {
        this.interests.addAll(interests);
    }

//    public void browseEvents() {
//        for (Event event : Database.events) {
//            if (this.interests.isEmpty()) {
//                event.displaySummary();
//            }
//            else if (this.interests.getFirst().equals(event.getCategory())) {
//                event.displaySummary();
//            }
//        }
//    }

    public void buyTickets(Event event){
        if (this.wallet.getBalance() >= event.getPrice()) {
            this.wallet.transferToOrganizer(event.getPrice(), event.getTitle(), event.organizer );
            event.registerAttendee(this);
//            System.out.println("Ticket purchased successfully.");
        }
        else {
//            System.out.println("Insufficient funds.");
//            System.out.println("Please try again.");
        }
//        displayDashboard();
    }

    private void showAlert(String title, String message) {//ali
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private List<Event> getSortedEvents() {//ali
        List<Event> events = new ArrayList<>(Database.events);

        // Create two lists: matching interests and others
        List<Event> matchingInterests = new ArrayList<>();
        List<Event> otherEvents = new ArrayList<>();

        // Split events based on interests
        if (this.interests != null && !this.interests.isEmpty()) {
            for (Event event : events) {
                if (this.interests.contains(event.getCategory())) {
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

    public void viewEvents(Stage primaryStage) {//ali
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
                if (event.getAttendees().contains(this)) {
                    showAlert("Error", "You already bought a ticket for this event.");
                    return;
                }

                // Check if attendee has enough money
                if (this.wallet.getBalance() < event.getPrice()) {
                    showAlert("Error", "You don't have enough money.");
                    return;
                }

                // Try to buy the ticket
                this.buyTickets(event);
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
        primaryStage.setScene(new Scene(layout, 600, 400));
    }

    public void refundTickets(Event event) {
        event.removeAttendee(this);
        this.wallet.refund( event,this);
    }

    public void viewTickets() {
        for (Event event : Database.events) {
            if (event.getAttendees().contains(this)) {
                event.displaySummary();
            }
        }
    }

    public void addFunds(int amount){
        this.wallet.addFunds(amount);
    }

    @Override
    public void displayDashboard() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Welcome " + this.username + "!");
//        System.out.println("1. Browse Events");
//        System.out.println("2. Buy Tickets");
//        System.out.println("3. Refund Tickets");
//        System.out.println("4. View Profile");
//        System.out.println("5. Add Funds");
//        System.out.println("6. Add Interest");
//        System.out.println("7. Remove All Interests");
//        System.out.println("8. Update Password");
//        System.out.println("9. Logout");
//        System.out.print("Enter your choice: ");
//        int choice = Integer.parseInt(scanner.nextLine());
//        switch (choice) {
//            case 1:
//                browseEvents();
//                displayDashboard();
//                break;
//            case 2:
//                System.out.println("Choose an event to buy tickets:");
//                browseEvents();
//                System.out.print("Enter event Name: ");
//                String eventName = scanner.nextLine();
//                Event event = (Event) Database.getEntityByUsername(eventName);
//                assert event != null;
//                buyTickets(event);
//                break;
//            case 3:
//                System.out.println("Choose an event to refund tickets:");
//                for (Event event1 : Database.events) {
//                    if (event1.getCategory().equals(this.interests.getFirst())) {
//                        event1.displaySummary();
//                    }
//                }
//                System.out.print("Enter event Name: ");
//                String eventName1 = scanner.nextLine();
//                Event event1 = (Event) Database.getEntityByUsername(eventName1);
//                assert event1 != null;
//                refundTickets(event1);
//                break;
//            case 4:
//                System.out.println("Profile Info:");
//                System.out.println("Username: " + username);
//                System.out.println("Date of Birth: " + dateOfBirth);
//                System.out.println("Address: " + address);
//                System.out.println("Gender: " + gender);
//                System.out.println("Wallet Balance: " + wallet.getBalance());
//                System.out.println("Interests: " + interests);
//                System.out.println("Tickets: ");
//                viewTickets();
//                break;
//            case 5:
//                System.out.print("Enter amount to add: ");
//                int amount = Integer.parseInt(scanner.nextLine());
//                addFunds(amount);
//                break;
//            case 6:
//                System.out.print("Enter interest to add: ");
//                String interest = scanner.nextLine();
//                addInterests(List.of(interest));
//                break;
//            case 7:
//                this.interests.clear();
//                break;
//            case 8:
//                System.out.println("Update Password");
//                System.out.print("Enter new password: ");
//                String newPassword = scanner.nextLine();
//                updatePassword(newPassword);
//                break;
//            case 9:
//                this.logout();
//                break;
//            default:
//                System.out.println("Invalid choice!");
//                displayDashboard();
//                break;
//        }
    }
    public void displayDashboard(Stage primaryStage) {
        VBox attendeePane = new VBox(10);
        attendeePane.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label attendeeLabel = new Label("Attendee Dashboard");
        Button viewEventsButton = new Button("View Events");
        Button myTicketsButton = new Button("See My Tickets");
        Button profileButton = new Button("View Profile");
        Button logoutButton = new Button("Logout");
        viewEventsButton.setOnAction(e -> viewEvents(primaryStage));
//        myTicketsButton.setOnAction(e -> mainLayout.setCenter(createListPane("My Tickets", myTickets)));
//        profileButton.setOnAction(e -> mainLayout.setCenter(createProfilePane()));
        logoutButton.setOnAction(e -> primaryStage.setScene(LoginRegisterSystem.loginScene));
        attendeePane.getChildren().addAll(attendeeLabel, viewEventsButton, myTicketsButton, profileButton, logoutButton);
        primaryStage.setScene(new Scene(attendeePane, 500, 400));
    }
//    private BorderPane mainLayout;
//    private VBox AttendeePurchasedEvents(String title, ObservableList<String> items) {
//        VBox listPane = new VBox(10);
//        listPane.setStyle("-fx-padding: 20; -fx-alignment: center;");
//        Label titleLabel = new Label(title);
//        ListView<String> listView = new ListView<>(items);
//        Button backButton = new Button("Back");
//        if (title.equals("Available Events") || title.equals("My Tickets") || title.equals("Attendee Profile")) {
//            backButton.setOnAction(e -> mainLayout.setCenter(AttendeeDashboard()));
//        } else {
//            backButton.setOnAction(e -> mainLayout.setCenter(AdminDashboard()));
//        }
//        listPane.getChildren().addAll(titleLabel, listView, backButton);
//        return listPane;
//    }
}