package pg.proj.pg.cipher.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CipherType {
    RSA("RSA", "RSA"),
    AES("AES/CBC/PKCS5Padding", "AES");

    private final String strValue;

    private final String algorithmType;
}
