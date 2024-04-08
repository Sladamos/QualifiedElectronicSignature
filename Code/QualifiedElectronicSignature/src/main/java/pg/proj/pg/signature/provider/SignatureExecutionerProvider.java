package pg.proj.pg.signature.provider;

import pg.proj.pg.signature.executioner.SignatureExecutioner;

public interface SignatureExecutionerProvider {
    SignatureExecutioner getSignatureExecutioner();
    String getUniqueName();
}
