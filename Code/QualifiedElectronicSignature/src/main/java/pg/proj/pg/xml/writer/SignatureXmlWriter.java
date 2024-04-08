package pg.proj.pg.xml.writer;

import pg.proj.pg.xml.info.SignatureXmlInfo;

public interface SignatureXmlWriter {
    String toXml(SignatureXmlInfo xmlInfo);
}
