package pg.proj.pg.cipher.extractor;

import pg.proj.pg.iv.InitializationVector;

public class HardcodedCipherExtractor implements CipherExtractor {

    private final static int IV_LENGTH = 16;

    @Override
    public InitializationVector extractIvFromArray(byte[] arr) {
        byte[] iv = new byte[IV_LENGTH];
        System.arraycopy(arr, 0, iv, 0, IV_LENGTH);
        return new InitializationVector(iv);
    }

    @Override
    public byte[] extractKeyContentFromArray(byte[] arr) {
        int keySize = arr.length - IV_LENGTH;
        byte[] keyContent = new byte[keySize];
        System.arraycopy(arr, IV_LENGTH, keyContent, 0, keySize);
        return keyContent;
    }
}
