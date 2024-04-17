package pg.proj.pg.signature.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.password.info.PasswordInfo;
import pg.proj.pg.password.provider.PasswordProvider;
import pg.proj.pg.password.selector.PasswordSelector;
import pg.proj.pg.signature.executioner.SignatureExecutioner;
import pg.proj.pg.signature.executioner.SignatureExecutionerImpl;
import pg.proj.pg.signature.info.EncryptedSignatureExecutionerInfo;
import pg.proj.pg.signature.info.SignatureExecutionerInfo;
import pg.proj.pg.signature.initializer.SignatureExecutionerInitializer;
import pg.proj.pg.signature.unlocker.SignatureExecutionerInfoUnlocker;

import java.util.function.Supplier;

@AllArgsConstructor
public class EncryptedSignatureExecutionerProvider implements SignatureExecutionerProvider {

    @Getter
    private final String uniqueName;

    private final SignatureExecutionerInfoUnlocker signatureExecutionerInfoUnlocker;

    private final PasswordSelector passwordSelector;

    private final SignatureExecutionerInitializer signatureExecutionerInitializer;

    private final Supplier<EncryptedSignatureExecutionerInfo> signatureExecutionerInfoSupplier;
    @Override
    public SignatureExecutioner getSignatureExecutioner() {
        EncryptedSignatureExecutionerInfo executionerInfo = signatureExecutionerInfoSupplier.get();
        PasswordProvider passwordProvider = passwordSelector.selectPassword();
        PasswordInfo password = passwordProvider.getPasswordInfo();
        SignatureExecutionerInfo unlockedExecutionerInfo =
                signatureExecutionerInfoUnlocker.unlock(executionerInfo, password);
        try {
            return new SignatureExecutionerImpl(unlockedExecutionerInfo, signatureExecutionerInitializer);
        } catch (Exception e) {
            throw new BasicAppError("Cannot decrypt key: " + e.getMessage());
        }
    }
}

