package pg.proj.pg.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pg.proj.pg.file.extension.FileExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileExtensionTest {

    @ParameterizedTest
    @CsvSource({
            "TXT, txt",
            "PDF, pdf",
            "CYP, cyp",
            "EPK, epk",
            "PUK, puk",
            "PPK, ppk"
    })
    public void shouldReturnCorrectStringWhenExtensionProvided(FileExtension extension, String expected) {
        assertThat(extension.strValue()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "txt, TXT",
            "pdf, PDF",
            "cyp, CYP",
            "epk, EPK",
            "puk, PUK",
            "ppk, PPK"
    })
    public void shouldReturnCorrectExtensionWhenFromStringCreated(String input, FileExtension expected) {
        assertThat(FileExtension.fromString(input)).isEqualTo(expected);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUnknownExtensionProvided() {
        FileExtension uut = FileExtension.UNKNOWN;
        assertThatThrownBy(uut::strValue).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenFromRandomStringCreated() {
        assertThatThrownBy(() -> FileExtension.fromString("incorrectRandomString$#$"))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
