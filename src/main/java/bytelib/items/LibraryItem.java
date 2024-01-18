package bytelib.items;


import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public abstract class LibraryItem implements Serializable {

    protected Long id;
    protected String title;
    protected String author;
    protected Integer pageNumber;

    protected boolean isAvailable;
    protected Date publicationDate;

    public LibraryItem(String title, Date publicationDate,Integer pageNumber) {
        this.title = title;
        this.pageNumber = pageNumber;
        this.publicationDate = publicationDate;
        this.isAvailable = true;
    }

    public LibraryItem(Long id, String title, Date publicationDate, Integer pageNumber) {
        this.title = title;
        this.pageNumber = pageNumber;
        this.publicationDate = publicationDate;
        this.isAvailable = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LibraryItem other = (LibraryItem) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
