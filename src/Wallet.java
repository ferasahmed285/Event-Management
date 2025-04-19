import java.util.ArrayList;
import java.util.List;

public class Wallet{
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