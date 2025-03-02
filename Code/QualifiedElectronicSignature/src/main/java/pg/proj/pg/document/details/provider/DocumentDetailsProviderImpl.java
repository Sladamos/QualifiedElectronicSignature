package pg.proj.pg.document.details.provider;

import pg.proj.pg.document.details.DocumentDetails;
import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.file.info.FileInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class DocumentDetailsProviderImpl implements DocumentDetailsProvider {
    @Override
    public DocumentDetails getDocumentDetails(FileInfo fileInfo) {
        try {
            Path filePath = Path.of(fileInfo.canonicalPath());
            BasicFileAttributes fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
            return new DocumentDetails(fileAttributes.size(), fileAttributes.creationTime().toInstant(),
                    fileAttributes.lastModifiedTime().toInstant(), fileInfo.fileName(), fileInfo.extension());
        } catch (IOException e) {
            throw new BasicAppError("Unable to get details from file");
        }
    }
}
