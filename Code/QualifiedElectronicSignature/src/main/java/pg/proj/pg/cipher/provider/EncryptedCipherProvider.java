package pg.proj.pg.cipher.provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import pg.proj.pg.cipher.executioner.CipherExecutioner;
import pg.proj.pg.cipher.executioner.CipherExecutionerImpl;
import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.key.info.KeyInfoImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Supplier;

@AllArgsConstructor
public class EncryptedCipherProvider implements CipherProvider {

    @Getter
    private final String uniqueName;

    private final CipherExecutioner passwordDecryptorInfo;

    private final Supplier<CipherInfo> cipherInfoSupplier;

    @Override
    public CipherExecutioner getCipher() {
        String pin = "4554"; //TODO: ask for pin
        //TODO: hash pin with PasswordHasher to byte[].
        // then i need decryptorInfo which contains keyGen, cipherType, cipher and keygen.
        // then i will pass pinHash as keyContent and create new executioner to decrypt key

        //it all should be done in separate class fe. passwordDecryptor
        byte[] hashedPin = hashPassword(pin);
        CipherInfo cipherInfo = cipherInfoSupplier.get();
        try {
            byte[] hashedKey = cipherInfo.keyInfo().keyContent();
            byte[] unhashedKey = cipherDecryptor.decrypt(hashedKey); //TODO: passPinToDecryptor
            KeyInfo unhashedKeyInfo = new KeyInfoImpl(unhashedKey);
        return new CipherExecutionerImpl(new CipherInfo(cipherInfo.cipher(),
                cipherInfo.keyGen(), unhashedKeyInfo, cipherInfo.cipherType()));
        } catch (Exception e) {
            throw new BasicAppError("Cannot decrypt key: " + e.getMessage());
        }
    }

    @SneakyThrows
    private byte[] hashPassword(String password) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(password.getBytes());
    }

}
