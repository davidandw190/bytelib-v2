package bytelib.scenes;

import bytelib.Library;
import bytelib.enums.UserType;
import bytelib.items.*;
import bytelib.items.books.Novel;
import bytelib.items.books.Textbook;
import bytelib.items.periodical.Article;
import bytelib.items.periodical.Journal;
import bytelib.items.periodical.Periodical;
import bytelib.users.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.List;

public class DisplayItemsScene {
    private final VBox root;
    private final Stage primaryStage;
    private final Library library;
    private final UserType userType;
    private final User loggedInUser;

    public DisplayItemsScene(Stage primaryStage, Library library, User loggedInUser) {
        this.primaryStage = primaryStage;
        this.library = library;
        this.userType = library.getUserType(loggedInUser.getUserId());
        this.loggedInUser = loggedInUser;

        root = new VBox(20);
        root.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20;");
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Scientific Catalogue");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        root.getChildren().add(titleLabel);

        List<LibraryItem> itemsToDisplay = getScientificCatalogue();
        displayItems(itemsToDisplay);

        Button backButton = createButton("Back to Main Menu", this::goBackToMainMenu);
        root.getChildren().add(backButton);
    }

    private List<LibraryItem> getScientificCatalogue() {
        return library.getScientificItemsAndSortByTitle();
    }

    private void displayItems(List<LibraryItem> items) {
        TableView<LibraryItem> tableView = new TableView<>();
        ObservableList<LibraryItem> observableItems = FXCollections.observableArrayList(items);

        TableColumn<LibraryItem, Integer> indexColumn = new TableColumn<>("#");
        indexColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(tableView.getItems().indexOf(param.getValue()) + 1));

        TableColumn<LibraryItem, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue() instanceof Journal ? "Journal" :
                (param.getValue() instanceof Article ? "Article" : "Textbook")));

        TableColumn<LibraryItem, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Add more columns based on the properties of LibraryItem'


        TableColumn<LibraryItem, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> {
            LibraryItem item = cellData.getValue();
            if (item instanceof Article) {
                return new SimpleStringProperty(String.join(", ", ((Article) item).getAuthors()));
            } else if (item instanceof Journal) {
                return new SimpleStringProperty(String.join(", ", ((Journal) item).getAuthors()));
            } else if (item instanceof Novel) {
                return new SimpleStringProperty(String.join(", ", ((Novel) item).getAuthors()));
            } else if (item instanceof Textbook) {
                return new SimpleStringProperty(String.join(", ", ((Textbook) item).getAuthors()));
            } else {
                return new SimpleStringProperty("");
            }
        });

//        TableColumn<LibraryItem, String> citationsColumn = new TableColumn<>("Citations");
//        citationsColumn.setCellValueFactory(param -> {
//            if (param.getValue() instanceof Citeable) {
//                long numberOfCitations = ((Citeable) param.getValue()).getNumberOfCitations();
//                return new SimpleObjectProperty<>(numberOfCitations > 0
//                        ? String.valueOf(numberOfCitations)
//                        : "-");
//            } else {
//                return new SimpleObjectProperty<>("-");
//            }
//        });
//
//        TableColumn<LibraryItem, String> editionColumn = new TableColumn<>("Edition");
//        editionColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue() instanceof Textbook
//                ? String.valueOf(((Textbook) param.getValue()).getEdition())
//                : "-"));
//
//
//        TableColumn<LibraryItem, String> issueColumn = new TableColumn<>("Issue");
//        issueColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue() instanceof Journal
//                ? String.valueOf(((Journal) param.getValue()).getIssue())
//                : "-"));
//
//        TableColumn<LibraryItem, String> pagesColumn = new TableColumn<>("Pages");
//        pagesColumn.setCellValueFactory(new PropertyValueFactory<>("pageNumber"));
//
//        TableColumn<LibraryItem, Date> publicationDateColumn = new TableColumn<>("Publication Date");
//        publicationDateColumn.setCellValueFactory(param -> {
//            Date publicationDate;
//            if (param.getValue() instanceof Periodical) {
//                publicationDate = ((Periodical) param.getValue()).getPublicationDate();
//            } else {
//                publicationDate = param.getValue().getPublicationDate();
//            }
//            return new SimpleObjectProperty<>(publicationDate);
//        });
//
//        TableColumn<LibraryItem, String> availabilityColumn = new TableColumn<>("Availability");
//        availabilityColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().isAvailable() ? "Available" : "Not Available"));

        TableColumn<LibraryItem, String> citationsColumn = new TableColumn<>("Citations");
        citationsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(getCitations(param.getValue())));

        TableColumn<LibraryItem, String> editionColumn = new TableColumn<>("Edition");
        editionColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(getEdition(param.getValue())));

        TableColumn<LibraryItem, String> issueColumn = new TableColumn<>("Issue");
        issueColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(getIssue(param.getValue())));

        TableColumn<LibraryItem, String> pagesColumn = new TableColumn<>("Pages");
        pagesColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(getPages(param.getValue())));

        TableColumn<LibraryItem, Date> publicationDateColumn = new TableColumn<>("Publication Date");
        publicationDateColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(getPublicationDate(param.getValue())));

        TableColumn<LibraryItem, String> availabilityColumn = new TableColumn<>("Availability");
        availabilityColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(isAvailable(param.getValue())));

        tableView.getColumns().addAll(indexColumn, typeColumn, titleColumn, authorColumn, citationsColumn,
                editionColumn, issueColumn, pagesColumn, publicationDateColumn, availabilityColumn);


        tableView.setItems(observableItems);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefWidth(900);

        VBox.setVgrow(tableView, Priority.ALWAYS);

        root.getChildren().add(tableView);
    }

    public VBox getRoot() {
        return root;
    }

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14;");
        button.setOnAction(event -> action.run());
        button.setMinWidth(200);
        return button;
    }

    private void goBackToMainMenu() {
        LibraryMenuScene menuScene = new LibraryMenuScene(primaryStage, library, loggedInUser);
        primaryStage.setScene(new Scene(menuScene.getRoot(), 600, 500));
    }

    private String getCitations(LibraryItem item) {
        if (item instanceof Citeable) {
            long numberOfCitations = ((Citeable) item).getNumberOfCitations();
            return numberOfCitations > 0 ? String.valueOf(numberOfCitations) : "-";
        } else {
            return "-";
        }
    }

    private String getEdition(LibraryItem item) {
        return (item instanceof Textbook)
                ? String.valueOf(((Textbook) item).getEdition())
                : "-";
    }

    private String getIssue(LibraryItem item) {
        if (item instanceof Journal) {
            return String.valueOf(((Journal) item).getIssue());
        } else {
          return "-";
        }
    }

    private String getPages(LibraryItem item) {
        return String.valueOf(item.getPageNumber());
    }

    private Date getPublicationDate(LibraryItem item) {
        return item.getPublicationDate();
    }

    private String isAvailable(LibraryItem item) {
        return item.isAvailable() ? "Available" : "Not Available";
    }
}
