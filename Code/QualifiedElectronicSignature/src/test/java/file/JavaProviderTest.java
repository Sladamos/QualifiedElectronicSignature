package file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.provider.FileProviderImpl;

import static org.assertj.core.api.Assertions.*;

public class JavaProviderTest {

    public FileInfo fileInfoMock;

    @BeforeEach
    public void initializeTemplate() {
        fileInfoMock = new FileInfo("testPath", "testName", FileExtension.UNKNOWN);
    }

    @Test
    public void should_returnClonedFileInfo_when_getFileInfoCalled() {
        FileProvider fileProvider = new FileProviderImpl(fileInfoMock);
        FileInfo fileInfo = fileProvider.getFileInfo();
        assertThat(fileInfo).isNotSameAs(fileInfoMock);
    }

    @Test
    public void should_returnSamePath_when_getFileInfoCalled() {
        FileProvider fileProvider = new FileProviderImpl(fileInfoMock);
        FileInfo fileInfo = fileProvider.getFileInfo();
        assertThat(fileInfo.canonicalPath()).isEqualTo(fileInfoMock.canonicalPath());
    }

    @Test
    public void should_returnSameName_when_getFileInfoCalled() {
        FileProvider fileProvider = new FileProviderImpl(fileInfoMock);
        FileInfo fileInfo = fileProvider.getFileInfo();
        assertThat(fileInfo.fileName()).isEqualTo(fileInfoMock.fileName());
    }

    @Test
    public void should_returnSameExtension_when_getFileInfoCalled() {
        FileProvider fileProvider = new FileProviderImpl(fileInfoMock);
        FileInfo fileInfo = fileProvider.getFileInfo();
        assertThat(fileInfo.extension()).isEqualTo(fileInfoMock.extension());
    }
}
