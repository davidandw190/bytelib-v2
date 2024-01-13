package bytelib.scenes;

import bytelib.Library;
import bytelib.enums.UserType;
import bytelib.users.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibraryMenuScene {
    private final VBox root;
    private final Stage primaryStage;
    private final Library library;
    private final UserType userType;
    private final User loggedInUser;

    public LibraryMenuScene(Stage primaryStage, Library library, User loggedInUser) {
        this.primaryStage = primaryStage;
        this.library = library;
        this.userType = library.getUserType(loggedInUser.getUserId());
        this.loggedInUser = loggedInUser;

        if (userType != null) {
            System.out.println("USER TYPE IS:  " + userType.name());
        }

        root = new VBox(20);
        root.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20;");
        root.setAlignment(Pos.CENTER);

        Button logoutButton = createMenuButton("Logout", this::logout);

        if (userType == UserType.BORROWER) {
            Button viewBooksButton = createMenuButton("View Books Catalogue", this::handleViewBooks);
            Button viewScientificButton = createMenuButton("View Scientific Catalogue", this::handleViewBooks);
            Button borrowButton = createMenuButton("Borrow Item", this::handleBorrowBook);
            Button returnButton = createMenuButton("Return Item", this::handleReturnBook);
            Button citeButton = createMenuButton("Cite a Scientific Item", this::handleCiteScientific);
            Button myRequestsButton = createMenuButton("My Borrow Requests", this::handleMyRequests);

            Label welcomeLabel = new Label("Welcome, " + loggedInUser.getUsername() + "!");
            welcomeLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            root.getChildren().addAll(viewBooksButton, viewScientificButton, borrowButton, returnButton, citeButton, myRequestsButton, logoutButton);

        } else if (userType == UserType.LIBRARIAN) {
            Button viewBooksButton = createMenuButton("View Books Catalogue", this::viewBooksCatalogue);
            Button viewScientificButton = createMenuButton("View Scientific Catalogue", this::viewScientificCatalogue);
            Button addBookButton = createMenuButton("Add Item To Stock", this::addItem);
            Button removeBookButton = createMenuButton("Remove From Stock", this::removeItem);
            Button viewRequestsButton = createMenuButton("View Borrow Requests", this::viewBorrowRequests);

            Label welcomeLabel = new Label("Welcome, Librarian!");
            welcomeLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            root.getChildren().addAll(viewBooksButton, viewScientificButton, addBookButton, removeBookButton, viewRequestsButton, logoutButton);
        }

    }


    public VBox getRoot() {
        return root;
    }

    private void handleViewBooks() {
    }


    private Button createMenuButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #008000; -fx-text-fill: white; -fx-font-size: 14;");
        button.setOnAction(event -> {
            action.run();
//            addTapAnimation(button);
        });
        button.setMinWidth(200);
//        addHoverAnimation(button);
        return button;
    }

    private void viewScientificCatalogue() {
        System.out.println("Viewing Scientific Catalogue...");
    }

    private void handleBorrowBook() {
        System.out.println("Borrowing a Book");
    }

    private void handleReturnBook() {
        System.out.println("Returning a Book");
    }

    private void handleCiteScientific() {
        System.out.println("Citing a Scientific Item");
    }

    private void handleMyRequests() {
        System.out.println("Viewing My Borrow Requests");
    }

    private void addItem() {
        System.out.println("Adding Book...");
    }

    private void removeItem() {
        System.out.println("Removing Book...");
    }

    private void viewBorrowRequests() {
        System.out.println("Viewing Borrow Requests...");
    }

    private void logout() {
        System.out.println("Logging out. Returning to the main menu.");
        showMenuScene();
    }

    private void viewBooksCatalogue() {
//        Stage booksStage = new Stage();
//        booksStage.setTitle("Books Catalogue");
//
//        // Create the table view
//        TableView<Book> tableView = new TableView<>();
//        ObservableList<Book> booksData = FXCollections.observableArrayList(library.getBooksCatalogue());
//
//        // Define columns
//        TableColumn<Book, String> idColumn = new TableColumn<>("ID");
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//
//        TableColumn<Book, String> nameColumn = new TableColumn<>("Name");
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//        TableColumn<Book, String> statusColumn = new TableColumn<>("Status");
//        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
//
//        // Add columns to the table
//        tableView.getColumns().addAll(idColumn, nameColumn, statusColumn);
//
//        // Set the data to the table
//        tableView.setItems(booksData);
//
//        // Create a button to go back
//        Button backButton = new Button("Back");
//        backButton.setOnAction(event -> booksStage.close());
//
//        // Create a button to add a book
//        Button addBookButton = new Button("Add Book");
//        addBookButton.setOnAction(event -> addItem());
//
//        // Create a VBox layout for the scene
//        VBox booksLayout = new VBox(30);
//        booksLayout.getChildren().addAll(tableView, backButton, addBookButton);
//        booksLayout.setAlignment(Pos.CENTER);
//
//        // Create the scene
//        Scene booksScene = new Scene(booksLayout, 800, 600);
//
//        // Set the scene to the stage
//        booksStage.setScene(booksScene);
//
//        // Show the stage
//        booksStage.show();
    }

    private void showMenuScene() {
        MainMenuScene mainMenuScene = new MainMenuScene(primaryStage, library);
        primaryStage.setScene(new Scene(mainMenuScene.getRoot(), 500, 400));
    }

}
