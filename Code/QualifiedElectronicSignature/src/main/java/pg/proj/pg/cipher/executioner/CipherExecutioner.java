package pg.proj.pg.cipher.executioner;

public interface CipherExecutioner {
    byte[] encrypt(byte[] source);
    byte[] decrypt(byte[] source);
    byte[] batchEncrypt(byte[] source);
    byte[] batchDecrypt(byte[] source);
}
