//update dashboard
import java.time.LocalDate;
import java.util.List;

public class Attendee extends User {
    private List<String> interests;
    public Wallet wallet;

    public Attendee(String username, String password, LocalDate dateOfBirth, String address, Gender gender, List<String> interests) {
        super(username, password, dateOfBirth, address, gender);
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void browseEvents() {
        for (Event event : Database.events) {
            if (event.getCategory().equals(this.interests.get(0))) {
                event.displaySummary();
            }
        }
    }

    public void buyTickets(Event event , Organizer organizer){
        if (this.wallet.getBalance() >= event.getPrice()) {
            this.wallet.transferToOrganizer(event.getPrice(), event.getTitle(), organizer );
            event.registerAttendee(this.username);
        }
    }

    public void refundTickets(Event event , Organizer organizer) {
        event.removeAttendee(this, organizer);
        this.wallet.refund( event,this,organizer);
    }

    public void viewTickets() {
        for (Event event : Database.events) {
            if (event.getAttendees().contains(this.username)) {
                event.displaySummary();
            }
        }
    }

    public void addFunds(int amount){
        this.wallet.addFunds(amount);
    }

    @Override
    public void displayDashboard() {
        //update
        System.out.println("Welcome " + this.username + "!");
        System.out.println("1. Browse Events");
        System.out.println("2. View Tickets");
        System.out.println("3. Add Funds");
        System.out.println("4. Logout");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(System.console().readLine());
        switch (choice) {
            case 1:
                browseEvents();
                break;
            case 2:
                viewTickets();
                break;
            case 3:
                addFunds(Integer.parseInt(System.console().readLine()));
                break;
            case 4:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                    displayDashboard();
                break;
        }
    }
}