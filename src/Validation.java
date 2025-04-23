/// user validation
public interface Validation {
    boolean hasEnoughBalance(Attendee attendee, double amount);
    boolean canFitAttendeesInRoom(Room room, int attendeeCount);
    boolean isUsernameTaken(String username);
    boolean meetsPasswordCriteria(String password);
    boolean isRoomAvailableAtTime(Room room, java.time.LocalDateTime dateTime);
}