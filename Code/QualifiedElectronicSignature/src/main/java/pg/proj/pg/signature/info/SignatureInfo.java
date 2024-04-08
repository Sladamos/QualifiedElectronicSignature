    package pg.proj.pg.signature.info;

    import pg.proj.pg.document.info.DocumentInfo;

    public record SignatureInfo(DocumentInfo sourceInfo, byte[] signedValue) {
    }
