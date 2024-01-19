package bytelib.scenes;

import bytelib.Library;
import bytelib.enums.*;
import bytelib.users.*;
import bytelib.items.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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


        Button addButton = createButton("Add Textbook", () -> {
            // Validate fields before calling handleAddTextbook
                handleAddTextbook(
                        getTextFromLabeledField(textbookLayout, "title"),
                        getTextFromLabeledField(textbookLayout, "description"),
                        getTextFromLabeledField(textbookLayout, "page"),
                        getTextFromLabeledField(textbookLayout, "publisher"),
                        getTextFromLabeledField(textbookLayout, "volume"),
                        getTextFromLabeledField(textbookLayout, "edition"),
                        getTextFromLabeledField(textbookLayout, "citation"),
                        getLocalDateFromLabeledDatePicker(textbookLayout, "publicationDate"),
                        getTextFromLabeledField(textbookLayout, "authors"),
                        getSelectedItemFromLabeledComboBox(textbookLayout, "domain")
                );
        });

        Button backButton = createButton("Back", textbookStage::close);

        textbookLayout.getChildren().addAll(titleLabel, addButton, backButton);


        Scene textbookScene = new Scene(scrollPane, 600, 800);
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
                getSelectedItemFromLabeledComboBox(articleLayout, "domain")
        ));

        Button backButton = createButton("Back", articleStage::close);

        articleLayout.getChildren().addAll(addButton, backButton);


        Scene textbookScene = new Scene(scrollPane, 600, 800);
        articleStage.setScene(textbookScene);
        articleStage.show();
    }

    private void showJournalForm() {
        Stage journalStage = new Stage();
        journalStage.setTitle("Add New Journal");

        VBox journalLayout = new VBox(10);
        journalLayout.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20;");
        journalLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Add New Journal");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(journalLayout);

        journalLayout.getChildren().addAll(titleLabel);


        addLabeledTextField(journalLayout, "Title:", "Enter Title", "title");
        addLabeledTextArea(journalLayout, "Enter Abstract", "abstract");
        addLabeledTextField(journalLayout, "Publisher:", "Enter Publisher", "publisher");
        addLabeledNumberField(journalLayout, "Issue:", "Enter Issue No.", "issue");
        addLabeledNumberField(journalLayout, "Number of Pages:", "Enter Page Number", "page");
        addLabeledNumberField(journalLayout, "Citation Number:", "Enter Citation Number", "citation");
        addLabeledDatePicker(journalLayout, "Publication Date:", "publicationDate");

        addLabeledTextField(journalLayout, "Authors:", "Enter author names separated by commas", "authors");

        addLabeledComboBox(journalLayout, "Domain:", "Select Journal Topic Or Domain", "domain",
                "SCIENCE", "TECHNOLOGY", "ENGINEERING", "MEDICINE", "BIOLOGY", "CHEMISTRY", "PHYSICS",
                "MATHEMATICS", "SOCIAL_SCIENCE", "HUMANITIES", "ECONOMICS", "COMPUTER_SCIENCE", "DISTRIBUTED_SYSTEMS",
                "MACHINE_LEARNING", "ENVIRONMENTAL_SCIENCE","EDUCATION", "LAW", "OTHER");

        addLabeledComboBox(journalLayout, "Publishing Interval:", "Select Journal Publishing Interval", "intervals",
                "MONTHLY", "QUARTERLY",  "YEARLY", "OTHER");


        Button addButton = createButton("Add Article", () -> handleAddJournal(
                getTextFromLabeledField(journalLayout, "title"),
                getTextFromLabeledField(journalLayout, "abstract"),
                getTextFromLabeledField(journalLayout, "publisher"),
                getTextFromLabeledField(journalLayout, "page"),
                getTextFromLabeledField(journalLayout, "issue"),
                getTextFromLabeledField(journalLayout, "citation"),
                getLocalDateFromLabeledDatePicker(journalLayout, "publicationDate"),
                getTextFromLabeledField(journalLayout, "authors"),
                getSelectedItemFromLabeledComboBox(journalLayout, "domain"),
                getSelectedItemFromLabeledComboBox(journalLayout, "intervals")
        ));

        Button backButton = createButton("Back", journalStage::close);

        journalLayout.getChildren().addAll(addButton, backButton);


        Scene textbookScene = new Scene(scrollPane, 600, 800);
        journalStage.setScene(textbookScene);
        journalStage.show();
    }

    private void showNovelForm() {
        Stage novelStage = new Stage();
        novelStage.setTitle("Add Textbook");

        VBox novelLayout = new VBox(10);
        novelLayout.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20;");
        novelLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Add Textbook");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(novelLayout);


        addLabeledTextField(novelLayout, "Title:", "Enter Title", "title");
        addLabeledTextArea(novelLayout,  "Enter Description", "description");
        addLabeledNumberField(novelLayout, "Number of Pages:", "Enter Page Number", "page");
        addLabeledTextField(novelLayout, "Publisher:", "Enter Publisher", "publisher");
        addLabeledNumberField(novelLayout, "Volume:", "Enter Volume", "volume");
        addLabeledDatePicker(novelLayout, "Publication Date:", "publicationDate");

        addLabeledTextField(novelLayout, "Authors:", "Enter author names separated by commas", "authors");

        addLabeledComboBox(novelLayout, "Genre:", "Select Genre", "genre",
                "FANTASY", "SCIENCE_FICTION", "PHILOSOPHY", "PSYCHOLOGY", "POEMS", "ROMANCE", "SCIENCE",
                "ASTRONOMY", "POLITICS", "PHYSICS", "OTHER");


        Button addButton = createButton("Add Textbook", () -> handleAddNovel(
                getTextFromLabeledField(novelLayout, "title"),
                getTextFromLabeledField(novelLayout, "description"),
                getTextFromLabeledField(novelLayout, "page"),
                getTextFromLabeledField(novelLayout, "publisher"),
                getTextFromLabeledField(novelLayout, "volume"),
                getTextFromLabeledField(novelLayout, "volume"),
                getLocalDateFromLabeledDatePicker(novelLayout, "publicationDate"),
                getTextFromLabeledField(novelLayout, "authors"),
                getSelectedItemFromLabeledComboBox(novelLayout, "genre")
        ));

        Button backButton = createButton("Back", novelStage::close);

        novelLayout.getChildren().addAll(titleLabel, addButton, backButton);


        Scene novelScene = new Scene(scrollPane, 600, 800);
        novelStage.setScene(novelScene);
        novelStage.show();
    }

    private void handleAddNovel(String title, String description, String pages, String publisher, String volume, String edition, LocalDate publicationDate, String authors, String genreField) {

        if (validateNovelFields(title, description, pages, publisher, volume, edition, publicationDate, authors, genreField)) {
            try {
                library.addItemToStock(LibraryItemType.NOVEL, title, description , Integer.parseInt(pages), publisher, Integer.parseInt(volume),
                        null, Integer.parseInt(edition), null, Date.valueOf(publicationDate), convertCommaSeparatedToList(authors),
                        null, BookGenre.valueOf(genreField), null);

                showSuccessPopup("Novel Added To Stock", "The novel has been successfully added to the library.");

            } catch (Exception e) {
                showErrorPopup("Error Adding Novel", e.getMessage());
            }
        }
    }

    private void handleAddJournal(String title, String abstractText, String publisher, String pages, String issue, String citations,
                                  LocalDate pubDate, String authors, String domainField, String publishingIntervalField) {

        if (validateJournalFields(title, abstractText, pages, publisher, issue, pages, pubDate, authors, domainField, publishingIntervalField)) {

            try {
                library.addItemToStock(LibraryItemType.JOURNAL, title, abstractText , Integer.parseInt(pages), publisher,
                        null, Integer.parseInt(issue), null, Integer.parseInt(citations), Date.valueOf(pubDate),
                        convertCommaSeparatedToList(authors), ResearchDomain.valueOf(domainField), null,
                        PublishingIntervals.valueOf(publishingIntervalField));

                showSuccessPopup("Journal Added To Stock", "The textbook has been successfully added to the library.");

            } catch (Exception e) {
                showErrorPopup("Error Adding Textbook", e.getMessage());
            }
        }
    }

    private void handleAddArticle(String title, String abstractText, String pages, String citations, LocalDate pubDate, String authors, String domainField) {

        if (validateArticleFields(title, abstractText, pages, citations, pubDate, authors, domainField)) {
            try {
                library.addItemToStock(LibraryItemType.ARTICLE, title, abstractText , Integer.parseInt(pages), null,
                        null, null, null, Integer.parseInt(citations), Date.valueOf(pubDate),
                        convertCommaSeparatedToList(authors), ResearchDomain.valueOf(domainField), null, null);

                showSuccessPopup("Article Added", "The article has been successfully added to the library.");

            } catch (Exception e) {
                showErrorPopup("Error Adding Article", e.getMessage());
            }
        }
    }



    private List<String> convertCommaSeparatedToList(String s) {
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .toList();
    }

    private void addLabeledTextField(VBox container, String labelText, String promptText, String id) {
        Label label = new Label(labelText);
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

    private void addLabeledDatePicker(VBox container, String labelText, String id) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");
        DatePicker datePicker = new DatePicker();
        datePicker.setId(id);
        container.getChildren().addAll(label, datePicker);
    }

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
                                   LocalDate pubDate, String authors, String domainField) {

        if (validateTextbookFields(title, description, pages, publisher, volume, edition, citations, pubDate, authors, domainField)) {

            try {
                library.addItemToStock(LibraryItemType.TEXTBOOK, title, description, Integer.parseInt(pages), null, Integer.parseInt(volume),
                        null, Integer.parseInt(edition), Integer.parseInt(citations), Date.valueOf(pubDate), convertCommaSeparatedToList(authors), ResearchDomain.valueOf(domainField),
                        null, null);

                showSuccessPopup("Textbook Added", "The textbook has been successfully added to the library.");
            } catch (Exception e) {
                showErrorPopup("Error Adding Textbook", e.getMessage());
            }
        }

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

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #008000; -fx-text-fill: white; -fx-font-size: 14;");
        button.setOnAction(event -> action.run());
        button.setMinWidth(200);
        return button;
    }

    private void goBackToMainMenu() {
        LibraryMenuScene menuScene = new LibraryMenuScene(primaryStage, library, loggedInUser);
        primaryStage.setScene(new Scene(menuScene.getRoot(), 600, 500));
    }

    private boolean validateTextbookFields(String title, String description, String pages, String publisher, String volume, String edition, String citations,
                                           LocalDate pubDate, String authors,  String domainField) {

        if (title == null || title.isEmpty()) {
            showErrorPopup("Invalid Input", "Please enter the title of the textbook.");
            return false;
        }

        if (description == null || description.isEmpty()) {
            showErrorPopup("Invalid Input", "Please provide a description for the textbook.");
            return false;
        }

        if (pages == null || pages.isEmpty() || !isValidNumber(pages)) {
            showErrorPopup("Invalid Input", "Please enter a valid number of pages for the textbook.");
            return false;
        }

        if (edition == null || edition.isEmpty() || !isValidNumber(edition)) {
            showErrorPopup("Invalid Input", "Please enter a valid edition for the textbook.");
            return false;
        }

        if (citations == null || citations.isEmpty() || !isValidNumber(citations)) {
            showErrorPopup("Invalid Input", "Please enter a valid citations number for the textbook.");
            return false;
        }

        if (publisher == null || publisher.isEmpty()) {
            showErrorPopup("Invalid Input", "Please enter the publisher of the textbook.");
            return false;
        }

        if (volume == null || volume.isEmpty() || !isValidNumber(volume)) {
            showErrorPopup("Invalid Input", "Please enter a valid volume for the textbook.");
            return false;
        }

        if (pubDate == null) {
            showErrorPopup("Invalid Input", "Please select the publication date of the textbook.");
            return false;
        }

        if (domainField == null) {
            showErrorPopup("Invalid Input","Please enter a domain for you textbook.");
            return false;
        }

        if (authors == null || authors.isEmpty()) {
            showErrorPopup("Invalid Input","Please enter at least one author for the textbook.");
            return false;
        }

        return true;
    }

    private boolean validateArticleFields(String title, String abstractText, String pages, String citations,
                                           LocalDate pubDate, String authors, String domainField) {

        if (title == null || title.isEmpty()) {
            showErrorPopup("Invalid Input", "Please enter the title of the article.");
            return false;
        }

        if (abstractText == null || abstractText.isEmpty()) {
            showErrorPopup("Invalid Input", "Please provide an abstract for the article.");
            return false;
        }

        if (pages == null || pages.isEmpty() || !isValidNumber(pages)) {
            showErrorPopup("Invalid Input", "Please enter a valid number of pages for the article.");
            return false;
        }

        if (pubDate == null) {
            showErrorPopup("Invalid Input", "Please select the publication date of the article.");
            return false;
        }

        if (authors == null || authors.isEmpty()) {
            showErrorPopup("Invalid Input","Please enter at least one author for the article.");
            return false;
        }

        if (citations == null || citations.isEmpty() || !isValidNumber(citations)) {
            showErrorPopup("Invalid Input", "Please enter a valid citations number for the article.");
            return false;
        }

        if (domainField == null) {
            showErrorPopup("Invalid Input","Please enter a domain for your article.");
            return false;
        }

        return true;
    }

    private boolean validateJournalFields(String title, String abstractText, String pages, String publisher, String issue,  String citations,
                                           LocalDate pubDate, String authors,  String domainField, String publishingInterval) {


        if (title == null || title.isEmpty()) {
            showErrorPopup("Invalid Input", "Please enter the title of the journal.");
            return false;
        }

        if (abstractText == null || abstractText.isEmpty()) {
            showErrorPopup("Invalid Input", "Please provide a description for the journal.");
            return false;
        }

        if (pages == null || pages.isEmpty() || !isValidNumber(pages)) {
            showErrorPopup("Invalid Input", "Please enter a valid number of pages for the journal.");
            return false;
        }

        if (publisher == null || publisher.isEmpty()) {
            showErrorPopup("Invalid Input", "Please enter the publisher of the journal.");
            return false;
        }

        if (pubDate == null) {
            showErrorPopup("Invalid Input", "Please select the publication date of the journal.");
            return false;
        }

        if (authors == null || authors.isEmpty()) {
            showErrorPopup("Invalid Input","Please enter at least one author for the journal.");
            return false;
        }

        if (issue == null || issue.isEmpty() || !isValidNumber(issue)) {
            showErrorPopup("Invalid Input", "Please enter a valid edition for the journal.");
            return false;
        }

        if (citations == null || citations.isEmpty() || !isValidNumber(citations)) {
            showErrorPopup("Invalid Input", "Please enter a valid citations number for the journal.");
            return false;
        }

        if (domainField == null) {
            showErrorPopup("Invalid Input","Please enter a domain for your journal.");
            return false;
        }

        if (publishingInterval == null) {
            showErrorPopup("Invalid Input","Please enter a domain for your journal.");
            return false;
        }

        return true;
    }

    private boolean validateNovelFields(String title, String description, String pages, String publisher, String volume,

                                           String edition, LocalDate pubDate, String authors,  String genreField) {

        if (title == null || title.isEmpty()) {
            showErrorPopup("Invalid Input", "Please enter the title of the novel.");
            return false;
        }

        if (description == null || description.isEmpty()) {
            showErrorPopup("Invalid Input", "Please provide a description for the novel.");
            return false;
        }

        if (pages == null || pages.isEmpty() || !isValidNumber(pages)) {
            showErrorPopup("Invalid Input", "Please enter a valid number of pages for the novel.");
            return false;
        }

        if (publisher == null || publisher.isEmpty()) {
            showErrorPopup("Invalid Input", "Please enter the publisher of the novel.");
            return false;
        }

        if (volume == null || volume.isEmpty() || !isValidNumber(volume)) {
            showErrorPopup("Invalid Input", "Please enter a valid volume for the novel.");
            return false;
        }

        if (edition == null || edition.isEmpty() || !isValidNumber(edition)) {
            showErrorPopup("Invalid Input", "Please enter a valid edition for the novel.");
            return false;
        }

        if (pubDate == null) {
            showErrorPopup("Invalid Input", "Please select the publication date of the novel.");
            return false;
        }

        if (authors == null || authors.isEmpty()) {
            showErrorPopup("Invalid Input","Please enter at least one author for the novel.");
            return false;
        }

        if (genreField == null) {
            showErrorPopup("Invalid Input","Please enter a genre for your novel.");
            return false;
        }

        return true;
    }


    private boolean isValidNumber(String number) {
        try {
            double doubleValue = Double.parseDouble(number);
            return doubleValue > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showSuccessPopup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorPopup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
