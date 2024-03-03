package file;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pg.proj.pg.file.extension.FileExtension;

public class FileExtensionTest {

    @Test
    public void should_returnTwoExtensions_when_getAllowedExtensionsCalled() {
        var extensions = FileExtension.getAllowedExtensions();
        assertThat(extensions).hasSize(2);
    }

    @Test
    public void should_returnCppExtension_when_getAllowedExtensionsCalled() {
        var extensions = FileExtension.getAllowedExtensions();
        assertThat(extensions).contains("cpp");
    }

    @Test
    public void should_returnTxtExtension_when_getAllowedExtensionsCalled() {
        var extensions = FileExtension.getAllowedExtensions();
        assertThat(extensions).contains("txt");
    }

    @Test
    public void should_returnTxtExtension_when_fromTxtStringCreated() {
        var extension = FileExtension.fromString("txt");
        assertThat(extension).isEqualTo(FileExtension.TXT);
    }

    @Test
    public void should_returnCppExtension_when_fromTxtStringCreated() {
        var extension = FileExtension.fromString("cpp");
        assertThat(extension).isEqualTo(FileExtension.CPP);
    }

    @Test
    public void should_throwIllegalArgumentException_when_fromRandomStringCreated() {
        assertThatThrownBy(() -> FileExtension.fromString("incorrectRandomString$#$"))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
