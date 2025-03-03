package pg.proj.pg.xml.parser;

import org.junit.jupiter.api.Test;
import pg.proj.pg.author.info.AuthorInfo;
import pg.proj.pg.document.details.DocumentDetails;
import pg.proj.pg.document.info.DocumentInfo;
import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.signature.info.SignatureInfo;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class XadesXmlSignatureParserTest {

    private final XadesXmlSignatureParser uut = new XadesXmlSignatureParser();

    @Test
    public void shouldCreateValidSignatureInfoFromValidXmlFile() throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("signature.xml")) {
            String signature = new String(is.readAllBytes());
            SignatureInfo signatureInfo = uut.fromXml(signature);
            SignatureInfo xmlInfo = buildTestSignatureInfo();
            assertThat(signatureInfo).isEqualTo(xmlInfo);
        }
    }

    @Test
    public void shouldThrowBasicAppErrorWhenInvalidXmlFilePassed() throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("signature-invalid.xml")) {
            String signature = new String(is.readAllBytes());
            assertThatThrownBy(() -> uut.fromXml(signature)).isInstanceOf(BasicAppError.class);
        }
    }

    private SignatureInfo buildTestSignatureInfo() {
        return SignatureInfo.builder()
                .sourceInfo(DocumentInfo.builder()
                        .authorInfo(new AuthorInfo("TestAuthor"))
                        .documentDateTime(LocalDateTime.of(2000, 1, 1, 0, 0).toInstant(ZoneOffset.UTC))
                        .documentDetails(DocumentDetails.builder()
                                .creationDate(LocalDateTime.of(1999, 1, 1, 0, 0).toInstant(ZoneOffset.UTC))
                                .modificationDate(LocalDateTime.of(1999, 1, 2, 0, 0).toInstant(ZoneOffset.UTC))
                                .fileExtension(FileExtension.PDF)
                                .fileName("TestName")
                                .size(100)
                                .build())
                        .build())
                .signedValue(new byte[]{84, 101, 115, 116, 67, 111, 110, 116, 101, 110, 116})
                .build();

    }
}