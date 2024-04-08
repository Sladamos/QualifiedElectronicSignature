package pg.proj.pg.file.provider;

import lombok.AllArgsConstructor;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.extension.FileExtensionProvider;
import pg.proj.pg.file.extension.FileExtensionProviderImpl;
import pg.proj.pg.file.info.FileInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@AllArgsConstructor
public class FileProviderImpl implements FileProvider {

    private final FileInfo template;

    public static FileProviderImpl fromSource(FileProvider sourceFileProvider, FileExtension extension,
                                              String postfix) {
        FileInfo fileInfo = sourceFileProvider.getFileInfo();
        String newName = "%s_%s.%s".formatted(fileInfo.fileName().split("\\.")[0], postfix, extension.strValue());
        Path path = FileSystems.getDefault().getPath(fileInfo.canonicalPath());
        Path newPath = path.resolveSibling(newName);
        String newCanonicalPath = String.valueOf(newPath);
        FileInfo destinationFileInfo = new FileInfo(newCanonicalPath, newName, extension);
        return new FileProviderImpl(destinationFileInfo);
    }

    public static FileProviderImpl createFromFile(File file) {
        FileExtensionProvider fileExtensionProvider = new FileExtensionProviderImpl();
        FileExtension extension = fileExtensionProvider.fromFileName(file.getName());
        try {
            FileInfo fileInfo = new FileInfo(file.getCanonicalPath(), file.getName(), extension);
            return new FileProviderImpl(fileInfo);
        } catch (IOException err) {
            throw new BasicAppError(err.getMessage());
        }
    }

    public FileInfo getFileInfo() {
        if(template == null) {
            throw new CriticalAppError("Unable to get information about file");
        }
        return new FileInfo(template.canonicalPath(), template.fileName(), template.extension());
    }
}
