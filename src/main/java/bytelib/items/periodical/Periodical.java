package bytelib.items.periodical;

import bytelib.enums.ResearchDomain;
import bytelib.items.Citeable;
import bytelib.items.LibraryItem;

import java.sql.Date;
import java.util.Objects;

public abstract class Periodical extends LibraryItem implements Citeable {

    private long numberOfCitations;
    private String publisher;
    private ResearchDomain domain;

    public Periodical(Long id, String title, Date pubDate, ResearchDomain domain, String publisher, int numberOfCitations, int pageNumber) {
        super(id, title, pubDate, pageNumber);
        this.domain = domain;
        this.numberOfCitations = numberOfCitations;
        this.publisher = publisher;
    }

    public Periodical(String title, Date pubDate, ResearchDomain domain, String publisher, int numberOfCitations) {
        super(title, pubDate, 0);
        this.domain = domain;
        this.publisher = publisher;
        this.numberOfCitations = numberOfCitations;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Periodical that = (Periodical) obj;
        return numberOfCitations == that.numberOfCitations && domain == that.domain;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numberOfCitations, domain);
    }

    @Override
    public Long getNumberOfCitations() {
        return this.numberOfCitations;
    }

    public void setNumberOfCitations(long numberOfCitations) {
        this.numberOfCitations = numberOfCitations;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public ResearchDomain getDomain() {
        return domain;
    }

    public void setDomain(ResearchDomain domain) {
        this.domain = domain;
    }
}
