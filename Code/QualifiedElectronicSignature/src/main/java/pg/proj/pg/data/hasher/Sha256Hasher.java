package pg.proj.pg.data.hasher;

import pg.proj.pg.error.BasicAppError;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Hasher implements Hasher {
    @Override
    public byte[] hash(byte[] source) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(source);
        } catch (NoSuchAlgorithmException e) {
            throw new BasicAppError("Something went wrong during hashing");
        }
    }
}
