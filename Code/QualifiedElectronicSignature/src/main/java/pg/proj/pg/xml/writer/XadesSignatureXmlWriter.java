package pg.proj.pg.xml.writer;

import pg.proj.pg.xml.info.SignatureXmlInfo;

import java.util.Arrays;
import java.util.Base64;

public class XadesSignatureXmlWriter implements SignatureXmlWriter {
    @Override
    public String toXml(SignatureXmlInfo xmlInfo) {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlBuilder.append("<document>\n");
        xmlBuilder.append("\t<details>\n");
        xmlBuilder.append("\t\t<lastModificationDate>").append(xmlInfo.sourceInfo().documentDetails().modificationDate()).append("</lastModificationDate>\n");
        xmlBuilder.append("\t\t<creationDate>").append(xmlInfo.sourceInfo().documentDetails().creationDate()).append("</creationDate>\n");
        xmlBuilder.append("\t\t<fileName>").append(xmlInfo.sourceInfo().documentDetails().fileInfo().fileName().toLowerCase().split("\\.")[0]).append("</fileName>\n");
        xmlBuilder.append("\t\t<fileExtension>").append(xmlInfo.sourceInfo().documentDetails().fileInfo().extension().strValue()).append("</fileExtension>\n");
        xmlBuilder.append("\t\t<length>").append(xmlInfo.sourceInfo().documentDetails().size()).append("</length>\n");
        xmlBuilder.append("\t</details>\n");
        xmlBuilder.append("\t<content>\n");
        xmlBuilder.append("\t\t<author>").append(xmlInfo.sourceInfo().authorInfo().name()).append("</author>\n");
        xmlBuilder.append("\t\t<signatureDate>").append(xmlInfo.sourceInfo().documentDateTime()).append("</signatureDate>\n");
        xmlBuilder.append("\t\t<signature>").append(Arrays.toString(Base64.getEncoder().encode(xmlInfo.signedValue()))).append("</signature>\n");
        xmlBuilder.append("\t</content>\n");
        xmlBuilder.append("</document>");
        return xmlBuilder.toString();
    }
}
