import java.util.*;

class Transaction {
    String type;
    double amount;
    String eventId;
    String date;

    // Constructor — this runs when you create a new Transaction
    public Transaction(String type, double amount, String eventId) {
        this.type = type;
        this.amount = amount;
        this.eventId = eventId;
        this.date = java.time.LocalDateTime.now().toString();
    }

    public String toString() {
        return "[" + date + "] " + type + ": $" + amount +
                (eventId != null ? " (Event ID: " + eventId + ")" : "");
    }
}