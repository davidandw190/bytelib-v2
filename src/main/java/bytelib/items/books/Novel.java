package bytelib.items.books;

import bytelib.enums.BookGenre;
import bytelib.items.Borrowable;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Novel extends Book implements Borrowable {

    private int volume;
    private BookGenre genre;

    public Novel(String title, String author, BookGenre genre, int vol, LocalDate pubDate) {
        super(title, pubDate);
        this.author = author;
        this.volume = vol;
        this.genre = genre;
    }

    public Novel(String title, String author, BookGenre genre, LocalDate pubDate) {
        super(title, pubDate);
        this.volume = 1;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Novel novel = (Novel) obj;
        return volume == novel.volume && genre == novel.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume, genre);
    }


    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
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
