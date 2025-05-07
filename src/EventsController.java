package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class EventsController {

    @FXML
    private Label MyEvents;

    @FXML
    private Button add;

    @FXML
    private Label category;

    @FXML
    private ComboBox<String> currentEvents;

    @FXML
    private Label date;

    @FXML
    private Button delete;

    @FXML
    private Label description;

    @FXML
    private Button edit;

    @FXML
    private Label eventDetails;

    @FXML
    private HBox hbox;

    @FXML
    private Label organizer;

    @FXML
    private Label price;

    @FXML
    private Label rooms;

    @FXML
    private Label title;

    @FXML
    private TextField txtCat;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextArea txtdesc;

    @FXML
    private TextField txtprice;

    @FXML
    private TextField txtrooms;

    @FXML
    private VBox vbox1;

    @FXML
    void addOn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddController.fxml"));
            Parent popupRoot = loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle("Add Event");
            popupStage.setScene(new Scene(popupRoot));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.showAndWait();
            currentEventsOn(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void currentEventsOn(ActionEvent event) {
        currentEvents.getItems().clear();

        for (Event e : Database.events) {
            currentEvents.getItems().add(e.getTitle());
        }
    }

    public void initialize() {
        currentEventsOn(null); // Fill ComboBox with events on load

        currentEvents.setOnAction(e -> {
            String selectedTitle = currentEvents.getValue();

            for (Event ev : Database.events) {
                if (ev.getTitle().equals(selectedTitle)) {
                    txtTitle.setText(ev.getTitle());
                    txtdesc.setText(ev.getDescription());
                    txtprice.setText(String.valueOf(ev.getPrice()));
                    txtCat.setText(ev.getCategory());
                    txtDate.setText(ev.getDateTime().toLocalDate().toString());
                    txtrooms.setText(ev.room.getName());
                    break;
                }
            }
        });
    }

    @FXML
    void deleteOn(ActionEvent event) {
        String selectedTitle = currentEvents.getValue();

        if (selectedTitle != null) {
            Event eventToDelete = null;

            for (Event e : Database.events) {
                if (e.getTitle().equals(selectedTitle)) {
                    eventToDelete = e;
                    break;
                }
            }

            if (eventToDelete != null) {
                Database.removeEntity(eventToDelete);
                currentEventsOn(null); // Refresh the ComboBox

                // Clear form fields
                txtTitle.clear();
                txtdesc.clear();
                txtprice.clear();
                txtCat.clear();
                txtDate.clear();
                txtrooms.clear();
            }
        }

    }

    @FXML
    void editOn(ActionEvent event) {
        try {
            String selectedTitle = currentEvents.getValue();
            if (selectedTitle == null)
                return;
    
            Event selectedEvent = null;
            for (Event ev : Database.events) {
                if (ev.getTitle().equals(selectedTitle)) {
                    selectedEvent = ev;
                    break;
                }
            }
    
            if (selectedEvent == null)
                return;
    
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditController.fxml"));
            Parent popupRoot = loader.load();
    
            // Get the controller instance and pass the event to it
            EditController controller = loader.getController();
            controller.SetText(selectedEvent); 
    
            Stage popupStage = new Stage();
            popupStage.setTitle("Edit Event");
            popupStage.setScene(new Scene(popupRoot));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.showAndWait();
    
            //currentEventsOn(null);
            currentEvents.setValue(null); // clear selection
            currentEvents.setValue(selectedTitle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

}
