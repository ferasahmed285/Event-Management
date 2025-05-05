import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDate;

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
        private Scene showAllEvents() {
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
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));

        TableColumn<Event, String> organizerCol = new TableColumn<>("Organizer");
        organizerCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().organizer.getUsername())
        );

        tableView.getColumns().addAll(
            titleCol, descCol, categoryCol,
            dateTimeCol, priceCol, attendeesCol,
            roomCol, organizerCol
        );

        List<Event> events = Database.events;
        ObservableList<Event> data = FXCollections.observableArrayList(events);
        tableView.setItems(data);

        Button backButton = new Button("Back");
        //backButton.setOnAction(e -> primaryStage.setScene(createStartScene()));

        VBox layout = new VBox(10, backButton, tableView);
        layout.setPadding(new Insets(20));

        return new Scene(layout, 800, 600);
    }

    private Scene viewAllUsers() {//feras
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
        //backButton.setOnAction(e -> primaryStage.setScene(createStartScene()));

        VBox layout = new VBox(Database.users.size(), backButton, table);
        layout.setStyle("-fx-padding: 20");

        return new Scene(layout, 800, 400);
    }
}