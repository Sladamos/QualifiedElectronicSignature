package pg.proj.pg.signature.info;

import pg.proj.pg.key.generator.KeyGen;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.signature.type.SignatureType;

import java.security.Signature;

public record SignatureInfo(Signature signature, KeyGen keyGen, KeyInfo keyInfo, SignatureType signatureType) {
}
