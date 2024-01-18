package bytelib.items.books;

import bytelib.enums.ResearchDomain;
import bytelib.items.Borrowable;
import bytelib.items.Citeable;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Textbook extends Book implements Borrowable, Citeable {

    protected int edition;
    protected ResearchDomain topic;
    protected String description;
    protected List<String> authors;
    protected long numberOfCitations;
    protected int numberOfPages;

    public Textbook(Long id, String title, String description, Integer pages, String publisher, Integer edition, Integer citations, Date pubDate, List<String> authors, ResearchDomain topic) {
        super(id, title, pubDate, pages);
        this.description = description;
        this.numberOfPages = pages;
        this.publisher = publisher;
        this.authors = authors;
        this.topic = topic;
        this.edition = edition;
        this.numberOfCitations = citations;
    }



    public Textbook(String title, String description, Integer pages, String publisher, Integer edition, Integer citations, Date pubDate, List<String> authors, ResearchDomain topic) {
        super(title, pubDate, pages);
        this.description = description;
        this.numberOfPages = pages;
        this.publisher = publisher;
        this.topic = topic;
        this.authors = authors;
        this.edition = edition;
        this.numberOfCitations = citations;
    }

    public Textbook(String title, String description, Integer pages, String publisher, Integer edition, Integer citations, Date pubDate, List<String> authors) {
        super(title, pubDate, pages);
        this.description = description;
        this.numberOfPages = pages;
        this.publisher = publisher;
        this.authors = authors;
        this.edition = edition;
        this.numberOfCitations = citations;
    }

    @Override
    public void setStatusBorrowed() {
        this.isAvailable = false;
    }

    @Override
    public void setStatusAvailable() {
        this.isAvailable = false;
    }

    @Override
    public String getCitation() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedPubDate = dateFormat.format(publicationDate);

        StringBuilder citationBuilder = new StringBuilder();
        citationBuilder.append(getAuthor())
                .append(". \"").append(getTitle()).append(".\" ")
                .append("Edition ").append(getEdition()).append(". ")
                .append("Published on ").append(formattedPubDate).append(". ")
                .append("Research Domain: ").append(getTopic()).append(". ")
                .append(getNumberOfCitations()).append(" citations.");

        return citationBuilder.toString();
    }

    @Override
    public void cite() {
        this.setNumberOfCitations(this.getNumberOfCitations()+1);
    }

    @Override
    public Long getNumberOfCitations() {
        return this.numberOfCitations;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Textbook textbook = (Textbook) obj;
        return edition == textbook.edition && topic == textbook.topic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), edition, topic);
    }


    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public ResearchDomain getTopic() {
        return topic;
    }

    public void setTopic(ResearchDomain topic) {
        this.topic = topic;
    }

    public void setNumberOfCitations(long numberOfCitations) {
        this.numberOfCitations = numberOfCitations;
    }

    public List<String> getAuthors() {
        return this.authors;
    }
}
