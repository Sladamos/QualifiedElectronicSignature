package pg.proj.pg.signature.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SignatureType {
    RSA("SHA512withRSA", "RSA");

    private final String signatureType;

    private final String algorithmType;
}
