package bytelib.items.periodical;

import bytelib.enums.ResearchDomain;
import bytelib.items.Citeable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Article extends Periodical implements Citeable {
    private List<String> authors;
    private String abstractText;


    public Article(String title, Date pubDate, ResearchDomain domain, int numberOfCitations, List<String> authors, String publisher, String abstractText) {
        super(title, pubDate, domain, publisher, numberOfCitations);
        this.authors = authors;
        this.abstractText = abstractText;
    }


    @Override
    public String getCitation() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String pubDateStr = dateFormat.format(publicationDate);

        return String.format("%s. \"%s.\" (%s): %s %d citations.",
                authorsToString(), getTitle(),
                pubDateStr, getPublisher(), getNumberOfCitations());
    }

    @Override
    public void cite() {
        this.setNumberOfCitations(this.getNumberOfCitations()+1);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Article article = (Article) obj;
        return  Objects.equals(authors, article.authors) &&
                Objects.equals(super.getPublisher(), article.getPublisher()) &&
                Objects.equals(abstractText, article.abstractText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), authors, super.getPublisher(), abstractText);
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    private String authorsToString() {
        return String.join(", ", authors);
    }
}
