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
    public void should_returnPdfString_when_pdfExtensionProvided() {
        FileExtension extension = FileExtension.PDF;
        String value = extension.strValue();
        assertThat(value).isEqualTo("pdf");
    }

    @Test
    public void should_returnCypString_when_cypExtensionProvided() {
        FileExtension extension = FileExtension.CYP;
        String value = extension.strValue();
        assertThat(value).isEqualTo("cyp");
    }

    @Test
    public void should_returnEpkString_when_epkExtensionProvided() {
        FileExtension extension = FileExtension.EPK;
        String value = extension.strValue();
        assertThat(value).isEqualTo("epk");
    }

    @Test
    public void should_returnPukString_when_pukExtensionProvided() {
        FileExtension extension = FileExtension.PUK;
        String value = extension.strValue();
        assertThat(value).isEqualTo("puk");
    }

    @Test
    public void should_returnPpkString_when_ppkExtensionProvided() {
        FileExtension extension = FileExtension.PPK;
        String value = extension.strValue();
        assertThat(value).isEqualTo("ppk");
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
    public void should_returnPdfExtension_when_fromPdfStringCreated() {
        var extension = FileExtension.fromString("pdf");
        assertThat(extension).isEqualTo(FileExtension.PDF);
    }

    @Test
    public void should_returnEpkExtension_when_fromEpkStringCreated() {
        var extension = FileExtension.fromString("epk");
        assertThat(extension).isEqualTo(FileExtension.EPK);
    }

    @Test
    public void should_returnPukExtension_when_fromPukStringCreated() {
        var extension = FileExtension.fromString("puk");
        assertThat(extension).isEqualTo(FileExtension.PUK);
    }

    @Test
    public void should_returnPpkExtension_when_fromPpkStringCreated() {
        var extension = FileExtension.fromString("ppk");
        assertThat(extension).isEqualTo(FileExtension.PPK);
    }

    @Test
    public void should_throwIllegalArgumentException_when_fromRandomStringCreated() {
        assertThatThrownBy(() -> FileExtension.fromString("incorrectRandomString$#$"))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
