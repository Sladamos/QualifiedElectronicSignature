package pg.proj.pg.signature.executioner;

import lombok.AllArgsConstructor;
import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.signature.info.SignatureExecutionerInfo;
import pg.proj.pg.signature.initializer.SignatureExecutionerInitializer;

import java.security.Signature;
import java.security.SignatureException;

@AllArgsConstructor
public class SignatureExecutionerImpl implements SignatureExecutioner {

    private final SignatureExecutionerInfo executionerInfo;

    private final SignatureExecutionerInitializer executionerInitializer;

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
