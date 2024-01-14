package bytelib.enums;

import java.io.Serializable;

public enum ResearchDomain implements Serializable {
    SCIENCE("SCIENCE"),
    TECHNOLOGY("TECHNOLOGY"),
    ENGINEERING("ENGINEERING"),
    MEDICINE("MEDICINE"),
    BIOLOGY("BIOLOGY"),
    CHEMISTRY("CHEMISTRY"),
    PHYSICS("PHYSICS"),
    MATHEMATICS("MATHEMATICS"),
    SOCIAL_SCIENCE("SOCIAL SCIENCE"),
    HUMANITIES("HUMANITIES"),
    ECONOMICS("ECONOMICS"),
    COMPUTER_SCIENCE("COMPUTER SCIENCE"),
    DISTRIBUTED_SYSTEMS("DISTRIBUTED SYSTEMS"),
    MACHINE_LEARNING("MACHINE LEARNING"),
    ENVIRONMENTAL_SCIENCE("ENVIRONMENTAL SCIENCE"),
    EDUCATION("EDUCATION"),
    LAW("LAW"),
    OTHER("OTHER");

    private final String displayName;

    ResearchDomain(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
