package pg.proj.pg.signature.executioner;

import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.signature.info.SignatureExecutionerInfo;
import pg.proj.pg.signature.initializer.SignatureExecutionerInitializer;

import java.security.Signature;
import java.security.SignatureException;

public class SignatureExecutionerImpl implements SignatureExecutioner {

    private final SignatureExecutionerInfo executionerInfo;

    private final SignatureExecutionerInitializer executionerInitializer;

    public SignatureExecutionerImpl(SignatureExecutionerInfo executionerInfo,
                                    SignatureExecutionerInitializer executionerInitializer) {
        this.executionerInfo = executionerInfo;
        this.executionerInitializer = executionerInitializer;
    }

    @Override
    public byte[] sign(byte[] source) {
        Signature signature = executionerInitializer.initializeExecutioner(executionerInfo);
        try {
            signature.update(source);
            return signature.sign();
        } catch (SignatureException e) {
            throw new BasicAppError("Unable to sign");
        }
    }
}
