package org.pojava.util;

import junit.framework.TestCase;

public class CompressionTester extends TestCase {

    /**
     * This dataset returns a comma-separated list of numbers and characters.
     *
     * @return some numbers and characters for testing
     */
    private String generateTestString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append(Integer.toString(i));
            sb.append(", ");
            sb.append(Character.forDigit(i % 36, 36));
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    /**
     * This dataset uses all characters and is highly compressible.
     *
     * @return some binary data for testing
     */
    private byte[] generateTestBinary() {
        int loop = 100;
        int chars = 256;
        byte[] bytes = new byte[loop * chars];
        for (int i = 0; i < loop; i++) {
            for (int ch = 0; ch < chars; ch++) {
                byte c = new Integer(ch).byteValue();
                bytes[loop * i + ch] = c;
            }
        }
        return bytes;
    }

    public void testCompressedString() throws Exception {
        Compression compressor = new Compression();
        byte[] original = generateTestString().getBytes();
        byte[] compressed = compressor.compress(original);
        byte[] uncompressed = compressor.decompress(compressed);
        double ratio = compressed.length
                / new Double(uncompressed.length);
        // Do we have data to compress?
        assertTrue(original.length > 0);
        // Did compression result in compressed data?
        assertTrue(compressed.length > 0);
        // Was it smaller than 40% of the original?
        assertTrue(ratio < 0.4);
        // Does it expand into the original?
        assertEquals(new String(original), new String(uncompressed));
    }

    public void testCompressedBinary() throws Exception {
        Compression compressor = new Compression();
        byte[] original = generateTestBinary();
        byte[] compressed = compressor.compress(original);
        byte[] uncompressed = compressor.decompress(compressed);
        double ratio = compressed.length
                / new Double(uncompressed.length);
        // Do we have data to compress?
        assertTrue(original.length > 0);
        // Did compression result in compressed data?
        assertTrue(compressed.length > 0);
        // Was it smaller than 2% of the original?
        assertTrue(ratio < 0.02);
        // Does it expand into the original?
        assertEquals(new String(original), new String(uncompressed));
    }
}
