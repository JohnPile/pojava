package org.pojava.util;

/*
 Copyright 2008-09 John Pile

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

/**
 * A class for reversible encodings. Typical encodings are designed to represent binary data in
 * a more portable or printable format. Currently supported encodings include Base64,
 * Hexadecimal.
 *
 * @author John Pile
 */
public class EncodingTool {

    // Mapping table for converting a 4-bit nybble to hex characters.
    private static final char[] hexmap = "0123456789abcdef".toCharArray();

    private static final char[] e64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
            .toCharArray();
    private static final byte[] d64 = initD64();

    // Index the e64 offsets from their values
    private static byte[] initD64() {
        byte[] decoderMap = new byte[128];
        for (int i = 0; i < 64; i++) {
            decoderMap[e64[i]] = (byte) i;
        }
        return decoderMap;
    }

    /**
     * Encode binary data into a Base-64 array of printable characters.
     *
     * @param src Binary data to encode
     * @return Base-64 encoded string
     */
    public static String base64Encode(byte[] src) {
        int unpadded = (src.length * 8 + 5) / 6;
        int padding = 4 - (unpadded % 4);
        int d = 0;
        int e = 3;
        long buffer = 0;
        if (padding == 4)
            padding = 0;
        char[] encoded = new char[unpadded + padding];

        while (d < src.length) {
            // Push into buffer in 8-bit chunks
            for (int i = 0; i < 3; i++) {
                buffer <<= 8;
                buffer |= ((d < src.length ? src[d++] & 0x00ff : 0));
            }
            // Pop from buffer in 6-bit chunks
            for (int i = 0; i < 4; i++) {
                encoded[e--] = e64[(int) (buffer & 0x3f)];
                buffer >>>= 6;
            }
            e += 8;
        }
        while (padding > 0) {
            encoded[unpadded + --padding] = '=';
        }
        return new String(encoded);
    }

    /**
     * Decode a String from Base64 format. It strips whitespace before decoding.
     *
     * @param s a Base64 String to be decoded.
     * @return An array containing the decoded data.
     * @throws IllegalArgumentException if the input is not valid Base64 encoded data.
     */
    public static byte[] base64Decode(String s) {
        if (s == null) {
            return new byte[0];
        }
        return base64Decode(StringTool.stripWhitespace(s).toCharArray());
    }

    /**
     * Decode a Base64 message back to its original byte array.
     *
     * @param encoded a character array containing the Base64 encoded data.
     * @return A byte array containing the decoded data.
     */
    public static byte[] base64Decode(char[] encoded) {
        int length = encoded.length * 6 / 8;
        if (encoded.length > 0 && encoded[encoded.length - 1] == '=') {
            length--;
            if (encoded.length > 1 && encoded[encoded.length - 2] == '=') {
                length--;
            }
            if (encoded.length > 2 && encoded[encoded.length - 3] == '=') {
                length--;
            }
        }
        byte[] decoded = new byte[length];
        long buffer;
        int e = 0;
        int d = 0;
        while (e < encoded.length) {
            // Enqueue 24 bits into a buffer
            buffer = d64[encoded[e++]] << 18;
            buffer |= (d64[encoded[e++]] << 12);
            buffer |= (d64[encoded[e++]] << 6);
            buffer |= (d64[encoded[e++]]);
            // Dequeue 24 bits off of the buffer
            decoded[d++] = (byte) ((buffer & 0x00FF0000) >>> 16);
            if (d < length) {
                decoded[d++] = (byte) ((buffer & 0x0000FF00) >>> 8);
            }
            if (d < length) {
                decoded[d++] = (byte) (buffer & 0x000000FF);
            }
        }
        return decoded;
    }

    /**
     * Interpret a hex character as a nybble.
     *
     * @param c hexadecimal character to encode
     * @return integer between 0 and 15.
     */
    private static int hex2int(char c) {
        int nybble;
        if (c >= '0' && c <= '9') {
            nybble = c - '0';
        } else if (c >= 'a' && c <= 'f') {
            nybble = 10 + c - 'a';
        } else if (c >= 'A' && c <= 'F') {
            nybble = 10 + c - 'A';
        } else {
            throw new IllegalArgumentException("Hex value MUST be in range [0-9a-fA-F]");
        }
        return nybble;
    }

    /**
     * Convert a hex-encoded string back to a byte array. This String version strips whitespace
     * before decoding.
     *
     * @param hex Hexadecimal string
     * @return decoded array of bytes
     */
    public static byte[] hexDecode(String hex) {
        if (hex == null) {
            return new byte[0];
        }
        return hexDecode(StringTool.stripWhitespace(hex).toCharArray());
    }

    /**
     * Convert a hex-encoded character array back to a byte array.
     *
     * @param hexChars array of hex-encoded characters
     * @return original byte array
     */
    public static byte[] hexDecode(char[] hexChars) {
        int byteCt = hexChars.length / 2;
        if (2 * byteCt != hexChars.length) {
            throw new IllegalArgumentException("Hex value MUST be two digits per byte.");
        }
        byte[] bytes = new byte[byteCt];
        for (int i = 0; i < byteCt; i++) {
            bytes[i] = (byte) (hex2int(hexChars[2 * i]) << 4 | hex2int(hexChars[2 * i + 1]));
        }
        return bytes;
    }

    /**
     * Output a hex-encoded representation of a byte array
     *
     * @param bytes of binary data to encode
     * @return Hex encoded representation of binary data
     */
    public static String hexEncode(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(hexmap[0x0F & (aByte >>> 4)]);
            sb.append(hexmap[0x0F & aByte]);
        }
        return sb.toString();
    }

    /**
     * Convert a sequence of binary data into '0' and '1' characters.
     *
     * @param bytes binary data to encode
     * @return sequence of ones and zeros representing binary values.
     */
    public static String base2Encode(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length);
        for (byte b : bytes) {
            sb.append((b & 0x80) == 0 ? '0' : '1');
            sb.append((b & 0x40) == 0 ? '0' : '1');
            sb.append((b & 0x20) == 0 ? '0' : '1');
            sb.append((b & 0x10) == 0 ? '0' : '1');
            sb.append((b & 0x08) == 0 ? '0' : '1');
            sb.append((b & 0x04) == 0 ? '0' : '1');
            sb.append((b & 0x02) == 0 ? '0' : '1');
            sb.append((b & 0x01) == 0 ? '0' : '1');
        }
        return sb.toString();
    }

    /**
     * Convert a sequence of '1' and '0' characters back into byte values.
     *
     * @param chars array of packed characters in the set [01].
     * @return binary array derived from zeros and ones
     */
    public static byte[] base2Decode(char[] chars) {
        if (chars.length % 8 != 0) {
            throw new IllegalArgumentException("Encoded value MUST be eight digits per byte.");
        }
        byte[] bytes = new byte[chars.length / 8];
        int inIdx = 0;
        int outIdx = 0;
        while (inIdx < chars.length) {
            int decoded = 0;
            for (int i = 0; i < 8; i++) {
                char b = chars[inIdx++];
                byte bit = 0;
                if (b == '1') {
                    bit++;
                } else if (b != '0') {
                    throw new IllegalArgumentException(
                            "Encoded value must consist of only '0' or '1' characters.");
                }
                decoded = (decoded << 1) | bit;
            }
            bytes[outIdx++] = (byte) (decoded);
        }
        return bytes;
    }

    /**
     * Convert a sequence of '0' and '1' characters into a byte array, ignoring whitespace.
     *
     * @param onesAndZeros a string of [01] and whitespace characters.
     * @return binary encoded version of ones and zeros packed into bytes.
     */
    public static byte[] base2Decode(String onesAndZeros) {
        StringBuilder sb = new StringBuilder();
        char[] chars = onesAndZeros.toCharArray();
        for (char c : chars) {
            if (c == '1' || c == '0') {
                sb.append(c);
            } else if (!(c == ' ' || c == '\t' || c == '\n' || c == '\r')) {
                throw new IllegalArgumentException("Invalid character '" + c
                        + "' in onesAndZeros.");
            }
        }
        return base2Decode(sb.toString().toCharArray());
    }

}
