package bytelib.scenes;

import bytelib.Library;
import bytelib.enums.CatalogueType;
import bytelib.enums.UserType;
import bytelib.items.Citeable;
import bytelib.items.LibraryItem;
import bytelib.items.books.Novel;
import bytelib.items.books.Textbook;
import bytelib.items.periodical.Article;
import bytelib.items.periodical.Journal;
import bytelib.users.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.List;

public class DisplayItemsScene {
    private final VBox root;
    private final Stage primaryStage;
    private final Library library;
    private final UserType userType;
    private final User loggedInUser;
    private final CatalogueType catalogueType;

    public DisplayItemsScene(Stage primaryStage, Library library, User loggedInUser, CatalogueType catalogueType) {
        this.primaryStage = primaryStage;
        this.library = library;
        this.userType = library.getUserType(loggedInUser.getUserId());
        this.loggedInUser = loggedInUser;
        this.catalogueType = catalogueType;

        root = new VBox(20);
        root.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20;");
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(catalogueType.getLabel());
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        root.getChildren().add(titleLabel);

        List<LibraryItem> itemsToDisplay = getCatalogueItems();
        displayCatalogueItems(itemsToDisplay);

        Button backButton = createButton("Back to Main Menu", this::goBackToMainMenu);
        root.getChildren().add(backButton);
    }

    private List<LibraryItem> getCatalogueItems() {
        return catalogueType == CatalogueType.SCIENTIFIC
                ? library.getScientificItemsAndSortByTitle()
                : library.getBooksCatalogueItemsAndSortByTitle();
    }

    private void displayCatalogueItems(List<LibraryItem> items) {
        if (catalogueType == CatalogueType.SCIENTIFIC) {
            displayScientificItems(items);
        } else {
            displayBooks(items);
        }
    }

    private void displayScientificItems(List<LibraryItem> items) {
        TableView<LibraryItem> tableView = new TableView<>();
        ObservableList<LibraryItem> observableItems = FXCollections.observableArrayList(items);

        TableColumn<LibraryItem, Void> citationButtonColumn = new TableColumn<>("Cite");
        citationButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button citeButton = new Button("Cite");

            {
                citeButton.setOnAction(event -> citeSelectedItem(tableView, getIndex()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(citeButton);
                }
            }
        });

        TableColumn<LibraryItem, Void> removeButtonColumn = new TableColumn<>("Remove");
        removeButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button removeButton = new Button("Remove");

            {
                removeButton.setOnAction(event -> removeSelectedItem(tableView, getIndex()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });

        TableColumn<LibraryItem, Integer> indexColumn = new TableColumn<>("#");
        indexColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(tableView.getItems().indexOf(param.getValue()) + 1));

        TableColumn<LibraryItem, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue() instanceof Journal ? "Journal" :
                (param.getValue() instanceof Article ? "Article" : "Textbook")));

        TableColumn<LibraryItem, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));


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


        if (userType == UserType.BORROWER) {
            tableView.getColumns().addAll(indexColumn, typeColumn, titleColumn, authorColumn, citationsColumn,
                    editionColumn, issueColumn, pagesColumn, publicationDateColumn, citationButtonColumn, availabilityColumn);
        } else if (userType == UserType.LIBRARIAN) {
            tableView.getColumns().addAll(indexColumn, typeColumn, titleColumn, authorColumn, citationsColumn,
                    editionColumn, issueColumn, pagesColumn, publicationDateColumn, availabilityColumn, citationButtonColumn, removeButtonColumn);
        }


        tableView.setItems(observableItems);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefWidth(900);

        VBox.setVgrow(tableView, Priority.ALWAYS);

        root.getChildren().add(tableView);
    }

    private void citeSelectedItem(TableView tableView, int rowIndex) {
        LibraryItem selectedItem = (LibraryItem) tableView.getItems().get(rowIndex);
        System.out.println("CITED ITEM: " + selectedItem.getTitle());

        Long itemId = selectedItem.getId();

        try {
            if (selectedItem instanceof Citeable) {
                library.addCitationToItem(itemId, 1);
                refreshTableView(tableView);
                displayCitationMessage(((Citeable) selectedItem).getCitation());
            }
        } catch (Exception e) {
            showErrorPopup("Error", "Error while citing item. Please try again later.");
        }


    }

    private void removeSelectedItem(TableView<LibraryItem> tableView, int rowIndex) {
        LibraryItem selectedItem = tableView.getItems().get(rowIndex);
        System.out.println("REMOVED ITEM: " + selectedItem.getTitle());

        Long itemId = selectedItem.getId();

        try {
            library.removeItem(itemId);
            tableView.getItems().remove(rowIndex);
            tableView.refresh();
            displayItemRemovedMessage(selectedItem.getTitle());
        } catch (Exception e) {

        }

        refreshTableView(tableView);

    }

    private void displayItemRemovedMessage(String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Removal Successful");
        alert.setHeaderText(null);
        alert.setContentText("Item with name '" + title +"' removed successfully");

        alert.showAndWait();
    }

    private void refreshTableView(TableView tableView) {
        tableView.refresh();
    }

    private void displayCitationMessage(String citation) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Citation Successful");
        alert.setHeaderText(null);
        alert.setContentText("CITATION: " + citation);

        alert.showAndWait();
    }

    private void displayBooks(List<LibraryItem> items) {
        TableView<LibraryItem> tableView = new TableView<>();
        ObservableList<LibraryItem> observableItems = FXCollections.observableArrayList(items);

        TableColumn<LibraryItem, Integer> indexColumn = new TableColumn<>("#");
        indexColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(tableView.getItems().indexOf(param.getValue()) + 1));

        TableColumn<LibraryItem, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue() instanceof Textbook
                ? "Textbook"
                : "Novel")
        );

        TableColumn<LibraryItem, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));


        TableColumn<LibraryItem, String> authorColumn = new TableColumn<>("Author(s)");
        authorColumn.setCellValueFactory(cellData -> {
            LibraryItem item = cellData.getValue();
            if (item instanceof Textbook) {
                return new SimpleStringProperty(String.join(", ", ((Textbook) item).getAuthors()));

            } else if (item instanceof Novel) {
                return new SimpleStringProperty(String.join(", ", ((Novel) item).getAuthors()));

            } else {
                return new SimpleStringProperty("");
            }
        });

        TableColumn<LibraryItem, Void> removeButtonColumn = new TableColumn<>("Remove");
        removeButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button removeButton = new Button("Remove");

            {
                removeButton.setOnAction(event -> removeSelectedItem(tableView, getIndex()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });

        TableColumn<LibraryItem, String> citationsColumn = new TableColumn<>("Citations");
        citationsColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(getCitations(param.getValue())));

        TableColumn<LibraryItem, String> volumeColumn = new TableColumn<>("Volume");
        volumeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(getVolume(param.getValue())));

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

        if (userType == UserType.BORROWER) {
            tableView.getColumns().addAll(indexColumn, typeColumn, titleColumn, authorColumn, citationsColumn,
                    editionColumn, issueColumn, pagesColumn, publicationDateColumn, availabilityColumn);
        } else if (userType == UserType.LIBRARIAN) {
            tableView.getColumns().addAll(indexColumn, typeColumn, titleColumn, authorColumn, citationsColumn,
                    editionColumn, issueColumn, pagesColumn, publicationDateColumn, availabilityColumn, removeButtonColumn);
        }

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
        button.setStyle("-fx-background-color: #008000; -fx-text-fill: white; -fx-font-size: 14;");
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

    private String getVolume(LibraryItem item) {
        return (item instanceof Novel)
                ? String.valueOf(((Novel) item).getVolume())
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
        return item.isAvailable() ? "AVAILABLE" : "BORROWED";
    }

    private void showErrorPopup(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
