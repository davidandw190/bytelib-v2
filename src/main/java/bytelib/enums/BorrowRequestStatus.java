package bytelib.enums;

import java.io.Serializable;

public enum BorrowRequestStatus implements Serializable {
    PENDING_APPROVAL("PENDING_APPROVAL"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    OVERDUE("OVERDUE"),
    RETURNED("RETURNED");

    private final String displayName;

    BorrowRequestStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
