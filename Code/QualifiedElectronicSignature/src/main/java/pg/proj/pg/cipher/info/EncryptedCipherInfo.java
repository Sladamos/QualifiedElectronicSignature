package pg.proj.pg.cipher.info;

import pg.proj.pg.iv.InitializationVector;

public record EncryptedCipherInfo(CipherInfo cipherInfo, InitializationVector iv) {
}
