
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    // FXML components
    @FXML
    private ComboBox<String> currentEvents;
    @FXML
    private TextField txtTitle, txtprice, txtCat, txtDate, txtrooms;
    @FXML
    private TextArea txtdesc;

    private Organizer currentOrganizer;
    private Stage primaryStage;

    // Correct setData method with only 2 parameters
    public void setData(Organizer organizer, Stage stage) {
        this.currentOrganizer = organizer;
        this.primaryStage = stage;
        loadOrganizerEvents();
    }

    private void loadOrganizerEvents() {
        currentEvents.getItems().clear();

        if (Database.events != null) {
            for (Event event : Database.events) {
                if (event.getOrganizer().equals(currentOrganizer)) {
                    currentEvents.getItems().add(event.getTitle());
                }
            }
        }
    }

    private void showSelectedEventDetails() {
        String selectedTitle = currentEvents.getValue();
        if (selectedTitle != null) {
            Database.events.stream()
                    .filter(e -> e.getTitle().equals(selectedTitle) &&
                            e.getOrganizer().equals(currentOrganizer))
                    .findFirst()
                    .ifPresent(this::displayEventDetails);
        }
    }

    private void displayEventDetails(Event event) {
        txtTitle.setText(event.getTitle());
        txtdesc.setText(event.getDescription());
        txtprice.setText(String.format("%.2f", event.getPrice()));
        txtCat.setText(event.getCategory());
        txtDate.setText(event.getDateTime().toLocalDate().toString());
        txtrooms.setText(event.getRoom().getName());
    }

    @FXML
    private void backOnAction() {
        currentOrganizer.displayDashboard(primaryStage);
    }

    @FXML
    void addOn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddController.fxml"));
            Parent popupRoot = loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle("Add Event");
            popupStage.setScene(new Scene(popupRoot));
            popupStage.initModality(Modality.APPLICATION_MODAL);

            
            AddController controller = loader.getController();
            controller.setCurrentOrganizer(currentOrganizer);

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

            // currentEventsOn(null);
            currentEvents.setValue(null); // clear selection
            currentEvents.setValue(selectedTitle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backOn(ActionEvent event) {
        currentOrganizer.displayDashboard(primaryStage);

    }

}
