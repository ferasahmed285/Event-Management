import java.time.LocalDate;

public abstract class User {

    protected String username;
    protected String password;
    protected LocalDate dateOfBirth;
    protected Gender gender;

    public enum Gender {
        MALE, FEMALE, OTHER;
    }

    public User() {
    }

    public User(String username, String password, LocalDate dateOfBirth, Gender gender) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public boolean login(String username, String password) {
        return (this.username.equals(username) && this.password.equals(password));
    }

    public void logout() {
        System.out.println("You have logged out");
    }

    public void updateProfile(String newUsername, String newPassword, LocalDate newDateOfBirth, Gender newGender) {

        this.username = newUsername;
        this.password = newPassword;
        this.dateOfBirth = newDateOfBirth;
        this.gender = newGender;
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

    public Gender getGender() {
        return gender;
    }

}

