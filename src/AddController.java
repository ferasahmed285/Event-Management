import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    @FXML
    private Label addEvent, category, date, description, enter, organizer, price, rooms, title;

    @FXML
    private ComboBox<String> selectCategory;

    @FXML
    private ComboBox<String> selectRooms;

    @FXML
    private DatePicker selectDate;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtTitle, txtprice;

    @FXML
    private Button submit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Populate category ComboBox with category names
        ObservableList<String> categoryNames = FXCollections.observableArrayList();
        for (Category cat : Database.categories) {
            categoryNames.add(cat.getCategoryname());
        }
        selectCategory.setItems(categoryNames);

        // Populate room ComboBox with room names
        ObservableList<String> roomNames = FXCollections.observableArrayList();
        for (Room room : Database.rooms) {
            roomNames.add(room.getName());
        }
        selectRooms.setItems(roomNames);

        // Listener to check if all required fields are filled
        Runnable checkInputs = () -> {
            boolean allFilled = !txtTitle.getText().isEmpty() &&
                    !txtDescription.getText().isEmpty() &&
                    !txtprice.getText().isEmpty() &&
                    selectDate.getValue() != null &&
                    selectCategory.getValue() != null &&
                    selectRooms.getValue() != null;

            submit.setDisable(!allFilled); // enable if all inputs are filled
        };

        // Add listeners to trigger the check
        txtTitle.textProperty().addListener((obs, oldVal, newVal) -> checkInputs.run());
        txtDescription.textProperty().addListener((obs, oldVal, newVal) -> checkInputs.run());
        txtprice.textProperty().addListener((obs, oldVal, newVal) -> checkInputs.run());
        selectDate.valueProperty().addListener((obs, oldVal, newVal) -> checkInputs.run());
        selectCategory.valueProperty().addListener((obs, oldVal, newVal) -> checkInputs.run());
        selectRooms.valueProperty().addListener((obs, oldVal, newVal) -> checkInputs.run());
    }

    @FXML
    void selectCategoryOn(ActionEvent event) {
        String selected = selectCategory.getValue();

    }

    @FXML
    void selectRoomsOn(ActionEvent event) {
        String selected = selectRooms.getValue();

    }

    @FXML
    void submitOn(ActionEvent event) {

        try {
            String title = txtTitle.getText();
            String description = txtDescription.getText();
            double price = Double.parseDouble(txtprice.getText());

            LocalDate date = selectDate.getValue();
            LocalTime time = LocalTime.of(10, 0); // fixed time
            LocalDateTime dateTime = LocalDateTime.of(date, time);

            String categoryName = selectCategory.getValue();
            String roomName = selectRooms.getValue();

            // Find selected room
            Room selectedRoom = null;
            for (Room room : Database.rooms) {
                if (room.getName().equalsIgnoreCase(roomName)) {
                    selectedRoom = room;
                    break;
                }
            }

            if (selectedRoom == null) {
                
                return;
            }

            // Find existing organizer from dummy data
            Organizer existingOrganizer = null;
            for (User user : Database.users) {
                if (user instanceof Organizer && user.getUsername().equals("organizer1")) {
                    existingOrganizer = (Organizer) user;
                    break;
                }
            }

            if (existingOrganizer == null) {
                
                return;
            }

            // Create and add event using existing organizer
            Event newEvent = new Event(title, description, dateTime, price, categoryName, selectedRoom, existingOrganizer);
           

            System.out.println("Event created: " + newEvent.getTitle());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

}
