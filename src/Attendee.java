//handle null error

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Attendee extends User {
    public List<String> interests;
    public Wallet wallet = new Wallet(100);

    public Attendee(String username, String password, LocalDate dateOfBirth, String address, Gender gender) {
        super(username, password, dateOfBirth, address, gender);
    }

    public void addInterests(List<String> interests) {
        this.interests.addAll(interests);
    }

//    public void browseEvents() {
//        for (Event event : Database.events) {
//            if (this.interests.isEmpty()) {
//                event.displaySummary();
//            }
//            else if (this.interests.getFirst().equals(event.getCategory())) {
//                event.displaySummary();
//            }
//        }
//    }


    public List<String> getInterests() {
        if (this.interests == null) {
            return Collections.singletonList("GENERAL");
        }
        else{
            return interests;
        }
    }

    public void buyTickets(Event event){
        if (this.wallet.getBalance() >= event.getPrice()) {
            this.wallet.transferToOrganizer(event.getPrice(), event.getTitle(), event.organizer );
            event.registerAttendee(this);
//            System.out.println("Ticket purchased successfully.");
        }
        else {
//            System.out.println("Insufficient funds.");
//            System.out.println("Please try again.");
        }
//        displayDashboard();
    }

    private void showAlert(String title, String message) {//ali
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private List<Event> getSortedEvents() {//ali
        List<Event> events = new ArrayList<>(Database.events);

        // Create two lists: matching interests and others
        List<Event> matchingInterests = new ArrayList<>();
        List<Event> otherEvents = new ArrayList<>();

        // Split events based on interests
        if (this.interests != null && !this.interests.isEmpty()) {
            for (Event event : events) {
                if (this.interests.contains(event.getCategory())) {
                    matchingInterests.add(event);
                } else {
                    otherEvents.add(event);
                }
            }
        } else {
            otherEvents = events; // If no interests, show all events
        }

        // Sorting each list by date
        matchingInterests.sort((e1, e2) -> e1.getDateTime().compareTo(e2.getDateTime()));
        otherEvents.sort((e1, e2) -> e1.getDateTime().compareTo(e2.getDateTime()));

        // Combining lists with matching interests first
        List<Event> sortedEvents = new ArrayList<>();
        sortedEvents.addAll(matchingInterests);
        sortedEvents.addAll(otherEvents);

        return sortedEvents;
    }

    public void viewEvents(Stage primaryStage) {//ali
        // Create a vertical box to hold everything
        VBox layout = new VBox(10); // 10 is spacing between items
        layout.setStyle("-fx-padding: 20;"); // Add some padding

        // Add a title
        Label titleLabel = new Label("Available Events");

        // Create a ListView to show events
        ListView<Event> eventsList = new ListView<>();
        eventsList.setCellFactory(param -> new ListCell<Event>() {
            @Override
            protected void updateItem(Event event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event == null) {
                    setGraphic(null);
                } else {
                    // Format event details
                    String eventInfo = "Title: " + event.getTitle() +
                            ", Category: " + event.getCategory() +
                            ", Date: " + event.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                            ", Price: $" + event.getPrice();
                    Label eventLabel = new Label(eventInfo);

                    // Create a Buy button for this event
                    Button buyButton = new Button("Buy");
                    buyButton.setOnAction(e -> {
                        // Check if attendee already bought a ticket
                        if (event.getAttendees().contains(Attendee.this)) {
                            showAlert("Error", "You already bought a ticket for this event.");
                            return;
                        }

                        // Check if attendee has enough money
                        if (Attendee.this.wallet.getBalance() < event.getPrice()) {
                            showAlert("Error", "You don't have enough money.");
                            return;
                        }

                        // Try to buy the ticket
                        Attendee.this.buyTickets(event);
                        showAlert("Success", "Ticket bought for " + event.getTitle() + "!");

                        // Refresh the list
                        eventsList.getItems().setAll(getSortedEvents());
                    });

                    // Put label and button in a horizontal box
                    HBox eventRow = new HBox(10, eventLabel, buyButton);
                    setGraphic(eventRow);
                }
            }
        });

// Get and sort events
        List<Event> sortedEvents = getSortedEvents();

// Add events to ListView
        eventsList.getItems().addAll(sortedEvents);

        // Add a Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> displayDashboard(primaryStage));

        // Add everything to the layout
        layout.getChildren().add(0, titleLabel); // Add title at the top
        layout.getChildren().add(1, eventsList); // Add list after title

        // Add Back button at the bottom
        layout.getChildren().add(backButton);

        // Create and return the Scene
        primaryStage.setScene(new Scene(layout, 600, 400));
    }

    public void refundTickets(Event event) {
        event.removeAttendee(this);
        this.wallet.refund( event,this);
    }

//    public void viewTickets() {
//        for (Event event : Database.events) {
//            if (event.getAttendees().contains(this)) {
//                event.displaySummary();
//            }
//        }
//    }
        private void myTickets(Stage primaryStage) {
        // Table setup
        TableView<Event> table = new TableView<>();

        // Columns
        TableColumn<Event, String> titleCol = new TableColumn<>("Event Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Event, String> dateCol = new TableColumn<>("Description");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Event, String> category = new TableColumn<>("Category");
        category.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Event, String> dateCol1 = new TableColumn<>("Date & Time");
        dateCol1.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        TableColumn<Event,String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Event, String> roomCol = new TableColumn<>("Room");
            roomCol.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().room.getName()));

        TableColumn<Event, String> organizerCol = new TableColumn<>("Organizer");
            organizerCol.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().organizer.getUsername()));

        TableColumn<Event, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(createButtonCellFactory());

        table.getColumns().addAll(titleCol, dateCol, category, dateCol1, priceCol, roomCol, organizerCol, actionCol);

        // Data loading
        ObservableList<Event> data = FXCollections.observableArrayList();
        for (Event event : Database.events) {
            if (event.getAttendees().contains(this)) {
                data.add(event);
            }
        }
        table.setItems(data);

        // Back button
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> displayDashboard(primaryStage));

        BorderPane root = new BorderPane();
        root.setCenter(table);
        HBox bottomBox = new HBox(backBtn);
        bottomBox.setPadding(new Insets(10));
        root.setBottom(bottomBox);

        primaryStage.setScene(new Scene(root, 800, 600));
    }

    private Callback<TableColumn<Event, Void>, TableCell<Event, Void>> createButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Event, Void> call(final TableColumn<Event, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Refund");

                    {
                        btn.setOnAction(e -> {
                            Event event = getTableView().getItems().get(getIndex());
                            refundTickets(event);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
    }
    Label addressLabel = new Label(this.address);
    ObservableList<String> interest = FXCollections.observableArrayList(this.getInterests());
    ListView<String> interestsListView = new ListView<>(interest);

    public void showProfileWindow(Stage parentStage) {
        Stage stage = new Stage(); // New window for profile page
        stage.initOwner(parentStage); // Set the main window as the owner

        interestsListView.setPrefHeight(100);

        HBox interestButtons = new HBox(10,
                createButton("Add", e -> addInterest()),
                createButton("Delete", e -> deleteSelectedInterest())
        );
        interestButtons.setPadding(new Insets(0, 0, 10, 0));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("Username: " + this.username),
                addressLabel,
                new Label("Interests:"),
                interestsListView,
                interestButtons,
                new Label("Balance: EGP " + this.wallet.getBalance()),
                createButton("Edit Password", e -> changePassword()),
                createButton("Edit Address", e -> editText("Address", addressLabel)),
                createButton("Add Funds (Fawry)", e -> addFunds())
        );

        stage.setScene(new Scene(layout, 360, 520));
        stage.setTitle("Attendee Profile");
        stage.show();
    }

    Button createButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button btn = new Button(text);
        btn.setOnAction(action);
        return btn;
    }

    void editText(String field, Label label) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit " + field);
        dialog.setContentText("New " + field + ":");
        dialog.showAndWait().ifPresent(input -> {
            if (input.trim().isEmpty()) {
                showAlert("Error", field + " cannot be blank.", Alert.AlertType.ERROR);
            } else {
                label.setText(field + ": " + input);
            }
        });
    }

    void addInterest() {
        // Create a list to hold category names for the drop-down menu
        ObservableList<String> categoryNames = FXCollections.observableArrayList();

        // Iterate through the categories in Database.categories and add each category's name to the list
        for (Category category : Database.categories) {
            if (!interest.contains(category.getCategoryname())) {
                categoryNames.add(category.getCategoryname()); // Get category name
            }
        }

        // Show a ChoiceDialog with the list of categories as available interests
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, categoryNames);
        dialog.setTitle("Add Interest");
        dialog.setHeaderText("Select an interest");
        dialog.setContentText("Choose an interest:");

        dialog.showAndWait().ifPresent(input -> {
            if (input == null || input.trim().isEmpty()) {
                showAlert("Error", "Interest cannot be blank.", Alert.AlertType.ERROR);
            } else {
                interest.add(input.trim());
            }
        });
    }

    void deleteSelectedInterest() {
        String selected = interestsListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select an interest to delete.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete \"" + selected + "\"?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                interest.remove(selected);
            }
        });
    }

    void changePassword() {
        PasswordField oldPass = new PasswordField();
        PasswordField newPass = new PasswordField();
        VBox box = new VBox(10, new Label("Old Password:"), oldPass, new Label("New Password:"), newPass);
        box.setPadding(new Insets(10));

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Change Password");
        dialog.getDialogPane().setContent(box);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                if (oldPass.getText().isEmpty() || newPass.getText().isEmpty())
                    showAlert("Error", "Password fields cannot be empty.", Alert.AlertType.ERROR);
            }
            return null;
        });
        dialog.showAndWait();
    }

    void addFunds() {
        int fawryCode = 100000 + (int) (Math.random() * 900000);

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Fawry Payment Instructions");
        info.setHeaderText("Visit the nearest Fawry stand");
        info.setContentText("Give this code to the Fawry agent: " + fawryCode);
        info.showAndWait();
    }

    void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addFunds(int amount){
        this.wallet.addFunds(amount);
    }

    @Override
    public void displayDashboard() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Welcome " + this.username + "!");
//        System.out.println("1. Browse Events");
//        System.out.println("2. Buy Tickets");
//        System.out.println("3. Refund Tickets");
//        System.out.println("4. View Profile");
//        System.out.println("5. Add Funds");
//        System.out.println("6. Add Interest");
//        System.out.println("7. Remove All Interests");
//        System.out.println("8. Update Password");
//        System.out.println("9. Logout");
//        System.out.print("Enter your choice: ");
//        int choice = Integer.parseInt(scanner.nextLine());
//        switch (choice) {
//            case 1:
//                browseEvents();
//                displayDashboard();
//                break;
//            case 2:
//                System.out.println("Choose an event to buy tickets:");
//                browseEvents();
//                System.out.print("Enter event Name: ");
//                String eventName = scanner.nextLine();
//                Event event = (Event) Database.getEntityByUsername(eventName);
//                assert event != null;
//                buyTickets(event);
//                break;
//            case 3:
//                System.out.println("Choose an event to refund tickets:");
//                for (Event event1 : Database.events) {
//                    if (event1.getCategory().equals(this.interests.getFirst())) {
//                        event1.displaySummary();
//                    }
//                }
//                System.out.print("Enter event Name: ");
//                String eventName1 = scanner.nextLine();
//                Event event1 = (Event) Database.getEntityByUsername(eventName1);
//                assert event1 != null;
//                refundTickets(event1);
//                break;
//            case 4:
//                System.out.println("Profile Info:");
//                System.out.println("Username: " + username);
//                System.out.println("Date of Birth: " + dateOfBirth);
//                System.out.println("Address: " + address);
//                System.out.println("Gender: " + gender);
//                System.out.println("Wallet Balance: " + wallet.getBalance());
//                System.out.println("Interests: " + interests);
//                System.out.println("Tickets: ");
//                viewTickets();
//                break;
//            case 5:
//                System.out.print("Enter amount to add: ");
//                int amount = Integer.parseInt(scanner.nextLine());
//                addFunds(amount);
//                break;
//            case 6:
//                System.out.print("Enter interest to add: ");
//                String interest = scanner.nextLine();
//                addInterests(List.of(interest));
//                break;
//            case 7:
//                this.interests.clear();
//                break;
//            case 8:
//                System.out.println("Update Password");
//                System.out.print("Enter new password: ");
//                String newPassword = scanner.nextLine();
//                updatePassword(newPassword);
//                break;
//            case 9:
//                this.logout();
//                break;
//            default:
//                System.out.println("Invalid choice!");
//                displayDashboard();
//                break;
//        }
    }
    public void displayDashboard(Stage primaryStage) {
        VBox attendeePane = new VBox(10);
        attendeePane.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label attendeeLabel = new Label("Attendee Dashboard");
        Button viewEventsButton = new Button("View Events");
        Button myTicketsButton = new Button("See My Tickets");
        Button profileButton = new Button("View Profile");
        Button logoutButton = new Button("Logout");
        viewEventsButton.setOnAction(e -> viewEvents(primaryStage));
        myTicketsButton.setOnAction(e -> myTickets(primaryStage));
        profileButton.setOnAction(e -> showProfileWindow(primaryStage));
        logoutButton.setOnAction(e -> primaryStage.setScene(LoginRegisterSystem.loginScene));
        attendeePane.getChildren().addAll(attendeeLabel, viewEventsButton, myTicketsButton, profileButton, logoutButton);
        primaryStage.setScene(new Scene(attendeePane, 500, 400));
    }
}