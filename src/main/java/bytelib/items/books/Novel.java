package bytelib.items.books;

import bytelib.enums.BookGenre;
import bytelib.items.Borrowable;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Novel extends Book implements Borrowable {

    private Integer volume;
    private BookGenre genre;
    private List<String> authors;
    private Integer pageNumber;
    private String description;
    private String publisher;

    public Novel(String title, String description , Integer pageNumber, String publisher, Integer volume, Date pubDate, List<String> authors, BookGenre genre) {
        super(title, pubDate);
        this.volume = volume;
        this.description = description;
        this.pageNumber = pageNumber;
        this.publisher = publisher;
        this.authors = authors;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Novel novel = (Novel) obj;
        return Objects.equals(volume, novel.volume) && genre == novel.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume, genre);
    }


    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getPublisher() {
        return publisher;
    }

    @Override
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public void setStatusBorrowed() {
        this.isAvailable = false;
    }

    @Override
    public void setStatusAvailable() {
        this.isAvailable = false;
    }
}
