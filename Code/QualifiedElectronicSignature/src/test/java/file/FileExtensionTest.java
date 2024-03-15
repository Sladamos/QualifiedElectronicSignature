package file;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pg.proj.pg.file.extension.FileExtension;

public class FileExtensionTest {

    @Test
    public void should_returnTxtString_when_txtExtensionProvided() {
        FileExtension extension = FileExtension.TXT;
        String value = extension.strValue();
        assertThat(value).isEqualTo("txt");
    }

    @Test
    public void should_returnCppString_when_cppExtensionProvided() {
        FileExtension extension = FileExtension.CPP;
        String value = extension.strValue();
        assertThat(value).isEqualTo("cpp");
    }

    @Test
    public void should_returnCypString_when_cypExtensionProvided() {
        FileExtension extension = FileExtension.CYP;
        String value = extension.strValue();
        assertThat(value).isEqualTo("cyp");
    }

    @Test
    public void should_throwIllegalArgumentException_when_unknownExtensionProvided() {
        FileExtension extension = FileExtension.UNKNOWN;
        assertThatThrownBy(extension::strValue).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void should_returnTxtExtension_when_fromTxtStringCreated() {
        var extension = FileExtension.fromString("txt");
        assertThat(extension).isEqualTo(FileExtension.TXT);
    }

    @Test
    public void should_returnCypExtension_when_fromCypStringCreated() {
        var extension = FileExtension.fromString("cyp");
        assertThat(extension).isEqualTo(FileExtension.CYP);
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
