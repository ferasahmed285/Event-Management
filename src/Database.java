import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.LocalDate;

public class Database {

    // Attributes as a list of objects
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Event> events = new ArrayList<>();
    public static ArrayList<Room> rooms = new ArrayList<>();
    public static ArrayList<Category> categories = new ArrayList<>();

    // 1. Method to Load sample data (dummy data)
    public static void initializeDummyData() {
//        Room room = new Room(1, "test" , 10 , 1 , new ArrayList<String>(), "test" );
        ArrayList<String> timeSlots = new ArrayList<>();
        timeSlots.add("10:00 AM");
        timeSlots.add("1:00 PM");
        timeSlots.add("3:00 PM");
        new Room(1, "Room A", 50, 0, new ArrayList<>(timeSlots), null);
        new Room(2, "Room B", 30, 0, new ArrayList<>(timeSlots), null);
        new Category("category1", "Technology", "New tech events");
        new Category("100", "Music", "listen songs");
        new Category("101", "Sports", "watch matches");
        new Category("102", "Education", "read books");
        new Category("103", "GENERAL", "GENERAL");
        new Admin("admin1", "password1", LocalDate.of(2006, 2, 4), "newCairo", User.Gender.MALE, "CEO", "9-5");
        new Organizer("organizer1", "password2", LocalDate.of(2005, 8, 2), "Alex", User.Gender.MALE );
        new Attendee("attendee1", "attendee2", LocalDate.of(2000, 1, 1), "Cairo", User.Gender.MALE);
//        new Event("Happy Work", "Workshop" ,null , 20 , "KIDS", rooms.get(1) , new Organizer("feras" , "password123" , LocalDate.of(2005, 8, 2), "Alex", User.Gender.MALE));
        Organizer organizer = new Organizer("feras", "password123", LocalDate.of(2005, 8, 2), "Alex",User.Gender.MALE);
        Room room = new Room(3, "Room C", 20, 0, new ArrayList<>(timeSlots), null);
        new Event("Happy Work", "Workshop", LocalDateTime.now(), 20, "Music",room , organizer);
    }

    // 2. Method to Add a new entity to the appropriate list
    public static void addEntity(Object entity) {
        if (entity instanceof User) {
            users.add((User) entity);
        } else if (entity instanceof Event) {
            events.add((Event) entity);
        } else if (entity instanceof Room) {
            rooms.add((Room) entity);
        } else if (entity instanceof Category) {
            categories.add((Category) entity);
        }
    }

    // 3. Method to Remove an existing entity from the appropriate list
    public static void removeEntity(Object entity) {
        if (entity instanceof User) {
            users.remove(entity);
        } else if (entity instanceof Event) {
            events.remove(entity);
        } else if (entity instanceof Room) {
            rooms.remove(entity);
        } else if (entity instanceof Category) {
            categories.remove(entity);
        }
    }

    // 4. Method to Retrieve an entity by its username
    public static Object getEntityByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        for (Event event : events) {
            if (event.getTitle().equals(username)) return event;
        }
        for (Room room : rooms) {
            if (room.getName().equals(username)) return room;
        }
        for (Category category : categories) {
            if (category.getCategoryid().equals(username)) return category;
        }
        return null; // Not found
    }
}