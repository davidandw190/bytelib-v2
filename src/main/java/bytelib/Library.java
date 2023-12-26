package bytelib;

import bytelib.enums.BookGenre;
import bytelib.enums.PublishingIntervals;
import bytelib.enums.ResearchDomain;
import bytelib.exceptions.ItemCurrentlyBorrowedException;
import bytelib.exceptions.ItemNotAvailableForBorrowException;
import bytelib.exceptions.ItemNotFoundException;
import bytelib.items.Borrowable;
import bytelib.items.Citeable;
import bytelib.items.LibraryItem;
import bytelib.items.books.Book;
import bytelib.items.books.Novel;
import bytelib.items.books.Textbook;
import bytelib.items.periodical.Article;
import bytelib.items.periodical.Journal;
import bytelib.items.periodical.Periodical;
import bytelib.users.Borrower;
import bytelib.users.Librarian;
import bytelib.users.User;


import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;


public class Library implements Serializable {
    private String name;
    private Librarian librarian;
    private List<User> users;

    private List<Book> booksCatalogue;
    private List<Periodical> scientificCatalogue;

    private List<BorrowRequest> borrowRequests;

    private double perDayFine;
    private int bookReturnDeadline;

    public Library(String name, Librarian librarian, int bookReturnDeadline, double perDayFine) {
        this.name = name;
        this.librarian = librarian;
        this.bookReturnDeadline = bookReturnDeadline;
        this.perDayFine = perDayFine;
        this.booksCatalogue = new LinkedList<>();
        this.scientificCatalogue = new LinkedList<>();
        this.borrowRequests = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public Library() {}

    public void saveData(String filePath, String format) {
        if (format.equalsIgnoreCase("objectstream")) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
                saveToStream(objectOutputStream);
                System.out.println("[*] Library saved successfully to " + filePath);
            } catch (IOException e) {
                System.out.println("Error saving library to " + filePath + ": " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Invalid format specified.");
        }
    }

    public void loadData(String filePath, String format) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath));

            if (format.equalsIgnoreCase("objectstream")) {
                loadFromStream(objectInputStream);
            } else {
                throw new IllegalArgumentException("Invalid format specified.");
            }

            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveToStream(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this);
    }

    private void loadFromStream(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Library library = (Library) objectInputStream.readObject();

        // to update the static currentId fields for User and LibraryItem
        User.setCurrentIdNumber(library.getCurrentUserId());
        LibraryItem.setCurrentIdNumber(library.getCurrentLibraryItemId());

        // to replace the current library with the loaded library
        this.name = library.getName();
        this.librarian = library.getLibrarian();
        this.users = library.getUsers();
        this.booksCatalogue = library.getBooksCatalogue();
        this.scientificCatalogue = library.getScientificCatalogue();
        this.borrowRequests = library.getBorrowRequests();
        this.perDayFine = library.getPerDayFine();
        this.bookReturnDeadline = library.getBookReturnDeadline();
    }

    private BigInteger getCurrentUserId() {
        if (!this.users.isEmpty()) {
            return this.users.get(this.users.size() - 1).getUserId();
        }
        return BigInteger.ZERO;
    }

    private BigInteger getCurrentLibraryItemId() {
        BigInteger currBookId = !this.booksCatalogue.isEmpty() ?
                this.booksCatalogue.get(this.booksCatalogue.size() - 1).getId() :
                BigInteger.ZERO;

        BigInteger currScientificId = !this.scientificCatalogue.isEmpty() ?
                this.scientificCatalogue.get(this.scientificCatalogue.size() - 1).getId() :
                BigInteger.ZERO;

        return currBookId.max(currScientificId);
    }

    public List<Citeable> getCitableBooks() {
        List<Citeable> citeableBooks = new ArrayList<>();
        for (LibraryItem item : booksCatalogue) {
            if (item instanceof Citeable) {
                citeableBooks.add((Citeable) item);
            }
        }
        return citeableBooks;
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


    // Utility method to check if a book with the given details already exists
    private boolean canBookBeAdded(Book newBook) {
        for (LibraryItem existingItem : this.booksCatalogue) {
            if (newBook instanceof Novel && existingItem instanceof Novel && ((Novel) newBook).equals((Novel) existingItem)) {
                return false; // A book with the same details already exists
            } else if (newBook instanceof Textbook && existingItem instanceof Textbook && ((Textbook) newBook).equals((Textbook) existingItem)) {
                return false;
            }
        }
        return true;
    }

    // Utility method to check if a periodical with the given details already exists
    private boolean canPeriodicalBeAdded(Periodical newPeriodical) {
        for (LibraryItem existingItem : this.scientificCatalogue) {
            if (newPeriodical instanceof Article && existingItem instanceof Article && ((Article) newPeriodical).equals((Article) existingItem)) {
                return false; // An article with the same details already exists
            } else if (newPeriodical instanceof Journal && existingItem instanceof Journal && ((Journal) newPeriodical).equals((Journal) existingItem)) {
                return false;
            }
        }
        return true;
    }


    public void requestBorrow(Borrower borrower, Borrowable borrowable) throws ItemNotAvailableForBorrowException {
        if (borrowable.isAvailable()) {
            BorrowRequest borrowRequest = borrowable.initiateBorrowRequest(borrower);
            borrowRequests.add(borrowRequest);
            borrowable.setStatusBorrowed();
        } else {
            throw new ItemNotAvailableForBorrowException("Book not available for borrowing.");
        }
    }

    public void acceptBorrowRequest(BorrowRequest borrowRequest) {
        borrowRequest.acceptRequest(bookReturnDeadline);
    }

    public double getReturnFee(BorrowRequest request) {
        return request.computeReturnFee(perDayFine, bookReturnDeadline);
    }

    public void rejectBorrowRequest(BorrowRequest borrowRequest) {
        borrowRequest.rejectRequest();
    }

    public void returnBorrowedItem(BorrowRequest borrowRequest) {
        borrowRequest.returnItem();
        double returnFee = borrowRequest.computeReturnFee(this.perDayFine, this.bookReturnDeadline);
        System.out.println("asadas");
    }

//    public void payOverdueFee(BorrowRequest borrowRequest, double fee) {
//        borrowRequest.getBorrowable().setStatusAvailable();
//        borrowRequest.getBorrower().
//
//    }

    public void checkOverdueItems() {
        for (BorrowRequest borrowRequest : borrowRequests) {
            if (borrowRequest.checkIfOverdue()) {
                double fine = borrowRequest.computeReturnFee((int) perDayFine, bookReturnDeadline);
                System.out.println("Item overdue! Fine: " + fine);
            }
        }
    }

    public List<BorrowRequest> getUserBorrowRequests(Borrower borrower) {
        return borrowRequests.stream()
                .filter(request -> request.getBorrower().equals(borrower))
                .collect(Collectors.toList());
    }


    public Borrower loginBorrower(String usernameOrEmail, String password) {
        // Try to find the user by username or email
        Borrower borrower = (Borrower) findBorrowerByUsernameOrEmail(usernameOrEmail).orElseThrow();

        if (borrower.checkCredentials(usernameOrEmail, password)) {
            System.out.println("credentials OK");
            return borrower;
        }

        System.out.println("credentials NOT OK");

        return null;
    }

    private Optional<User> findBorrowerByUsernameOrEmail(String usernameOrEmail) {
        return this.users.stream()
                .filter(user -> user instanceof Borrower)
                .filter(borrower -> borrower.getUsername().equals(usernameOrEmail) || borrower.getEmail().equals(usernameOrEmail))
                .findFirst();
    }

    public void populateLibraryWithMockValues() {
        Borrower mockBorrower1 = new Borrower("user1", "123", "borrower@email.com", "987654321");
        Borrower mockBorrower2 = new Borrower("user2", "123", "mockuser@email.com", "987654321");
        users.add(mockBorrower1);
        users.add(mockBorrower2);

        Book mockBook1 = new Novel("Mock Novel 1", "Mock Author", BookGenre.ASTRONOMY, 1, new Date());
        Book mockBook2 = new Textbook("Mock Textbook 1", "Mock Author", ResearchDomain.COMPUTER_SCIENCE, 2,  new Date());
        Book mockBook3 = new Novel("Mock Novel 2", "Mock Author", BookGenre.FANTASY, 2, new Date());
        Book mockBook4 = new Textbook("Mock Textbook 2", "Mock Author", ResearchDomain.COMPUTER_SCIENCE, 1,  new Date());

        booksCatalogue.add(mockBook1);
        booksCatalogue.add(mockBook2);
        booksCatalogue.add(mockBook3);
        booksCatalogue.add(mockBook4);

        ArrayList<String> authors = new ArrayList<>();
        authors.add("author1");
        authors.add("author2");

        // Mock periodicals
        Article mockArticle = new Article("Mock Article", new Date(), ResearchDomain.PHYSICS, 32, authors, "Publisher", "Abstract Text Here");
        Journal mockJournal = new Journal("Mock Journal", new Date(), ResearchDomain.BIOLOGY, 12, PublishingIntervals.MONTHLY, "Publisher", 5, 3);

        scientificCatalogue.add(mockArticle);
        scientificCatalogue.add(mockJournal);

    }

    public void addUser(Borrower user) {
        users.add(user);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Book> getBooksCatalogue() {
        return booksCatalogue;
    }

    public void setBooksCatalogue(List<Book> booksCatalogue) {
        this.booksCatalogue = booksCatalogue;
    }

    public List<Periodical> getScientificCatalogue() {
        return scientificCatalogue;
    }

    public void setScientificCatalogue(List<Periodical> scientificCatalogue) {
        this.scientificCatalogue = scientificCatalogue;
    }

    public List<BorrowRequest> getBorrowRequests() {
        return borrowRequests;
    }

    public void setBorrowRequests(List<BorrowRequest> borrowRequests) {
        this.borrowRequests = borrowRequests;
    }

    public double getPerDayFine() {
        return perDayFine;
    }

    public void setPerDayFine(double perDayFine) {
        this.perDayFine = perDayFine;
    }

    public int getBookReturnDeadline() {
        return bookReturnDeadline;
    }

    public void setBookReturnDeadline(int bookReturnDeadline) {
        this.bookReturnDeadline = bookReturnDeadline;
    }

    public boolean isEmailTaken(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    public boolean isUsernameTaken(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    public void addBook(Book newBook) {
        this.booksCatalogue.add(newBook);
    }

    public Book findBookById(BigInteger bookId) throws ItemNotFoundException {
        for (Book book : booksCatalogue) {
            if (book.getId().equals(bookId)) {
                return book;
            }
        }
        throw new ItemNotFoundException("Book with ID " + bookId + " not found.");
    }

    public Periodical findPeriodicalById(BigInteger itemId) throws ItemNotFoundException {
        for (Periodical item : scientificCatalogue) {
            if (item.getId().equals(itemId)) {
                return item;
            }
        }
        throw new ItemNotFoundException("Item with ID " + itemId + " not found.");
    }

    public void addPeriodical(Periodical newPeriodical) {
        this.scientificCatalogue.add(newPeriodical);

    }

    public void removeBook(Book selectedBook) throws ItemCurrentlyBorrowedException {
        if (!selectedBook.isAvailable()) {
            throw new ItemCurrentlyBorrowedException("The item you want to remove is currently borrowed by a user.");
        }

        booksCatalogue.remove(selectedBook);
    }

    public void removePeriodical(Periodical selectedItem) {
        scientificCatalogue.remove(selectedItem);
    }

}
