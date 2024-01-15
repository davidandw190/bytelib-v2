package bytelib.scenes;

import bytelib.Library;
import bytelib.enums.LibraryItemType;
import bytelib.enums.ResearchDomain;
import bytelib.enums.UserType;
import bytelib.users.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AddItemScene {
    private final VBox root;
    private final Stage primaryStage;
    private final Library library;
    private final UserType userType;
    private final User loggedInUser;

    public AddItemScene(Stage primaryStage, Library library, User loggedInUser) {
        this.primaryStage = primaryStage;
        this.library = library;
        this.userType = library.getUserType(loggedInUser.getUserId());
        this.loggedInUser = loggedInUser;

        root = new VBox(20);
        root.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20;");
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Add Book");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        root.getChildren().add(titleLabel);

        ComboBox<String> bookTypeComboBox = new ComboBox<>();
        bookTypeComboBox.getItems().addAll("Article", "Textbook", "Novel", "Journal");
        bookTypeComboBox.setPromptText("Select Book Type");
        root.getChildren().add(bookTypeComboBox);

        Button nextButton = createButton("Next", () -> handleNextButton(bookTypeComboBox.getValue()));
        root.getChildren().add(nextButton);

        Button backButton = createButton("Back to Main Menu", this::goBackToMainMenu);
        root.getChildren().add(backButton);
    }

    public VBox getRoot() {
        return root;
    }

    private void handleNextButton(String selectedBookType) {
        if (selectedBookType == null || selectedBookType.isEmpty()) {
            showErrorPopup("Select Book Type", "Please select a book type before proceeding.");
            return;
        }

        switch (selectedBookType) {
            case "Article":
                showArticleForm();
                break;
            case "Textbook":
                showTextbookForm();
                break;
            case "Novel":
                showNovelForm();
                break;
            case "Journal":
                showJournalForm();
                break;
            default:
                showErrorPopup("Invalid Book Type", "Please select a valid book type.");
        }
    }


    private void addLabeledNumberField(VBox container, String labelText, String promptText, String id) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");
        TextField textField = createNumberField(promptText);
        textField.setId(id);
        container.getChildren().addAll(label, textField);
    }

    private void showTextbookForm() {
        Stage textbookStage = new Stage();
        textbookStage.setTitle("Add Textbook");

        VBox textbookLayout = new VBox(10);
        textbookLayout.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20;");
        textbookLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Add Textbook");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(textbookLayout);


        addLabeledTextField(textbookLayout, "Title:", "Enter Title", "title");
        addLabeledTextArea(textbookLayout,  "Enter Description", "description");
        addLabeledNumberField(textbookLayout, "Number of Pages:", "Enter Page Number", "page");
        addLabeledTextField(textbookLayout, "Publisher:", "Enter Publisher", "publisher");
        addLabeledNumberField(textbookLayout, "Volume:", "Enter Volume", "volume");
        addLabeledNumberField(textbookLayout, "Edition:", "Enter Edition", "edition");
        addLabeledNumberField(textbookLayout, "Citation Number:", "Enter Citation Number", "citation");
        addLabeledDatePicker(textbookLayout, "Publication Date:", "publicationDate");

        addLabeledTextField(textbookLayout, "Authors:", "Enter author names separated by commas", "authors");

        addLabeledComboBox(textbookLayout, "Domain:", "Select Genre", "domain",
                "SCIENCE", "TECHNOLOGY", "ENGINEERING", "MEDICINE", "BIOLOGY", "CHEMISTRY", "PHYSICS",
                "MATHEMATICS", "SOCIAL SCIENCE", "HUMANITIES", "ECONOMICS", "COMPUTER SCIENCE", "DISTRIBUTED SYSTEMS",
                "MACHINE LEARNING", "ENVIRONMENTAL SCIENCE","EDUCATION", "LAW", "OTHER");


        Button addButton = createButton("Add Textbook", () -> handleAddTextbook(
                getTextFromLabeledField(textbookLayout, "title"),
                getTextFromLabeledField(textbookLayout, "description"),
                getTextFromLabeledField(textbookLayout, "page"),
                getTextFromLabeledField(textbookLayout, "publisher"),
                getTextFromLabeledField(textbookLayout, "volume"),
                getTextFromLabeledField(textbookLayout, "edition"),
                getTextFromLabeledField(textbookLayout, "citation"),
                getLocalDateFromLabeledDatePicker(textbookLayout, "publicationDate"),
                getTextFromLabeledField(textbookLayout, "authors"),
                ResearchDomain.valueOf(getSelectedItemFromLabeledComboBox(textbookLayout, "domain"))
        ));

        Button backButton = createButton("Back", textbookStage::close);

        textbookLayout.getChildren().addAll(titleLabel, addButton, backButton);


        Scene textbookScene = new Scene(scrollPane, 700, 700);
        textbookStage.setScene(textbookScene);
        textbookStage.show();
    }

    private void showArticleForm() {
        Stage articleStage = new Stage();
        articleStage.setTitle("Add New Article");

        VBox articleLayout = new VBox(10);
        articleLayout.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20;");
        articleLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Add New Article");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(articleLayout);

        articleLayout.getChildren().addAll(titleLabel);


        addLabeledTextField(articleLayout, "Title:", "Enter Title", "title");
        addLabeledTextArea(articleLayout, "Enter Abstract", "abstract");
        addLabeledNumberField(articleLayout, "Number of Pages:", "Enter Page Number", "page");
        addLabeledNumberField(articleLayout, "Citation Number:", "Enter Citation Number", "citation");
        addLabeledDatePicker(articleLayout, "Publication Date:", "publicationDate");

        addLabeledTextField(articleLayout, "Authors:", "Enter author names separated by commas", "authors");

        addLabeledComboBox(articleLayout, "Domain:", "Select Genre", "domain",
                "SCIENCE", "TECHNOLOGY", "ENGINEERING", "MEDICINE", "BIOLOGY", "CHEMISTRY", "PHYSICS",
                "MATHEMATICS", "SOCIAL SCIENCE", "HUMANITIES", "ECONOMICS", "COMPUTER SCIENCE", "DISTRIBUTED SYSTEMS",
                "MACHINE LEARNING", "ENVIRONMENTAL SCIENCE","EDUCATION", "LAW", "OTHER");


        Button addButton = createButton("Add Article", () -> handleAddArticle(
                getTextFromLabeledField(articleLayout, "title"),
                getTextFromLabeledField(articleLayout, "abstract"),
                getTextFromLabeledField(articleLayout, "page"),
                getTextFromLabeledField(articleLayout, "citation"),
                getLocalDateFromLabeledDatePicker(articleLayout, "publicationDate"),
                getTextFromLabeledField(articleLayout, "authors"),
                ResearchDomain.valueOf(getSelectedItemFromLabeledComboBox(articleLayout, "domain"))
        ));

        Button backButton = createButton("Back", articleStage::close);

        articleLayout.getChildren().addAll(addButton, backButton);


        Scene textbookScene = new Scene(scrollPane, 600, 800);
        articleStage.setScene(textbookScene);
        articleStage.show();
    }

    private void handleAddArticle(String title, String abstractText, String pages, String citations, LocalDate pubDate, String authors, ResearchDomain domain) {
        library.addItemToStock(LibraryItemType.ARTICLE, title, abstractText , Integer.parseInt(pages), null, null, null, null, Integer.parseInt(citations), pubDate, convertCommaSeparatedToList(authors), domain, null, null);
    }

    private List<String> convertCommaSeparatedToList(String s) {
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .toList();
    }

    // Helper method to add labeled text field
    private void addLabeledTextField(VBox container, String labelText, String promptText, String id) {
        Label label = new Label(labelText);2
        label.setStyle("-fx-font-weight: bold;");
        TextField textField = createTextField(promptText);
        textField.setId(id);
        container.getChildren().addAll(label, textField);
    }

    private void addLabeledTextArea(VBox container, String labelText, String id) {
        Label label = new Label(labelText + ":");
        label.setStyle("-fx-font-weight: bold;");
        TextArea textArea = createTextArea("Enter " + id);
        textArea.setId(id);
        container.getChildren().addAll(label, textArea);
    }

    // Helper method to add labeled date picker
    private void addLabeledDatePicker(VBox container, String labelText, String id) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");
        DatePicker datePicker = new DatePicker();
        datePicker.setId(id);
        container.getChildren().addAll(label, datePicker);
    }

    // Helper method to add labeled combo box
    private void addLabeledComboBox(VBox container, String labelText, String promptText, String id, String... items) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setPromptText(promptText);
        comboBox.setId(id);
        container.getChildren().addAll(label, comboBox);
    }

    // Helper method to get text from labeled field
    private String getTextFromLabeledField(VBox container, String id) {
        TextInputControl inputControl = (TextInputControl) container.lookup("#" + id);
        return inputControl.getText();
    }

    // Helper method to get selected item from labeled combo box
    private String getSelectedItemFromLabeledComboBox(VBox container, String id) {
        ComboBox<String> comboBox = (ComboBox<String>) container.lookup("#" + id);
        return comboBox.getValue();
    }

    // Helper method to get local date from labeled date picker
    private LocalDate getLocalDateFromLabeledDatePicker(VBox container, String id) {
        DatePicker datePicker = (DatePicker) container.lookup("#" + id);
        return datePicker.getValue();
    }

    private TextField createNumberField(String prompt) {
        TextField textField = createTextField(prompt);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        return textField;
    }


    private void handleAddTextbook(String title, String description, String pages, String publisher, String volume, String edition, String citations,
                                   LocalDate pubDate, String authors, ResearchDomain domain) {


        library.addItemToStock(LibraryItemType.TEXTBOOK, title, description , Integer.parseInt(pages), null, Integer.parseInt(volume),
                null,  Integer.parseInt(edition), Integer.parseInt(citations), pubDate, convertCommaSeparatedToList(authors), domain,
                null, null);
    }


    private TextArea createTextArea(String prompt) {
        TextArea textArea = new TextArea();
        textArea.setPromptText(prompt);
        textArea.getStyleClass().add("registration-text-field");
        return textArea;
    }

    private TextField createTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-width: 1px; -fx-padding: 5px;");
        return textField;
    }

    private void showNovelForm() {
        // Implement form for adding a novel
        // You can create additional UI elements (labels, text fields, etc.) here
        System.out.println("Adding a novel...");
    }

    private void showJournalForm() {
        // Implement form for adding a journal
        // You can create additional UI elements (labels, text fields, etc.) here
        System.out.println("Adding a journal...");
    }

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #008000; -fx-text-fill: white; -fx-font-size: 14;");
        button.setOnAction(event -> action.run());
        button.setMinWidth(200);
        return button;
    }
    
    private void showErrorPopup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void goBackToMainMenu() {
        LibraryMenuScene menuScene = new LibraryMenuScene(primaryStage, library, loggedInUser);
        primaryStage.setScene(new Scene(menuScene.getRoot(), 500, 400));
    }
}
