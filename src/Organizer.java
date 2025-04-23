//update profile, chat with admin
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Organizer extends User {

    private List<Event> eventsOrganized;
    public Wallet wallet;

    public Organizer(String username, String password, LocalDate dateOfBirth, String address, Gender gender) {
        super(username, password, dateOfBirth, address, gender);
        this.wallet = new Wallet(0);
    }

    public void createEvent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter event Capacity: ");
        int Capacity = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter event Category  : ");
        String Category = scanner.nextLine();
        System.out.print("Choose event Room     : ");
        Room room = new Room(scanner);
        eventsOrganized.add(new Event(scanner, Capacity, Category , room , this));
    }

    public void updateEvent(Event event) {
        System.out.println("Choose which field to update: ");
        System.out.println("1. Title");
        System.out.println("2. Description");
        System.out.println("3. Date & Time");
        System.out.println("4. Price");
        int choice = Integer.parseInt(System.console().readLine());
        switch (choice) {
            case 1:
                event.inputTitle(new Scanner(System.in));
                break;
            case 2:
                event.inputDescription(new Scanner(System.in));
                break;
            case 3:
                event.inputDateTime(new Scanner(System.in));
                break;
            case 4:
                event.inputPrice(new Scanner(System.in));
                break;
        }
    }

    public void deleteEvent(Event event) {
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