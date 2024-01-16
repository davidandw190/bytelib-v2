package bytelib.enums;

import java.io.Serializable;

public enum BookGenre implements Serializable {
    FANTASY("Fantasy"),
    SCIENCE_FICTION("Science Fiction"),
    PHILOSOPHY("Philosophy"),
    PSYCHOLOGY("Psychology"),
    POEMS("Poems"),
    ROMANCE("Romance"),
    SCIENCE("Science"),
    ASTRONOMY("Astronomy"),
    POLITICS("Politics"),
    PHYSICS("Physics"),
    OTHER("Other");

    private final String displayName;

    BookGenre(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
