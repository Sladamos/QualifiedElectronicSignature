package org.example;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

public class Main {

    private static final int IV_LENGTH = 16;

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to RSA Key Pair Generator!");

        int keyLength = 4096;
        String pin = getInput("Enter pin number: ");
        String path = getInput("Enter path to save keys: ");

        KeyPair keyPair = generateRSAKeyPair(keyLength);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] hashedPin = hashSHA256(pin.getBytes());

        savePEMKey(keyTo64(publicKey.getEncoded()), path + "/public_key.puk");
        saveEncryptedPEMKey(privateKey, hashedPin, path + "/private_key.epk");

        System.out.println("RSA key pair generated and private key encrypted successfully!");
    }

    public static byte[] hashSHA256(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(input);
    }

    private static KeyPair generateRSAKeyPair(int keyLength) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength);
        return keyPairGenerator.generateKeyPair();
    }

    private static String getInput(String prompt) {
        System.out.print(prompt);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine().trim();
        } catch (IOException e) {
            return "";
        }
    }

    private static void savePEMKey(byte[] keyBytes, String path) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(keyBytes);
        }
    }

    private static byte[] keyTo64(byte[] key) {
        return Base64.getEncoder().encode(key);
    }

    private static void saveEncryptedPEMKey(PrivateKey privateKey, byte[] pin, String path) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] nonce = new byte[IV_LENGTH];
        random.nextBytes(nonce);
        byte[] encryptedKey = encryptPrivateKey(keyTo64(privateKey.getEncoded()), pin, nonce);
        savePEMKey(concat(nonce, encryptedKey), path);
    }

    private static byte[] encryptPrivateKey(byte[] key, byte[] hashedPin, byte[] nonce) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,
                new SecretKeySpec(hashedPin, "AES"),
                new IvParameterSpec(nonce));
        return cipher.doFinal(key);
    }

    private static byte[] concat(byte[] x, byte[] y) {
        byte[] result = new byte[x.length + y.length];
        System.arraycopy(x, 0, result, 0, x.length);
        System.arraycopy(y, 0, result, x.length, y.length);
        return result;
    }
}