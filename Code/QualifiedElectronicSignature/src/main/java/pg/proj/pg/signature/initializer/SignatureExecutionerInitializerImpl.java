package pg.proj.pg.signature.initializer;

import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.key.generator.PrivateKeyGen;
import pg.proj.pg.signature.info.SignatureExecutionerInfo;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.Signature;

public class SignatureExecutionerInitializerImpl implements SignatureExecutionerInitializer {
    @Override
    public Signature initializeExecutioner(SignatureExecutionerInfo executionerInfo) {
        byte[] keyBytes = executionerInfo.keyInfo().keyContent();
        PrivateKeyGen keyGen = executionerInfo.keyGen();
        String algorithmType = executionerInfo.signatureType().getAlgorithmType();
        Signature signature = executionerInfo.signature();
        PrivateKey key = keyGen.getPrivateKey(keyBytes, algorithmType);
        try {
            signature.initSign(key);
            return signature;
        } catch (InvalidKeyException e) {
            throw new BasicAppError("Unable to init cipher");
        }
    }
}
