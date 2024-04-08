package pg.proj.pg.signature.selector;

import pg.proj.pg.signature.provider.SignatureExecutionerProvider;

public interface SignatureExecutionerSelector {
    SignatureExecutionerProvider selectExecutioner();
}
