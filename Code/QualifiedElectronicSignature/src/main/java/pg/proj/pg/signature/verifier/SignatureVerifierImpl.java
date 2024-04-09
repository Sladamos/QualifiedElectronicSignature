package pg.proj.pg.signature.verifier;

public class SignatureVerifierImpl implements SignatureVerifier {
    @Override
    public boolean isSignatureValid(byte[] message, byte[] signature) {
        return false;
    }
}
