package pg.proj.pg.cipher.executioner;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.initializer.CipherInitializer;
import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.error.definition.BasicAppError;

import javax.crypto.Cipher;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class CipherExecutionerImpl implements CipherExecutioner {

    private final CipherInfo cipherInfo;

    private final CipherInitializer cipherInitializer;

    private final int MAXIMUM_ENCRYPT_BATCH_SIZE = 501;

    private final int MAXIMUM_DECRYPT_BATCH_SIZE = 512;

    @Override
    public byte[] encrypt(byte[] source) {
        Cipher cipher = cipherInitializer.initializeCipher(cipherInfo, Cipher.ENCRYPT_MODE);
        return encryptContent(cipher, source);
    }

    @Override
    public byte[] decrypt(byte[] source) {
        Cipher cipher = cipherInitializer.initializeCipher(cipherInfo, Cipher.DECRYPT_MODE);
        return decryptContent(cipher, source);
    }

    @Override
    public byte[] batchEncrypt(byte[] source) {
        Cipher cipher = cipherInitializer.initializeCipher(cipherInfo, Cipher.ENCRYPT_MODE);
        int numberOfBatches = (int) Math.ceil((double) source.length / MAXIMUM_ENCRYPT_BATCH_SIZE);
        List<byte[]> encryptedBatches = new LinkedList<>();
        for(int i = 0; i < numberOfBatches; i++) {
            byte[] batch = generateEncryptBatchFromSource(source, i * MAXIMUM_ENCRYPT_BATCH_SIZE);
            byte[] encryptedBatch = encryptContent(cipher, batch);
            encryptedBatches.add(encryptedBatch);
        }
        return joinBatches(encryptedBatches);
    }

    @Override
    public byte[] batchDecrypt(byte[] source) {
        Cipher cipher = cipherInitializer.initializeCipher(cipherInfo, Cipher.DECRYPT_MODE);
        int decryptIt = 0;
        List<byte[]> decodedBatches = new LinkedList<>();
        while(decryptIt < source.length) {
            byte[] batch = generateDecryptBatchFromSource(source, decryptIt);
            byte[] decryptedBatch = decryptContent(cipher, batch);
            decodedBatches.add(decryptedBatch);
            decryptIt += batch.length;
        }
        return joinBatches(decodedBatches);
    }

    private byte[] encryptContent(Cipher cipher, byte[] content) {
        try {
            return cipher.doFinal(content);
        } catch (Exception e) {
            throw new BasicAppError("Unable to encrypt content");
        }
    }

    private byte[] generateEncryptBatchFromSource(byte[] message, int offset) {
        int limit = Math.min(message.length, offset + MAXIMUM_ENCRYPT_BATCH_SIZE);
        return Arrays.copyOfRange(message, offset, limit);
    }

    private byte[] joinBatches(List<byte[]> decodedBatches) {
        int outputSize = decodedBatches.stream().mapToInt(arr -> arr.length).sum();
        byte[] joinedBatches = new byte[outputSize];
        int batchesIt = 0;
        for(byte[] batch: decodedBatches) {
            System.arraycopy(batch, 0, joinedBatches, batchesIt, batch.length);
            batchesIt += batch.length;
        }
        return joinedBatches;
    }

    private byte[] decryptContent(Cipher cipher, byte[] source) {
        try {
            return cipher.doFinal(source);
        } catch (Exception e) {
            throw new BasicAppError("Unable to decrypt content");
        }
    }

    private byte[] generateDecryptBatchFromSource(byte[] message, int offset) {
        int limit = Math.min(message.length, offset + MAXIMUM_DECRYPT_BATCH_SIZE);
        return Arrays.copyOfRange(message, offset, limit);
    }

}
