import java.time.LocalDate;

public abstract class User {

    protected String username;
    protected String password;
    protected LocalDate dateOfBirth;
    protected String address;
    protected Gender gender;

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public User(String username, String password, LocalDate dateOfBirth, String address, Gender gender) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        Database.addEntity(this);
    }

    public boolean login(String username, String password) {
        return (this.username.equals(username) && this.password.equals(password));
    }

    public void logout() {
        System.out.println("You have logged out");
        Main main;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public abstract void displayDashboard();

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public Gender getGender() {
        return gender;
    }

}