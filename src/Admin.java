import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    private String role;
    private String workingHours;

    public Admin(String username, String password, LocalDate dateOfBirth, String address, Gender gender, String role, String workingHours) {
        super(username, password, dateOfBirth, address, gender);
        this.role = role;
        this.workingHours = workingHours;
    }

@Override
    public void displayDashboard() {
        System.out.println("=== Admin Dashboard ===");
        System.out.println("Username: " + username);
        System.out.println("Role: " + role);
        System.out.println("Working Hours: " + workingHours);
        System.out.println("1. View All Rooms");
        System.out.println("2. View All Events");
        System.out.println("3. View All Attendees");
        System.out.println("4. Create/Update/Delete Categories");
        System.out.println("5. Ban Users");
        System.out.println("6. Generate Reports");
        System.out.println("----------------------------\n");
    }

    public void displayDashboard(Stage primaryStage) {
        VBox adminPane = new VBox(10);
        adminPane.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label adminLabel = new Label("Welcome, " + this   .getUsername());
        adminLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Button categoryButton = new Button("Manage Categories");
        Button roomButton = new Button("Manage Rooms");
        Button eventButton = new Button("View All Events");
        Button usersButton = new Button("View All Users");
        Button logoutButton = new Button("Logout");
        categoryButton.setMaxWidth(Double.MAX_VALUE);
        roomButton.setMaxWidth(Double.MAX_VALUE);
        eventButton.setMaxWidth(Double.MAX_VALUE);
        usersButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        categoryButton.setOnAction(e -> openCategoryManager(primaryStage));
        roomButton.setOnAction(e -> openRoomManager(primaryStage));
        eventButton.setOnAction(e -> showAllEvents(primaryStage));
        usersButton.setOnAction(e -> showAllUsers(primaryStage));
        logoutButton.setOnAction(e -> primaryStage.setScene(LoginRegisterSystem.loginScene));
        adminPane.getChildren().addAll(adminLabel, categoryButton, roomButton, eventButton, usersButton, logoutButton);
        VBox layout = new VBox(10, adminPane);
        layout.setStyle("-fx-padding: 20");
        layout.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(layout, 400, 300));
    }

    public void addRoom(Room room) {
        Database.rooms.add(room);
        System.out.println("Room added successfully");
    }

    public void addEvent(Event e) {
        Database.events.add(e);
        System.out.println("Event added successfully");
    }

    public void addAttendee(User user) {
        Database.users.add(user);
        System.out.println("User added successfully");
    }

    public void showAllRooms() {
        System.out.println("=== All Rooms ===");
        for (Room r : Database.rooms) {
            System.out.println("ID: " + r.getId() + "\nName: " + r.getName() + "\nCapacity: " + r.getRoomCapacity());
        }
    }
        // A list to store all room objects for the table view
    ObservableList<Room> roomList = FXCollections.observableArrayList();

    public void openRoomManager(Stage primaryStage) {
        // Load initial dummy data from the database
        roomList.setAll(Database.rooms); // Add rooms from the database to our list

        // Table to display rooms
        TableView<Room> table = new TableView<>(roomList);

        // Define table columns
        TableColumn<Room, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Room, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Room, Integer> capacityColumn = new TableColumn<>("Capacity");
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("roomCapacity"));

        TableColumn<Room, Integer> guestsColumn = new TableColumn<>("Guests");
        guestsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfGuests"));

        // Add all columns to the table
        table.getColumns().addAll(idColumn, nameColumn, capacityColumn, guestsColumn);

        // Buttons
        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button backButton = new Button("Back");

        // When the Add button is clicked
        addButton.setOnAction(e -> showRoomForm(null, table));

        // When the Edit button is clicked
        editButton.setOnAction(e -> {
            Room selectedRoom = table.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {
                showRoomForm(selectedRoom, table);
            }
        });

        // When the Delete button is clicked
        deleteButton.setOnAction(e -> {
            Room selectedRoom = table.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Delete Room");
                confirmation.setHeaderText("Are you sure?");
                confirmation.setContentText("Do you really want to delete this room?");

                confirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        roomList.remove(selectedRoom);
                        Database.removeEntity(selectedRoom);
                    }
                });
            }
        });
        backButton.setOnAction(e -> displayDashboard(primaryStage));

        // Layout for buttons and main window
        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton, backButton);
        VBox layout = new VBox(10, new Label("Room List:"), table, buttonBox);
        layout.setPadding(new Insets(10));

        // Set up and show the main scene
        primaryStage.setScene(new Scene(layout, 700, 400));
        primaryStage.setTitle("Room Manager");
    }

    // Shows the form to add or edit a room
    private void showRoomForm(Room roomToEdit, TableView<Room> table) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);

        // Text fields
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField capacityField = new TextField();
        TextField guestsField = new TextField();

        // If editing, fill in the current room's info
        if (roomToEdit != null) {
            popup.setTitle("Edit Room");
            idField.setText(String.valueOf(roomToEdit.getId()));
            idField.setDisable(true); // ID shouldn't be changed
            nameField.setText(roomToEdit.getName());
            capacityField.setText(String.valueOf(roomToEdit.getRoomCapacity()));
            guestsField.setText(String.valueOf(roomToEdit.getNumberOfGuests()));
        } else {
            popup.setTitle("Add New Room");
        }

        // Save button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                int capacity = Integer.parseInt(capacityField.getText().trim());
                int guests = Integer.parseInt(guestsField.getText().trim());

                // Validate input
                if (name.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "Room name cannot be empty.");
                    return;
                }

                if (guests > capacity) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", "Number of guests exceeds room capacity.");
                    return;
                }

                if (roomToEdit == null) {
                    // Check for duplicate ID
                    for (Room r : roomList) {
                        if (r.getId() == id) {
                            showAlert(Alert.AlertType.ERROR, "Duplicate ID", "A room with this ID already exists.");
                            return;
                        }
                    }

                    // Create new room
                    ArrayList<String> defaultDates = new ArrayList<>();
                    defaultDates.add("10:00 AM - 12:00 PM");
                    defaultDates.add("1:00 PM - 3:00 PM");

                    Room newRoom = new Room(id, name, capacity, guests, defaultDates, null);
                    roomList.add(newRoom);
                } else {
                    // Update existing room
                    roomList.remove(roomToEdit);
                    Room updatedRoom = new Room(roomToEdit.getId(), name, capacity, guests, roomToEdit.organiserDate, roomToEdit.attendeeDate);
                    roomList.add(updatedRoom);
                }

                table.refresh();
                popup.close();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid numbers.");
            }
        });

        // Layout for the popup
        VBox popupLayout = new VBox(10,
                new Label("Room ID:"), idField,
                new Label("Name:"), nameField,
                new Label("Capacity:"), capacityField,
                new Label("Number of Guests:"), guestsField,
                saveButton
        );
        popupLayout.setPadding(new Insets(10));

        popup.setScene(new Scene(popupLayout, 300, 300));
        popup.show();
    }

    public void createCategory(Category category) {
        Database.categories.add(category);
    }

    public void updateCategory(String id, String newName, String newDesc) {
        for (Category cat : Database.categories) {
            if (cat.getCategoryid().equals(id)) {
                cat.updateDetails(newName, newDesc);
                return;
            }
        }

    }

    public void deleteCategory(String categoryName) {
        Category category = (Category) Database.getEntityByUsername(categoryName);
        Database.categories.remove(category);
    }

    public void openCategoryManager(Stage parentStage) {
        ObservableList<Category> categoryList = FXCollections.observableArrayList();

        // Initialize sample data
        categoryList.setAll(Database.categories);

        // Table to show categories
        TableView<Category> table = new TableView<>(categoryList);

        // Table columns
        TableColumn<Category, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("categoryid"));

        TableColumn<Category, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("categoryname"));

        TableColumn<Category, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().addAll(idCol, nameCol, descCol);

        // Buttons
        Button addBtn = new Button("Add");
        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");
        Button backBtn = new Button("Back");

        // Add button action
        addBtn.setOnAction(e -> showPopup(null, table));

        // Edit button action
        editBtn.setOnAction(e -> {
            Category selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showPopup(selected, table);
            }
        });

        // Delete button action
        deleteBtn.setOnAction(e -> {
            Category selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                // Confirm before deleting
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirm Delete");
                confirm.setHeaderText("Delete Category?");
                confirm.setContentText("Category will be deleted");

                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // Remove events related to this category
                        Database.events.removeIf(event -> event.getCategory().equals(selected.getCategoryname()));
                        // Remove category
                        categoryList.remove(selected);
                        Database.removeEntity(selected);
                    }
                });
            }
        });
        backBtn.setOnAction(e -> displayDashboard(parentStage));

        // Layout
        HBox buttonBox = new HBox(10, addBtn, editBtn, deleteBtn, backBtn);
        VBox root = new VBox(10, new Label("Categories:"), table, buttonBox);
        root.setPadding(new Insets(10));

        // Show window
        parentStage.setScene(new Scene(root, 600, 400));
        parentStage.setTitle("Category Manager");
    }

// Show popup for adding or editing category
    private void showPopup(Category category, TableView<Category> table) {
        Stage popup = new Stage();
        popup.setTitle(category == null ? "Add Category" : "Edit Category");

        TextField idField = new TextField();
        idField.setPromptText("ID");
        if (category != null) {
            idField.setText(category.getCategoryid());
            idField.setDisable(true); // Prevent ID editing
        }

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        if (category != null) nameField.setText(category.getCategoryname());

        TextField descField = new TextField();
        descField.setPromptText("Description");
        if (category != null) descField.setText(category.getDescription());

        Button saveBtn = new Button("Save");

        saveBtn.setOnAction(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String desc = descField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || desc.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields are required.");
                return;
            }

            if (category == null) {
                // Check for duplicate ID
                boolean idExists = Database.categories.stream()
                        .anyMatch(cat -> cat.getCategoryid().equalsIgnoreCase(id));

                if (idExists) {
                    showAlert(Alert.AlertType.ERROR, "Duplicate ID", "A category with this ID already exists.");
                    return;
                }

                // Add new category
                Category newCategory = new Category(id, name, desc);
                table.getItems().add(newCategory);
            } else {
                // Update existing category
                category.updateDetails(name, desc);
                table.refresh();
            }

            popup.close();
        });

        VBox layout = new VBox(10, idField, nameField, descField, saveBtn);
        layout.setPadding(new Insets(10));

        popup.setScene(new Scene(layout));
        popup.showAndWait();
    }

    // Show alert method
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void generateReport() {
        System.out.println("--- SYSTEM REPORT ---");
        System.out.println("Total Users: " + Database.users.size());
        System.out.println("Total Events: " + Database.events.size());
        System.out.println("Total Rooms: " + Database.rooms.size());
        System.out.println("Total Categories: " + Database.categories.size());
    }

    public List<Category> getCategories() {
        return Database.categories;
    }

    public List<Event> getEvents() {
        return Database.events;
    }

//    public void showAllEvents() {
//        System.out.println("=== All Events ===");
//        for (Event e : Database.events) {
//            e.displaySummary();
//
//        }
    private void showAllEvents(Stage primaryStage) {//feras
        TableView<Event> tableView = new TableView<>();

        TableColumn<Event, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Event, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Event, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Event, String> dateTimeCol = new TableColumn<>("Date & Time");
        dateTimeCol.setCellValueFactory(cellData -> {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return new SimpleStringProperty(cellData.getValue().getDateTime().format(fmt));
        });

        TableColumn<Event, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Event, Integer> attendeesCol = new TableColumn<>("Attendees");
        attendeesCol.setCellValueFactory(cellData ->
            new SimpleIntegerProperty(cellData.getValue().getAttendees().size()).asObject()
        );

        TableColumn<Event, String> roomCol = new TableColumn<>("Room");
        roomCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().room.getName()));

        TableColumn<Event, String> organizerCol = new TableColumn<>("Organizer");
        organizerCol.setCellValueFactory(new PropertyValueFactory<>("organizer"));

        tableView.getColumns().addAll(
            titleCol, descCol, categoryCol,
            dateTimeCol, priceCol, attendeesCol,
            roomCol, organizerCol
        );

        List<Event> events = Database.events;
        ObservableList<Event> data = FXCollections.observableArrayList(events);
        tableView.setItems(data);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> displayDashboard(primaryStage));

        VBox layout = new VBox(10, backButton, tableView);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-padding: 20");
        primaryStage.setScene(new Scene(layout, 700, 400));
    }

    private void showAllUsers(Stage primaryStage) {//feras
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

        TableColumn<User, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(data -> {
            if (data.getValue() instanceof Organizer) {
                return new ReadOnlyStringWrapper("Organizer");
            }
            return new ReadOnlyStringWrapper("Attendee");
        });

        table.getColumns().addAll(usernameCol, dobCol, addressCol, genderCol, typeCol);//common

        table.setItems(FXCollections.observableArrayList(Database.users));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> displayDashboard(primaryStage));

        VBox layout = new VBox(Database.users.size(), backButton, table);
        layout.setStyle("-fx-padding: 20");
        primaryStage.setScene(new Scene(layout, 500, 400));
    }
}