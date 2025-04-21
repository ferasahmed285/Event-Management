import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {

    private String role;
    private String workingHours;

    private List<Room> rooms = new ArrayList<>();
    private List<Event> events = new ArrayList<>();
    private List<User> attendees = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public Admin(String username, String password, LocalDate dateOfBirth, Gender gender, String role, String workingHours) {
        super(username, password, dateOfBirth, gender);
        this.role = role;
        this.workingHours = workingHours;
    }

    @Override
    public void displayDashboard() {
        System.out.println("=== Admin Dashboard ===");
        System.out.println("Username: " + username);
        System.out.println("Role: " + role);
    }

    public void addRoom(Scanner scanner) {
        Room newRoom = new Room(scanner);
        rooms.add(newRoom);
        System.out.println("Room added successfully");
    }

    public void showAllRooms() {
        System.out.println("=== All Rooms ===");
        for (Room r : rooms) {
            System.out.println("ID: " + r.getId() + " | Name: " + r.getName() + " | Capacity: " + r.getRoomCapacity());
        }
    }

    public void showAllEvents() {
        System.out.println("=== All Events ===");
        for (Event e : events) {
            e.displaySummary();
            System.out.println("------------------");
        }
    }

//    public void showAllAttendees() {
//        System.out.println("=== All Attendees ===");
//        for (User u : attendees) {
//            System.out.println("User ID: " + u.getUserId() + " | Username: " + u.getUsername());
//        }
//    }

    public void createCategory(Scanner scanner) {
        System.out.print("Enter Category ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Category Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Description: ");
        String desc = scanner.nextLine();

        categories.add(new Category(id, name, desc));
        System.out.println("Category added.");
    }

    public void updateCategory(Scanner scanner) {
        System.out.print("Enter Category ID to update: ");
        String id = scanner.nextLine();

        for (Category c : categories) {
            if (c.categoryid.equals(id)) {
                System.out.print("New Name: ");
                String newName = scanner.nextLine();
                System.out.print("New Description: ");
                String newDesc = scanner.nextLine();
                c.updateDetails(newName, newDesc);
                System.out.println("Category updated.");
                return;
            }
        }
        System.out.println("Category not found.");
    }

    public void deleteCategory(Scanner scanner) {
        System.out.print("Enter Category ID to delete: ");
        String id = scanner.nextLine();

        categories.removeIf(c -> c.categoryid.equals(id));
        System.out.println("Category deleted (if existed).");
    }

    public void approveEvent(Scanner scanner) {
        System.out.print("Enter Event Title to approve: ");
        String title = scanner.nextLine();

        for (Event e : events) {
            if (e.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Event \"" + title + "\" is approved.");

                return;
            }
        }
        System.out.println("Event not found.");
    }

//    public void banUser(Scanner scanner) {
//        System.out.print("Enter User ID to ban: ");
//        String id = scanner.nextLine();
//
//        for (User u : attendees) {
//            if (u.getUserId().equals(id)) {
//                System.out.println("User " + u.getUsername() + " has been banned.");
//                return;
//            }
//        }
//        System.out.println("User not found.");
//    }

    public void generateReport() {
        System.out.println("=== System Report ===");
        System.out.println("Total Rooms: " + rooms.size());
        System.out.println("Total Events: " + events.size());
        System.out.println("Total Categories: " + categories.size());
        System.out.println("Total Attendees: " + attendees.size());
        System.out.println("Report Generated.");
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    public void addAttendee(User user) {
        attendees.add(user);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Event> getEvents() {
        return events;
    }

}
