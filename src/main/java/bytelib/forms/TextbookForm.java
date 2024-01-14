package bytelib.forms;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class TextbookForm {

    private GridPane root;
    private TextField titleField;
    private TextField publisherField;
    private TextArea descriptionArea;
    private TextField pageField;
    private TextField volumeField;
    private TextField editionField;
    private TextField citationField;
    private TextField ratingField;
    private DatePicker pubDatePicker;
    private DatePicker returnDatePicker;
    private ChoiceBox<String> genreChoiceBox;
    private ChoiceBox<String> domainChoiceBox;
    private ChoiceBox<String> intervalChoiceBox;
    private TextArea authorsArea;
    private Text validationText;

    public TextbookForm() {
        root = new GridPane();
        root.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20;");
        root.setAlignment(javafx.geometry.Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        initForm();
    }

    public GridPane getRoot() {
        return root;
    }

    private void initForm() {
        // Initialize form components
        titleField = createTextField("Title");
        publisherField = createTextField("Publisher");
        descriptionArea = createTextArea("Description");
        pageField = createTextField("Page No");
        volumeField = createTextField("Volume");
        editionField = createTextField("Edition");
        citationField = createTextField("Citation No");
        ratingField = createTextField("Rating");
        pubDatePicker = createDatePicker("Publication Date");
        returnDatePicker = createDatePicker("Return Date");

        // ChoiceBoxes for genre, domain, and publishing interval
        genreChoiceBox = createChoiceBox("Genre", "FANTASY", "SCIENCE_FICTION", "PHILOSOPHY", "OTHER");
        domainChoiceBox = createChoiceBox("Domain", "SCIENCE", "TECHNOLOGY", "ENGINEERING", "OTHER");
        intervalChoiceBox = createChoiceBox("Publishing Interval", "WEEKLY", "MONTHLY", "QUARTERLY", "OTHER");

        // TextArea for authors input
        authorsArea = new TextArea();
        authorsArea.setPromptText("Enter authors (separated by commas)");
        authorsArea.getStyleClass().add("registration-text-area");

        // Text for validation messages
        validationText = new Text();
        validationText.getStyleClass().add("validation-text");

        // Add components to the form
        root.add(titleField, 0, 0, 2, 1);
        root.add(publisherField, 0, 1, 2, 1);
        root.add(descriptionArea, 0, 2, 2, 1);
        root.add(pageField, 0, 3);
        root.add(volumeField, 1, 3);
        root.add(editionField, 0, 4);
        root.add(citationField, 1, 4);
        root.add(ratingField, 0, 5);
        root.add(pubDatePicker, 1, 5);
        root.add(returnDatePicker, 0, 6, 2, 1);
        root.add(genreChoiceBox, 0, 7);
        root.add(domainChoiceBox, 1, 7);
        root.add(intervalChoiceBox, 0, 8, 2, 1);
        root.add(authorsArea, 0, 9, 2, 1);
        root.add(validationText, 0, 10, 2, 1);
    }

    private TextField createTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.getStyleClass().add("registration-text-field");
        return textField;
    }

    private TextArea createTextArea(String prompt) {
        TextArea textArea = new TextArea();
        textArea.setPromptText(prompt);
        textArea.getStyleClass().add("registration-text-area");
        return textArea;
    }

    private DatePicker createDatePicker(String prompt) {
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText(prompt);
        datePicker.getStyleClass().add("registration-date-picker");
        return datePicker;
    }

    private ChoiceBox<String> createChoiceBox(String prompt, String... choices) {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(choices);
//        choiceBox.setPromptText(prompt);
        choiceBox.getStyleClass().add("registration-choice-box");
        return choiceBox;
    }
}