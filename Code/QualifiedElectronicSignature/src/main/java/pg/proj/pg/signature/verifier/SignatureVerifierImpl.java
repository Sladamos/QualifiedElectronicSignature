package pg.proj.pg.signature.verifier;

import lombok.AllArgsConstructor;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.signature.info.SignatureVerifierInfo;
import pg.proj.pg.signature.initializer.SignatureVerifierInitializer;

import java.security.Signature;
import java.security.SignatureException;

@AllArgsConstructor
public class SignatureVerifierImpl implements SignatureVerifier {

    private final SignatureVerifierInfo verifierInfo;

    private final SignatureVerifierInitializer verifierInitializer;

    @Override
    public boolean isSignatureValid(byte[] message, byte[] messageSignature) {
        Signature signature = verifierInitializer.initializeVerifier(verifierInfo);
        try {
            signature.update(message);
            return signature.verify(messageSignature);
        } catch (SignatureException e) {
            throw new BasicAppError("Unable to verify");
        }
    }
}
