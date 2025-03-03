    package pg.proj.pg.signature.info;

    import lombok.Builder;
    import pg.proj.pg.document.info.DocumentInfo;

    import java.util.Arrays;

    @Builder
    public record SignatureInfo(DocumentInfo sourceInfo, byte[] signedValue) {

        @Override
        public boolean equals(Object obj) {
            if(obj == this) {
                return true;
            }

            if(!(obj instanceof SignatureInfo(DocumentInfo info, byte[] value))) {
                return false;
            }

            return sourceInfo.equals(info) && Arrays.equals(signedValue, value);
        }
    }
