package pg.proj.pg.signature.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pg.proj.pg.signature.info.SignatureVerifierInfo;
import pg.proj.pg.signature.initializer.SignatureVerifierInitializer;
import pg.proj.pg.signature.verifier.SignatureVerifier;
import pg.proj.pg.signature.verifier.SignatureVerifierImpl;

import java.util.function.Supplier;

@AllArgsConstructor
public class PlainSignatureVerifierProvider implements SignatureVerifierProvider {

    @Getter
    private final String uniqueName;

    private final SignatureVerifierInitializer verifierInitializer;

    private final Supplier<SignatureVerifierInfo> verifierInfoSupplier;

    @Override
    public SignatureVerifier getSignatureVerifier() {
        SignatureVerifierInfo verifierInfo = verifierInfoSupplier.get();
        return new SignatureVerifierImpl(verifierInfo, verifierInitializer);
    }
}
