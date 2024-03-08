package pg.proj.pg.cipher.info;

import pg.proj.pg.cipher.generator.KeyGen;

import javax.crypto.Cipher;

public record CipherInfo(Cipher cipher, KeyGen keyGen, String keyStr, String cipherType) {
}
