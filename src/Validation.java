/// user validation
public interface Validation {
    void addFunds(double amount);

    void refund(Event event, Attendee attendee);

    void transferToOrganizer(double amount, String title, Organizer organizer);
}