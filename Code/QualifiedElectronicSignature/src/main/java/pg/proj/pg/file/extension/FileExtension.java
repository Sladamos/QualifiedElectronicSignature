package pg.proj.pg.file.extension;

public enum FileExtension {
    TXT,
    CPP,
    CYP,
    UNKNOWN;

    /**
     * Throws {@code IllegalArgumentException} when
     * substring doesn't match any extension.
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
        }
        throw new IllegalArgumentException("Incorrect substring");
    }

    public String strValue() {
        switch (this) {
            case CPP: {
                return "cpp";
            }
            case TXT: {
                return "txt";
            }
            case CYP: {
                return "cyp";
            }
        }
        throw new IllegalArgumentException("Incorrect extension");
    }
}
