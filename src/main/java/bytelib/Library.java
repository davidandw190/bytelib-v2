package bytelib;

import bytelib.enums.*;
import bytelib.items.books.Textbook;
import bytelib.items.periodical.Article;
import bytelib.items.periodical.Journal;
import bytelib.persistence.DBConnector;
import bytelib.security.PasswordEncoder;
import bytelib.users.Borrower;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;


public class Library implements Serializable {
    private Connection dbConnection;

    private static final String LOGIN_QUERY =
            "SELECT * FROM users WHERE (username = ? OR email = ?) LIMIT 1";

    public static final String REGISTRATION_QUERY =
            "INSERT INTO users (username, email, password, phone_no, role_id) VALUES (?, ?, ?, ?, ?)";

    public Library(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    private void closeConnection() {
        DBConnector.closeConnection();
    }

    public Borrower loginBorrower(String usernameOrEmail, String password) {
        try (PreparedStatement statement = dbConnection.prepareStatement(LOGIN_QUERY)) {
            statement.setString(1, usernameOrEmail);
            statement.setString(2, usernameOrEmail);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    BigInteger userId = new BigInteger(resultSet.getString("user_id"));
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String phoneNo = resultSet.getString("phone_no");
                    String storedPasswordHash = resultSet.getString("password");

                    if (PasswordEncoder.verifyPassword(password, storedPasswordHash)) {
                        System.out.println("Credentials OK");
                        return new Borrower(userId, username, storedPasswordHash, email, phoneNo);
                    }
                }
            }

            System.out.println("Credentials NOT OK");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void registerUser(String username, String password, String email, String phoneNo, String userTypeName) {
        if (!isUsernameTaken(username) && !isEmailTaken(email)) {
            try {
                Long accountTypeId = getUserTypeIdByName(userTypeName);

                String sql = "INSERT INTO users (username, password, email, phone_no, role_id) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, PasswordEncoder.hashPassword(password));
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, phoneNo);
                    preparedStatement.setLong(5, accountTypeId);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Long getUserTypeIdByName(String typeName) {
        try {
            String sql = "SELECT type_id FROM user_type WHERE name = ?";
            try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
                preparedStatement.setString(1, typeName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getLong("type_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: Handle the exception properly or throw a custom exception
        }
        return null;
    }

    public UserType getUserType(BigInteger userId) {
        try {
            String sql = "SELECT user_type.name FROM users JOIN user_type ON users.role_id = user_type.type_id WHERE user_id = ?";
            try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
                preparedStatement.setBigDecimal(1, new BigDecimal(userId));
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String userType = resultSet.getString("name");
                        return UserType.valueOf(userType);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: Handle the exception properly or throw a custom exception
        }
        return null;
    }

//    // Utility method to check if a book with the given title already exists
//    private boolean canItemBeAdded(LibraryItem newItem) {
//        if (newItem instanceof Book) {
//            return canBookBeAdded((Book) newItem);
//        } else if (newItem instanceof Periodical) {
//            return canPeriodicalBeAdded((Periodical) newItem);
//        } else {
//            throw new IllegalArgumentException("Invalid item type");
//            // TODO: add custom exception here
//        }
//    }


//    public void requestBorrow(Borrower borrower, Borrowable borrowable) throws ItemNotAvailableForBorrowException {
//        if (borrowable.isAvailable()) {
//            BorrowRequest borrowRequest = borrowable.initiateBorrowRequest(borrower);
//            borrowRequests.add(borrowRequest);
//            borrowable.setStatusBorrowed();
//        } else {
//            throw new ItemNotAvailableForBorrowException("Book not available for borrowing.");
//        }
//    }
//
//    public void acceptBorrowRequest(BorrowRequest borrowRequest) {
//        borrowRequest.acceptRequest(bookReturnDeadline);
//    }
//
//    public double getReturnFee(BorrowRequest request) {
//        return request.computeReturnFee(perDayFine, bookReturnDeadline);
//    }
//
//    public void rejectBorrowRequest(BorrowRequest borrowRequest) {
//        borrowRequest.rejectRequest();
//    }
//
//    public void returnBorrowedItem(BorrowRequest borrowRequest) {
//        borrowRequest.returnItem();
//        double returnFee = borrowRequest.computeReturnFee(this.perDayFine, this.bookReturnDeadline);
//        System.out.println("asadas");
//    }
//
////    public void payOverdueFee(BorrowRequest borrowRequest, double fee) {
////        borrowRequest.getBorrowable().setStatusAvailable();
////        borrowRequest.getBorrower().
////
////    }
//
//    public void checkOverdueItems() {
//        for (BorrowRequest borrowRequest : borrowRequests) {
//            if (borrowRequest.checkIfOverdue()) {
//                double fine = borrowRequest.computeReturnFee((int) perDayFine, bookReturnDeadline);
//                System.out.println("Item overdue! Fine: " + fine);
//            }
//        }
//    }
//
//    public List<BorrowRequest> getUserBorrowRequests(Borrower borrower) {
//        return borrowRequests.stream()
//                .filter(request -> request.getBorrower().equals(borrower))
//                .collect(Collectors.toList());
//    }
//
//
    public boolean isUsernameTaken(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            // TODO throw proper custom exception
        }
    }

    public boolean isEmailTaken(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            // TODO throw proper custom exception
        }
    }

    public boolean addItemToStock(LibraryItemType itemType,
                                  String title,
                                  String description,
                                  Integer pages,
                                  String publisher,
                                  Integer volume,
                                  Integer issue,
                                  Integer edition,
                                  Integer citations,
                                  LocalDate pubDate,
                                  List<String> authors,
                                  ResearchDomain domain,
                                  BookGenre genre,
                                  PublishingIntervals publishingInterval) {
        try {
            dbConnection.setAutoCommit(false);

            if (title == null || title.isEmpty() || description == null || description.isEmpty()) {
                throw new IllegalArgumentException("Title and description cannot be empty");
            }

            long itemTypeId = -1;

            assert itemType != null;

            switch (itemType) {
                case NOVEL -> {
                    itemTypeId = getItemTypeIdByName(LibraryItemType.NOVEL.name());

                    if (isItemExists(title, itemTypeId)) {
                        System.out.println(itemType.name() + " with title '" + title + "' already exists.");
                        return false;
                    }

                    Long genreId = getGenreIdByName(genre.name());

                    Textbook textbook = new Textbook(title, description, pages, publisher, volume, edition, citations, pubDate, authors);

                    insertTextbook(textbook, genreId, itemTypeId);

                }

                case ARTICLE -> {
                    itemTypeId = getItemTypeIdByName(LibraryItemType.ARTICLE.name());


                    if (isItemExists(title, itemTypeId)) {
                        System.out.println(itemType.name() + " with title '" + title + "' already exists.");
                        return false;
                    }


                    Article newArticle = new Article(title, description, publisher, pages, citations, pubDate, authors, domain);

                    Long topicId = getTopicIdByName(domain.name());

                    insertArticle(newArticle, topicId, itemTypeId);
                }

                case TEXTBOOK -> {
                    itemTypeId = getItemTypeIdByName(LibraryItemType.TEXTBOOK.name());

                    if (isItemExists(title, itemTypeId)) {
                        System.out.println(itemType.name() + " with title '" + title + "' already exists.");
                        return false;
                    }

                    Long topicId = getTopicIdByName(domain.name());

                    Textbook textbook = new Textbook(title, description, pages, publisher, volume, edition, citations, pubDate, authors);

                    insertTextbook(textbook, topicId, itemTypeId);
                }

                case JOURNAL -> {
                    itemTypeId = getItemTypeIdByName(LibraryItemType.JOURNAL.name());

                    if (isItemExists(title, itemTypeId)) {
                        System.out.println(itemType.name() + " with title '" + title + "' already exists.");
                        return false;
                    }

                    Journal newJournal = new Journal(title, description, publisher, volume, issue, pages, citations, pubDate, authors, domain, publishingInterval);

                    Long topicId = getTopicIdByName(domain.name());

                    Long publishingIntervalId = getPublishingIntervalIdByName(publishingInterval.name());

                    insertJournal(newJournal, topicId, itemTypeId, publishingIntervalId);
                }
            }

            dbConnection.commit();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                dbConnection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            return false;

        } finally {
            try {
                dbConnection.setAutoCommit(true);

            } catch (SQLException autoCommitException) {
                autoCommitException.printStackTrace();
            }
        }
    }

    private Long getGenreIdByName(String name) {
        String sql = "SELECT genre_id FROM book_genre WHERE display_name = ?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("genre_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private Long getPublishingIntervalIdByName(String name) {
        String sql = "SELECT interval_id FROM publishing_intervals WHERE display_name = ?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("type_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private void insertJournal(Journal newJournal, Long topicId, Long itemTypeId, Long publishingIntervalId) {
        String insertQuery = "INSERT INTO library_items (title, description, publisher, volume, issue, page_no, citation_no, pub_date, topic_id, item_type_id, publishing_interval_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, newJournal.getTitle());
            preparedStatement.setString(2, newJournal.getAbstractText());
            preparedStatement.setString(3, newJournal.getPublisher());
            preparedStatement.setInt(4, newJournal.getVolume());
            preparedStatement.setInt(5, newJournal.getIssue());
            preparedStatement.setInt(6, newJournal.getPageNumber());
            preparedStatement.setLong(7, newJournal.getNumberOfCitations());
            preparedStatement.setDate(8, Date.valueOf(newJournal.getPublicationDate()));
            preparedStatement.setLong(9, topicId);
            preparedStatement.setLong(10, itemTypeId);
            preparedStatement.setLong(11, publishingIntervalId);
            preparedStatement.executeUpdate();


            // get the generated item_id
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long itemId = generatedKeys.getLong(1);
                updateLibraryItemAuthors(itemId, newJournal.getAuthors());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Long getItemTypeIdByName(String typeName) throws SQLException {
        String sql = "SELECT type_id FROM item_type WHERE display_name = ?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
            preparedStatement.setString(1, typeName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("type_id");
                }
            }
        }
        return null;
    }

    private Long getTopicIdByName(String topicName) throws SQLException {
        String sql = "SELECT domain_id FROM research_domain WHERE display_name = ?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
            preparedStatement.setString(1, topicName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("domain_id");
                }
            }
        }
        return null;
    }

    public Long getIdByName(String typeName) {
        try {
            String sql = "SELECT type_id FROM user_type WHERE display_name = ?";
            try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
                preparedStatement.setString(1, typeName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getLong("type_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: Handle the exception properly or throw a custom exception
        }
        return null;
    }

    private boolean isItemExists(String title, Long itemTypeId) throws SQLException {
        String query = "SELECT COUNT(*) FROM library_items WHERE title = ? AND item_type_id = ?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setLong(2, itemTypeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    private void insertTextbook(Textbook textbook, Long topicId, Long itemTypeId) throws SQLException {
        String insertQuery = "INSERT INTO library_items (title, description, page_no, publisher, edition, citation_no, pub_date, topic_id, item_type_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, textbook.getTitle());
            preparedStatement.setString(2, textbook.getDescription());
            preparedStatement.setInt(3, textbook.getNumberOfPages());
            preparedStatement.setString(4, textbook.getPublisher());
            preparedStatement.setInt(5, textbook.getEdition());
            preparedStatement.setLong(6, textbook.getNumberOfCitations());
            preparedStatement.setDate(7, Date.valueOf(textbook.getPublicationDate()));
            preparedStatement.setLong(9, topicId);
            preparedStatement.setLong(10, itemTypeId);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long itemId = generatedKeys.getLong(1);
                updateLibraryItemAuthors(itemId, textbook.getAuthors());
            }
        }
    }

    private void updateLibraryItemAuthors(long itemId, List<String> authors) throws SQLException {
        for (String authorName : authors) {
            long authorId = getAuthorIdByName(authorName.trim());
            if (authorId == -1) {
                authorId = insertAuthor(authorName.trim());
            }

            String updateAuthorsQuery = "INSERT INTO library_item_authors (item_id, author_id) VALUES (?, ?)";
            try (PreparedStatement updateAuthorsStatement = dbConnection.prepareStatement(updateAuthorsQuery)) {
                updateAuthorsStatement.setLong(1, itemId);
                updateAuthorsStatement.setLong(2, authorId);
                updateAuthorsStatement.executeUpdate();
            }
        }
    }

    private long getAuthorIdByName(String authorName) throws SQLException {
        String sql = "SELECT author_id FROM authors WHERE author_name = ?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
            preparedStatement.setString(1, authorName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("author_id");
                }
            }
        }
        return -1;
    }

    private long insertAuthor(String authorName) throws SQLException {
        String insertAuthorQuery = "INSERT INTO authors (author_name) VALUES (?)";
        try (PreparedStatement insertAuthorStatement = dbConnection.prepareStatement(insertAuthorQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertAuthorStatement.setString(1, authorName);
            insertAuthorStatement.executeUpdate();

            ResultSet generatedKeys = insertAuthorStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
        }
        return -1;
    }


    private void insertArticle(Article newArticle, Long topicId, Long itemTypeId) {
        String insertQuery = "INSERT INTO library_items (title, description, page_no, citation_no, pub_date, topic_id, item_type_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, newArticle.getTitle());
            preparedStatement.setString(2, newArticle.getAbstractText());
            preparedStatement.setInt(3, newArticle.getPageNumber());
            preparedStatement.setLong(4, newArticle.getNumberOfCitations());
            preparedStatement.setDate(5, Date.valueOf(newArticle.getPublicationDate()));
            preparedStatement.setLong(6, topicId);
            preparedStatement.setLong(7, itemTypeId);
            preparedStatement.executeUpdate();

            // get the generated item_id
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long itemId = generatedKeys.getLong(1);
                updateLibraryItemAuthors(itemId, newArticle.getAuthors());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//
//    public Book findBookById(BigInteger bookId) throws ItemNotFoundException {
//        for (Book book : booksCatalogue) {
//            if (book.getId().equals(bookId)) {
//                return book;
//            }
//        }
//        throw new ItemNotFoundException("Book with ID " + bookId + " not found.");
//    }
//
//    public Periodical findPeriodicalById(BigInteger itemId) throws ItemNotFoundException {
//        for (Periodical item : scientificCatalogue) {
//            if (item.getId().equals(itemId)) {
//                return item;
//            }
//        }
//        throw new ItemNotFoundException("Item with ID " + itemId + " not found.");
//    }
//
//    public void addPeriodical(Periodical newPeriodical) {
//        this.scientificCatalogue.add(newPeriodical);
//
//    }
//
//    public void removeBook(Book selectedBook) throws ItemCurrentlyBorrowedException {
//        if (!selectedBook.isAvailable()) {
//            throw new ItemCurrentlyBorrowedException("The item you want to remove is currently borrowed by a user.");
//        }
//
//        booksCatalogue.remove(selectedBook);
//    }
//
//    public void removePeriodical(Periodical selectedItem) {
//        scientificCatalogue.remove(selectedItem);
//    }

}
