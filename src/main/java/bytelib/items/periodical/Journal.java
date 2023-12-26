package bytelib.items.periodical;

import bytelib.enums.ResearchDomain;
import bytelib.enums.PublishingIntervals;
import bytelib.items.Citeable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Journal extends Periodical implements Citeable {
    private PublishingIntervals publishingInterval;
    private String publisher;
    private int volume;
    private int issue;

    public Journal(String title, Date pubDate, ResearchDomain domain, int numberOfCitations, PublishingIntervals publishingInterval, String publisher, int volume, int issue) {
        super(title, pubDate, domain, publisher, numberOfCitations);
        this.publishingInterval = publishingInterval;
        this.publisher = publisher;
        this.volume = volume;
        this.issue = issue;
    }


    @Override
    public String getCitation() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String pubDateStr = dateFormat.format(publicationDate);

        return String.format("%s. \"%s.\" %d.%d (%s): %d citations.",
                publisher, title, volume, issue, pubDateStr, getNumberOfCitations());
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
                volume == journal.volume &&
                issue == journal.issue;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), publishingInterval, volume, issue);
        result = 31 * result + publisher.length();
        return result;
    }


    public PublishingIntervals getPublishingInterval() {
        return publishingInterval;
    }

    public void setPublishingInterval(PublishingIntervals publishingInterval) {
        this.publishingInterval = publishingInterval;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

}
