import java.util.*;

class Room {
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
        scanner.nextLine();
        return roomId;
    }

    private String inputName(Scanner scanner) {
        System.out.print("Enter Room Name: ");
        return scanner.nextLine();
    }

    private int inputRoomCapacity(Scanner scanner) {
        System.out.print("Enter the capacity of the room: ");
        int maxCap = scanner.nextInt();
        scanner.nextLine();
        return maxCap;
    }

    private List<String> inDates(Scanner scanner) {
        List<String> dates = new ArrayList<>();
        System.out.print("Enter the number of dates available for reservation: ");
        int number = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= number; i++) {
            System.out.print("Enter time slot number " + i + ": ");
            String dateAndTime = scanner.nextLine();
            dates.add(dateAndTime);
        }
        return dates;
    }

    private int inputGuestNumber(Scanner scanner) {
        int guests;
        while (true) {
            System.out.print("Enter the number of guests: ");
            guests = scanner.nextInt();
            scanner.nextLine();

            if (guests <= roomCapacity) {
                return guests;
            } else {
                System.out.println("Room capacity exceeded");
            }
        }
    }

    private String chosenDate(Scanner scanner) {
        System.out.println("Choose a booking time from available dates:");
        for (int i = 0; i < organiserDate.size(); i++) {
            System.out.println((i + 1) + ". " + organiserDate.get(i));
        }

        int choice;
        while (true) {
            System.out.print("Enter your choice (1 to " + organiserDate.size() + "): ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= 1 && choice <= organiserDate.size()) {
                return organiserDate.get(choice - 1);
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
    public void reservationStat(){
      if(this.numberOfGuests > 0){
        System.out.println("Room is reserved");
      }
        else {
          System.out.println("Room is empty");
        }
    }
    public void clearRoom() {
        this.name = null;
        System.out.println("Room " + id + " has been cleared.");
    }

    public void displayRoomInfo() {
        if(this.name != null){
            System.out.println("Room ID: " + id);
            System.out.println("Room Name: " + name);
            System.out.println("Room Capacity: " + roomCapacity);
            System.out.println("Number of Guests: " + numberOfGuests);

            System.out.println("Available Time Slots:");
            for (int i = 0; i < organiserDate.size(); i++) {
                System.out.println((i + 1) + ". " + organiserDate.get(i));
            }

            System.out.println("Remaining Capacity: " + (roomCapacity - numberOfGuests));
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