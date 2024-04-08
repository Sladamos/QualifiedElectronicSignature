package pg.proj.pg.document.details.provider;

import pg.proj.pg.document.details.DocumentDetails;
import pg.proj.pg.file.info.FileInfo;

public interface DocumentDetailsProvider {
    DocumentDetails getDocumentDetails(FileInfo fileInfo);
}
