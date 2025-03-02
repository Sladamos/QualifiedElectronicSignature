package pg.proj.pg.signature.info;

import pg.proj.pg.key.generator.PrivateKeyGen;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.signature.type.SignatureType;

import java.security.Signature;

public record SignatureExecutionerInfo(Signature signature, PrivateKeyGen keyGen,
                                       KeyInfo keyInfo, SignatureType signatureType) {
}
