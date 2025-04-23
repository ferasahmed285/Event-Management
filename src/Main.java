//dummy data of room w event
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Event Management System");
        Database.initializeDummyData();
        System.out.println("=== Login ===");
        System.out.println("Enter username: ");
        String username = System.console().readLine();
        System.out.println("Enter password: ");
        String password = System.console().readLine();
        User user = (User) Database.getEntityByUsername(username);
        if (user != null) {
            if (user.login(username, password)) {
                user.displayDashboard();
            } else {
                System.out.println("Invalid username or password");
            }
        }
    }
}