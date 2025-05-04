import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrganizerDashboard  {

    private void showOrganizerMenuScene(Stage stage, Organizer organizer) {
        Label welcomeLabel = new Label("Welcome, " + organizer.getUsername());
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button eventsButton = new Button("1 - Events");
        Button profileButton = new Button("2 - Profile");
        Button chatButton = new Button("3 - Chat with Admin");
        Button logoutButton = new Button("4 - Logout");

        eventsButton.setMaxWidth(Double.MAX_VALUE);
        profileButton.setMaxWidth(Double.MAX_VALUE);
        chatButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setMaxWidth(Double.MAX_VALUE);

//        eventsButton.setOnAction(e -> showEventsScene(stage));
//        profileButton.setOnAction(e -> showProfileScene(stage));
//        chatButton.setOnAction(e -> showChatScene(stage));
//        logoutButton.setOnAction(e -> performLogout(stage));

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
}