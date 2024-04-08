package pg.proj.pg.signature.initializer;

import pg.proj.pg.signature.info.SignatureExecutionerInfo;

import java.security.Signature;

public interface SignatureExecutionerInitializer {
    Signature initializeExecutioner(SignatureExecutionerInfo executionerInfo);
}
