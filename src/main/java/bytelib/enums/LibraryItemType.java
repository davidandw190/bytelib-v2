package bytelib.enums;

import java.io.Serializable;

public enum LibraryItemType implements Serializable {
    TEXTBOOK("TEXTBOOK"),
    NOVEL("NOVEL"),
    ARTICLE("ARTICLE"),
    JOURNAL("JOURNAL");

    private final String displayName;

    LibraryItemType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
