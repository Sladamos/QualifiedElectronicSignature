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

    public DocumentInfoProviderImpl(DocumentDetailsProvider detailsProvider,
                                    AuthorProvider authorProvider,
                                    DateProvider dateProvider) {
        this.detailsProvider = detailsProvider;
        this.authorProvider = authorProvider;
        this.dateProvider = dateProvider;
    }

    @Override
    public DocumentInfo getDocumentInfo(FileInfo fileInfo) {
        return new DocumentInfo(detailsProvider.getDocumentDetails(fileInfo), authorProvider.getAuthor(), dateProvider.getDate());
    }
}
