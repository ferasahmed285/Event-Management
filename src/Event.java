import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Event {
    private String title, description, category;
    private LocalDateTime dateTime;
    private double price;
    private List<Attendee> attendees = new ArrayList<>();
    private int capacity;
    private boolean isDeleted = false;
    private Room room;

    public Event(Scanner scanner, int capacity, String category, Room room) {
        this.capacity = capacity;
        this.category = category;
        this.room = room;
        inputTitle(scanner);
        inputDescription(scanner);
        inputDateTime(scanner);
        inputPrice(scanner);
        Database.addEntity(this);
    }

    public void inputTitle(Scanner scanner) {
        System.out.print("Enter Event Title: ");
        title = scanner.nextLine();
    }

    public void inputDescription(Scanner scanner) {
        System.out.print("Enter Event Description: ");
        description = scanner.nextLine();
    }

    public void inputDateTime(Scanner scanner) {
        while (true) {
            System.out.print("Enter Event Date and Time (Year-Month-Day Hour : Minutes): ");
            try {
                dateTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                break;
            } catch (Exception e) {
                System.out.println("Invalid format. Try again.");
            }
        }
    }

    public void inputPrice(Scanner scanner) {
        while (true) {
            System.out.print("Enter ticket price: ");
            if (scanner.hasNextDouble()) {
                price = scanner.nextDouble();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Invalid price.");
                scanner.nextLine();
            }
        }
    }

    public void deleteEvent(Organizer organizer) {
        issueRefunds(organizer);
        releaseRoom();
        Database.removeEntity(this);
        isDeleted = true;
        System.out.println("Event '" + title + "' has been removed from the database.");
    }

    //refund
    private void issueRefunds(Organizer organizer) {
        for (Attendee attendee : attendees) {
            attendee.wallet.refund(this,attendee,organizer);
            System.out.println("Refunded " + price + " EGP to: " + attendee);
            System.out.println("Organizer charged " + price + " EGP refunded to: " + attendee);
        }
    }

    private void releaseRoom() {
        if (room != null) {
            room.clearRoom();
            System.out.println("Room for event '" + title + "' has been released.");
        } else {
            System.out.println("No room assigned to this event.");
        }
    }



    public void displaySummary() {
        if (isDeleted) {
            System.out.println("Event deleted");
            return;
        }

        System.out.println("Event Summary:");
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Date & Time: " + dateTime);
        System.out.println("Category: " + category);
        System.out.println("Price: " + price + " EGP");
        System.out.println("Attendees: " + attendees.size());
        System.out.println("Remaining Capacity: " + remainingCapacity());
    }

    public void registerAttendee(String username) {
        if (isDeleted) {
            System.out.println("Cannot register. Event has been deleted.");
            return;
        }
        if (attendees.contains(username)) {
            System.out.println(username + " is already registered.");
        } else if (attendees.size() < capacity) {
            attendees.add(username);
            System.out.println(username + " has been registered.");
        } else {
            System.out.println("Room is full.");
        }
    }

    public boolean removeAttendee(Attendee username , Organizer organizer) {
        if (attendees.remove(username)) {
            username.wallet.refund(this, username, organizer);
            System.out.println(username + " has been removed.");
            System.out.println("Refunded " + price + " EGP to: " + username);
            System.out.println("Organizer charged " + price + " EGP refunded to: " + username);
            return true;
        }
        System.out.println(username + " not found.");
        return false;
    }

    public int remainingCapacity() {
        return capacity - attendees.size();
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public List<String> getAttendees() { return attendees; }
}