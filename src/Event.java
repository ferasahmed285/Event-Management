import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Event {
    private String title, description, category;
    private LocalDateTime dateTime;
    private double price;
    private List<String> attendees = new ArrayList<>();
    private int capacity;

    public Event(Scanner scanner, int capacity, String category) {
        this.capacity = capacity;
        this.category = category;
        inputTitle(scanner);
        inputDescription(scanner);
        inputDateTime(scanner);
        inputPrice(scanner);
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

    public void deleteEvent() {
        description = title + " event not found";
        category = title + " event not found";
        dateTime = null;
        price = 0;
        attendees.clear();
        capacity = 0;
    }

    public void displaySummary() {
        if (capacity == 0) {
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

    public void registerAttendee(String name) {
        if (attendees.contains(name)) {
            System.out.println(name + " is already registered.");
        } else if (attendees.size() < capacity) {
            attendees.add(name);
        } else {
            System.out.println("Room is full.");
        }
    }

    public boolean removeAttendee(String name) {
        if (attendees.remove(name)) {
            System.out.println(name + " has been removed.");
            return true;
        }
        System.out.println(name + " not found.");
        return false;
    }

    public int remainingCapacity() {
        return capacity - attendees.size();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getAttendees() {
        return attendees;
    }
}
//da bel delete mengheir transfer w change balance