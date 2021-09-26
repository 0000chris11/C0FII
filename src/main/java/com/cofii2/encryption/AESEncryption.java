package com.cofii2.encryption;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
//import javax.xml

class AESEncryption {

    private AESEncryption(String keyName) throws Exception {
        SecretKey secKey = getSecretEncryptionKey();
        byte[] cipherText = encryptText(keyName, secKey);
        String decryptedText = decryptText(cipherText, secKey);

        System.out.println("Original Text:" + keyName);
        System.out.println("AES Key (Hex Form):"+ bytesToHex(secKey.getEncoded()));
        System.out.println("Encrypted Text (Hex Form):"+bytesToHex(cipherText));
        System.out.println("Descrypted Text:"+decryptedText);
    }

    private SecretKey getSecretEncryptionKey() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128); // The AES key size in number of bits

        return generator.generateKey();
    }

    private byte[] encryptText(String plainText, SecretKey secKey) throws Exception {
        // AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        return aesCipher.doFinal(plainText.getBytes());
    }

    private String decryptText(byte[] byteCipherText, SecretKey secKey) throws Exception {
        // AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
        return new String(bytePlainText);
    }

    private String bytesToHex(byte[] hash) {
        //javax.xml.bind package missing
        return null; //DatatypeConverter.printHexBinary(hash);
    }
}
