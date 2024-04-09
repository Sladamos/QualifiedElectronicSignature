package pg.proj.pg.signature.provider;

import lombok.AllArgsConstructor;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.signature.info.SignatureInfo;
import pg.proj.pg.xml.parser.XmlSignatureParser;

@AllArgsConstructor
public class XmlSignatureInfoProvider implements SignatureInfoProvider {

    private final XmlSignatureParser xmlSignatureParser;

    private final FileContentOperator fileContentOperator;

    @Override
    public SignatureInfo getSignatureInfo(FileInfo fileInfo) {
        String xml = fileContentOperator.loadStrFileContent(fileInfo);
        return xmlSignatureParser.fromXml(xml);
    }
}
