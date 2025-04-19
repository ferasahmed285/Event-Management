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

class Category{
    String categoryid;
    String categoryname;
    String description;


    public Category(String categoryid, String categoryname, String description) {
        this.categoryid = categoryid;
        this.categoryname = categoryname;
        this.description = description;
    }
    public List<Event> getEvents(List<Event> allevents) {
        List<Event> filtered = new ArrayList<>();

        for (Event e : allevents) {
            if (e.categoryid.equals(this.categoryid)) {
                filtered.add(e);
            }
        }

        return filtered;
    }
    public void updateDetails(String newName, String newDescription) {
        this.categoryname = newName;
        this.description = newDescription;
    }

}
class Wallet{
    double balance;
    List<Transaction> transactions;

    public Wallet (double balance){
        this.balance =balance;
        this.transactions = new ArrayList<>();
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public double addFunds(double amount){
        if (amount > 0) {
            balance += amount;
            transactions.add(new Transaction("Deposit", amount, null));
            System.out.println("Added $" + amount + " to wallet.");
        } else {
            System.out.println("Amount must be positive.");
        }
        return balance;
    }

    public double deductFunds(double amount) {
        if (balance >= amount){
           balance -= amount;
            transactions.add(new Transaction("Purchase", amount, null));
            System.out.println("Deducted $" + amount + " from wallet.");
        }
        else{
            System.out.println("No Enough Balance");
            return balance;
        }
        return balance;
    }
    public void checkBalance(){
        System.out.println(balance);
    }
    public void transferToOrganizer(double amount, String eventId) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction("Transfer to Organizer", amount, eventId));
            System.out.println("Transferred $" + amount + " to organizer for event: " + eventId);
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }

}
