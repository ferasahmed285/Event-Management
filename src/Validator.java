import java.time.LocalDateTime;

public class Validator implements Validation {

    @Override
    public boolean hasEnoughBalance(Attendee attendee, double amount) {
        return attendee.wallet.getBalance() >= amount;
    }

    @Override
    public boolean canFitAttendeesInRoom(Room room, int attendeeCount) {
        return room.getRoomCapacity() >= attendeeCount;
    }

    @Override
    public boolean isUsernameTaken(String username) {
        for (User user : Database.users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean meetsPasswordCriteria(String password) {
        return password.length() >= 6;
    }

    @Override
    public boolean isRoomAvailableAtTime(Room room, LocalDateTime dateTime) {
        for (Event event : Database.events) {
            if (event.room.equals(room) && event.getDateTime().equals(dateTime)) {
                return false;
            }
        }
        return true;
    }
}