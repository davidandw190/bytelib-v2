package bytelib.scenes;

import bytelib.Library;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BorrowerMenuScene {
    private final VBox root;
    private final Stage primaryStage;
    private final Library library;

    public BorrowerMenuScene(Stage primaryStage, Library library) {
        this.primaryStage = primaryStage;
        this.library = library;

        root = new VBox(20);
        root.setStyle("-fx-background-color: #ecf0f1;");
        root.setAlignment(Pos.CENTER);

        Button viewBooksButton = createMenuButton("View Books Catalogue", this::handleViewBooks);
        Button viewScientificButton = createMenuButton("View Scientific Catalogue", this::handleViewScientific);
        Button borrowButton = createMenuButton("Borrow Book", this::handleBorrowBook);
        Button returnButton = createMenuButton("Return Book", this::handleReturnBook);
        Button citeButton = createMenuButton("Cite a Scientific Item", this::handleCiteScientific);
        Button myRequestsButton = createMenuButton("My Borrow Requests", this::handleMyRequests);
        Button logoutButton = createMenuButton("Logout", this::handleLogout);

        root.getChildren().addAll(
                viewBooksButton, viewScientificButton, borrowButton,
                returnButton, citeButton, myRequestsButton, logoutButton
        );
    }

    public VBox getRoot() {
        return root;
    }

    private Button createMenuButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #008000; -fx-text-fill: white; -fx-font-size: 14;");
        button.setOnAction(event -> action.run());
        button.setMinWidth(200);
        return button;
    }

    private void handleViewBooks() {
        showMessage("Viewing Books Catalogue");
    }

    private void handleViewScientific() {
        showMessage("Viewing Scientific Catalogue");
    }

    private void handleBorrowBook() {
        showMessage("Borrowing a Book");
    }

    private void handleReturnBook() {
        showMessage("Returning a Book");
    }

    private void handleCiteScientific() {
        showMessage("Citing a Scientific Item");
    }

    private void handleMyRequests() {
        showMessage("Viewing My Borrow Requests");
    }

    private void handleLogout() {
        showMessage("Logging out. Returning to the main menu.");
        showMenuScene();
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showMenuScene() {
        MenuScene menuScene = new MenuScene(primaryStage, library);
        primaryStage.setScene(new Scene(menuScene.getRoot(), 500, 400));
    }
}
