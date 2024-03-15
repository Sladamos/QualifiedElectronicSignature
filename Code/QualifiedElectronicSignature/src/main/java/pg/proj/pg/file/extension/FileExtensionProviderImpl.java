package pg.proj.pg.file.extension;

public class FileExtensionProviderImpl implements FileExtensionProvider {

    @Override
    public FileExtension fromFileName(String fileName) {
        int extensionIndex = fileName.lastIndexOf(".");
        if(extensionIndex <= 0) {
            return FileExtension.UNKNOWN;
        }

        try {
            return FileExtension.fromString(fileName.substring(extensionIndex + 1));
        } catch (IllegalArgumentException err) {
            return FileExtension.UNKNOWN;
        }
    }

}
