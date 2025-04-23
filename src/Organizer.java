import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Organizer extends User {

    private List<Event> eventsOrganized;
    public Wallet wallet;

    public Organizer(String username, String password, LocalDate dateOfBirth, String address, Gender gender) {
        super(username, password, dateOfBirth, address, gender);
        this.wallet = new Wallet(0);
        this.eventsOrganized = Database.events;
        for (Event event : eventsOrganized) {
            event.organizer = this;
        }
        System.out.println("Organizer created successfully.");
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
            price = scanner.nextDouble();
            if (price < 0) {
                System.out.println("Invalid price.");
            }
        }
        System.out.println(" ");
        System.out.print("Enter event Category:");
        String Category = scanner.nextLine();
        System.out.println(" ");
        viewAvailableRooms();
        System.out.println("Enter room name:");
        String roomName = scanner.nextLine();
        Room room = (Room) Database.getEntityByUsername(roomName);
        eventsOrganized.add(new Event(title, description, dateTime, price, Category, room, this));
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
                        dateTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid format. Try again.");
                    }
                }
                break;
            case 4:
                while (true) {
                    System.out.print("Enter ticket price: ");
                    if (scanner.hasNextDouble()) ;
                    double price = scanner.nextDouble();
                    if (price > 0) {
                         event.setPrice(price);
                         System.out.println("Ticket price updated successfully.");
                         break;
                    }
                    else {
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
                }
                else if (room.getRoomCapacity() <= event.getAttendees().size()) {
                    System.out.println("Room is full. Please try again.");
                    updateEvent(event);
                    return;
                }
                else if (room.getRoomCapacity() < event.getAttendees().size()) {
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
    }

    public void deleteEvent(Event event) {
        eventsOrganized.remove(event);
        event.deleteEvent(this);
    }

    public void viewAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : Database.rooms) {
                room.displayRoomInfo();
        }
    }

    public void viewAttendeesForMyEvents() {
        System.out.println("Attendees for My Events:");
            for (Event event : eventsOrganized) {
            System.out.println(event.getTitle() + ": " + event.getAttendees());
            }
    }

    public void viewMyEvents() {
        System.out.println("My Events:");
        for (Event event : eventsOrganized) {
            event.displaySummary();
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
                for (Event event : eventsOrganized) {
                    event.displaySummary();
                }
                System.out.print("Enter event ID: ");
                String eventID = scanner.nextLine();
                Event event = (Event) Database.getEntityByUsername(eventID);
                updateEvent(event);
                break;
            case 3:
                System.out.println("Choose an event to delete:");
                for (Event event1 : eventsOrganized) {
                    event1.displaySummary();
                }
                System.out.print("Enter event ID: ");
                String eventID1 = scanner.nextLine();
                Event event1 = (Event) Database.getEntityByUsername(eventID1);
                assert event1 != null;
                deleteEvent(event1);
                break;
            case 4:
                viewAvailableRooms();
                break;
            case 5:
                viewAttendeesForMyEvents();
                break;
            case 6:
                viewMyEvents();
                break;
            case 7:
                chatWithAdmin("Hello Admin!");
                break;
            case 8:
                System.out.println("Update Password");
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                updatePassword(newPassword);
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
}