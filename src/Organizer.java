
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Organizer extends User {

    public Wallet wallet;
    private Stage primaryStage;

    public Organizer(String username, String password, LocalDate dateOfBirth, String address, Gender gender) {
        super(username, password, dateOfBirth, address, gender);
        this.wallet = new Wallet(0);
    }

    public void createEvent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Event Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Event Description: ");
        String description = scanner.nextLine();
        LocalDateTime dateTime;
        while (true) {
            System.out.print("Enter Event Date and Time (YYYY-MM-DD HH:MM): ");
            try {
                dateTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                break;
            } catch (Exception e) {
                System.out.println("Invalid format. Try again.");
            }
        }
        double price = 0;
        while (price <= 0) {
            System.out.print("Enter ticket price: ");
            String temp = scanner.nextLine();
            price = Double.parseDouble(temp);
            if (price < 0) {
                System.out.println("Invalid price.");
            }
        }
        System.out.print("Enter event Category:");
        String Category = scanner.nextLine();
        viewAvailableRooms();
        System.out.println("Enter room name:");
        String roomName = scanner.nextLine();
        Room room = (Room) Database.getEntityByUsername(roomName);
        new Event(title, description, dateTime, price, Category, room, this);
        System.out.println("Event created successfully.");
        displayDashboard();
    }

    public void updateEvent(Event event) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose which field to update: ");
        System.out.println("1. Title");
        System.out.println("2. Description");
        System.out.println("3. Date and Time");
        System.out.println("4. Price");
        System.out.println("5. Category");
        System.out.println("6. Room");
        System.out.println("7. Cancel");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(System.console().readLine());
        switch (choice) {
            case 1:
                System.out.print("Enter Event Title: ");
                String title = scanner.nextLine();
                event.setTitle(title);
                System.out.println("Event title updated successfully.");
                break;
            case 2:
                System.out.print("Enter Event Description: ");
                String description = scanner.nextLine();
                event.setDescription(description);
                System.out.println("Event description updated successfully.");
                break;
            case 3:
                LocalDateTime dateTime;
                while (true) {
                    System.out.print("Enter Event Date and Time (Year-Month-Day Hour : Minutes): ");
                    try {
                        dateTime = LocalDateTime.parse(scanner.nextLine(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid format. Try again.");
                    }
                }
                break;
            case 4:
                while (true) {
                    System.out.print("Enter ticket price: ");
                    if (scanner.hasNextDouble())
                        ;
                    double price = scanner.nextDouble();
                    if (price > 0) {
                        event.setPrice(price);
                        System.out.println("Ticket price updated successfully.");
                        break;
                    } else {
                        System.out.println("Invalid price.");
                    }
                }
                break;
            case 5:
                System.out.print("Enter event Category  : ");
                String Category = scanner.nextLine();
                event.setCategory(Category);
                System.out.println("Event Category updated successfully.");
                break;
            case 6:
                System.out.print("Choose event Room     : ");
                viewAvailableRooms();
                System.out.print("Enter room name: ");
                String roomName = scanner.nextLine();
                Room room = (Room) Database.getEntityByUsername(roomName);
                if (room == null) {
                    System.out.println("Room not found. Please try again.");
                    updateEvent(event);
                    return;
                } else if (room.getRoomCapacity() <= event.getAttendees().size()) {
                    System.out.println("Room is full. Please try again.");
                    updateEvent(event);
                    return;
                } else if (room.getRoomCapacity() < event.getAttendees().size()) {
                    System.out.println("Room is not large enough. Please try again.");
                    updateEvent(event);
                    return;
                }
                event.setRoom(room);
                System.out.println("Event Room updated successfully.");
                break;
            default:
                System.out.println("Invalid choice!");
                updateEvent(event);
        }
        displayDashboard();
    }

    public void deleteEvent(Event event) {
        event.deleteEvent(this);
    }

    public void viewAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : Database.rooms)
            room.displayRoomInfo();
    }

    public void viewAttendeesForMyEvents() {
        System.out.println("Attendees for My Events:");
        for (Event event : Database.events) {
            if (event.organizer.equals(this)) {
                System.out.println(event.getTitle() + ": " + event.getAttendees());
            }
        }
    }

    public void viewMyEvents() {
        System.out.println("My Events:");
        for (Event event : Database.events) {
            if (event.organizer.equals(this)) {
                event.displaySummary();
            }
        }
    }

    public void chatWithAdmin(String message) {
        System.out.println("NOT IMPLEMENTED");
    }

    @Override
    public void displayDashboard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome " + this.username + "!");
        System.out.println("1. Create Event");
        System.out.println("2. Update Event");
        System.out.println("3. Delete Event");
        System.out.println("4. View Available Rooms");
        System.out.println("5. View Attendees for My Events");
        System.out.println("6. View My Events");
        System.out.println("7. Chat with Admin");
        System.out.println("8. Update Password");
        System.out.println("9. Logout");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                createEvent();
                break;
            case 2:
                System.out.println("Choose an event to update:");
                for (Event event : Database.events) {
                    if (event.organizer.equals(this)) {
                        event.displaySummary();
                    }
                }
                System.out.print("Enter event name: ");
                String eventName = scanner.nextLine();
                Event event = (Event) Database.getEntityByUsername(eventName);
                updateEvent(event);
                break;
            case 3:
                System.out.println("Choose an event to delete:");
                for (Event event1 : Database.events) {
                    if (event1.organizer.equals(this)) {
                        event1.displaySummary();
                    }
                }
                System.out.print("Enter event Name: ");
                String eventName1 = scanner.nextLine();
                Event event1 = (Event) Database.getEntityByUsername(eventName1);
                assert event1 != null;
                deleteEvent(event1);
                System.out.println("Event deleted successfully.");
                displayDashboard();
                break;
            case 4:
                viewAvailableRooms();
                displayDashboard();
                break;
            case 5:
                viewAttendeesForMyEvents();
                displayDashboard();
                break;
            case 6:
                viewMyEvents();
                displayDashboard();
                break;
            case 7:
                chatWithAdmin("Hello Admin!");
                System.out.println("Message sent successfully.");
                displayDashboard();
                break;
            case 8:
                System.out.println("Update Password");
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                updatePassword(newPassword);
                System.out.println("Password updated successfully.");
                displayDashboard();
                break;
            case 9:
                this.logout();
                break;
            default:
                System.out.println("Invalid choice!");
                displayDashboard();
                break;
        }
    }

    public void receiveFunds(double amount) {
        this.wallet.addFunds(amount);
    }

    public void displayDashboard(Stage primaryStage) {

        this.primaryStage = primaryStage;
        Label welcomeLabel = new Label("Welcome, " + this.getUsername());
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button eventsButton = new Button("1 - Events");
        Button profileButton = new Button("2 - Profile");
        Button chatButton = new Button("3 - Chat with Admin");
        Button logoutButton = new Button("4 - Logout");

        eventsButton.setMaxWidth(Double.MAX_VALUE);
        profileButton.setMaxWidth(Double.MAX_VALUE);
        chatButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        eventsButton.setOnAction(e -> showAllEvents(primaryStage));
        profileButton.setOnAction(e -> viewProfile());
        // chatButton.setOnAction(e -> showChatScene(stage));
        logoutButton.setOnAction(e -> primaryStage.setScene(LoginRegisterSystem.loginScene));

        VBox menuRoot = new VBox(10,
                welcomeLabel,
                eventsButton,
                profileButton,
                chatButton,
                logoutButton);
        menuRoot.setAlignment(Pos.CENTER);
        menuRoot.setPadding(new Insets(20));
        primaryStage.setScene(new Scene(menuRoot, 400, 300));
    }

    public void showAllEvents(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EventsController.fxml"));
            Parent root = loader.load();

            EventsController controller = loader.getController();
            controller.setData(this, primaryStage); 

            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileOrganizer.fxml"));
            Parent root = loader.load();

            ProfileOrganizer profile = loader.getController();
            profile.setData(this, this.primaryStage); // Use the stored stage reference

            Scene profileScene = new Scene(root, 600, 500);
            this.primaryStage.setScene(profileScene);
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}

