package bytelib.scenes;

import bytelib.Library;
import bytelib.enums.UserType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuScene {
    private final VBox root;
    private final Stage primaryStage;
    private final Library library;

    public MainMenuScene(Stage primaryStage, Library library) {
        this.primaryStage = primaryStage;
        this.library = library;

        root = new VBox(20);
        root.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20;");
        root.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome to the ByteLib Library");
        welcomeLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Button userLoginButton = createMenuButton("Log In as User", UserType.BORROWER, "login");
        Button librarianLoginButton = createMenuButton("Log In as Librarian", UserType.LIBRARIAN, "login");
        Button registerButton = createMenuButton("Register a New Account", UserType.BORROWER, "registration");
        Button exitButton = createMenuButton("Exit", null, "exit");

        root.getChildren().addAll(welcomeLabel, userLoginButton, librarianLoginButton, registerButton, exitButton);
    }

    public VBox getRoot() {
        return root;
    }

    private Button createMenuButton(String text, UserType userType, String type) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #008000; -fx-text-fill: white; -fx-font-size: 14;");
        button.setOnAction(event -> handleButtonClick(userType, type));
        button.setMinWidth(200);
        return button;
    }

    private void handleButtonClick(UserType userType, String actionType) {
        if (userType == null) {
            primaryStage.close();

        } else if (userType == UserType.LIBRARIAN || userType == UserType.BORROWER) {

            if (actionType.equals("login")) {
                showLoginScene(userType);

            } else if (actionType.equals("registration")) {
                showRegisterScene();
            }
        }
    }

    private void showLoginScene(UserType userType) {
        LoginScene loginScene = new LoginScene(primaryStage, library, userType, primaryStage.getScene());
        System.out.println(userType.name());
        primaryStage.setScene(new Scene(loginScene.getRoot(), 500, 400));
    }

    private void showRegisterScene() {
        RegistrationScene registrationScene = new RegistrationScene(primaryStage, library, primaryStage.getScene());
        primaryStage.setScene(new Scene(registrationScene.getRoot(), 500, 400));
    }
}
