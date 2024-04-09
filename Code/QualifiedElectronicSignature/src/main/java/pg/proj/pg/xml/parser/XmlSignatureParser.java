package pg.proj.pg.xml.parser;

import pg.proj.pg.signature.info.SignatureInfo;

public interface XmlSignatureParser {
    SignatureInfo fromXml(String xml);
}
