import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrganizerDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
//        // Create a dummy Organizer instance (inject your own as needed)
//        Organizer organizer = new Organizer("Organizer's name");
//
//        // Start page: only a button to open the menu
//        Button openMenuBtn = new Button("Open Organizer Menu");
//        openMenuBtn.setOnAction(e -> showMenuScene(primaryStage, organizer));
//
//        VBox startRoot = new VBox(openMenuBtn);
//        startRoot.setAlignment(Pos.CENTER);
//        startRoot.setPadding(new Insets(20));
//        startRoot.setSpacing(10);
//
//        Scene startScene = new Scene(startRoot, 300, 200);
//        primaryStage.setTitle("Welcome");
//        primaryStage.setScene(startScene);
//        primaryStage.show();
    }

    /**
     * Builds and shows the menu scene with options for the given organizer.
     */
    private void showOrganizerMenuScene(Stage stage, Organizer organizer) {
        // Welcome label with dynamic organizer name
        Label welcomeLabel = new Label("Welcome, " + organizer.getUsername());
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Menu buttons
        Button eventsButton = new Button("1 - Events");
        Button profileButton = new Button("2 - Profile");
        Button chatButton = new Button("3 - Chat with Admin");
        Button logoutButton = new Button("4 - Logout");

        // Uniform button sizing
        eventsButton.setMaxWidth(Double.MAX_VALUE);
        profileButton.setMaxWidth(Double.MAX_VALUE);
        chatButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        // Hook up to scene-change methods (implement these separately)
        eventsButton.setOnAction(e -> showEventsScene(stage));
        profileButton.setOnAction(e -> showProfileScene(stage));
        chatButton.setOnAction(e -> showChatScene(stage));
        logoutButton.setOnAction(e -> performLogout(stage));

        VBox menuRoot = new VBox(10,
                                  welcomeLabel,
                                  eventsButton,
                                  profileButton,
                                  chatButton,
                                  logoutButton);
        menuRoot.setAlignment(Pos.CENTER);
        menuRoot.setPadding(new Insets(20));
        menuRoot.setPrefWidth(300);

        Scene menuScene = new Scene(menuRoot);
        stage.setTitle("Organizer Dashboard");
        stage.setScene(menuScene);
    }

    // Placeholder methods for actual scene transitions
    private void showEventsScene(Stage stage) {
        // TODO: switch to Events scene
    }

    private void showProfileScene(Stage stage) {
        // TODO: switch to Profile scene
    }

    private void showChatScene(Stage stage) {
        // TODO: switch to Chat with Admin scene
    }

    private void performLogout(Stage stage) {
        // TODO: handle logout (e.g., return to login/start scene)
    }

    public static void main(String[] args) {
        launch(args);
    }
}
