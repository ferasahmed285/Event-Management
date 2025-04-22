import java.util.*;

public class Room {
    private int id;
    private String name;
    private int numberOfGuests;
    private int roomCapacity;
    private List<String> organiserDate;
    private String attendeeDate;

    public Room(Scanner scanner) {
        this.id = inputId(scanner);
        this.name = inputName(scanner);
        this.roomCapacity = inputRoomCapacity(scanner);
        this.numberOfGuests = inputGuestNumber(scanner);
        this.organiserDate = inDates(scanner);
        this.attendeeDate = chosenDate(scanner);
    }

    private int inputId(Scanner scanner) {
        System.out.print("Enter room ID: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // Organiser's function
        return roomId;
    }

    private String inputName(Scanner scanner) {
        System.out.print("Enter Room Name: ");
        return scanner.nextLine(); // Organiser's function
    }

    private int inputRoomCapacity(Scanner scanner) {
        System.out.print("Enter the capacity of the room: ");
        int maxCap = scanner.nextInt();
        scanner.nextLine(); // Organiser's function
        return maxCap;
    }
    private List<String> inDates(Scanner scanner) {
        List<String> dates = new ArrayList<>();
        System.out.print("Enter the number of dates available for reservation");
        int number = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= number; i++) {
            System.out.print("Enter time slot number" + i );
            String dateAndTime = scanner.nextLine();
            dates.add(dateAndTime);     //Organiser's function
        }
        return dates;
    }
    private int inputGuestNumber(Scanner scanner) {
        int guests;
        while (true) {
            System.out.print("Enter the number of guests: ");
            guests = scanner.nextInt();
            scanner.nextLine(); // Attendee's function

            if (guests <= roomCapacity) {
                return guests;
            } else {
                System.out.println("Room capacity exceeded");
            }
        }
    }
    private String chosenDate(Scanner scanner) {
        System.out.println(" Choose a booking time from available dates:");
        for (int i = 0; i < organiserDate.size(); i++) {
            System.out.println((i + 1) + ". " + organiserDate.get(i));
        }

        int choice;
        while (true) {
            System.out.print("Enter your choice (1 to " + organiserDate.size() + "): "); // Attendee's function
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= 1 && choice <= organiserDate.size()) {
                return organiserDate.get(choice - 1);
            } else {
                System.out.println(" Invalid choice.");
            }
        }
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getRoomCapacity() {
        return roomCapacity;
    }
    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}
