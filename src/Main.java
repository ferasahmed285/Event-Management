public class Main {
    public static void main(String[] args) {
        Database.initializeDummyData();
                Database.initializeDummyData();
        System.out.println("=== Event Management System ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(System.console().readLine());
        switch (choice) {
            case 1:
                System.out.print("Enter username: ");
                String username = System.console().readLine();
                System.out.print("Enter password: ");
                String password = System.console().readLine();
                User user = (User) Database.getEntityByUsername(username);
                if (user != null) {
                    if (user.login(username, password)) {
                        user.displayDashboard();
                    }
                    else {
                        System.out.println("Invalid username or password");
                    }
                }
                else{
                    System.out.println("Invalid username or password");
                    main(args);
                }
                break;
            case 2:
                System.out.print("Enter username: ");
                String username1 = System.console().readLine();
                System.out.print("Enter password: ");
                String password1 = System.console().readLine();
                System.out.print("Enter your Birthday: ");
                String dateOfBirth = System.console().readLine();
                System.out.print("Enter your Address: ");
                String address = System.console().readLine();
                System.out.print("Enter your Gender:");
                String gender = System.console().readLine();
                System.out.println("ERR");
                //Attendee newuser = new Attendee(username1,password1,dateOfBirth,address,gender,null);
                break;
            case 3:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice!");
                main(args);
                break;
        }

    }
}