import java.util.ArrayList;
import java.util.List;

public class Validation {
    private List<String> usernames = new ArrayList<>();

    public class Instructor {
        Wallet wallet;

        public Instructor(double balance) {
            this.wallet = new Wallet(balance);
        }

        public Wallet getWallet() {
            return wallet;
        }

        public class Wallet {
            private double balance;

            public Wallet(double balance) {
                this.balance = balance;
            }

            public double getBalance() {
                return balance;
            }
        }
    }

    public boolean hasSufficientBalance(Instructor instructor, double amount) {
        return instructor.wallet.getBalance() >= amount;
    }

    public boolean isValidUsername(String username) {
        if (username == null || username.length() <= 0) return false;
        for (int i = 0; i < username.length(); i++) {
            char ch = username.charAt(i);
            if (!Character.isLetterOrDigit(ch) && ch != '_') return false;
        }
        return !usernames.contains(username);
    }

    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    public void addUsername(String username) {
        if (isValidUsername(username)) {
            usernames.add(username);
        }
    }

    public List<String> getUsernames() {
        return usernames;
    }
}