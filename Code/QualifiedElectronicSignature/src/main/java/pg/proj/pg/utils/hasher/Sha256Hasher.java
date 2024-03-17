package pg.proj.pg.utils.hasher;

import pg.proj.pg.error.definition.BasicAppError;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Hasher implements Hasher {
    @Override
    public byte[] hashStr(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(str.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new BasicAppError("Something went wrong during hashing");
        }
    }
}
