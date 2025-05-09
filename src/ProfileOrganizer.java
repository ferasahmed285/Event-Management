
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfileOrganizer {

    private Organizer organizer;
    private Stage primaryStage;
    private String originalAddress; // To track changes

    @FXML
    private Label MyProfile;
    @FXML
    private Label address;
    @FXML
    private Button back;
    @FXML
    private Label balance;
    @FXML
    private Label dateOfBirth;

    @FXML
    private Button editPassword;
    @FXML
    private Label gender;
    @FXML
    private HBox hbox;
    @FXML
    private Button save;

    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtBalance;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtUsername;
    @FXML
    private Label username;
    @FXML
    private VBox vbox1;
    @FXML
    private VBox vbox2;
    @FXML
    private Button withdraw;

    public void setData(Organizer organizer, Stage primaryStage) {
        this.organizer = organizer;
        this.primaryStage = primaryStage;
        displayOrganizerData();
    }

    private void displayOrganizerData() {
        if (organizer != null) {
            txtUsername.setText(organizer.getUsername());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            txtDate.setText(organizer.getDateOfBirth().format(formatter));

            originalAddress = organizer.getAddress();
            txtAddress.setText(originalAddress);

            txtGender.setText(organizer.getGender().toString());

            if (organizer.wallet != null) {
                txtBalance.setText(String.format("EGP %.2f", organizer.wallet.getBalance()));
            } else {
                txtBalance.setText("EGP 0.00");
            }

            // Make address field editable by default
            setAddressFieldEditable(true);
            save.setDisable(true); // Save starts disabled

            // Listener for address changes
            txtAddress.textProperty().addListener((obs, oldVal, newVal) -> {
                save.setDisable(newVal.equals(originalAddress));
            });
        }
    }

    private void setAddressFieldEditable(boolean editable) {
        txtAddress.setEditable(editable);
        String editableStyle = "-fx-background-color: white; -fx-border-color: #ccc;";
        String nonEditableStyle = "-fx-background-color: #f0f0f0; -fx-border-color: transparent;";
        txtAddress.setStyle(editable ? editableStyle : nonEditableStyle);
    }

    @FXML
    void saveOn(ActionEvent event) {
        String newAddress = txtAddress.getText().trim();
        if (!newAddress.isEmpty() && organizer != null) {
            organizer.setAddress(newAddress);
            originalAddress = newAddress; // Update the original address
            save.setDisable(true); // Disable save after successful save
            showAlert("Success", "Address updated successfully", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    void backOn(ActionEvent event) {
        if (organizer != null) {
            organizer.displayDashboard(primaryStage);
        }
    }

    @FXML
    void editPasswordOn(ActionEvent event) {
        changePassword();
    }

    @FXML
    void withdrawOn(ActionEvent event) {
        withdrawFunds();
    }

    private void changePassword() {
        if (organizer == null)
            return;

        PasswordField oldPass = new PasswordField();
        PasswordField newPass = new PasswordField();

        VBox box = new VBox(10,
                new Label("Old Password:"), oldPass,
                new Label("New Password:"), newPass);
        box.setPadding(new Insets(10));

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Change Password");
        dialog.getDialogPane().setContent(box);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                if (oldPass.getText().isEmpty() || newPass.getText().isEmpty()) {
                    showAlert("Error", "Password fields cannot be empty.", Alert.AlertType.ERROR);
                } else if (!organizer.getPassword().equals(oldPass.getText())) {
                    showAlert("Error", "Old password is incorrect.", Alert.AlertType.ERROR);
                } else {
                    organizer.setPassword(newPass.getText());
                    showAlert("Success", "Password changed successfully.", Alert.AlertType.INFORMATION);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    void withdrawFunds() {
        if (organizer == null || organizer.wallet == null) {
            showAlert("Error", "Wallet not available", Alert.AlertType.ERROR);
            return;
        }

        int fawryCode = 100000 + (int) (Math.random() * 900000);

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Fawry Payment Instructions");
        info.setHeaderText("Visit the nearest Fawry stand");
        info.setContentText("Give this code to the Fawry agent: " + fawryCode);
        info.showAndWait();
    }

    public void addFunds(int amount) {
        if (organizer != null && organizer.wallet != null) {
            organizer.wallet.addFunds(amount);
            displayOrganizerData(); // Refresh the display
        }
    }
}
