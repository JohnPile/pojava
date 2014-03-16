package org.pojava.util;

import junit.framework.TestCase;

public class StringToolTester extends TestCase {

    public void testIsInteger() {
        assertTrue(StringTool.isInteger("0"));
        assertTrue(StringTool.isInteger("-123456"));
        assertTrue(StringTool.isInteger("999"));
        assertFalse(StringTool.isInteger(null));
        assertFalse(StringTool.isInteger(""));
        assertFalse(StringTool.isInteger("-123-"));
        assertFalse(StringTool.isInteger(""));
        assertFalse(StringTool.isInteger("one"));
        assertFalse(StringTool.isInteger("1.23"));
        assertFalse(StringTool.isInteger("123Four"));
    }

    public void testOnlyDigits() {
        assertTrue(StringTool.onlyDigits("123412341234"));
        assertTrue(StringTool.onlyDigits("0"));
        assertFalse(StringTool.onlyDigits("-123"));
        assertFalse(StringTool.onlyDigits(null));
        assertFalse(StringTool.onlyDigits("A1B2"));
        assertFalse(StringTool.onlyDigits("1A"));
    }

    public void testParseIntFragment() {
        assertEquals(123, StringTool.parseIntFragment("123"));
        assertEquals(123, StringTool.parseIntFragment("123.45"));
        assertEquals(321, StringTool.parseIntFragment("321Penguins"));
        assertEquals(-4, StringTool.parseIntFragment("-4M"));
        assertEquals(18, StringTool.parseIntFragment("18:14"));
        assertEquals(0, StringTool.parseIntFragment(null));
    }

    public void testCapitalize() {
        assertEquals("Hey, Mo", StringTool.capitalize("hey, Mo"));
        assertEquals("Hey, mo", StringTool.capitalize("hey, mo"));
    }

    public void testCamelFromUnderscore() {
        assertEquals("iAmACamel", StringTool.camelFromUnderscore("i_am_a_camel"));
        assertEquals("iAmACamel", StringTool.camelFromUnderscore("I_am_a_Camel"));
        assertEquals("iAte_naan", StringTool.camelFromUnderscore("i_ate__naan"));
        assertEquals("server_1", StringTool.camelFromUnderscore("server_1"));
        assertEquals("underDog", StringTool.camelFromUnderscore("under_dog"));
        assertEquals("a1AndA2", StringTool.camelFromUnderscore("a1_and_a2"));
    }

    public void testUnderscoreFromCamel() {
        assertEquals("i_am_a_camel", StringTool.underscoreFromCamel("iAmACamel"));
        assertEquals("i_ate__naan", StringTool.underscoreFromCamel("iAte_naan"));
        assertEquals("server_1", StringTool.underscoreFromCamel("server_1"));
        assertEquals("under_dog", StringTool.underscoreFromCamel("underDog"));
        assertEquals("a1_and_a2", StringTool.underscoreFromCamel("a1AndA2"));
    }

    public void testStripWhitespace() {
        assertEquals("Vapidfoxpawszipquicklyundermybrightjar.",
                StringTool.stripWhitespace("   \tVapid fox paws\tzip quickly \t \tunder my bright jar .\n\n "));
    }

    public void testPad() {
        assertEquals("Ten       ", StringTool.pad("Ten", 10));
        assertEquals(" eleven    ", StringTool.pad(" eleven ", 11));
        // Pad does not truncate or trim.
        assertEquals("three ", StringTool.pad("three ", 3));
    }

    public void testTrue() {
        assertEquals(true, StringTool.isTrue("Yes"));
        assertEquals(true, StringTool.isTrue("y"));
        assertEquals(true, StringTool.isTrue("True"));
        assertEquals(true, StringTool.isTrue("t"));
        assertEquals(true, StringTool.isTrue("1"));
        assertEquals(false, StringTool.isTrue("No"));
        assertEquals(false, StringTool.isTrue("n"));
        assertEquals(false, StringTool.isTrue("False"));
        assertEquals(false, StringTool.isTrue("f"));
        assertEquals(false, StringTool.isTrue("0"));
        assertEquals(false, StringTool.isTrue("z"));
        assertEquals(false, StringTool.isTrue(""));
        assertEquals(false, StringTool.isTrue(null));
    }

    public void testParseCommandQuotes() {
        String[] cmd = StringTool.parseCommand("useradd \"Joshua Timothy\"");
        assertEquals("useradd", cmd[0]);
        assertEquals("Joshua Timothy", cmd[1]);
        cmd = StringTool.parseCommand("promote \"Jacob  Andrew\" to  Captain");
        assertEquals("promote", cmd[0]);
        assertEquals("Jacob  Andrew", cmd[1]);
        assertEquals("to", cmd[2]);
        assertEquals("Captain", cmd[3]);
    }

    public void testParseCommandApostrophes() {
        String[] cmd = StringTool.parseCommand("promote 'Jacob's [sic] dog to Captain");
        assertEquals("promote", cmd[0]);
        assertEquals("Jacobs", cmd[1]);
        assertEquals("[sic]", cmd[2]);
        assertEquals("dog", cmd[3]);
        assertEquals("to", cmd[4]);
        assertEquals("Captain", cmd[5]);
        cmd = StringTool.parseCommand("promote \"Jacob's dog\" to Captain");
        assertEquals("promote", cmd[0]);
        assertEquals("Jacob's dog", cmd[1]);
        assertEquals("to", cmd[2]);
        assertEquals("Captain", cmd[3]);
        try {
            StringTool.parseCommand("My shell wouldn't tolerate this");
            fail("Uncaught open single quote.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Unclosed quotes in argument.", ex.getMessage());
        }
    }

    public void testParseCommandBacktics() {
        String[] cmd = StringTool.parseCommand("dir poj-`date +%d`.log");
        assertEquals("dir", cmd[0]);
        assertEquals("poj-`date +%d`.log", cmd[1]);
    }
}
