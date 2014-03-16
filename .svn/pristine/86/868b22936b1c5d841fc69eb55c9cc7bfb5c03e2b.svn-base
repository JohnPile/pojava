package org.pojava.util;

import java.util.ArrayList;

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
 * A set of methods for performing string manipulation, mostly to support internal needs without requiring external libraries.
 */
public class StringTool {

    /**
     * Returns a zero-based offset if the left-most characters of str match all of the characters of any member of list.
     *
     * @param list list of strings from which you want to match a prefix
     * @param str prefix
     * @return -1 if no match, else indexed offset
     * @deprecated better to attack this problem from a different angle
     */
    public static int indexedStartMatch(String[] list, String str) {
        for (int i = 0; i < list.length; i++) {
            int len = list[i].length();
            if (str.length() >= len && list[i].equals(str.substring(0, len))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * True if a string matches /^[-]?[0-9]+$/
     *
     * @param s String to match
     * @return true if string is a valid integer
     */
    public static boolean isInteger(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        char c = s.charAt(0);
        if (s.length() == 1) {
            return c >= '0' && c <= '9';
        }
        return (c == '-' || c >= '0' && c <= '9') && onlyDigits(s.substring(1));
    }

    /**
     * True if a string matches /^[tTyY1].*$/
     *
     * @param s string to match
     * @return true if string represents true
     */
    public static boolean isTrue(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        char c = s.charAt(0);
        return c == 't' || c == 'T' || c == 'y' || c == 'Y' || c == '1';
    }

    /**
     * True if a string has only digits in it.
     *
     * @param s string to match
     * @return true if string is composed of only digits.
     */
    public static boolean onlyDigits(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * True if a string starts with a digit.
     *
     * @param s string to match
     * @return true if string starts with a digit.
     */
    public static boolean startsWithDigit(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        char c = s.charAt(0);
        return (c >= '0' && c <= '9');
    }

    /**
     * Parse an integer from left-to-right until non-digit reached
     *
     * @param str string to parse
     * @return first integer greedily matched from a string
     */
    public static int parseIntFragment(String str) {
        if (str == null) {
            return 0;
        }
        int parsed = 0;
        boolean isNeg = false;
        char[] strip = str.toCharArray();
        char c = strip[0];
        if (c == '-') {
            isNeg = true;
        } else if (c >= '0' && c <= '9') {
            parsed = c - '0';
        } else {
            return 0;
        }
        for (int i = 1; i < strip.length; i++) {
            c = strip[i];
            if (c >= '0' && c <= '9') {
                parsed = 10 * parsed + c - '0';
            } else {
                break;
            }
        }
        return isNeg ? -parsed : parsed;
    }

    /**
     * Capitalize the first character of a String.
     *
     * @param str string to change
     * @return Capitalize the first character.
     */
    public static String capitalize(String str) {
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * Convert string of this_style to thisStyle.
     *
     * @param str string to change
     * @return camelCase from underscored_words
     */
    public static String camelFromUnderscore(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        char[] ar = str.toCharArray();
        int backref = 0;
        ar[0] = Character.toLowerCase(ar[0]);
        for (int i = 0; i < ar.length; i++) {
            if (ar[i] == '_') {
                if (ar[i + 1] >= '0' && ar[i + 1] <= '9') {
                    ar[i - backref] = ar[i];
                } else {
                    ar[i - backref++] = Character.toUpperCase(ar[i++ + 1]);
                }
            } else {
                ar[i - backref] = ar[i];
            }
        }
        return new String(ar).substring(0, ar.length - backref);
    }

    /**
     * Convert strings of thisStyle to this_style.
     *
     * @param str string to change
     * @return underscored_words from camelCase
     */
    public static String underscoreFromCamel(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        char[] a = str.toCharArray();
        for (char element : a) {
            char c = element;
            if (c >= 'A' && c <= 'Z') {
                sb.append('_');
                c += ('a' - 'A');
            } else if (c == '_') {
                sb.append('_');
            } else if (c >= '0' && c <= '9' && sb.length() > 0 && sb.charAt(sb.length() - 1) == '_') {
                sb.setLength(sb.length() - 1);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Collapses a string with whitespace characters, including carriage returns, into a one-line string with no spaces or tabs.
     *
     * @param str string to change
     * @return A string with all whitespace removed.
     */
    public static String stripWhitespace(String str) {
        char[] from = str.toCharArray();
        char[] to = new char[from.length];
        int t = 0;
        for (char c : from) {
            if (!(c == ' ' || c == '\t' || c == '\n' || c == '\r')) {
                to[t++] = c;
            }
        }
        return new String(to, 0, t);
    }

    /**
     * Right-pad a string to a fixed width with spaces.
     *
     * @param str string to right-pad
     * @param width new minimum width (will not truncate non-spaces)
     * @return Padded string of minimal width.
     */
    public static String pad(String str, int width) {
        if (width < str.length()) {
            return str;
        }
        StringBuilder sb = new StringBuilder(width);
        sb.append(str);
        for (int i = 0; i < width - str.length(); i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    /**
     * Convert a shell command to an equivalent String array.
     *
     * @param cmd Command including parameters.
     * @return String array parsed from cmd.
     */
    public static String[] parseCommand(String cmd) {
        ArrayList<String> list = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        char[] chars = cmd.toCharArray();
        boolean isLiteral = false;
        char litchar = ' ';
        String[] command = {};
        for (char c : chars) {
            if (isLiteral) {
                if (c == litchar) {
                    isLiteral = false;
                    if (c == '`') {
                        sb.append(c);
                    }
                } else {
                    sb.append(c);
                }
            } else {
                if (c == '\'' || c == '\"' || c == '`') {
                    isLiteral = true;
                    litchar = c;
                    if (c == '`') {
                        sb.append(c);
                    }
                } else if (c == ' ') {
                    if (sb.length() > 0) {
                        list.add(sb.toString());
                        sb.setLength(0);
                    }
                } else {
                    sb.append(c);
                }
            }
        }
        if (sb.length() > 0) {
            list.add(sb.toString());
            sb.setLength(0);
        }
        if (isLiteral) {
            throw new IllegalArgumentException("Unclosed quotes in argument.");
        }
        return list.toArray(command);
    }

}
