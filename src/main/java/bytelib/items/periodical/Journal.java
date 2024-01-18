package bytelib.items.periodical;

import bytelib.enums.PublishingIntervals;
import bytelib.enums.ResearchDomain;
import bytelib.items.Citeable;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class Journal extends Periodical implements Citeable {
    private PublishingIntervals publishingInterval;
    private String publisher;
    private List<String> authors;
    private String abstractText;
    private int issue;


    public Journal(String title, String abstractText, String publisher, Integer issue, Integer pageNumber, Integer numberOfCitations, Date pubDate, List<String> authors, ResearchDomain domain, PublishingIntervals publishingInterval) {
        super(title, pubDate, domain, publisher, numberOfCitations);
        this.publishingInterval = publishingInterval;
        this.authors = authors;
        this.issue = issue;
        this.abstractText = abstractText;
    }

    public Journal(Long id, String title, String abstractText, String publisher, Integer issue, Integer pageNumber, Integer numberOfCitations, Date pubDate, List<String> authors, ResearchDomain domain, PublishingIntervals publishingInterval) {
        super(id, title, pubDate, domain, publisher, numberOfCitations, pageNumber);
        this.publishingInterval = publishingInterval;
        this.authors = authors;
        this.issue = issue;
        this.abstractText = abstractText;
    }


    @Override
    public String getCitation() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String pubDateStr = dateFormat.format(publicationDate);

        return String.format("%s. \"%s.\" %d.%d (%s): %d citations.",
                publisher, title, issue, pubDateStr, getNumberOfCitations());
    }

    @Override
    public void cite() {
        this.setNumberOfCitations(this.getNumberOfCitations() + 1);

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Journal journal = (Journal) obj;
        return publishingInterval == journal.publishingInterval &&
                publisher.equals(journal.publisher) &&
                issue == journal.issue;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), publishingInterval, issue);
        result = 31 * result + publisher.length();
        return result;
    }

    public PublishingIntervals getPublishingInterval() {
        return publishingInterval;
    }

    public void setPublishingInterval(PublishingIntervals publishingInterval) {
        this.publishingInterval = publishingInterval;
    }

    @Override
    public String getPublisher() {
        return publisher;
    }

    @Override
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
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

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public Date getPublicationDate() {
        return this.publicationDate;
    }
}
