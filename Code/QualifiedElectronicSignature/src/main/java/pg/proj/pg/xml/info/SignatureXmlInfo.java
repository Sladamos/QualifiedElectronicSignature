    package pg.proj.pg.xml.info;

    import pg.proj.pg.document.info.DocumentInfo;

    public record SignatureXmlInfo(DocumentInfo sourceInfo, byte[] signedValue) {
    }
