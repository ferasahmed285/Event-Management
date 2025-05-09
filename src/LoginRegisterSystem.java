import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class LoginRegisterSystem extends Application {//hamza

    public static Scene loginScene;
    public Scene registerScene;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Database.initializeDummyData();

        createLoginScene();
        createRegisterScene();

        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Event Management System");
        primaryStage.show();
    }

    public void createLoginScene() {
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

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        ComboBox<String> genderCombo = new ComboBox<>();
        genderCombo.setPromptText("Select Gender");
        genderCombo.setItems(FXCollections.observableArrayList(
                "MALE", "FEMALE", "OTHER"
        ));

        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.setPromptText("Select Role");
        roleCombo.setItems(FXCollections.observableArrayList("ORGANIZER", "ATTENDEE")); // Only these two roles

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
        grid.add(new Label("Address:"), 0, 4);
        grid.add(addressField, 1, 4);
        grid.add(new Label("Gender:"), 0, 5);
        grid.add(genderCombo, 1, 5);
        grid.add(new Label("Role:"), 0, 6);
        grid.add(roleCombo, 1, 6);

        HBox buttonBox = new HBox(20, backButton, registerButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 0, 7, 2, 1);

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

            if (addressField.getText().isEmpty()) {
                showAlert("Error", "Address cannot be empty");
                return;
            }

            if (genderCombo.getValue() == null) {
                showAlert("Error", "Please select a gender");
                return;
            }

            if (roleCombo.getValue() == null) {
                showAlert("Error", "Please select a role");
                return;
            }

            // Create user based on selected role
            LocalDate dob = LocalDate.now();
            String address = addressField.getText();
            User.Gender gender = User.Gender.valueOf(genderCombo.getValue());

            switch (roleCombo.getValue()) {
                case "ORGANIZER":
                    new Organizer(
                            usernameField.getText(),
                            passwordField.getText(),
                            dob, address, gender
                    );
                    break;
                case "ATTENDEE":
                    new Attendee(
                            usernameField.getText(),
                            passwordField.getText(),
                            dob, address, gender
                    );
                    break;
            }

            showAlert("Success", "Account created successfully!");
            primaryStage.setScene(loginScene);
        });

        backButton.setOnAction(e -> primaryStage.setScene(loginScene));

        registerScene = new Scene(grid, 500, 450);
    }

    private void openUserDashboard(User user) {
        // This would open different dashboards based on user type
        if (user instanceof Admin) {
            ((Admin) user).displayDashboard(primaryStage);
        } else if (user instanceof Organizer) {
            ((Organizer) user).displayDashboard(primaryStage);
        } else if (user instanceof Attendee) {
            ((Attendee) user).displayDashboard(primaryStage);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}