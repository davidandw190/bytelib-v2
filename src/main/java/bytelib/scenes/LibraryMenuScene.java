package bytelib.scenes;

import bytelib.Library;
import bytelib.enums.CatalogueType;
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
            Button viewBooksButton = createMenuButton("View Books Catalogue", this::showBooksCatalogueScene);
            Button viewScientificButton = createMenuButton("View Scientific Catalogue", this::showScientificCatalogueScene);
            Button borrowButton = createMenuButton("Borrow Item", this::handleBorrowBook);
            Button returnButton = createMenuButton("Return Item", this::handleReturnBook);
            Button citeButton = createMenuButton("Cite a Scientific Item", this::handleCiteScientific);
            Button myRequestsButton = createMenuButton("My Borrow Requests", this::handleMyRequests);

            Label welcomeLabel = new Label("Welcome, " + loggedInUser.getUsername() + "!");
            welcomeLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            root.getChildren().addAll(viewBooksButton, viewScientificButton, borrowButton, returnButton, citeButton, myRequestsButton, logoutButton);

        } else if (userType == UserType.LIBRARIAN) {
            Button viewBooksButton = createMenuButton("View Books Catalogue", this::showBooksCatalogueScene);
            Button viewScientificButton = createMenuButton("View Scientific Catalogue", this::showScientificCatalogueScene);
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
        AddItemScene addItemScene = new AddItemScene(primaryStage, library, loggedInUser);

        primaryStage.setScene(new Scene(addItemScene.getRoot(), 600, 500));
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

    private void showBooksCatalogueScene() {
        DisplayItemsScene scene = new DisplayItemsScene(primaryStage, library, loggedInUser, CatalogueType.BOOKS);
        primaryStage.setScene(new Scene(scene.getRoot(), 1200, 800));
    }

    private void showScientificCatalogueScene() {
        DisplayItemsScene scene = new DisplayItemsScene(primaryStage, library, loggedInUser, CatalogueType.SCIENTIFIC);
        primaryStage.setScene(new Scene(scene.getRoot(), 1200, 800));
    }

    private void showMenuScene() {
        MainMenuScene mainMenuScene = new MainMenuScene(primaryStage, library);
        primaryStage.setScene(new Scene(mainMenuScene.getRoot(), 600, 500));
    }
}
