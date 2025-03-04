package pg.proj.pg.document.info.provider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pg.proj.pg.author.AuthorInfo;
import pg.proj.pg.author.provider.AuthorProvider;
import pg.proj.pg.date.provider.DateProvider;
import pg.proj.pg.document.details.DocumentDetails;
import pg.proj.pg.document.details.provider.DocumentDetailsProvider;
import pg.proj.pg.document.info.DocumentInfo;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.info.FileInfo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentInfoProviderImplTest {

    @Mock
    private DocumentDetailsProvider detailsProvider;

    @Mock
    private AuthorProvider authorProvider;

    @Mock
    private DateProvider dateProvider;

    @Mock
    private FileInfo fileInfo;

    @InjectMocks
    public DocumentInfoProviderImpl uut;

    @Test
    void shouldReturnValidDocumentInfo() {
        AuthorInfo authorInfo = new AuthorInfo("Test author");
        LocalDateTime localDateTime = LocalDateTime.now();
        DocumentDetails documentDetails = DocumentDetails.builder()
                .size(10)
                .fileName("testName")
                .fileExtension(FileExtension.PDF)
                .modificationDate(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .creationDate(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .build();
        when(authorProvider.getAuthor()).thenReturn(authorInfo);
        when(detailsProvider.getDocumentDetails(fileInfo)).thenReturn(documentDetails);
        when(dateProvider.getDate()).thenReturn(localDateTime);
        DocumentInfo documentInfo = uut.getDocumentInfo(fileInfo);
        assertAll("Should create valid document info",
                () -> assertEquals(authorInfo, documentInfo.authorInfo()),
                () -> assertEquals(documentDetails, documentInfo.documentDetails()),
                () -> assertEquals(localDateTime.atZone(ZoneId.systemDefault()).toInstant(), documentInfo.documentDateTime())
        );
    }
}