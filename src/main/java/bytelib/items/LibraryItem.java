package bytelib.items;


import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public abstract class LibraryItem implements Serializable {

    protected final BigInteger id;
    protected String title;
    protected String author;

    protected boolean isAvailable;
    protected LocalDate publicationDate;

    private static BigInteger currentIdNumber = BigInteger.ZERO;

    public LibraryItem(String title, LocalDate publicationDate) {
        LibraryItem.currentIdNumber = currentIdNumber.add(BigInteger.ONE);
        this.id = currentIdNumber;
        this.title = title;
        this.publicationDate = publicationDate;
        this.isAvailable = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LibraryItem that = (LibraryItem) obj;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public BigInteger getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAuthor() {
        return this.author;
    }

}
