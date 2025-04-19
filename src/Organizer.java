import org.w3c.dom.events.Event;//temp

import java.util.List;

public class Organizer extends User {
//eventsOrganized (List<Event>): Events created by the organizer.
//walletBalance (double): Accumulated earnings from ticket sales (if not solely tracked via individual Wallet objects).

    private List<Event> eventsOrganized;
    private double walletBalance;

//createEvent(Event event): Adds a new event (validates the chosen room’s availability).
//updateEvent(Event event): Updates details of an event the organizer created.
//deleteEvent(String eventId): Cancels or removes an event.
//viewAvailableRooms(): Fetches a list of rooms available for event booking.
//viewAttendeesForMyEvents(): Lists attendees registered for the organizer’s events.
//chatWithAdmin(String message): Uses network functionality (Milestone 2) for direct communication with the Admin.

    public void createEvent(Event event) {

}

public void updateEvent(Event event) {

}

public void deleteEvent(String eventId) {

}

public void viewAvailableRooms() {

}

public void viewAttendeesForMyEvents() {

}

public void chatWithAdmin(String message) {

}

    @Override
    public void displayDashboard() {

    }
}
