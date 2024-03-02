package pg.proj.pg.file.extension;

public interface FileExtensionProvider {
    FileExtension fromFileName(String fileName);
}
