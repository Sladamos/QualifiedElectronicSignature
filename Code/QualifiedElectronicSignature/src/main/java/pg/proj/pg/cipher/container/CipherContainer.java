package pg.proj.pg.cipher.container;

public interface CipherContainer {
    byte[] encrypt(byte[] source);
    byte[] decrypt(byte[] source);
}
