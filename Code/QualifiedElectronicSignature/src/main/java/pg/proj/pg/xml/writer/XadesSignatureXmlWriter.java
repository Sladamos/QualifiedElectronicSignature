package pg.proj.pg.xml.writer;

import pg.proj.pg.signature.info.SignatureInfo;

import java.util.Base64;

public class XadesSignatureXmlWriter implements SignatureXmlWriter {
    @Override
    public String toXml(SignatureInfo xmlInfo) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<document>\n" +
                "\t<details>\n" +
                "\t\t<lastModificationDate>" + xmlInfo.sourceInfo().documentDetails().modificationDate() + "</lastModificationDate>\n" +
                "\t\t<creationDate>" + xmlInfo.sourceInfo().documentDetails().creationDate() + "</creationDate>\n" +
                "\t\t<fileName>" + xmlInfo.sourceInfo().documentDetails().fileInfo().fileName().toLowerCase().split("\\.")[0] + "</fileName>\n" +
                "\t\t<fileExtension>" + xmlInfo.sourceInfo().documentDetails().fileInfo().extension().strValue() + "</fileExtension>\n" +
                "\t\t<length>" + xmlInfo.sourceInfo().documentDetails().size() + "</length>\n" +
                "\t</details>\n" +
                "\t<content>\n" +
                "\t\t<author>" + xmlInfo.sourceInfo().authorInfo().name() + "</author>\n" +
                "\t\t<signatureDate>" + xmlInfo.sourceInfo().documentDateTime() + "</signatureDate>\n" +
                "\t\t<signature>" + Base64.getEncoder().encodeToString(xmlInfo.signedValue()) + "</signature>\n" +
                "\t</content>\n" +
                "</document>";
    }
}
