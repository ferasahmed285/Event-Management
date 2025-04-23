import java.util.List;
import java.time.LocalDate;

public class Admin extends User {

    private String role;
    private String workingHours;

    public Admin(String username, String password, LocalDate dateOfBirth, String address, Gender gender, String role, String workingHours) {
        super(username, password, dateOfBirth, address, gender);
        this.role = role;
        this.workingHours = workingHours;
    }

@Override
    public void displayDashboard() {
        System.out.println("=== Admin Dashboard ===");
        System.out.println("Username: " + username);
        System.out.println("Role: " + role);
        System.out.println("Working Hours: " + workingHours);
        System.out.println("1. View All Rooms");
        System.out.println("2. View All Events");
        System.out.println("3. View All Attendees");
        System.out.println("4. Create/Update/Delete Categories");
        System.out.println("5. Ban Users");
        System.out.println("6. Generate Reports");
        System.out.println("----------------------------\n");
    }

    public void addRoom(Room room) {
        Database.rooms.add(room);
        System.out.println("Room added successfully");
    }

    public void addEvent(Event e) {
        Database.events.add(e);
        System.out.println("Event added successfully");
    }

    public void addAttendee(User user) {
        Database.users.add(user);
        System.out.println("User added successfully");
    }

    public void showAllRooms() {
        System.out.println("=== All Rooms ===");
        for (Room r : Database.rooms) {
            System.out.println("ID: " + r.getId() + "\nName: " + r.getName() + "\nCapacity: " + r.getRoomCapacity());
        }
    }

    public void showAllEvents() {
        System.out.println("=== All Events ===");
        for (Event e : Database.events) {
            e.displaySummary();

        }
    }

    public void createCategory(Category category) {
        Database.categories.add(category);
    }

//    public void updateCategory(String id, String newName, String newDesc) {
//        for (Category cat : Database.categories) {
//            if (cat.getId().equals(id)) {
//                cat.updateDetails(newName, newDesc);
//                return;
//            }
//        }
//
//    }
//
//    public void deleteCategory(String categoryName) {
//        Category category = (Category) Database.getEntityByUsername(categoryName);
//        Database.categories.remove(category);
//    }
//
//    public void banUser(String username) {
//        User user = (User) Database.getEntityByUsername(username);
//        Database.users.remove(user);
//    }

    public void generateReport() {
        System.out.println("--- SYSTEM REPORT ---");
        System.out.println("Total Users: " + Database.users.size());
        System.out.println("Total Events: " + Database.events.size());
        System.out.println("Total Rooms: " + Database.rooms.size());
        System.out.println("Total Categories: " + Database.categories.size());
    }

    public List<Category> getCategories() {
        return Database.categories;
    }

    public List<Event> getEvents() {
        return Database.events;
    }

}