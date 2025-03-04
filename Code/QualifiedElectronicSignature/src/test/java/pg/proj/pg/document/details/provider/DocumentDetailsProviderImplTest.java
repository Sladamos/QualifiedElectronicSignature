package pg.proj.pg.document.details.provider;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pg.proj.pg.document.details.DocumentDetails;
import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.info.FileInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class DocumentDetailsProviderImplTest {

    private final DocumentDetailsProviderImpl uut = new DocumentDetailsProviderImpl();

    @Test
    void shouldThrowBasicAppErrorWhenIOExceptionOccurs() {
        String path = "/path/to/file";
        FileInfo fileInfo = new FileInfo(path, "fileName", FileExtension.CYP);

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAttributes(Path.of(path), BasicFileAttributes.class)).thenThrow(new IOException("File not found"));

            assertThatThrownBy(() -> uut.getDocumentDetails(fileInfo)).isInstanceOf(BasicAppError.class);
        }
    }

    @Test
    void shouldReturnValidDocumentDetails() {
        String path = "/path/to/file";
        String fileName = "fileName";
        FileExtension extension = FileExtension.CYP;
        FileTime fileTime = FileTime.fromMillis(System.currentTimeMillis());
        long fileSize = 100L;
        FileInfo fileInfo = new FileInfo(path, fileName, extension);
        BasicFileAttributes fileAttributes = Mockito.mock(BasicFileAttributes.class);
        when(fileAttributes.size()).thenReturn(fileSize);
        when(fileAttributes.lastModifiedTime()).thenReturn(fileTime);
        when(fileAttributes.creationTime()).thenReturn(fileTime);


        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAttributes(Path.of(path), BasicFileAttributes.class)).thenReturn(fileAttributes);
            assertThat(uut.getDocumentDetails(fileInfo)).isEqualTo(DocumentDetails.builder()
                    .fileExtension(extension)
                    .fileName(fileName)
                    .size(fileSize)
                    .creationDate(fileTime.toInstant())
                    .modificationDate(fileTime.toInstant())
                    .build());
        }
    }
}

    /*
    *
    try {
        Path filePath = Path.of(fileInfo.canonicalPath());
        BasicFileAttributes fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
        return new DocumentDetails(fileAttributes.size(), fileAttributes.creationTime().toInstant(),
                fileAttributes.lastModifiedTime().toInstant(), fileInfo.fileName(), fileInfo.extension());
    } catch (IOException e) {
        throw new BasicAppError("Unable to get details from file");
    }
    * */
