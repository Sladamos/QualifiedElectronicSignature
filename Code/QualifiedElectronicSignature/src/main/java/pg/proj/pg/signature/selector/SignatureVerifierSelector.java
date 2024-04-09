package pg.proj.pg.signature.selector;

import pg.proj.pg.signature.provider.SignatureVerifierProvider;

public interface SignatureVerifierSelector {
    SignatureVerifierProvider selectVerifier();
}
