public class Validation {
    private String[] usernames = new String[100];
    private int usernameCount = 0;

    private void expandUserArray() {
        String[] newArray = new String[usernames.length * 2];
        for (int i = 0; i < usernameCount; i++) {
            newArray[i] = usernames[i];
        }
        usernames = newArray;
    }

//    public boolean isRoomAvailable(Room room, String time) {
//        String[] bookedTimes = room.getBookedTimes();
//        for (int i = 0; i < bookedTimes.length; i++) {
//            if (time.equals(bookedTimes[i])) {
//                return false;
//            }
//        }
//        return true;
//    }
//testt
    public boolean hasSufficientBalance(Attendee attendee, double amount) {
        return attendee.wallet.getBalance() >= amount;
    }

    public boolean isValidUsername(String username) {
        if (username == null || username.length() < 5 || username.length() > 15) return false;

        for (int i = 0; i < username.length(); i++) {
            char ch = username.charAt(i);
            if (!Character.isLetterOrDigit(ch) && ch != '_') return false;
        }

        for (int i = 0; i < usernameCount; i++) {
            if (usernames[i].equals(username)) {
                return false;
            }
        }

        return true;
    }

    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }
}