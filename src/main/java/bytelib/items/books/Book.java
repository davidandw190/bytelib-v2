package bytelib.items.books;

import bytelib.BorrowRequest;
import bytelib.enums.BorrowRequestStatus;
import bytelib.items.Borrowable;
import bytelib.items.LibraryItem;
import bytelib.users.Borrower;

import java.time.LocalDate;
import java.sql.Date;
import java.util.Objects;

public abstract class Book extends LibraryItem implements Borrowable {

    protected String description;
    protected String publisher;
    protected double rating;

    protected LocalDate returnDate;

    public Book(Long id, String title, Date pubDate, int pageNumber) {
        super(id, title, pubDate, pageNumber);
        this.isAvailable = true;
        this.returnDate = null;
    }

    public Book(String title, Date pubDate, int pageNumber) {
        super(title, pubDate, pageNumber);
        this.isAvailable = true;
        this.returnDate = null;
    }

    @Override
    public BorrowRequest initiateBorrowRequest(Borrower borrower) {
        if (this.isAvailable) {
            BorrowRequest newRequest =  new BorrowRequest(borrower, this);
            newRequest.setStatus(BorrowRequestStatus.PENDING_APPROVAL);
            return newRequest;
        } else {
            System.out.println("Book not available for borrowing");
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Book book = (Book) obj;
        return  Double.compare(book.rating, rating) == 0 &&
                Objects.equals(description, book.description) &&
                Objects.equals(publisher, book.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, publisher, rating);
    }

    public void returnItem(BorrowRequest borrowRequest) {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getPublicationDate() {
        return this.publicationDate;
    }


}
