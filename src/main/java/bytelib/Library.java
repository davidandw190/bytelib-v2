package bytelib;

import bytelib.items.periodical.Periodical;
import bytelib.persistence.DBConnector;
import bytelib.security.PasswordEncoder;
import bytelib.users.Borrower;


import java.io.*;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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


    public void registerBorrower(String username, String password, String email, String phoneNo) {
        if (!isUsernameTaken(username) && !isEmailTaken(email)) {
            try {
                String sql = "INSERT INTO users (username, password, email, phone_no, role_id) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, PasswordEncoder.hashPassword(password));
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, phoneNo);
                    preparedStatement.setLong(5, 1);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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


    // Utility method to check if a periodical with the given details already exists
    private boolean canItemBeAdded(Periodical newPeriodical) {
        // TODO
        return false;
    }


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

//    private Optional<User> findBorrowerByUsernameOrEmail(String usernameOrEmail) {
//        return this.users.stream()
//                .filter(user -> user instanceof Borrower)
//                .filter(borrower -> borrower.getUsername().equals(usernameOrEmail) || borrower.getEmail().equals(usernameOrEmail))
//                .findFirst();
//    }
//
//    public void populateLibraryWithMockValues() {
//        Borrower mockBorrower1 = new Borrower("user1", "123", "borrower@email.com", "987654321");
//        Borrower mockBorrower2 = new Borrower("user2", "123", "mockuser@email.com", "987654321");
//        users.add(mockBorrower1);
//        users.add(mockBorrower2);
//
//        Book mockBook1 = new Novel("Mock Novel 1", "Mock Author", BookGenre.ASTRONOMY, 1, new Date());
//        Book mockBook2 = new Textbook("Mock Textbook 1", "Mock Author", ResearchDomain.COMPUTER_SCIENCE, 2,  new Date());
//        Book mockBook3 = new Novel("Mock Novel 2", "Mock Author", BookGenre.FANTASY, 2, new Date());
//        Book mockBook4 = new Textbook("Mock Textbook 2", "Mock Author", ResearchDomain.COMPUTER_SCIENCE, 1,  new Date());
//
//        booksCatalogue.add(mockBook1);
//        booksCatalogue.add(mockBook2);
//        booksCatalogue.add(mockBook3);
//        booksCatalogue.add(mockBook4);
//
//        ArrayList<String> authors = new ArrayList<>();
//        authors.add("author1");
//        authors.add("author2");
//
//        // Mock periodicals
//        Article mockArticle = new Article("Mock Article", new Date(), ResearchDomain.PHYSICS, 32, authors, "Publisher", "Abstract Text Here");
//        Journal mockJournal = new Journal("Mock Journal", new Date(), ResearchDomain.BIOLOGY, 12, PublishingIntervals.MONTHLY, "Publisher", 5, 3);
//
//        scientificCatalogue.add(mockArticle);
//        scientificCatalogue.add(mockJournal);
//
//    }
//
//
//    public void addBook(Book newBook) {
//        this.booksCatalogue.add(newBook);
//    }
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
