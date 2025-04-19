import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attendee extends User {
//address (String): Physical address.
//interests (List<String>): List of interests (e.g., ["Music", "Sports"]) used for filtering events.
//tickets (List<Ticket>): Collection of purchased event tickets (if a dedicated Ticket class is implemented).
//wallet (Wallet): Reference for all fund-related transactions.

    private String address;
    private List<String> interests;
    //private List<Ticket> tickets;
    public Wallet wallet;

//buyTicket(String eventId): Deducts ticket price from the wallet and registers the attendee for the event.
//browseEvents(): Displays available events filtered by interests or category.
//viewTickets(): Retrieves the list of purchased tickets.
//addFunds(double amount): Increases the wallet balance.
//rateEvent(String eventId, int rating): Submits feedback for a given event.

    public Attendee(String username, String password, LocalDate dateOfBirth, Gender gender) {
        super(username, password, dateOfBirth, gender);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void buyTickets(){

    }

    public void browseEvents() {

    }

    public void viewTickets() {

    }

    public void addFunds(){

    }

    public void rateEvent(){

    }

    @Override
    public void displayDashboard() {

    }
}