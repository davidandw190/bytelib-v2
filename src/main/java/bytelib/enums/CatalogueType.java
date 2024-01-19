package bytelib.enums;

public enum CatalogueType {
    SCIENTIFIC("Scientific Catalogue"),
    BOOKS("Books Catalogue");

    private final String label;

    CatalogueType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}