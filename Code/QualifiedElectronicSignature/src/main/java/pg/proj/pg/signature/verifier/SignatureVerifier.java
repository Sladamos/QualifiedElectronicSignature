package pg.proj.pg.signature.verifier;

public interface SignatureVerifier {
    boolean isSignatureValid(byte[] message, byte[] signature);
}
