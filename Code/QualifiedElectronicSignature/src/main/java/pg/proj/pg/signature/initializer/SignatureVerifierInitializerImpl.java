package pg.proj.pg.signature.initializer;

import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.key.generator.PublicKeyGen;
import pg.proj.pg.signature.info.SignatureVerifierInfo;

import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.Signature;

public class SignatureVerifierInitializerImpl implements SignatureVerifierInitializer {
    @Override
    public Signature initializeVerifier(SignatureVerifierInfo verifierInfo) {
        byte[] keyBytes = verifierInfo.keyInfo().keyContent();
        PublicKeyGen keyGen = verifierInfo.keyGen();
        String algorithmType = verifierInfo.signatureType().getAlgorithmType();
        Signature signature = verifierInfo.signature();
        PublicKey key = keyGen.generatePublicKey(keyBytes, algorithmType);
        try {
            signature.initVerify(key);
            return signature;
        } catch (InvalidKeyException e) {
            throw new BasicAppError("Unable to init verifier");
        }
    }
}
