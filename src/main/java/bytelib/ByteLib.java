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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.TextStringBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class ByteLib {

    public static void main(String[] args) {
//
//        if (args.length >= 1 && (args[0].equals("--persist") || args[0].equals("-p"))) {
//            persistenceEnabled = true;
//            library = loadLibrary(filePath, scanner);
//        } else {
//            library = initNewLibraryWithMockData(scanner);
//        }
//
//        clearScreen();
//        System.out.println("Welcome to  Library!");
//
//        while (true) {
//            displayMainMenu();
//
//            int choice = getChoice(scanner, 4);
//
//            switch (choice) {
//                case 1:
//                    loginAsLibrarian(library, scanner);
//                    break;
//                case 2:
//                    loginAsBorrower(library, scanner);
//                    break;
//                case 3:
//                    createBorrowerAccount(library, scanner);
//                    break;
//                case 4:
//                    System.out.println("\nExiting the Library Management System. Goodbye!");
//                    if (persistenceEnabled) {
//                        saveLibrary(library, filePath);
//                    }
//                    System.exit(0);
//                default:
//                    System.out.println("\nInvalid choice. Please try again.");
//            }
//        }
//    }
//
//
//    private static Library loadLibrary(String filePath, Scanner scanner) {
//        Library library = null;
//
//        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
//            library = new Library();  // Provide default values if necessary
//            library.loadData(filePath, "objectstream");
//            System.out.println(" [*] Library loaded successfully from " + filePath);
//        } catch (IOException e) {
//            System.out.println("Error loading library from " + filePath + ": " + e.getMessage());
//
//            System.out.println("Initializing default library..");
//            waitForEnter(scanner);
//            library = initNewLibraryWithMockData(scanner);
//            saveLibrary(library, DEFAULT_FILE_PATH);
//        }
//
//        return library;
//    }
//
//
//    private static Library initNewLibraryWithMockData(Scanner scanner) {
//        String libraryName;
//        do {
//            System.out.print("Enter the name of the new library (at least 3 characters): ");
//            libraryName = scanner.nextLine().trim();
//        } while (libraryName.length() < 3);
//
//        System.out.print("Enter the book return deadline (in days): ");
//        int bookReturnDeadline = getIntegerInput(scanner);
//
//        System.out.print("Enter the per day fine for overdue books: ");
//        double perDayFine = getDoubleInput(scanner);
//
//        Librarian librarian = new Librarian("admin", "123", "admin@email.com", "123456789");
//
//        Library library = new Library(libraryName, librarian, bookReturnDeadline, perDayFine);
//        library.populateLibraryWithMockValues();
//
//        return library;
//    }
//
//    private static int getIntegerInput(Scanner scanner) {
//        int input = -1;
//        while (input < 0) {
//            try {
//                input = Integer.parseInt(scanner.nextLine());
//                if (input < 0) {
//                    System.out.print("Please enter a non-negative integer: ");
//                }
//            } catch (NumberFormatException ex) {
//                System.out.print("Invalid input. Please enter a valid integer: ");
//            }
//        }
//        return input;
//    }
//
//    private static double getDoubleInput(Scanner scanner) {
//        double input = -1;
//        while (input < 0) {
//            try {
//                input = Double.parseDouble(scanner.nextLine());
//                if (input < 0) {
//                    System.out.print("Please enter a non-negative number: ");
//                }
//            } catch (NumberFormatException ex) {
//                System.out.print("Invalid input. Please enter a valid number: ");
//            }
//        }
//        return input;
//    }
//
//    private static void saveLibrary(Library library, String filePath) {
//        try {
//            library.saveData(filePath, "objectstream");
//        } catch (Exception e) {
//            System.out.println("Error saving library to " + filePath + ": " + e.getMessage());
//        }
//    }
//
//    private static void loginAsLibrarian(Library library, Scanner scanner) {
//        clearScreen();
//        System.out.println("Librarian Login:");
//
//        // Hardcoded librarian credentials
//        String librarianUsername = "admin";
//        String librarianPassword = "123";
//
//        System.out.println("Enter your username:");
//        String usernameInput = scanner.nextLine();
//
//        System.out.println("Enter your password:");
//        String passwordInput = scanner.nextLine();
//
//        if (librarianUsername.equals(usernameInput) && librarianPassword.equals(passwordInput)) {
//            System.out.println("\nLogin successful! Welcome, " + librarianUsername + "!");
//            waitForEnter(scanner);
//            librarianMenu(library, scanner);
//        } else {
//            System.out.println("\nInvalid credentials. Please try again.");
//            waitForEnter(scanner);
//        }
//    }
//
//    private static void loginAsBorrower(Library library, Scanner scanner) {
//        clearScreen();
//        System.out.println("Borrower Login:");
//
//        System.out.println("Enter your email or username:");
//        String usernameOrEmail = scanner.nextLine();
//
//        System.out.println("Enter your password:");
//        String password = scanner.nextLine();
//
//        if (isValidEmail(usernameOrEmail) || isValidUsername(usernameOrEmail)) {
//            // Valid email, attempt to log in
//            Borrower loggedInUser = library.loginBorrower(usernameOrEmail, password);
//
//            if (loggedInUser != null) {
//                System.out.println("\nLogin successful! Welcome, " + loggedInUser.getUsername() + "!");
//                waitForEnter(scanner);
//                borrowerMenu(library, loggedInUser, scanner);
//                return;
//            }
//        }
//
//        System.out.println("\nInvalid credentials. Please try again.");
//        waitForEnter(scanner);
//    }
//
//    private static void createBorrowerAccount(Library library, Scanner scanner) {
//        clearScreen();
//        System.out.println("Create a Borrower Account:");
//
//        System.out.println("Enter your username:");
//        String username = scanner.nextLine();
//
//        // Validate username
//        if (!isValidUsername(username)) {
//            System.out.println("Invalid username. It must be alphanumeric and between 5 to 20 characters.");
//            waitForEnter(scanner);
//            return;
//        }
//
//        // Check if the username is already taken
//        if (library.isUsernameTaken(username)) {
//            System.out.println("Username is already taken. Please choose another one.");
//            waitForEnter(scanner);
//            return;
//        }
//
//        System.out.println("Enter your email:");
//        String email = scanner.nextLine();
//
//        // Validate email
//        if (!isValidEmail(email)) {
//            System.out.println("Invalid email format.");
//            waitForEnter(scanner);
//            return;
//        }
//
//        // Check if the email is already taken
//        if (library.isEmailTaken(email)) {
//            System.out.println("Email is already registered. Please use another email.");
//            waitForEnter(scanner);
//            return;
//        }
//
//        System.out.println("Enter your phone number (10 digits):");
//        String phoneNumber = scanner.nextLine();
//
//        // Validate phone number
//        if (!isValidPhoneNumber(phoneNumber)) {
//            System.out.println("Invalid phone number. It must be a 10-digit number.");
//            waitForEnter(scanner);
//            return;
//        }
//
//        System.out.println("Enter your password:");
//        String password = scanner.nextLine();
//
//        // Validate password
//        if (!isValidPassword(password)) {
//            System.out.println("Invalid password. It must be at least 8 characters long, "
//                    + "contain at least one uppercase letter, one lowercase letter, and one digit.");
//            waitForEnter(scanner);
//            return;
//        }
//
//        // Create borrower account
//        Borrower newBorrower = new Borrower(username, password, email, phoneNumber);
//        library.addUser(newBorrower);
//
//        System.out.println("\nBorrower account created successfully!");
//        waitForEnter(scanner);
//
//        borrowerMenu(library, newBorrower, scanner);
//    }
//
//    private static void displayMainMenu() {
//        clearScreen();
//        System.out.println("\nMain Menu:");
//        System.out.println("1. Log In as Librarian");
//        System.out.println("2. Log In as Borrower");
//        System.out.println("3. Create an Account");
//        System.out.println("4. Exit");
//        System.out.print("\nEnter your choice: ");
//    }
//
//    private static void librarianMenu(Library library, Scanner scanner) {
//        while (true) {
//            clearScreen();
//            System.out.println("Librarian Menu:");
//            System.out.println("1. View Books Catalogue");
//            System.out.println("2. View Scientific Catalogue");
//            System.out.println("3. Add Book");
//            System.out.println("4. Remove Book");
//            System.out.println("5. View Borrow Requests");
//            System.out.println("6. Logout");
//
//            int choice = getChoice(scanner, 6);
//
//            switch (choice) {
//                case 1:
//                    viewBooksCatalogue(library);
//                    waitForEnter(scanner);
//                    break;
//                case 2:
//                    viewScientificCatalogue(library);
//                    waitForEnter(scanner);
//                    break;
//                case 3:
//                    addItem(scanner, library);
//                    break;
//                case 4:
//                    removeItem(scanner, library);
//                    break;
//                case 5:
//                    displayBorrowRequests(library);
//                    getLibrarianBorrowRequestsOptions(library, library.getBorrowRequests(), scanner);
//                    waitForEnter(scanner);
//                    break;
//                case 6:
//                    System.out.println("Logging out. Returning to the main menu.");
//                    waitForEnter(scanner);
//                    return;
//            }
//        }
//    }
//
//    private static void borrowerMenu(Library library, Borrower borrower, Scanner scanner) {
//        while (true) {
//            clearScreen();
//            System.out.println("Borrower Menu:");
//            System.out.println("1. View Books Catalogue");
//            System.out.println("2. View Scientific Catalogue");
//            System.out.println("3. Borrow Book");
//            System.out.println("4. Return Book");
//            System.out.println("5. Cite a Scientific Item");
//            System.out.println("6. My Borrow Requests");
//            System.out.println("7. Logout");
//
//            int choice = getChoice(scanner, 7);
//
//            switch (choice) {
//                case 1:
//                    viewBooksCatalogue(library);
//                    waitForEnter(scanner);
//                    break;
//                case 2:
//                    viewScientificCatalogue(library);
//                    waitForEnter(scanner);
//                    break;
//                case 3:
//                    borrowBook(library, borrower, scanner);
//                    break;
//                case 4:
//                    returnBook(scanner, borrower);
//                    break;
//                case 5:
//                    citeScientificItem(library, scanner);
//                    break;
//                case 6:
//                    displayBorrowRequestsForUser(library, borrower);
//                    waitForEnter(scanner);
//                    break;
//                case 7:
//                    System.out.println("Logging out. Returning to the main menu.");
//                    waitForEnter(scanner);
//                    return;
//            }
//        }
//    }
//
//    private static void citeScientificItem(Library library, Scanner scanner) {
//        clearScreen();
//        System.out.println("Cite a Scientific Item:");
//
//        List<Citeable> citeableItems = new ArrayList<>();
//        citeableItems.addAll(library.getScientificCatalogue());
//        citeableItems.addAll(library.getCitableBooks());
//
//        if (citeableItems.isEmpty()) {
//            System.out.println("No items available for citation.");
//            waitForEnter(scanner);
//            return;
//        }
//
//        System.out.println("Available Items for Citation:");
//        for (int i = 0; i < citeableItems.size(); i++) {
//            if (citeableItems.get(i) instanceof Book) {
//                System.out.println((i + 1) + ". " + ((Book) citeableItems.get(i)).getTitle());
//            } else {
//                System.out.println((i + 1) + ". " + ((Periodical) citeableItems.get(i)).getTitle());
//            }
//
//        }
//
//        int choice = getChoice(scanner, citeableItems.size());
//
//        // Cite the selected item
//        Citeable selectedItem = citeableItems.get(choice - 1);
//        selectedItem.cite();
//
//        System.out.println("\nCitation recorded successfully!");
//        waitForEnter(scanner);
//
//        System.out.println("\nThe citation is: " + selectedItem.getCitation());
//
//        waitForEnter(scanner);
//    }
//
//    private static void returnBook(Scanner scanner, Borrower borrower) {
//        System.out.println("IMPLEMENT THIS!!!!!");
//    }
//
//    private static void viewScientificCatalogue(Library library) {
//        clearScreen();
//        displayScientificCatalogue(library);
//    }
//
//    private static void viewBooksCatalogue(Library library) {
//        clearScreen();
//        displayBooksCatalogue(library);
//    }
//
//    private static String formatCellValue(String value) {
//        return String.format("%-" + COLUMN_WIDTH + "s", value);
//    }
//
//    public List<BorrowRequest> getBorrowRequestsForBorrower(Library library, Borrower borrower) {
//        return library.getBorrowRequests().stream()
//                .filter(borrowRequest -> borrowRequest.getBorrower().equals(borrower))
//                .collect(Collectors.toList());
//    }
//
//    public static void displayBooksCatalogue(Library library) {
//        System.out.println("== Books Catalogue ==");
//        List<String> columns = List.of("ID", "TITLE", "AUTHOR", "GENRE", "PUB DATE", "NO. CITATIONS", "STATUS");
//        List<LibraryItem> books = new ArrayList<>(library.getBooksCatalogue());
//
//        displayItems(books, columns);
//
//        System.out.println();
//    }
//
//
//    public static void displayScientificCatalogue(Library library) {
//
//        System.out.println("== Scientific Catalogue ==");
//        List<String> columns = List.of("ID", "TITLE", "AUTHORS", "PUBLISHER", "PUB DATE", "DOMAIN", "NO. CITATIONS");
//        List<LibraryItem> scientificItems = new ArrayList<>(library.getScientificCatalogue());
//
//        displayItems(scientificItems, columns);
//
//        System.out.println();
//    }
//
//    public static void displayBorrowRequests(Library library) {
//        System.out.println("== Borrow Requests ==");
//        List<String> columns = List.of("INDEX", "ITEM TITLE", "AUTHOR", "BORROWER NAME", "STATUS","REQUEST DATE", "BORROW DATE", "RETURN DATE", "FEE");
//        List<BorrowRequest> requests = new ArrayList<>(library.getBorrowRequests());
//
//        displayRequests(library, requests, columns);
//    }
//
//    public static void displayBorrowRequestsForUser(Library library, Borrower borrower) {
//        System.out.println("== My Borrow Requests ==");
//        List<String> columns = List.of("INDEX", "ITEM TITLE", "AUTHOR", "STATUS", "REQUEST DATE", "BORROW DATE", "RETURN DATE", "FEE");
//        List<BorrowRequest> requests = new ArrayList<>(borrower.getBorrowRequests(library));
//
//        displayRequests(library, requests, columns);
//
//        System.out.println();
//    }
//
//    private static String formatDate(Date date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        return sdf.format(date);
//    }
//
//    public static void displayRequests(Library library, List<BorrowRequest> requests, List<String> columns) {
//
//        TextStringBuilder header = new TextStringBuilder();
//        for (String column : columns) {
//            header.append(formatCellValue(column)).append(" | ");
//        }
//        System.out.println(header.toString());
//        Integer requestIndex = 0;
//        // Add a separator line between header and content
//        System.out.println(StringUtils.repeat("-", header.length()));
//
//        for (BorrowRequest request : requests) {
//            TextStringBuilder row = new TextStringBuilder();
//            for (String column : columns) {
//                String cellValue = getBorrowRequestCellValue(library, request, column, requestIndex);
//                row.append(formatCellValue(cellValue)).append(" | ");
//            }
//            System.out.println(row.toString());
//        }
//        System.out.println();
//    }
//
//
//    public static void displayItems(List<? extends LibraryItem> items, List<String> columns) {
//        if (items.isEmpty()) {
//            System.out.println("\nNo items available for borrowing at the moment.");
//            return;
//        }
//
//        TextStringBuilder header = new TextStringBuilder();
//
//        for (String column : columns) {
//            header.append(formatCellValue(column)).append(" | ");
//        }
//        System.out.println(header.toString());
//
//        // Add a separator line between header and content
//        System.out.println(StringUtils.repeat("-", header.length()));
//
//        for (LibraryItem item : items) {
//            TextStringBuilder row = new TextStringBuilder();
//            for (String column : columns) {
//                String cellValue = getLibraryItemCellValue(item, column);
//                row.append(formatCellValue(cellValue)).append(" | ");
//            }
//            System.out.println(row.toString());
//        }
//    }
//
//    private static String getBorrowRequestCellValue(Library library, BorrowRequest request, String column, Integer requestIndex) {
//        switch (column.toLowerCase()) {
//            case "index":
//                return (++requestIndex).toString();
//            case "item title":
//                return ((Book) request.getBorrowable()).getTitle();
//            case "author":
//                return ((Book) request.getBorrowable()).getAuthor();
//            case "status":
//                return request.getStatus().name();
//            case "request date":
//                if (request.getRequestDate() != null) {
//                return formatDate(request.getRequestDate());
//            }
//            break;
//            case "borrow date":
//                if (request.getBorrowDate() != null) {
//                    return formatDate(request.getBorrowDate());
//                }
//                break;
//
//            case "return date":
//                if (request.getReturnDate() != null) {
//                    return formatDate(request.getReturnDate());
//                }
//                break;
//            case "borrower name":
//                return request.getBorrower().getUsername();
//            case "deadline" :
//                if (request.getReturnDeadline() != null) {
//                    return formatDate(request.getReturnDeadline());
//                }
//                break;
//            case "fee":
//                if (request.checkIfOverdue()) {
//                    return String.valueOf(library.getReturnFee(request));
//                }
//        }
//        return "-";
//    }
//
//
//    private static String getLibraryItemCellValue(LibraryItem item, String column) {
//        switch (column.toLowerCase()) {
//            case "id":
//                return item.getId().toString();
//            case "title":
//                return item.getTitle();
//            case "author":
//                return item.getAuthor();
//            case "authors":
//                if (item instanceof Article) {
//                    List<String> authors = ((Article) item).getAuthors();
//                    if (authors != null && authors.size() > 1) {
//                        StringBuilder text = new StringBuilder();
//                        for (int i = 0; i < authors.size() - 1; i++) {
//                            text.append(authors.get(i)).append(",");
//                        }
//                        text.append(authors.get(authors.size() - 1));
//                        return text.toString();
//                    }
//                }
//                break;
//
//            case "available":
//                return item.isAvailable() ? "AVAILABLE" : "UNAVAILABLE";
//            case "pub date":
//                return formatDate(item.getPublicationDate());
//            case "description":
//                if (item instanceof Book) {
//                    return ((Book) item).getDescription();
//                }
//                break;
//            case "no. pages":
//                if (item instanceof Book) {
//                    return String.valueOf(((Book) item).getNumberOfPages());
//                }
//                break;
//            case "publisher":
//                if (item instanceof Book) {
//                    return ((Book) item).getPublisher();
//                } else {
//                    return ((Periodical) item).getPublisher();
//                }
//            case "rating":
//                if (item instanceof Book) {
//                    return String.valueOf(((Book) item).getRating());
//                }
//                break;
//            case "volume":
//                if (item instanceof Novel) {
//                    return String.valueOf(((Novel) item).getVolume());
//                }
//                break;
//            case "genre":
//                if (item instanceof Novel) {
//                    return ((Novel) item).getGenre().toString();
//                }
//                break;
//            case "edition":
//                if (item instanceof Textbook) {
//                    return String.valueOf(((Textbook) item).getEdition());
//                }
//                break;
//            case "topic":
//                if (item instanceof Textbook) {
//                    return ((Textbook) item).getTopic().toString();
//                }
//                break;
//            case "status":
//                if (item instanceof Borrowable) {
//                    if (((Borrowable) item).isAvailable()) {
//                        return "AVAILABLE";
//                    } else {
//                        return "BORROWED";
//                    }
//                }
//                break;
//            case "domain":
//                if (item instanceof Periodical) {
//                    return ((Periodical) item).getDomain().toString();
//                }
//                break;
//            case "no. citations":
//                if (item instanceof Citeable) {
//                    return String.valueOf(((Citeable) item).getNumberOfCitations());
//                }
//                break;
//
//        }
//        return "-";
//    }
//
//    private static void addItem(Scanner scanner, Library library) {
//        clearScreen();
//        System.out.println("Add an item to Catalog:");
//
//        System.out.println("\nChoose item type:");
//        System.out.println("1. Novel");
//        System.out.println("2. Textbook");
//        System.out.println("3. Journal");
//        System.out.println("4. Article");
//        System.out.println("5. Cancel");
//
//        int bookTypeChoice = getChoice(scanner, 5);
//
//        switch (bookTypeChoice) {
//            case 1:
//                addNovel(library, scanner);
//                break;
//            case 2:
//                addTextbook(library, scanner);
//                break;
//            case 3:
//                addJournal(library, scanner);
//                break;
//            case 4:
//                addArticle(library, scanner);
//                break;
//            case 5:
//                System.out.println("Cancelling");
//                break;
//            default:
//                System.out.println("\nInvalid choice. Returning to the main menu.");
//                waitForEnter(scanner);
//        }
//    }
//
//    private static void addTextbook(Library library, Scanner scanner) {
//        clearScreen();
//        System.out.println("Add Textbook to Catalog:");
//
//        System.out.println("Enter textbook title:");
//        String title = scanner.nextLine();
//
//        System.out.println("Enter author name:");
//        String author = scanner.nextLine();
//
//        System.out.println("Select Research Domain:");
//        ResearchDomain topic = getResearchDomainInput(scanner);
//
//        System.out.println("Enter edition:");
//        int edition = getIntInput(scanner, "Invalid input. Please enter a valid integer for the edition.");
//
//        System.out.println("Enter publication date (dd-MM-yyyy):");
//        Date publicationDate = getDateInput(scanner);
//
//        Textbook newTextbook = new Textbook(title, author, topic, edition, publicationDate);
//        library.addBook(newTextbook);
//
//        System.out.println("\nTextbook added to the catalog successfully!");
//        waitForEnter(scanner);
//    }
//
//    private static void addNovel(Library library, Scanner scanner) {
//        clearScreen();
//        System.out.println("Add Novel to Catalog:");
//
//        System.out.println("Enter novel title:");
//        String title = scanner.nextLine();
//
//        System.out.println("Enter author name:");
//        String author = scanner.nextLine();
//
//        System.out.println("Select Genre:");
//        BookGenre genre = getBookGenreInput(scanner);
//
//        System.out.println("Enter volume:");
//        int volume = getIntInput(scanner, "Invalid input. Please enter a valid integer for the volume.");
//
//        System.out.println("Enter publication date (dd-MM-yyyy):");
//        Date publicationDate = getDateInput(scanner);
//
//        Novel newNovel = new Novel(title, author, genre, volume, publicationDate);
//        library.addBook(newNovel);
//
//        System.out.println("\nNovel added to the catalog successfully!");
//        waitForEnter(scanner);
//    }
//
//    private static BookGenre getBookGenreInput(Scanner scanner) {
//        System.out.println("Available Genres:");
//        Arrays.stream(BookGenre.values()).forEach(genre -> System.out.println(genre.ordinal() + 1 + ". " + genre));
//        int choice = getChoice(scanner, BookGenre.values().length);
//        return BookGenre.values()[choice - 1];
//    }
//
//    private static void addArticle(Library library, Scanner scanner) {
//        clearScreen();
//        System.out.println("Add Article to Catalog:");
//
//        System.out.println("Enter article title:");
//        String title = scanner.nextLine();
//
//        System.out.println("Select Research Domain:");
//        ResearchDomain domain = getResearchDomainInput(scanner);
//
//        System.out.println("Enter number of citations:");
//        int numberOfCitations = getIntInput(scanner, "Invalid input. Please enter a valid integer for the number of citations.");
//
//        System.out.println("Enter authors (comma-separated):");
//        List<String> authors = Arrays.asList(scanner.nextLine().split(","));
//
//        System.out.println("Enter affiliation:");
//        String affiliation = scanner.nextLine();
//
//        System.out.println("Enter abstract text:");
//        String abstractText = scanner.nextLine();
//
//        System.out.println("Enter publication date (dd-MM-yyyy):");
//        Date publicationDate = getDateInput(scanner);
//
//        Article newArticle = new Article(title, publicationDate, domain, numberOfCitations, authors, affiliation, abstractText);
//        library.addPeriodical(newArticle);
//
//        System.out.println("\nArticle added to the catalog successfully!");
//        waitForEnter(scanner);
//    }
//
//    private static void addJournal(Library library, Scanner scanner) {
//        clearScreen();
//        System.out.println("Add Journal to Catalog:");
//
//        System.out.println("Enter journal title:");
//        String title = scanner.nextLine().trim();
//
//        System.out.println("Select Research Domain:");
//        ResearchDomain domain = getResearchDomainInput(scanner);
//
//        System.out.println("Enter publishing interval:");
//        PublishingIntervals publishingInterval = getPublishingIntervalInput(scanner);
//
//        System.out.println("Enter number of citations:");
//        int numberOfCitations = getIntInput(scanner, "Invalid input. Please enter a valid integer for the number of citations.");
//
//        System.out.println("Enter publisher:");
//        String publisher = scanner.nextLine().trim();
//
//        System.out.println("Enter volume:");
//        int volume = getIntInput(scanner, "Invalid input. Please enter a valid integer for the volume.");
//
//        System.out.println("Enter issue:");
//        int issue = getIntInput(scanner, "Invalid input. Please enter a valid integer for the issue.");
//
//        System.out.println("Enter publication date (dd-MM-yyyy):");
//        Date publicationDate = getDateInput(scanner);
//
//        Journal newJournal = new Journal(title, publicationDate, domain, numberOfCitations, publishingInterval, publisher, volume, issue);
//        library.addPeriodical(newJournal);
//
//        System.out.println("\nJournal added to the catalog successfully!");
//        waitForEnter(scanner);
//    }
//
//    private static ResearchDomain getResearchDomainInput(Scanner scanner) {
//        System.out.println("Available Research Domains:");
//        Arrays.stream(ResearchDomain.values()).forEach(domain -> System.out.println(domain.ordinal() + 1 + ". " + domain));
//        int choice = getChoice(scanner, ResearchDomain.values().length);
//        return ResearchDomain.values()[choice - 1];
//    }
//
//    private static PublishingIntervals getPublishingIntervalInput(Scanner scanner) {
//        System.out.println("Available Publishing Intervals:");
//        Arrays.stream(PublishingIntervals.values()).forEach(interval -> System.out.println(interval.ordinal() + 1 + ". " + interval));
//        int choice = getChoice(scanner, PublishingIntervals.values().length);
//        return PublishingIntervals.values()[choice - 1];
//    }
//
//    private static int getIntInput(Scanner scanner, String errorMessage) {
//        while (true) {
//            try {
//                return Integer.parseInt(scanner.nextLine().trim());
//            } catch (NumberFormatException e) {
//                System.out.println(errorMessage);
//            }
//        }
//    }
//
//    private static void borrowBook(Library library, Borrower borrower, Scanner scanner) {
//        displayBooksCatalogue(library);
//
//        try {
//            BigInteger bookId = getIdNumber(scanner, "Enter the book id to borrow or 0 to exit:");;
//            if (bookId.equals(BigInteger.ZERO)) {
//                System.out.println("Exiting the book borrowing process.");
//                return;
//            }
//
//            Book selectedBook = library.findBookById(bookId);
//            library.requestBorrow(borrower, selectedBook);
//            System.out.println("Borrow request for \"" + selectedBook.getTitle() + "\" has been initiated successfully! Please wait for librarian approval.");
//        } catch (ItemNotFoundException | ItemNotAvailableForBorrowException e) {
//            System.out.println("Error: " + e.getMessage());
//        } finally {
//            waitForEnter(scanner);
//        }
//    }
//
//    private static void getLibrarianBorrowRequestsOptions(Library library, List<BorrowRequest> requests, Scanner scanner) {
//        if (requests.isEmpty()) {
//            System.out.println("No borrow requests at the moment.. Come back in a few minutes");
//            return;
//        }
//
//        System.out.println("Select a borrow requests by its index to display its options: ACCEPT or REJECT \n Typing 0 will exit");
//        int requestIndex = getChoiceZeroIncluded(scanner, requests.size()) - 1;
//        if (requestIndex == -1) {
//            System.out.println("Exiting..");
//            return;
//        }
//
//        BorrowRequest selectedRequest = requests.get(requestIndex);
//        System.out.println("Please select the operation you want to perform on the BORROW REQUEST #" + requestIndex);
//        System.out.println("1. ACCEPT the borrow request");
//        System.out.println("2. REJECT the borrow request");
//        int operationChoice = getChoice(scanner, 2);
//        switch (operationChoice) {
//            case 1:
//                library.acceptBorrowRequest(selectedRequest);
//                System.out.println("Borrow request ACCEPTED successfully!");
//                break;
//            case 2:
//                library.rejectBorrowRequest(selectedRequest);
//                System.out.println("Borrow request REJECTED successfully!");
//                break;
//            default:
//                System.out.println("Invalid choice. Returning to the main menu.");
//                waitForEnter(scanner);
//
//        }
//    }
//
//    private static void removeItem(Scanner scanner, Library library) {
//        clearScreen();
//        System.out.println("Choose from what catalogue you want to remove an Item:");
//        System.out.println("1. Book Catalogue");
//        System.out.println("2. Scientific Catalogue");
//
//        int choice = getChoice(scanner, 2);
//
//        switch (choice) {
//            case 1:
//                removeBook(scanner, library);
//                break;
//            case 2:
//                removePeriodical(scanner, library);
//                break;
//            default:
//                System.out.println("Invalid choice. Returning to the main menu.");
//                waitForEnter(scanner);
//        }
//    }
//
//    private static void removeBook(Scanner scanner, Library library) {
//        displayBooksCatalogue(library);
//
//        try {
//            BigInteger bookId = getIdNumber(scanner, "Enter the ID of the book you want ro remove or 0 to exit:");;
//            if (bookId.equals(BigInteger.ZERO)) {
//                System.out.println("Exiting the book removal process.");
//                return;
//            }
//
//            Book selectedBook = library.findBookById(bookId);
//            library.removeBook(selectedBook);
//
//            System.out.println("Book removed successfully!");
//        } catch (ItemNotFoundException | ItemCurrentlyBorrowedException e) {
//            System.out.println("Error: " + e.getMessage());
//        } finally {
//            waitForEnter(scanner);
//        }
//    }
//
//    private static void removePeriodical(Scanner scanner, Library library) {
//        displayScientificCatalogue(library);
//
//        try {
//            BigInteger itemId = getIdNumber(scanner, "Enter the ID of the item you want ro remove or 0 to exit:");;
//            if (itemId.equals(BigInteger.ZERO)) {
//                System.out.println("Exiting the item removal process.");
//                return;
//            }
//
//            Periodical selectedItem = library.findPeriodicalById(itemId);
//            library.removePeriodical(selectedItem);
//
//            System.out.println("Item removed successfully!");
//        } catch (ItemNotFoundException e) {
//            System.out.println("Error: " + e.getMessage());
//        } finally {
//            waitForEnter(scanner);
//        }
//    }
//
////    private static void returnItem(Library library, Borrower borrower, Scanner scanner) {
////        displayBorrowedItems(borrower);
////
////        try {
////            BigInteger itemId = getItemNumber(scanner, "Enter the item id to return or 0 to exit:");
////            if (itemId.equals(BigInteger.ZERO)) {
////                System.out.println("Exiting the item return process.");
////                return;
////            }
////
//////            library.returnItem(borrower, itemId);
////            System.out.println("Item with ID " + itemId + " has been returned successfully!");
////        } catch (ItemNotFoundException | ItemNotBorrowedException e) {
////            System.out.println("Error: " + e.getMessage());
////        } finally {
////            waitForEnter(scanner);
////        }
////    }
////
//    private static BigInteger getIdNumber(Scanner scanner, String message) {
//        BigInteger choice = BigInteger.valueOf(-1);
//        boolean isValidChoice = false;
//
//        while (!isValidChoice) {
//            System.out.print(message);
//            try {
//                BigInteger bookId = scanner.nextBigInteger();
//                scanner.nextLine();
//                if (bookId.equals(BigInteger.ZERO)) {
//                    isValidChoice = true;
//                    return choice;
//                }
//                isValidChoice = true;
//                return bookId;
//
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input. Please enter a number.");
//            }
//        }
//        return choice;
//    }
//
//    private static Date getDateInput(Scanner scanner) {
//        Date publicationDate = null;
//        boolean dateValid = false;
//
//        while (!dateValid) {
//            try {
//                String inputDate = scanner.nextLine();
//                publicationDate = new SimpleDateFormat("dd-MM-yyyy").parse(inputDate);
//                dateValid = true;
//            } catch (ParseException e) {
//                System.out.println("Invalid date format. Please use dd-MM-yyyy.");
//            }
//        }
//
//        return publicationDate;
//    }
//
//    private static int getChoice(Scanner scanner, int maxOption) {
//        int choice = -1;
//        boolean isValidChoice = false;
//
//        while (!isValidChoice) {
//            System.out.print("Enter your choice (1-" + maxOption + "): ");
//            String input = scanner.nextLine().trim();
//
//            try {
//                choice = Integer.parseInt(input);
//
//                if (choice >= 1 && choice <= maxOption) {
//                    isValidChoice = true;
//                } else {
//                    System.out.println("Invalid choice. Please enter a number between 1 and " + maxOption + ".");
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input. Please enter a number.");
//            }
//        }
//
//        return choice;
//    }
//
//    private static int getChoiceZeroIncluded(Scanner scanner, int maxOption) {
//        int choice = -1;
//        boolean isValidChoice = false;
//
//        while (!isValidChoice) {
//            System.out.print("Enter your choice (0-" + maxOption + "): ");
//            String input = scanner.nextLine().trim();
//
//            try {
//                choice = Integer.parseInt(input);
//
//                if (choice >= 0 && choice <= maxOption) {
//                    isValidChoice = true;
//                } else {
//                    System.out.println("Invalid choice. Please enter a number between 0 and " + maxOption + ".");
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input. Please enter a number.");
//            }
//        }
//
//        return choice;
//    }
//
//
//    private static void clearScreen() {
//        try {
//            if (System.getProperty("os.name").contains("Windows")) {
//                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//            } else {
//                System.out.print(CLEAR_SCREEN_ANSI);
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void waitForEnter(Scanner scanner) {
//        System.out.println("\nPress Enter to continue...");
//        scanner.nextLine();
//    }
//
//    private static boolean isValidUsername(String username) {
//        return username.matches("^[a-zA-Z0-9]{5,20}$");
//    }
//
//    private static boolean isValidEmail(String email) {
//        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//        Pattern pattern = Pattern.compile(emailRegex);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
//
//    private static boolean isValidPassword(String password) {
//        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
//        return password.matches(passwordRegex);
//    }
//
//    private static boolean isValidPhoneNumber(String phoneNumber) {
//        String phoneRegex = "^[0-9]{10}$";
//        return phoneNumber.matches(phoneRegex);
//    }
    }
}