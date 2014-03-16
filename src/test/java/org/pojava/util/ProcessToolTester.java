package org.pojava.util;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;

public class ProcessToolTester extends TestCase {

    /**
     * Hopefully, java is on your PATH whether in Windows or Unix/Linux/Mac.
     *
     * @throws Exception
     */
    public void testSet() throws Exception {
        StringBuffer stdout = new StringBuffer();
        StringBuffer stderr = new StringBuffer();
        ProcessTool.exec("java", stdout, stderr);
        assertTrue((stdout.toString() + stderr.toString()).length() > 1000);
    }

    /**
     * Connect the output of a command to an output stream.
     * One popular OutputStream is the .getOutputStream() provided
     * in a Response object for a servlet, Struts action, or the like.
     *
     * @throws Exception
     */
    public void testParamAndStream() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StringBuffer stderr = new StringBuffer();
        String[] cmd = {"java", "-help"};
        ProcessTool.exec(cmd, baos, stderr);
        String str = baos.toString("UTF-8") + stderr.toString();
        assertTrue(str.length() > 1000);
        assertTrue(str.contains("classpath"));
    }

    public void testIntegratedParamAndStream() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StringBuffer stderr = new StringBuffer();
        String cmd = "java -help";
        ProcessTool.exec(cmd, baos, stderr);
        String str = baos.toString("UTF-8") + stderr.toString();
        assertTrue(str.length() > 1000);
        assertTrue(str.contains("classpath"));
    }

    /**
     * Process a command with parameters to a StringBuffer.
     *
     * @throws Exception
     */
    public void testParamAndBuffer() throws Exception {
        StringBuffer stdout = new StringBuffer();
        StringBuffer stderr = new StringBuffer();
        String[] cmd = {"java", "-help"};
        ProcessTool.exec(cmd, stdout, stderr);
        String str = stdout.toString() + stderr.toString();
        assertTrue(str.length() > 1000);
        assertTrue(str.contains("classpath"));
    }
}
