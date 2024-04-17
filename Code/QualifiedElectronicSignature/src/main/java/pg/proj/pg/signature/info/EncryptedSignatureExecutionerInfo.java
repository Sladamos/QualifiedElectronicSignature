package pg.proj.pg.signature.info;

import pg.proj.pg.iv.InitializationVector;

public record EncryptedSignatureExecutionerInfo (SignatureExecutionerInfo signatureExecutionerInfo,
                                                 InitializationVector iv) {
}
