import java.util.*;
import java.time.*;

public class Event {
    private String title;
    private String description;
    private String category;
    private LocalDateTime dateTime;
    private double price;
    private List<Attendee> attendees = new ArrayList<>();
    private boolean isDeleted = false;
    Room room;
    public Organizer organizer;

    public Event( String Title , String Description , LocalDateTime Time , double price ,String category, Room room , Organizer organizer) {
        this.category = category;
        this.room = room;
        this.title = Title;
        this.description = Description;
        this.dateTime = Time;
        this.price = price;
        Database.addEntity(this);
        this.organizer = organizer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDateTime(LocalDateTime dateTime){
        this.dateTime = dateTime;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setRoom(Room room){
        this.room = room;
    }

    public void deleteEvent(Organizer organizer) {
        issueRefunds(organizer);
        releaseRoom();
        Database.removeEntity(this);
        isDeleted = true;
        System.out.println("Event '" + title + "' has been removed from the database.");
    }

    private void issueRefunds(Organizer organizer) {
        for (Attendee attendee : attendees) {
            attendee.wallet.refund(this,attendee);
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
        System.out.println("Organizer: " + organizer.getUsername());
        System.out.println("Description: " + description);
        System.out.println("Date & Time: " + dateTime);
        System.out.println("Category: " + category);
        System.out.println("Price: " + price + " EGP");
        System.out.println("Attendees: " + attendees.size());
        System.out.println("Remaining Capacity: " + remainingCapacity());
    }

    public void registerAttendee(Attendee attendee) {
        if (isDeleted) {
            System.out.println("Cannot register. Event has been deleted.");
            return;
        }
        if (attendees.contains(attendee)) {
            System.out.println(attendee.getUsername() + " is already registered.");
        } else if (attendees.size() < room.getRoomCapacity() && remainingCapacity() > 0) {
            attendees.add(attendee);
            System.out.println(attendee.getUsername() + " has been registered.");
        } else {
            System.out.println("Room is full.");
        }
    }

    public void removeAttendee(Attendee username) {
        if (attendees.remove(username)) {
            username.wallet.refund(this, username);
            System.out.println(username + " has been removed.");
            System.out.println("Refunded " + price + " EGP to: " + username);
            System.out.println("Organizer charged " + price + " EGP refunded to: " + username);
            return;
        }
        System.out.println(username + " not found.");
    }

    public int remainingCapacity() {
        return room.getRoomCapacity() - attendees.size();
    }
    
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public List<Attendee> getAttendees() { return attendees; }
    public Room getRoom() {return room;}
    public String getRoomName() {   return room.getName(); }
}
