package bytelib.scenes;

import bytelib.Library;
import bytelib.enums.UserType;
import bytelib.users.Borrower;
import bytelib.users.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScene {
    private final VBox root;
    private final Stage primaryStage;
    private final Library library;
    private final UserType userType;
    private final Scene previousScene;
    private User loggedInUser;

    public LoginScene(Stage primaryStage, Library library, UserType userType, Scene previousScene) {
        this.primaryStage = primaryStage;
        this.library = library;
        this.userType = userType;
        this.previousScene = previousScene;

        root = new VBox(20);
        root.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 20;");
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        TextField usernameField = createTextField("Username/Email");
        PasswordField passwordField = createPasswordField("Password");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #860909; -fx-font-size: 14;");

        Button backButton = createButton("Cancel", () -> goBack(primaryStage));
        Button loginButton = createButton("Log In", () -> handleLogin(usernameField, passwordField, errorLabel));


        root.getChildren().addAll(titleLabel, usernameField, passwordField, errorLabel, loginButton, backButton);
        root.setPadding(new Insets(20));

        root.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 80;");
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        usernameField.setStyle("-fx-pref-width: 200; -fx-font-size: 14; -fx-padding: 5;");
        passwordField.setStyle("-fx-pref-width: 200; -fx-font-size: 14; -fx-padding: 5;");
        errorLabel.setStyle("-fx-text-fill: #860909; -fx-font-size: 14;");
        loginButton.setStyle("-fx-background-color: #008000; -fx-text-fill: white; -fx-font-size: 14; -fx-pref-width: 200; -fx-padding: 5;");
        backButton.setStyle("-fx-background-color: #008000; -fx-text-fill: white; -fx-font-size: 14; -fx-pref-width: 200; -fx-padding: 5;");

    }

    public VBox getRoot() {
        return root;
    }

    private TextField createTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.getStyleClass().add("login-text-field");
        return textField;
    }

    private PasswordField createPasswordField(String prompt) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(prompt);
        passwordField.getStyleClass().add("login-text-field");
        return passwordField;
    }

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #008000; -fx-text-fill: white; -fx-font-size: 14;");
        button.setOnAction(event -> action.run());
        return button;
    }

    private void handleLogin(TextField usernameField, PasswordField passwordField, Label errorLabel) {
        String usernameOrEmail = usernameField.getText().trim();
        String password = passwordField.getText();

        if (usernameOrEmail.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both username/email and password.");
            return;
        }

        if (userType == UserType.BORROWER) {
            loggedInUser = library.loginBorrower(usernameOrEmail, password);
        } else {
            String librarianUsername = "admin";
            String librarianPassword = "123";

            if (librarianUsername.equals(usernameOrEmail) && librarianPassword.equals(password)) {
                System.out.println("\nLogin successful! Welcome, " + librarianUsername + "!");

            } else {
                errorLabel.setText("Invalid credentials. Please try again.");
                return;
            }
        }

//        if (loggedInUser == null) {
//            errorLabel.setText("Invalid credentials. Please try again.");
//        } else {
            showLibraryMenuScene();
//        }
    }

    private void handleBorrowerLogin(String usernameOrEmail, String password, Label errorLabel) {
        Borrower loggedInUser = library.loginBorrower(usernameOrEmail, password);
        if (loggedInUser != null) {
            showSuccessPopup("Login successful! Welcome, " + loggedInUser.getUsername() + "!");
        } else {
            errorLabel.setText("Invalid credentials. Please try again.");
        }
    }

    private void handleLibrarianLogin(String usernameOrEmail, String password, Label errorLabel) {
        String librarianUsername = "admin";
        String librarianPassword = "123";
        if (librarianUsername.equals(usernameOrEmail) && librarianPassword.equals(password)) {
            System.out.println("\nLogin successful! Welcome, " + librarianUsername + "!");
            showLibraryMenuScene();
        } else {
            errorLabel.setText("Invalid credentials. Please try again.");
        }
    }

    private void showLibraryMenuScene() {
        UtilitiesMenuScene utilitiesMenuScene = new UtilitiesMenuScene(primaryStage, library, userType, loggedInUser);
        primaryStage.setScene(new Scene(utilitiesMenuScene.getRoot(), 500, 400));
    }

    private void goBack(Stage primaryStage) {
        primaryStage.setScene(previousScene);
    }

    private void showSuccessPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Nice");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
