import java.util.ArrayList;

public class Database {

    // Attributes as list of objects
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Event> events = new ArrayList<>();
    public static ArrayList<Room> rooms = new ArrayList<>();
    public static ArrayList<Category> categories = new ArrayList<>();

    // 1. Method to Load sample data (dummy data)
//    public static void initializeDummyData() {
//        users.add(new Admin("admin1", "admin2"));
//        users.add(new Organizer("organizer1", "organiser2"));
//        users.add(new Attendee("attendee1", "attendee2"));
//
//        rooms.add(new Room("Room1", "Room2"));
//
//        events.add(new Event("Workshop", "Meeting"));
//
//        categories.add(new Category("Tech"));
//    }

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
//    public static Object getEntityByUsername(String username) {
//        for (User user : users) {
//            if (user.getUsername().equals(username)) return user;
//        }
//        for (Event event : events) {
//            if (event.getName().equals(username)) return event;
//        }
//        for (Room room : rooms) {
//            if (room.getName().equals(username)) return room;
//        }
//        for (Category category : categories) {
//            if (category.getName().equals(username)) return category;
//        }
//        return null; // Not found
//    }

    
}