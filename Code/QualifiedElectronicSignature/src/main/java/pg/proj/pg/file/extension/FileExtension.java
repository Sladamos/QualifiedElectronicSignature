package pg.proj.pg.file.extension;

import java.util.List;

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

    public static List<String> getAllowedExtensions() {
        return List.of("cpp", "txt");
    }
}
