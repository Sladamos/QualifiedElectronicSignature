package pg.proj.pg.signature.provider;

import pg.proj.pg.signature.verifier.SignatureVerifier;

public interface SignatureVerifierProvider {
    SignatureVerifier getSignatureVerifier();
    String getUniqueName();
}
