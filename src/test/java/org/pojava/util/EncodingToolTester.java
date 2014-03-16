package org.pojava.util;

import junit.framework.TestCase;

public class EncodingToolTester extends TestCase {

    private static final boolean DEBUG = false;

    /**
     * This reference message was taken from http://en.wikipedia.org/wiki/Base64
     */
    private String pasted = "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCBie\n"
            + "\tSB0aGlzIHNpbmd1bGFyIHBhc3Npb24gZnJvbSBvdGhlciBhbmltYWxzLCB3aGljaCBpcyBhIGx1c\n"
            + "\t3Qgb2YgdGhlIG1pbmQsIHRoYXQgYnkgYSBwZXJzZXZlcmFuY2Ugb2YgZGVsaWdodCBpbiB0aGUgY\n"
            + "\t29udGludWVkIGFuZCBpbmRlZmF0aWdhYmxlIGdlbmVyYXRpb24gb2Yga25vd2xlZGdlLCBleGNlZ\n"
            + "\tWRzIHRoZSBzaG9ydCB2ZWhlbWVuY2Ugb2YgYW55IGNhcm5hbCBwbGVhc3VyZS4=";
    private String encoded = StringTool.stripWhitespace(pasted);

    private String decoded = "Man is distinguished, not only by his reason, but by this singular"
            + " passion from other animals, which is a lust of the mind, that by a perseverance"
            + " of delight in the continued and indefatigable generation of knowledge, exceeds"
            + " the short vehemence of any carnal pleasure.";

    public void testBase64Decode() {
        String test = new String(EncodingTool.base64Decode(encoded.toCharArray()));
        assertEquals(decoded, test);
    }

    public void testBase64DecodeString() {
        assertEquals(decoded, new String(EncodingTool.base64Decode(pasted)));
    }

    public void testBase64Encode() {
        String test = EncodingTool.base64Encode(decoded.getBytes());
        assertEquals(encoded, test);
    }

    /**
     * Base64 encodes in 24-bit chunks, padding where needed to reach 24 bits. This test tries
     * various lengths to ensure it is padded appropriately.
     */
    public void testBase64Padding() {
        for (int i = 0; i < 10; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                sb.append(j);
            }
            String encoded = EncodingTool.base64Encode(sb.toString().getBytes());
            String decoded = new String(EncodingTool.base64Decode(encoded));
            assertEquals(sb.toString(), decoded);
        }
    }

    public void testBase64Negative() {
        byte[] raw = {-28, -120};
        String encoded = EncodingTool.base64Encode(raw);
        byte[] decoded = EncodingTool.base64Decode(encoded);
        assertEquals(raw[0], decoded[0]);
    }

    public void testHexEncode() {
        byte[] oneByte = new byte[1];
        String[] strings = new String[256];
        for (int i = 0; i < 256; i++) {
            oneByte[0] = (byte) i;
            strings[i] = EncodingTool.hexEncode(oneByte);
        }
        // Test for all 256 possible combinations in alphabetical order.
        assertTrue(strings[0].equals("00"));
        for (int i = 1; i < 256; i++) {
            assertTrue(strings[i].compareTo(strings[i - 1]) > 0);
            assertTrue(strings[i].matches("^[a-f0-9]{2}$"));
        }
    }

    public void testHexDecode() {
        String example = "00 a1 B2 c3 ff";
        byte[] decoded = EncodingTool.hexDecode(example);
        assertEquals((byte) 0x00, decoded[0]);
        assertEquals((byte) 0xa1, decoded[1]);
        assertEquals((byte) 0xb2, decoded[2]);
        assertEquals((byte) 0xc3, decoded[3]);
        assertEquals((byte) 0xff, decoded[4]);
    }

    public void testHexDecodeNullString() {
        byte[] decoded = EncodingTool.hexDecode((String) null);
        assertEquals(0, decoded.length);
    }

    public void testHexDecodeInvalidStrings() {
        try {
            EncodingTool.hexDecode("zz");
            fail("Expecting IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            if (DEBUG) {
                System.out.println(ex.getMessage());
            }
        }
        try {
            EncodingTool.hexDecode("bad"); // Uneven length
            fail("Expecting IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            if (DEBUG) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void testBase2EncodeDecode() {
        String str = "abc";
        String binary = EncodingTool.base2Encode(str.getBytes());
        assertEquals("011000010110001001100011", binary);
        binary = binary.substring(0, 8) + " " + binary.substring(8, 16) + "\n"
                + binary.substring(16);
        String orig = new String(EncodingTool.base2Decode(binary));
        assertEquals("abc", orig);
    }

    public void testBase2DecodeShorty() {
        String tooShort = "0100101001";
        try {
            EncodingTool.base2Decode(tooShort.toCharArray());
            fail("Expecting IllegalArgumentException");
        } catch (IllegalArgumentException ex) {

        }
    }
}
