package pg.proj.pg.document.info.provider;

import pg.proj.pg.author.provider.AuthorProvider;
import pg.proj.pg.date.provider.DateProvider;
import pg.proj.pg.document.details.provider.DocumentDetailsProvider;
import pg.proj.pg.document.info.DocumentInfo;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.provider.FileProvider;

public class DocumentInfoProviderImpl implements DocumentInfoProvider {

    private final DocumentDetailsProvider detailsProvider;

    private final AuthorProvider authorProvider;

    private final DateProvider dateProvider;

    private final FileProvider fileProvider;

    public DocumentInfoProviderImpl(DocumentDetailsProvider detailsProvider,
                                    AuthorProvider authorProvider,
                                    DateProvider dateProvider,
                                    FileProvider fileProvider) {
        this.detailsProvider = detailsProvider;
        this.authorProvider = authorProvider;
        this.dateProvider = dateProvider;
        this.fileProvider = fileProvider;
    }

    @Override
    public DocumentInfo getDocumentInfo() {
        FileInfo fileInfo = fileProvider.getFileInfo();
        return new DocumentInfo(detailsProvider.getDocumentDetails(fileInfo), authorProvider.getAuthor(), dateProvider.getDate());
    }
}
