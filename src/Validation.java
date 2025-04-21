public interface Validation {

    boolean hasSufficientBalance(Attendee attendee, double amount);

    boolean isRoomCapacityValid(Room room, int numberOfGuests);

    boolean isValidUsername(String username);

    boolean isValidPassword(String password);

    void addUsername(String username);
}
