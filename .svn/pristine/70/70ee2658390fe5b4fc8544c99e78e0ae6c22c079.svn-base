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

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * This class provides a simplified interface for compressing binary data using the "Deflate"
 * algorithm common to "zip" compression.
 *
 * @author John Pile
 */
public class Compression {

    private final Deflater compressor = new Deflater();

    private final Inflater decompressor = new Inflater();

    public Compression() {
        // Default constructor
        compressor.setLevel(Deflater.BEST_SPEED);
    }

    /**
     * Compress using the ubiquitous "zip" compression.
     *
     * @param input Usually myString.getBytes()
     * @return Compressed version of byte array.
     */
    public byte[] compress(byte[] input) {
        compressor.reset();
        if (input.length == 0)
            return input;
        compressor.setInput(input);
        compressor.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        return bos.toByteArray();
    }

    /**
     * Decompress data that has already been compressed.
     *
     * @param compressedData byte array of zipped data
     * @return original data contained in a byte array.
     */
    public byte[] decompress(byte[] compressedData) throws DataFormatException {
        decompressor.reset();
        decompressor.setInput(compressedData);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(compressedData.length);
        byte[] buf = new byte[1024];
        while (!decompressor.finished()) {
            int count = decompressor.inflate(buf);
            if (count == 0) {
                break;
            }
            bos.write(buf, 0, count);
        }
        return bos.toByteArray();
    }

}
