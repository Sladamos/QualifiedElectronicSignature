package pg.proj.pg.file.extension;

public enum FileExtension {
    TXT,
    CPP,
    CYP,
    EPK,
    PUK,
    PPK,
    XML,
    UNKNOWN;

    /**
     * Throws {@code IllegalArgumentException} when
     * substring doesn't match any fileExtension.
     */
    public static FileExtension fromString(String substring) {
        switch (substring) {
            case "cpp": {
                return CPP;
            }
            case "txt": {
                return TXT;
            }
            case "cyp": {
                return CYP;
            }
            case "epk": {
                return EPK;
            }
            case "puk": {
                return PUK;
            }
            case "ppk": {
                return PPK;
            }
            case "xml": {
                return XML;
            }
        }
        throw new IllegalArgumentException("Incorrect substring");
    }

    public String strValue() {
        if(this == UNKNOWN) {
            throw new IllegalArgumentException("Unknown type");
        }
        return this.name().toLowerCase();
    }
}
