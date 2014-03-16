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

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The DataPump class was developed primarily for supporting Runtime.getRuntime().exec(...),
 * where you may need to gather from stdout and stderr simultaneously. Handling results using an
 * additional thread or two could prevent an app from locking up while waiting for you to read
 * from one of those two pipes. If you exec a single-threaded app that completely fills one 4k
 * pipe while you're waiting for data on the other pipe, it can block and may never get around
 * to either writing to or closing the pipe you're reading-- a classic deadlock preventable by
 * threading.
 *
 * @author John Pile
 */
public class DataPump implements Runnable {

    private static final Logger LOGGER = Logger.getLogger("org.org.pojava.util.DataPump");

    /**
     * Source of data stream
     */
    private InputStream in;

    /**
     * Destination of data stream (often intermediate)
     */
    private OutputStream out;

    /**
     * Holding pond
     */
    private StringBuffer textBuffer;

    /**
     * Connect pump from an Input Stream to an Output Stream
     *
     * @param dataSource Data Source
     * @param dataDest   Data Destination
     */
    public DataPump(final InputStream dataSource, final OutputStream dataDest) {
        this.in = dataSource;
        this.out = dataDest;
    }

    /**
     * Connect pump from an Input Stream to a String Buffer
     *
     * @param dataSource Data Source
     * @param buffer     Data Buffer (holding pond)
     */
    public DataPump(final InputStream dataSource, final StringBuffer buffer) {
        this.in = dataSource;
        this.textBuffer = buffer;
    }

    /**
     * Connectors are in place. Begin moving data.
     */
    public final void run() {
        try {
            if (out != null) {
                DataPump.pump(in, out);
            } else if (textBuffer != null) {
                DataPump.pump(in, textBuffer);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            // Just eat the exception. You're in a secondary stream.
        }
    }

    /**
     * This pump pulls data from one stream, pushing it to another.
     *
     * @param in  Data Source
     * @param out Data Destination
     * @throws java.io.IOException if read/write fails
     */
    public static void pump(final InputStream in, final OutputStream out) throws IOException {
        BufferedInputStream buffer = new BufferedInputStream(in);
        final int bucketSize = 1024;
        int drawn;
        byte[] bucket = new byte[bucketSize];
        try {
            while ((drawn = buffer.read(bucket)) >= 0) {
                out.write(bucket, 0, drawn);
            }
        } finally {
            buffer.close();
        }
    }

    /**
     * This pump extracts text from an input stream into a StringBuffer.
     *
     * @param in     Data Source
     * @param buffer Holding Buffer
     * @throws java.io.IOException if read/write fails
     */
    public static void pump(final InputStream in, final StringBuffer buffer) throws IOException {
        BufferedReader bReader = new BufferedReader(new InputStreamReader(in));
        final int bucketSize = 1024;
        int drawn;
        char[] bucket = new char[bucketSize];
        try {
            while ((drawn = bReader.read(bucket)) >= 0) {
                buffer.append(bucket, 0, drawn);
            }
        } finally {
            bReader.close();
        }
    }

}