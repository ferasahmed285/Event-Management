import java.util.ArrayList;
import java.util.List;

public class Wallet{
    double balance;
    List<Transaction> transactions;

    public Wallet(){

    }

    public Wallet (double balance){
        this.balance =balance;
        this.transactions = new ArrayList<>();
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void addFunds(double amount){
        if (amount > 0) {
            this.balance += amount;
            transactions.add(new Transaction("Deposit", amount, null));
            System.out.println("Added $" + amount + " to wallet.");
        } else {
            System.out.println("Amount must be positive.");
        }
    }


    public void checkBalance(){
        System.out.println(balance);
    }
    public void transferToOrganizer(double amount, String title,Organizer organizer) {
        if (balance >= amount) {
            this.balance -= amount;
            organizer.receiveFunds(amount);
            transactions.add(new Transaction("Transfer to Organizer", amount, title));
            System.out.println("Transferred $" + amount + " to organizer for event: " + title);
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }
    public void refund(Event event, Attendee attendee) {
        if (event.organizer.wallet.balance >= event.getPrice()) {
            event.organizer.wallet.balance -= event.getPrice();
            attendee.wallet.addFunds(event.getPrice());
            transactions.add(new Transaction("Transfer to Attendee", event.getPrice(), event.getTitle()));
            System.out.println("Refunded $" + event.getPrice() + " to Attendee for event: " + event.getTitle());
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }
//    public double Refund(double amount ){
//        if (amount > 0) {
//            balance += amount;
//            transactions.add(new Transaction("Refund", amount, null));
//            System.out.println("Refunded $" + amount + " to user.");
//        } else {
//            System.out.println("Refund amount must be positive.");
//        }
//        return balance;
//
//    }
}