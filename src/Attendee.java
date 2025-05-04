//handle null error
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Attendee extends User {
    public List<String> interests;
    public Wallet wallet;

    public Attendee(String username, String password, LocalDate dateOfBirth, String address, Gender gender) {
        super(username, password, dateOfBirth, address, gender);
        this.wallet = new Wallet(0);
        this.interests = List.of();
    }

    public void addInterests(List<String> interests) {
        this.interests.addAll(interests);
    }

    public void browseEvents() {
        for (Event event : Database.events) {
            if (this.interests.isEmpty()) {
                event.displaySummary();
            }
            else if (this.interests.getFirst().equals(event.getCategory())) {
                event.displaySummary();
            }
        }
    }

    public void buyTickets(Event event){
        if (this.wallet.getBalance() >= event.getPrice()) {
            this.wallet.transferToOrganizer(event.getPrice(), event.getTitle(), event.organizer );
            event.registerAttendee(this);
            System.out.println("Ticket purchased successfully.");
        }
        else {
            System.out.println("Insufficient funds.");
            System.out.println("Please try again.");
        }
        displayDashboard();
    }

    public void refundTickets(Event event) {
        event.removeAttendee(this);
        this.wallet.refund( event,this);
    }

    public void viewTickets() {
        for (Event event : Database.events) {
            if (event.getAttendees().contains(this)) {
                event.displaySummary();
            }
        }
    }

    public void addFunds(int amount){
        this.wallet.addFunds(amount);
    }

    @Override
    public void displayDashboard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome " + this.username + "!");
        System.out.println("1. Browse Events");
        System.out.println("2. Buy Tickets");
        System.out.println("3. Refund Tickets");
        System.out.println("4. View Profile");
        System.out.println("5. Add Funds");
        System.out.println("6. Add Interest");
        System.out.println("7. Remove All Interests");
        System.out.println("8. Update Password");
        System.out.println("9. Logout");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                browseEvents();
                displayDashboard();
                break;
            case 2:
                System.out.println("Choose an event to buy tickets:");
                browseEvents();
                System.out.print("Enter event Name: ");
                String eventName = scanner.nextLine();
                Event event = (Event) Database.getEntityByUsername(eventName);
                assert event != null;
                buyTickets(event);
                break;
            case 3:
                System.out.println("Choose an event to refund tickets:");
                for (Event event1 : Database.events) {
                    if (event1.getCategory().equals(this.interests.getFirst())) {
                        event1.displaySummary();
                    }
                }
                System.out.print("Enter event Name: ");
                String eventName1 = scanner.nextLine();
                Event event1 = (Event) Database.getEntityByUsername(eventName1);
                assert event1 != null;
                refundTickets(event1);
                break;
            case 4:
                System.out.println("Profile Info:");
                System.out.println("Username: " + username);
                System.out.println("Date of Birth: " + dateOfBirth);
                System.out.println("Address: " + address);
                System.out.println("Gender: " + gender);
                System.out.println("Wallet Balance: " + wallet.getBalance());
                System.out.println("Interests: " + interests);
                System.out.println("Tickets: ");
                viewTickets();
                break;
            case 5:
                System.out.print("Enter amount to add: ");
                int amount = Integer.parseInt(scanner.nextLine());
                addFunds(amount);
                break;
            case 6:
                System.out.print("Enter interest to add: ");
                String interest = scanner.nextLine();
                addInterests(List.of(interest));
                break;
            case 7:
                this.interests.clear();
                break;
            case 8:
                System.out.println("Update Password");
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                updatePassword(newPassword);
                break;
            case 9:
                this.logout();
                break;
            default:
                System.out.println("Invalid choice!");
                displayDashboard();
                break;
        }
    }
}