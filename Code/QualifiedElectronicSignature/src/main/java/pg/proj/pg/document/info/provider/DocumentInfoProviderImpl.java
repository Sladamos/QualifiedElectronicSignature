package pg.proj.pg.document.info.provider;

import lombok.AllArgsConstructor;
import pg.proj.pg.author.provider.AuthorProvider;
import pg.proj.pg.date.provider.DateProvider;
import pg.proj.pg.document.details.provider.DocumentDetailsProvider;
import pg.proj.pg.document.info.DocumentInfo;
import pg.proj.pg.file.info.FileInfo;

import java.time.ZoneId;

@AllArgsConstructor
public class DocumentInfoProviderImpl implements DocumentInfoProvider {

    private final DocumentDetailsProvider detailsProvider;

    private final AuthorProvider authorProvider;

    private final DateProvider dateProvider;

    @Override
    public DocumentInfo getDocumentInfo(FileInfo fileInfo) {
        return new DocumentInfo(detailsProvider.getDocumentDetails(fileInfo), authorProvider.getAuthor(),
                dateProvider.getDate().atZone(ZoneId.systemDefault()).toInstant());
    }
}
