package org.pojava.util;

import org.pojava.exception.InconceivableException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * This utility provides a simple interface for encrypting and decrypting data using
 * high-quality encryption algorithms such as AES-128 and AES-256.  You will need to
 * install the Java(TM) Cryptography Extension for encryption stronger than 128-bit.
 * <p/>
 * It also provides a means of importing and exporting both the algorithm and cipher
 * key as a String.
 * <p/>
 * Example usage:
 * <code>
 * String doc="This is the content to be encrypted";
 * byte[] raw=doc.getBytes();
 * String keyString=EncryptionTool.generateAES256WithCBCKeyString();
 * // (This keyString could be retrieved from JNDI, for example)
 * byte[] encrypted=EncryptionTool.encrypt(raw, keyString);
 * ...
 * byte[] decrypted=EncryptionTool.decrypt(encrypted, keyString);
 * String originalDoc=new String(decrypted);
 * </code>
 *
 * @author John Pile
 */
public class EncryptionTool {

    /**
     * Generate a unique 256bit AES key with CBC.
     *
     * @return a random AES 256 bit key with CBC and padding.
     */
    public static String generateAES256WithCBCKeyString() {
        SecretKey skey;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(256); // 256 bit AES encryption
            skey = kgen.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            throw new InconceivableException("AES really should be available... "
                    + ex.getMessage(), ex);
        }
        return "AES/CBC/PKCS5Padding " + EncodingTool.base64Encode(skey.getEncoded());
    }

    /**
     * Generate a unique 128bit AES key with CBC.
     *
     * @return a random AES 128 bit key with CBC and padding.
     */
    public static String generateAES128WithCBCKeyString() {
        SecretKey skey;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128); // 128 bit AES encryption
            skey = kgen.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            throw new InconceivableException("AES really should be available... "
                    + ex.getMessage(), ex);
        }
        return "AES/CBC/PKCS5Padding " + EncodingTool.base64Encode(skey.getEncoded());
    }

    /**
     * Encrypt or decrypt a message, depending on request.
     *
     * @param message Data to encrypt or decrypt
     * @param keyString String-based representation of key
     * @param cipherMode See Cipher static constants
     * @param ivps Initialization Vector
     * @return byte[] of transformed data
     * @throws java.security.InvalidKeyException
     * @throws java.security.InvalidAlgorithmParameterException
     */
    private static byte[] crypto(byte[] message, String keyString, int cipherMode, IvParameterSpec ivps)
            throws GeneralSecurityException {
        String pair[] = keyString.split("\\s+");
        if (pair.length != 2 || !pair[1].matches("^[A-Za-z0-9+/=]+$")) {
            throw new InvalidKeyException("keyString should be of format: 'ALG base64key'");
        }
        byte[] raw = EncodingTool.base64Decode(pair[1]);
        String[] alg = pair[0].split("/");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, alg[0]);
        Cipher cipher = Cipher.getInstance(pair[0]);
        if (alg.length == 1 || pair[0].contains("ECB")) {
            cipher.init(cipherMode, skeySpec);
        } else {
            cipher.init(cipherMode, skeySpec, ivps);
        }
        return cipher.doFinal(message);
    }

    /**
     * Encrypt a message.
     *
     * @param message Binary data to encrypt
     * @param keyString a string of the format 'ALG base64key'
     * @return An encrypted byte array from the source.
     * @throws java.security.GeneralSecurityException
     */
    public static byte[] encrypt(byte[] message, String keyString) throws GeneralSecurityException {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        IvParameterSpec ivps = new IvParameterSpec(iv);
        return crypto(message, keyString, Cipher.ENCRYPT_MODE, ivps);
    }

    /**
     * Decrypt a message.
     *
     * @param message   encrypted message
     * @param keyString a string of the format 'ALG base64key'
     * @return A decrypted byte array.
     * @throws java.security.GeneralSecurityException
     */
    public static byte[] decrypt(byte[] message, String keyString) throws GeneralSecurityException {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        IvParameterSpec ivps = new IvParameterSpec(iv);
        return crypto(message, keyString, Cipher.DECRYPT_MODE, ivps);
    }


    /**
     * Encrypt a message.
     *
     * @param message Binary data to encrypt
     * @param keyString a string of the format 'ALG base64key'
     * @param ivps      Initialization Value
     * @return An encrypted byte array from the source.
     * @throws java.security.GeneralSecurityException
     */
    public static byte[] encrypt(byte[] message, String keyString, IvParameterSpec ivps) throws GeneralSecurityException {
        return crypto(message, keyString, Cipher.ENCRYPT_MODE, ivps);
    }

    /**
     * Decrypt a message.
     *
     * @param message   encrypted message
     * @param keyString a string of the format 'ALG base64key'
     * @param ivps      Initialization Value
     * @return A decrypted byte array.
     * @throws java.security.GeneralSecurityException
     */
    public static byte[] decrypt(byte[] message, String keyString, IvParameterSpec ivps) throws GeneralSecurityException {
        return crypto(message, keyString, Cipher.DECRYPT_MODE, ivps);
    }

    /**
     * Generate an IvParameterSpec from a binary MD5 hash of a string.
     *
     * @param hashMe Plaintext value to hash
     * @return IvParameterSpec Initialization Vector (salt)
     */
    public static IvParameterSpec md5IvParameterSpec(String hashMe) {
        byte[] iv = HashingTool.md5Hash(hashMe).getBytes();
        return new IvParameterSpec(iv);
    }

}
