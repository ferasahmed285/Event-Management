import java.util.ArrayList;
import java.util.List;

class Wallet{
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


    public void checkBalance(){
        System.out.println(balance);
    }
    public void transferToOrganizer(double amount, String title,Organizer organizer) {
        if (balance >= amount) {
            balance -= amount;
            organizer.receiveFunds(amount);
            transactions.add(new Transaction("Transfer to Organizer", amount, title));
            System.out.println("Transferred $" + amount + " to organizer for event: " + title);
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }

}
