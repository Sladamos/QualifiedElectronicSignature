package pg.proj.pg.xml.writer;

import pg.proj.pg.signature.info.SignatureInfo;

public interface SignatureXmlWriter {
    String toXml(SignatureInfo xmlInfo);
}
