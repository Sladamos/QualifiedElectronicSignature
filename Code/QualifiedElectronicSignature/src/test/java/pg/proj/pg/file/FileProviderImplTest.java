package pg.proj.pg.file;

import org.junit.jupiter.api.Test;
import pg.proj.pg.error.CriticalAppError;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.provider.FileProviderImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileProviderImplTest {

    public final FileInfo fileInfoMock = new FileInfo("testPath", "testName", FileExtension.UNKNOWN);;

    @Test
    public void shouldThrowCriticalAppErrorWhenGetFileInfoCalledAndFileInfoIsNull() {
        FileProvider fileProvider = new FileProviderImpl(null);
        assertThatThrownBy(fileProvider::getFileInfo).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void shouldReturnClonedFileInfoWhenGetFileInfoCalled() {
        FileProvider fileProvider = new FileProviderImpl(fileInfoMock);
        FileInfo fileInfo = fileProvider.getFileInfo();
        assertThat(fileInfo).isNotSameAs(fileInfoMock);
    }

    @Test
    public void shouldReturnSamePathWhenGetFileInfoCalled() {
        FileProvider fileProvider = new FileProviderImpl(fileInfoMock);
        FileInfo fileInfo = fileProvider.getFileInfo();
        assertThat(fileInfo.canonicalPath()).isEqualTo(fileInfoMock.canonicalPath());
    }

    @Test
    public void shouldReturnSameNameWhenGetFileInfoCalled() {
        FileProvider fileProvider = new FileProviderImpl(fileInfoMock);
        FileInfo fileInfo = fileProvider.getFileInfo();
        assertThat(fileInfo.fileName()).isEqualTo(fileInfoMock.fileName());
    }

    @Test
    public void shouldReturnSameExtensionWhenGetFileInfoCalled() {
        FileProvider fileProvider = new FileProviderImpl(fileInfoMock);
        FileInfo fileInfo = fileProvider.getFileInfo();
        assertThat(fileInfo.extension()).isEqualTo(fileInfoMock.extension());
    }
}
