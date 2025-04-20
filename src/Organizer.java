import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Organizer extends User {

    private List<Event> eventsOrganized;
    private Wallet wallet;

//createEvent(Event event): Adds a new event (validates the chosen room’s availability).
//deleteEvent(String eventId): Cancels or removes an event.
//viewAvailableRooms(): Fetches a list of rooms available for event booking.


    public Organizer(String username, String password, LocalDate dateOfBirth, Gender gender) {
        super(username, password, dateOfBirth, gender);
        this.wallet = new Wallet(0);
    }

    public void createEvent() {
//DATABASE        Database.events.add(new Event(new Scanner(System.in), 0, ""));
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
//WAITING FOR CLASS ROOM TO BE IMPLEMENTED
}

    public void viewAttendeesForMyEvents() {
            for (Event event : eventsOrganized) {
            System.out.println(event.getTitle() + ": " + event.getAttendees().size());
            }
    }

//public void chatWithAdmin(String message) {
////THREADS
//}

    @Override
    public void displayDashboard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome " + this.username + "!");
        System.out.println("1. Create Event");
        System.out.println("2. View Events");
        System.out.println("3. View Available Rooms");
        System.out.println("4. View Attendees for My Events");
        System.out.println("5. Chat with Admin");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
    }
}
