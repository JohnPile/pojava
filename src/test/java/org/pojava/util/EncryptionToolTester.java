package org.pojava.util;

import junit.framework.TestCase;

import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidKeyException;

public class EncryptionToolTester extends TestCase {

    public void testGenerateAES256WithCBCKeyString() throws Exception {
        String keyString = EncryptionTool.generateAES256WithCBCKeyString();
        // System.out.println(keyString);
        assertTrue(keyString.matches("^AES/CBC/PKCS5Padding [A-Za-z0-9+/]{43}=$"));
    }

    public void testGenerateAES128WithCBCKeyString() throws Exception {
        String keyString = EncryptionTool.generateAES128WithCBCKeyString();
        // System.out.println(keyString);
        assertTrue(keyString.matches("^AES/CBC/PKCS5Padding [A-Za-z0-9+/]{22}==$"));
    }

    /**
     * Test encrypt/decrypt using default AES128CBC generated key.
     *
     * @throws Exception
     */
    public void testEncryptionWithGeneratedAES128Key() throws Exception {
        String orig = "Shh. We go IPO next week!";
        String keyString = EncryptionTool.generateAES128WithCBCKeyString();
        byte[] frozen = EncryptionTool.encrypt(orig.getBytes(), keyString);
        assertEquals(32, frozen.length);
        byte[] thawed = EncryptionTool.decrypt(frozen, keyString);
        String dest = new String(thawed);
        assertEquals(orig, dest);
    }

    /**
     * Test encrypt/decrypt using default AES256CBC generated key.
     *
     * @throws Exception
     */
    public void testEncryptionWithGeneratedAES256Key() throws Exception {
        String orig = "Shh. We go IPO next week!";
        String keyString = EncryptionTool.generateAES256WithCBCKeyString();
        try {
            byte[] frozen = EncryptionTool.encrypt(orig.getBytes(), keyString);
            assertEquals(32, frozen.length);
            byte[] thawed = EncryptionTool.decrypt(frozen, keyString);
            String dest = new String(thawed);
            assertEquals(orig, dest);
        } catch (InvalidKeyException ex) {
            throw new IllegalStateException("InvalidKeyException thrown: Add JCE to your JDK to support AES256", ex);
        }
    }

    /**
     * Use MD5 to generate a 128 bit initialization vector.
     *
     * @throws Exception
     */
    public void testMd5IvParameterSpec() throws Exception {
        String example = "example";
        IvParameterSpec ivps = EncryptionTool.md5IvParameterSpec(example);
        String md5 = HashingTool.md5Hash(example);
        String hex = new String(ivps.getIV());
        assertEquals(md5, hex);
    }

    public void testIvParameterSpec() throws Exception {
        byte[] iv = EncodingTool.hexDecode("3dafba429d9eb430b422da802c9fac41");
        IvParameterSpec ivps = new IvParameterSpec(iv);
        String hexKey = "06a9214036b8a15b512e03d534120006";
        String key = EncodingTool.base64Encode(EncodingTool.hexDecode(hexKey));
        String keyString = "AES/CBC/PKCS5Padding " + key;
        assertTrue(keyString.matches("^AES/CBC/PKCS5Padding [A-Za-z0-9+/]{22}==$"));
        String message = "Single block msg";
        byte[] encrypted = EncryptionTool.encrypt(message.getBytes(), keyString, ivps);
        String hex = EncodingTool.hexEncode(encrypted);
        // First block matches FIPS example
        assertEquals("e353779c1079aeb82708942dbe77181a", hex.substring(0, 32));
        String withoutCrlf = "41N3nBB5rrgnCJQtvncYGrl8gl4ceFFGVC05aUG85V0=";
        String encrypted64 = EncodingTool.base64Encode(EncodingTool.hexDecode(hex));
        // Whole output matches openssl aes-128-cbc.
        assertEquals(withoutCrlf, encrypted64);

		/*
         # msg.txt is a 16-byte file containing "Single block msg"
		 # Careful about the 16 bytes... no newline character at end.
		 openssl enc -aes-128-cbc -in msg.txt -a \
		 	-K 06a9214036b8a15b512e03d534120006 -iv 3dafba429d9eb430b422da802c9fac41
		 41N3nBB5rrgnCJQtvncYGrl8gl4ceFFGVC05aUG85V0=
		*/
    }

    /**
     * Match appendix C1 of the FIPS 197 reference
     *
     * @throws Exception
     */
    public void testFipsC1() throws Exception {
        byte[] plaintext = EncodingTool.hexDecode("00112233445566778899aabbccddeeff");
        byte[] key = EncodingTool.hexDecode("000102030405060708090a0b0c0d0e0f");
        String keyString = "AES/CBC/PKCS5Padding " + EncodingTool.base64Encode(key);
        byte[] enc = EncryptionTool.encrypt(plaintext, keyString);
        String encHex = EncodingTool.hexEncode(enc);
        // Does first block match FIPS 197, Appendix C.1?
        assertEquals("69c4e0d86a7b0430d8cdb78070b4c55a", encHex.substring(0, 32));
        byte[] dec = EncryptionTool.decrypt(enc, keyString);
        // Does result decrypt to the original plaintext?
        assertEquals(new String(dec), new String(plaintext));
    }

    /**
     * Match appendix C2 of the FIPS 197 reference
     *
     * @throws Exception
     */
    public void testFipsC2() throws Exception {
        byte[] plaintext = EncodingTool.hexDecode("00112233445566778899aabbccddeeff");
        byte[] key = EncodingTool.hexDecode("000102030405060708090a0b0c0d0e0f1011121314151617");
        String keyString = "AES/CBC/PKCS5Padding " + EncodingTool.base64Encode(key);
        byte[] enc = EncryptionTool.encrypt(plaintext, keyString);
        String encHex = EncodingTool.hexEncode(enc);
        // Does first block match FIPS 197, Appendix C.2?
        assertEquals("dda97ca4864cdfe06eaf70a0ec0d7191", encHex.substring(0, 32));
        byte[] dec = EncryptionTool.decrypt(enc, keyString);
        // Does result decrypt to the original plaintext?
        assertEquals(new String(dec), new String(plaintext));
    }


    /**
     * Encrypt two blocks with most the most basic AES encryption.
     * echo "Two block message." | openssl enc -aes-128-ecb -a -K 06a9214036b8a15b512e03d534120006 -iv 0
     *
     * @throws Exception
     */
    public void testOpensslAES128ECB() throws Exception {
        String msg = "Two block message.\n";
        String hexKey = "06a9214036b8a15b512e03d534120006";
        String b64key = EncodingTool.base64Encode(EncodingTool.hexDecode(hexKey));
        byte[] enc = EncryptionTool.encrypt(msg.getBytes(), "AES " + b64key);
        String enc64 = EncodingTool.base64Encode(enc);
        assertEquals("c8vcDVvznP0wOBl1ryv+2JpNl119fyNv0AIm+7SmIXk=", enc64);
    }

    /**
     * Encrypt to match AES128 Default
     * echo "Two block message." | openssl enc -aes128 -a \
     * -K 06a9214036b8a15b512e03d534120006 -iv 0
     * c8vcDVvznP0wOBl1ryv+2CecTyUoDy2y64T7nfxVAds=
     *
     * @throws Exception
     */
    public void testOpensslAES128() throws Exception {
        String msg = "Two block message.\n";
        String hexKey = "06a9214036b8a15b512e03d534120006";
        String key = EncodingTool.base64Encode(EncodingTool.hexDecode(hexKey));
        byte[] enc = EncryptionTool.encrypt(msg.getBytes(), "AES/CBC/PKCS5Padding " + key);
        assertEquals("c8vcDVvznP0wOBl1ryv+2CecTyUoDy2y64T7nfxVAds=", EncodingTool.base64Encode(enc));
    }

    /**
     * Encrypt to match openssl DES Default
     * echo -n "Now is the time for all." | openssl enc -des -a \
     * -K 0123456789abcdef -iv 1234567890abcdef
     * c8vcDVvznP0wOBl1ryv+2CecTyUoDy2y64T7nfxVAds=
     *
     * @throws Exception
     */
    public void testOpensslDES() throws Exception {
        String msg = "Now is the time for all.";
        String hexKey = "0123456789abcdef";
        String hexIv = "1234567890abcdef";
        String key = EncodingTool.base64Encode(EncodingTool.hexDecode(hexKey));
        IvParameterSpec ivps = new IvParameterSpec(EncodingTool.hexDecode(hexIv));
        byte[] enc = EncryptionTool.encrypt(msg.getBytes(), "DES/CBC/PKCS5Padding " + key, ivps);
        assertEquals("5cfN3ocr8nxD6TQAjDicD2l2PVaAUvgeoQEhwVj2Ues=", EncodingTool.base64Encode(enc));
    }

    /**
     * Encrypt to match openssl DES Default
     * echo -n "Now is the time for all." | openssl enc -des-ecb -a \
     * -K 0123456789abcdef -iv 1234567890abcdef
     * c8vcDVvznP0wOBl1ryv+2CecTyUoDy2y64T7nfxVAds=
     *
     * @throws Exception
     */
    public void testOpensslDESMatch() throws Exception {
        String msg = "Now is the time for all.";
        String hexKey = "0123456789abcdef";
        String hexIv = "1234567890abcdef";
        String key = EncodingTool.base64Encode(EncodingTool.hexDecode(hexKey));
        IvParameterSpec ivps = new IvParameterSpec(EncodingTool.hexDecode(hexIv));
        byte[] enc = EncryptionTool.encrypt(msg.getBytes(), "DES " + key, ivps);
        assertEquals("P6QOiphNSBVqJxeHq4iD+Q1yR+Te8AiKCG+aHXTJTU4=", EncodingTool.base64Encode(enc));
    }

}
