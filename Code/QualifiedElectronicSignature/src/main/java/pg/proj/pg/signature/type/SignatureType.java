package pg.proj.pg.signature.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SignatureType {
    RSA("SHA512withRSA");

    private final String algorithmType;
}
