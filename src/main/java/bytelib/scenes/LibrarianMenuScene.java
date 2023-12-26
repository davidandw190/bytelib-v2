package bytelib.scenes;

import bytelib.Library;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibrarianMenuScene {
    private final VBox root;
    private final Stage primaryStage;
    private final Library library;

    public LibrarianMenuScene(Stage primaryStage, Library library) {
        this.primaryStage = primaryStage;
        this.library = library;

        root = new VBox(20);
        root.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20;");
        root.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome, Librarian!");
        welcomeLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Button viewBooksButton = createMenuButton("View Books Catalogue", this::viewBooksCatalogue);
        Button viewScientificButton = createMenuButton("View Scientific Catalogue", this::viewScientificCatalogue);
        Button addBookButton = createMenuButton("Add Book", this::addItem);
        Button removeBookButton = createMenuButton("Remove Book", this::removeItem);
        Button viewRequestsButton = createMenuButton("View Borrow Requests", this::viewBorrowRequests);
        Button logoutButton = createMenuButton("Logout", this::logout);

        root.getChildren().addAll(welcomeLabel, viewBooksButton, viewScientificButton, addBookButton, removeBookButton, viewRequestsButton, logoutButton);
    }

    public VBox getRoot() {
        return root;
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

    private void viewBooksCatalogue() {
        System.out.println("Viewing Books Catalogue...");
    }

    private void viewScientificCatalogue() {
        System.out.println("Viewing Scientific Catalogue...");
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

    private void showMenuScene() {
        MenuScene menuScene = new MenuScene(primaryStage, library);
        primaryStage.setScene(new Scene(menuScene.getRoot(), 500, 400));
    }

}
