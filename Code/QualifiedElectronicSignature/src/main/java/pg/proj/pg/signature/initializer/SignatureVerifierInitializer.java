package pg.proj.pg.signature.initializer;

import pg.proj.pg.signature.info.SignatureExecutionerInfo;
import pg.proj.pg.signature.info.SignatureVerifierInfo;

import java.security.Signature;

public interface SignatureVerifierInitializer {
    Signature initializeVerifier(SignatureVerifierInfo verifierInfo);
}
