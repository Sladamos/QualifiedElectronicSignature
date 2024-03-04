package pg.proj.pg.file.extension;

public enum FileExtension {
    TXT,
    CPP,
    UNKNOWN;

    /**
     * Throws {@code IllegalArgumentException} when
     * substring doesn't match any extension.
     */
    public static FileExtension fromString(String substring) {
        if(substring.equals("cpp")) {
            return CPP;
        } else if(substring.equals("txt")) {
            return TXT;
        }
        throw new IllegalArgumentException("Incorrect substring");
    }

    public String strValue() {
        if(this.equals(CPP)) {
            return "cpp";
        } else if(this.equals(TXT)) {
            return "txt";
        }
        throw new IllegalArgumentException("Incorrect extension");
    }
}
