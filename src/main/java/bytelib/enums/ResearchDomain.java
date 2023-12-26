package bytelib.enums;

import java.io.Serializable;

public enum ResearchDomain implements Serializable {
    SCIENCE("Science"),
    TECHNOLOGY("Technology"),
    ENGINEERING("Engineering"),
    MEDICINE("Medicine"),
    BIOLOGY("Biology"),
    CHEMISTRY("Chemistry"),
    PHYSICS("Physics"),
    MATHEMATICS("Mathematics"),
    SOCIAL_SCIENCE("Social Science"),
    HUMANITIES("Humanities"),
    ECONOMICS("Economics"),
    COMPUTER_SCIENCE("Computer Science"),
    DISTRIBUTED_SYSTEMS("Distributed Systems"),
    MACHINE_LEARNING("Machine Learning"),
    ENVIRONMENTAL_SCIENCE("Environmental Science"),
    EDUCATION("Education"),
    LAW("Law"),
    OTHER("Other");

    private final String displayName;

    ResearchDomain(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
