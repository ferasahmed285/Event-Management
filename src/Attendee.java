//update dashboard
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

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
            event.registerAttendee(this);
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome " + this.username + "!");
        System.out.println("1. Browse Events");
        System.out.println("2. Buy Tickets");
        System.out.println("3. View Tickets");
        System.out.println("4. Refund Tickets");
        System.out.println("5. Update Profile");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                browseEvents();
                break;
            case 2:
                System.out.println("Choose an event to buy tickets:");
                for (Event event : Database.events) {
                    if (event.getCategory().equals(this.interests.get(0))) {
                        event.displaySummary();
                    }
                }
                System.out.print("Enter event ID: ");
                String eventID = scanner.nextLine();
                Event event = (Event) Database.getEntityByUsername(eventID);
                System.out.print("Enter organizer username: ");
                String organizerUsername = scanner.nextLine();
                Organizer organizer = (Organizer) Database.getEntityByUsername(organizerUsername);
                buyTickets(event, organizer);
                break;
                case 3:
                viewTickets();
                break;
                case 4:
                System.out.println("Choose an event to refund tickets:");
                for (Event event1 : Database.events) {
                    if (event1.getCategory().equals(this.interests.get(0))) {
                        event1.displaySummary();
                    }
                }
                System.out.print("Enter event ID: ");
                String eventID1 = scanner.nextLine();
                Event event1 = (Event) Database.getEntityByUsername(eventID1);
                System.out.print("Enter organizer username: ");
                String organizerUsername1 = scanner.nextLine();
                Organizer organizer1 = (Organizer) Database.getEntityByUsername(organizerUsername1);
                refundTickets(event1, organizer1);
                break;
            case 5:
                System.out.println("Update Profile");
                //t3ban
                break;
        }
    }
}