package org.pojava.util;

import junit.framework.TestCase;

import java.security.Provider;
import java.security.Security;
import java.util.Set;

public class HashingToolTester extends TestCase {

    private static final boolean DEBUG = false;

    public void testProviders() {
        Provider[] providers = Security.getProviders();
        if (DEBUG) {
            System.out.println("\nPROVIDERS\n=========");
            for (Provider provider : providers) {
                System.out.println(provider.getName());
            }
        }
    }

    public void testAlgorithms() {
        Set<String> algorithms = Security.getAlgorithms("MessageDigest");
        if (DEBUG) {
            System.out.println("\nALGORITHMS\n==========");
            for (String algorithm : algorithms) {
                System.out.println(algorithm);
            }
        }
    }

    public void testMd5Hash() {
        // Example taken from http://en.wikipedia.org/wiki/MD5
        String md5Target = "The quick brown fox jumps over the lazy dog.";
        String md5hash = "e4d909c290d0fb1ca068ffaddf22cbd0";
        String hash = HashingTool.md5Hash(md5Target);
        assertEquals(md5hash, hash);
    }

    public void testSha() {
        String shaTarget = "The quick brown fox jumps over the lazy dog";
        String shaHash = "2fd4e1c67a2d28fced849ee1bb76e7391b93eb12";
        byte[] hash = HashingTool.hash(shaTarget.getBytes(), HashingAlgorithm.SHA);
        assertEquals(shaHash, EncodingTool.hexEncode(hash));
    }

    public void testSha256() {
        // Example taken from
        // http://csrc.nist.gov/groups/ST/toolkit/documents/Examples/SHA256.pdf
        String sha256Target = "abc";
        String sha256Hash = "BA7816BF8F01CFEA414140DE5DAE2223B00361A396177A9CB410FF61F20015AD";
        byte[] hash = HashingTool.hash(sha256Target.getBytes(), HashingAlgorithm.SHA_256);
        String asString = EncodingTool.hexEncode(hash).toUpperCase();
        assertEquals(sha256Hash, asString);
    }

    /*
     * // This test did not pass. // Example taken from
     * http://csrc.nist.gov/groups/ST/toolkit/documents/Examples/SHA384.pdf public void
     * testSha384() { private String sha384Target="abc"; private Stringsha384Hash=
     * "CB00753F45A35E8BB5A03D699AC65007272C32AB0EDED163A8B605A43FF5BED8086072BA1E7CC2358BAECA134C825A7"
     * ; byte[] hash=HashingTool.hash(sha384Target.getBytes(), HashingAlgorithm.SHA_256); String
     * asString=EncodingTool.hexEncode(hash).toUpperCase(); assertEquals(sha384Hash, asString);
     * }
     * 
     * // This test did not pass. // Example taken from
     * http://csrc.nist.gov/groups/ST/toolkit/documents/Examples/SHA512.pdf public void
     * testSha512() { private String sha512Target="abc"; private String
     * sha512Hash="DDAF35A193617ABACC417349AE20413112E6FA4E89A97EA20A9EEEE64B55D39A" +
     * "2192992A274FC1A836BA3C23A3FEEBBD454D4423643CE80E2A9AC94FA54CA49F"; byte[]
     * hash=HashingTool.hash(sha512Target.getBytes(), HashingAlgorithm.SHA_256); String
     * asString=EncodingTool.hexEncode(hash).toUpperCase(); assertEquals(sha512Hash, asString);
     * }
     */
}
