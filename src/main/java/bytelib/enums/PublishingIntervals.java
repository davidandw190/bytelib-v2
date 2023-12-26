package bytelib.enums;

import java.io.Serializable;

public enum PublishingIntervals implements Serializable {
    WEEKLY("WEEKLY"),
    MONTHLY("MONTHLY"),
    QUARTERLY("QUARTERLY"),
    YEARLY("YEARLY"),
    OTHER("OTHER");

    private final String displayName;

    PublishingIntervals(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
