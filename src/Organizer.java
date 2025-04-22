//waiting for delete event
//update dashboard
//waiting for view available rooms
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Organizer extends User {

    private List<Event> eventsOrganized;
    private Wallet wallet;

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
        eventsOrganized.add(new Event(scanner, Capacity, Category));
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
        //DATABASE        Database.removeEntity(event);
    }

    public void viewAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : Database.rooms) {
                System.out.println(room.getId() + ": " + room.getName() + " (" + room.getRoomCapacity() + " seats)");
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
        //NOT NOW
    }

    @Override
    public void displayDashboard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome " + this.username + "!");
        System.out.println("1. Create Event");
        System.out.println("2. View My Events");
        System.out.println("3. View Available Rooms");
        System.out.println("4. View Attendees for My Events");
        System.out.println("5. Chat with Admin");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                createEvent();
                break;
            case 2:
                viewMyEvents();
                break;
            case 3:
                viewAvailableRooms();
                break;
            case 4:
                viewAttendeesForMyEvents();
                break;
            case 5:
                chatWithAdmin("TEST");
                break;
            case 6:
                logout();
                break;
            default:
                System.out.println("Invalid choice.");
                displayDashboard();
                break;
        }
    }

    public void receiveFunds(double amount) {
        this.wallet.addFunds(amount);
    }
}
