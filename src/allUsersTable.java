import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;

public class allUsersTable {

    private void showUserTableScene(Stage stage) {
        TableView<User> table = new TableView<>();

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(data ->
            new ReadOnlyStringWrapper(data.getValue().getUsername())
        );

        TableColumn<User, String> dobCol = new TableColumn<>("Date of Birth");
        dobCol.setCellValueFactory(data ->
            new ReadOnlyStringWrapper(data.getValue().getDateOfBirth().toString())
        );

        TableColumn<User, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(data ->
            new ReadOnlyStringWrapper(data.getValue().getAddress())
        );

        TableColumn<User, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(data ->
            new ReadOnlyStringWrapper(data.getValue().getGender().toString())
        );

        table.getColumns().addAll(usernameCol, dobCol, addressCol, genderCol);

        // Differentiated columns
        TableColumn<User, String> organizerCol = new TableColumn<>("Type");
        organizerCol.setCellValueFactory(data -> {
            if (data.getValue() instanceof Organizer) {
                return new ReadOnlyStringWrapper("Organizer");
            }
            return new ReadOnlyStringWrapper("");
        });

        TableColumn<User, String> interestsCol = new TableColumn<>("Interests");
        interestsCol.setCellValueFactory(data -> {
            if (data.getValue() instanceof Attendee) {
                Attendee a = (Attendee) data.getValue();
                return new ReadOnlyStringWrapper(String.join(", ", a.interests));
            }
            return new ReadOnlyStringWrapper("");
        });

        TableColumn<User, String> isAttendeeCol = new TableColumn<>("Is Attendee");
        isAttendeeCol.setCellValueFactory(data -> {
            if (data.getValue() instanceof Attendee) {
                return new ReadOnlyStringWrapper("Yes");
            }
            return new ReadOnlyStringWrapper("");
        });

        table.getColumns().addAll(organizerCol, interestsCol, isAttendeeCol);

        table.setItems(FXCollections.observableArrayList(Database.users));

        VBox layout = new VBox(10, table);
        layout.setStyle("-fx-padding: 20");
        Scene tableScene = new Scene(layout, 800, 400);
        stage.setScene(tableScene);
    }
}