import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import java.time.LocalDate;

public class LoginRegisterSystem extends Application {//hamza

    private Stage primaryStage;
    private Scene loginScene, registerScene;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Database.initializeDummyData(); // Initialize with your dummy data
        
        createLoginScene();
        createRegisterScene();
        
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Event Management System");
        primaryStage.show();
    }

    private void createLoginScene() {
        // Login Form Components
        Label titleLabel = new Label("Event Management Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-font-weight: bold;");
        
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");

        // Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(25));
        
        grid.add(titleLabel, 0, 0, 2, 1);
        grid.add(new Label("Username:"), 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(passwordField, 1, 2);
        
        HBox buttonBox = new HBox(20, registerButton, loginButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 0, 3, 2, 1);

        // Event Handlers
        loginButton.setOnAction(e -> {
            User user = (User) Database.getEntityByUsername(usernameField.getText());
            if (user != null && user.getPassword().equals(passwordField.getText())) {
                showAlert("Login Successful", "Welcome " + user.getUsername() + "!");
                // Here you would open the appropriate dashboard based on user type
                openUserDashboard(user);
            } else {
                showAlert("Login Failed", "Invalid username or password");
            }
        });
        
        registerButton.setOnAction(e -> primaryStage.setScene(registerScene));
        
        loginScene = new Scene(grid, 500, 400);
    }

    private void createRegisterScene() {
        // Registration Form Components
        Label titleLabel = new Label("Create New Account");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        PasswordField confirmField = new PasswordField();
        confirmField.setPromptText("Confirm Password");
        
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.setPromptText("Select Role");
        roleCombo.setItems(FXCollections.observableArrayList("ADMIN", "ORGANIZER", "ATTENDEE"));
        
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-font-weight: bold;");
        
        Button backButton = new Button("Back to Login");
        backButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white;");

        // Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));
        
        grid.add(titleLabel, 0, 0, 2, 1);
        grid.add(new Label("Username:"), 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(new Label("Confirm:"), 0, 3);
        grid.add(confirmField, 1, 3);
        grid.add(new Label("Role:"), 0, 4);
        grid.add(roleCombo, 1, 4);
        
        HBox buttonBox = new HBox(20, backButton, registerButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 0, 5, 2, 1);

        // Event Handlers
        registerButton.setOnAction(e -> {
            if (!passwordField.getText().equals(confirmField.getText())) {
                showAlert("Error", "Passwords don't match");
                return;
            }
            
            if (Database.getEntityByUsername(usernameField.getText()) != null) {
                showAlert("Error", "Username already exists");
                return;
            }
            
            // Create user based on selected role
            LocalDate dob = LocalDate.now(); // Default - add date picker in real implementation
            String address = ""; // Default
            User.Gender gender = User.Gender.OTHER; // Default
            
            switch(roleCombo.getValue()) {
                case "ADMIN":
                    Database.addEntity(new Admin(
                        usernameField.getText(),
                        passwordField.getText(),
                        dob, address, gender,
                        "System Admin", "9-5" // Default admin properties
                    ));
                    break;
                case "ORGANIZER":
                    Database.addEntity(new Organizer(
                        usernameField.getText(),
                        passwordField.getText(),
                        dob, address, gender
                    ));
                    break;
                case "ATTENDEE":
                    Database.addEntity(new Attendee(
                        usernameField.getText(),
                        passwordField.getText(),
                        dob, address, gender
                    ));
                    break;
            }
            
            showAlert("Success", "Account created successfully!");
            primaryStage.setScene(loginScene);
        });
        
        backButton.setOnAction(e -> primaryStage.setScene(loginScene));
        
        registerScene = new Scene(grid, 500, 400);
    }

    private void openUserDashboard(User user) {
        // This would open different dashboards based on user type
        if (user instanceof Admin) {
            ((Admin)user).displayDashboard();
        } else if (user instanceof Organizer) {
            ((Organizer)user).displayDashboard();
        } else if (user instanceof Attendee) {
            ((Attendee)user).displayDashboard();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}