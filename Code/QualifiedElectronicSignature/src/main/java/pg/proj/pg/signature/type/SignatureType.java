package pg.proj.pg.signature.type;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SignatureType {
    RSA("SHA256withRSA");

    private final String algorithmType;
}
