package bytelib.scenes;

import bytelib.Library;
import bytelib.dto.RegistrationRequest;
import bytelib.dto.RegistrationResponse;
import bytelib.enums.UserType;
import bytelib.users.Borrower;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RegistrationScene {
    private final GridPane root;
    private final Stage primaryStage;
    private final Library library;
    private final Scene previousScene;

    public RegistrationScene(Stage primaryStage, Library library, Scene previousScene) {
        this.primaryStage = primaryStage;
        this.library = library;
        this.previousScene = previousScene;

        root = new GridPane();
        root.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20;");
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);

        Label titleLabel = new Label("Create a ByteLib Account");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        TextField usernameField = createTextField("Username");
        TextField emailField = createTextField("Email");
        TextField phoneField = createTextField("Phone Number");
        PasswordField passwordField = createPasswordField("Password");
        PasswordField confirmPasswordField = createPasswordField("Confirm Password");

        ChoiceBox<String> accountTypeChoiceBox = createAccountTypeChoiceBox();

        Button registerButton = createButton("Register", () -> handleRegistration(usernameField, emailField, phoneField, passwordField, confirmPasswordField, accountTypeChoiceBox));
        Button backButton = createButton("Cancel", () -> goBack(primaryStage));

        root.add(titleLabel, 0, 0, 2, 1);
        root.add(usernameField, 0, 1, 2, 1);
        root.add(emailField, 0, 2, 2, 1);
        root.add(phoneField, 0, 3, 2, 1);
        root.add(passwordField, 0, 4, 2, 1);
        root.add(confirmPasswordField, 0, 5, 2, 1);
        root.add(accountTypeChoiceBox, 0, 6, 2, 1);
        root.add(registerButton, 0, 7);
        root.add(backButton, 1, 7);
    }

    public GridPane getRoot() {
        return root;
    }

    private TextField createTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.getStyleClass().add("registration-text-field");
        return textField;
    }

    private PasswordField createPasswordField(String prompt) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(prompt);
        passwordField.getStyleClass().add("registration-text-field");
        return passwordField;
    }

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #008000; -fx-text-fill: white; -fx-font-size: 14;");
        button.setOnAction(event -> action.run());
        return button;
    }

    private ChoiceBox<String> createAccountTypeChoiceBox() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(UserType.BORROWER.name(), UserType.LIBRARIAN.name());
        choiceBox.setValue(UserType.BORROWER.name());
        choiceBox.getStyleClass().add("registration-text-field");
        return choiceBox;
    }

    private void handleRegistration(TextField usernameField, TextField emailField, TextField phoneField, PasswordField passwordField, PasswordField confirmPasswordField, ChoiceBox<String> accountTypeChoiceBox) {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String accountType = accountTypeChoiceBox.getValue();

        if (!confirmPassword.equals(password)) {
            showErrorPopup("Passwords Don't Match", "Please ensure you entered your password and confirmation password correctly.");
            return;
        }

        if (!isValidUsername(username) || !isValidEmail(email) || !isValidPhoneNumber(phone) || !isValidPassword(password)) {
            showErrorPopup("Invalid Input", "Please check your input and try again.");
            return;
        }

        if (library.isUsernameTaken(username)) {
            showErrorPopup("Username Taken", "This username is already in use. Please choose another one.");
            return;
        }

        if (library.isEmailTaken(email)) {
            showErrorPopup("Email Taken", "An account with this email already exists. Please use a different email.");
            return;
        }

        RegistrationRequest registrationRequest = new RegistrationRequest(username, email, phone, password, accountType);
        RegistrationResponse registrationResponse = sendRegistrationRequest(registrationRequest);

        if (registrationResponse.success()) {
            showSuccessPopup(registrationResponse.message());
        } else {
            showErrorPopup("Registration Failed", registrationResponse.message());
        }
    }

    private RegistrationResponse sendRegistrationRequest(RegistrationRequest registrationRequest) {
        try (Socket socket = new Socket("localhost", 8888);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("REGISTER");

            out.writeObject(registrationRequest);

            return (RegistrationResponse) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new RegistrationResponse(false, "Registration failed.");
        }
    }

    private boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9]{5,20}$");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,20}$");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{10}$");
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{5,}$");
    }

    private void showSuccessPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorPopup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void goBack(Stage primaryStage) {
        primaryStage.setScene(previousScene);
    }
}
