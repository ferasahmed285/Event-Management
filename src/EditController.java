// package gui;

// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.net.URL;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.LocalTime;
// import java.util.ArrayList;
// import java.util.ResourceBundle;
// import gui.User.Gender;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.Initializable;
// import javafx.scene.control.*;
// import javafx.stage.Stage;
// import javafx.scene.Node;

// public class EditController {

//     @FXML
//     private ComboBox<String> selectCategory;

//     @FXML
//     private DatePicker selectDate;

//     @FXML
//     private ComboBox<String> selectRooms;

//     @FXML
//     private Button submit;

//     @FXML
//     private TextArea txtDescription;

//     @FXML
//     private TextField txtTitle;

//     @FXML
//     private TextField txtprice;

//     @FXML
//     private Label organizer;

//    /*  private Event eventToEdit; // Reference to the event being edited

//     // Method to receive the selected event from EventsController
//     public void setEventToEdit(Event event) {
//         this.eventToEdit = event;

//         // Populate fields with existing data
//         txtTitle.setText(event.getTitle());
//         txtDescription.setText(event.getDescription());
//         txtprice.setText(String.valueOf(event.getPrice()));
//         selectDate.setValue(event.getDateTime().toLocalDate());
//         organizer.setText(event.organizer.getUsername());

//         // Fill category dropdown
//         selectCategory.getItems().clear();
//         for (Category cat : Database.categories) {
//             selectCategory.getItems().add(cat.getName());
//         }
//         selectCategory.setValue(event.getCategory());

//         // Fill room dropdown
//         selectRooms.getItems().clear();
//         for (Room room : Database.rooms) {
//             selectRooms.getItems().add(room.getName());
//         }
//         selectRooms.setValue(event.room.getName());
//     } */

//     @FXML
//     void submitOn(ActionEvent event) {
//         /* if (eventToEdit == null)
//             return;

//         // Read new values from input fields
//         String newTitle = txtTitle.getText();
//         String newDescription = txtDescription.getText();
//         double newPrice = Double.parseDouble(txtprice.getText());
//         LocalDate selectedDate = selectDate.getValue();
//         String selectedCategory = selectCategory.getValue();
//         String selectedRoomName = selectRooms.getValue();

//         Room selectedRoom = null;
//         for (Room room : Database.rooms) {
//             if (room.getName().equals(selectedRoomName)) {
//                 selectedRoom = room;
//                 break;
//             }
//         }

//         // Update event properties
//         eventToEdit.setTitle(newTitle);
//         eventToEdit.setDescription(newDescription);
//         eventToEdit.setPrice(newPrice);
//         eventToEdit.setCategory(selectedCategory);
//         eventToEdit.setDateTime(LocalDateTime.of(selectedDate, eventToEdit.getDateTime().toLocalTime()));
//         eventToEdit.setRoom(selectedRoom);

//         // Close the window
//         ((Stage) submit.getScene().getWindow()).close();*/ 
//     }
// }

package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditController implements Initializable{

    @FXML
    private Label category;

    @FXML
    private Label date;

    @FXML
    private Label description;

    @FXML
    private Label editEvent;

    @FXML
    private Label enter;

    @FXML
    private Label organizer;

    @FXML
    private Label price;

    @FXML
    private Label rooms;

    @FXML
    private ComboBox<String> selectCategory;

    @FXML
    private DatePicker selectDate;

    @FXML
    private ComboBox<String> selectRooms;

    @FXML
    private Button submit;

    @FXML
    private Label title;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtprice;

    @FXML
    private VBox vbox1;

    @FXML
    private VBox vbox2;

    @FXML
    private VBox vbox3;

    @FXML
    private VBox vbox4;

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
    private Event eventToEdit;


    public void SetText(Event ev) {
        this.eventToEdit = ev;
                    txtTitle.setText(ev.getTitle());
                    txtDescription.setText(ev.getDescription());
                    txtprice.setText(String.valueOf(ev.getPrice()));

    // If selectDate is a DatePicker:
    selectDate.setValue(ev.getDateTime().toLocalDate());

    // If using ComboBoxes:
    selectCategory.setValue(ev.getCategory());
    selectRooms.setValue(ev.getRoom());

                }

    // private Event eventToEdit; // Reference to the event being edited

    // // Method to receive the selected event from EventsController
    // public void setEventToEdit(Event event) {
    // this.eventToEdit = event;

    // // Populate fields with existing data
    // txtTitle.setText(event.getTitle());
    // txtDescription.setText(event.getDescription());
    // txtprice.setText(String.valueOf(event.getPrice()));
    // selectDate.setValue(event.getDateTime().toLocalDate());
    // organizer.setText(event.organizer.getUsername());

    // // Fill category dropdown
    // selectCategory.getItems().clear();
    // for (Category cat : Database.categories) {
    // selectCategory.getItems().add(cat.getName());
    // }
    // selectCategory.setValue(event.getCategory());

    // // Fill room dropdown
    // selectRooms.getItems().clear();
    // for (Room room : Database.rooms) {
    // selectRooms.getItems().add(room.getName());
    // }
    // selectRooms.setValue(event.room.getName());
    // }

    @FXML
    void submitOn(ActionEvent event) {
        // Read new values from input fields
        String newTitle = txtTitle.getText();
        String newDescription = txtDescription.getText();
        double newPrice = Double.parseDouble(txtprice.getText());
        LocalDate selectedDate = selectDate.getValue();
        String selectedCategory = selectCategory.getValue();
        String selectedRoomName = selectRooms.getValue();  // ComboBox<String>
        Room selectedRoom = null;
        for (Room room : Database.rooms) {
            if (room.getName().equals(selectedRoomName)) {
                selectedRoom = room;
                break;
            }
        }
        // Update event properties
        eventToEdit.setTitle(newTitle);
        eventToEdit.setDescription(newDescription);
        eventToEdit.setPrice(newPrice);
        eventToEdit.setCategory(selectedCategory);
        eventToEdit.setDateTime(LocalDateTime.of(selectedDate, eventToEdit.getDateTime().toLocalTime()));
        eventToEdit.setRoom(selectedRoom);
        
        // Close the window
        ((Stage) submit.getScene().getWindow()).close();
    }
    

}
