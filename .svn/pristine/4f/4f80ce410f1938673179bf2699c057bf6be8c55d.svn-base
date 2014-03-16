package org.pojava.lang;

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

import org.pojava.datetime.DateTime;
import org.pojava.datetime.DateTimeFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * A BoundString represents a String with Bindings. Each binding is represented by a placeholder
 * mapped sequentially to a list of Binding objects.
 *
 * @author John Pile
 */
public class BoundString {

    private static final char PLACEHOLDER = '?';
    private static final char QUOT = '\'';
    private final StringBuffer sb = new StringBuffer();
    private final List<UncheckedBinding> bindings = new ArrayList<UncheckedBinding>();
    private static final String date2ms = "MM/dd/yyyy HH:mm:ss.SSS";

    /**
     * Construct an empty BoundString.
     */
    public BoundString() {
    }

    /**
     * Construct a BoundString from a String.
     */
    public BoundString(String str) {
        this.sb.append(str);
    }

    /**
     * Append to the existing string.
     *
     * @param str String to be appended
     */
    public void append(String str) {
        this.sb.append(str);
    }

    /**
     * Insert in front of the existing string.
     *
     * @param str String to be inserted
     */
    public void insert(String str) {
        this.sb.insert(0, str);
    }

    /**
     * Append another bound string including both string and bindings.
     *
     * @param bstr bound string to append
     */
    public void append(BoundString bstr) {
        this.sb.append(bstr.getString());
        this.bindings.addAll(bstr.getBindings());
    }

    /**
     * Insert another bound string including both string and bindings.
     *
     * @param bstr bound string to insert
     */
    public void insert(BoundString bstr) {
        this.sb.insert(0, bstr.getString());
        this.bindings.addAll(0, bstr.getBindings());
    }

    /**
     * Return the bindings bound to the string.
     *
     * @return List of Binding objects.
     */
    public List<UncheckedBinding> getBindings() {
        return this.bindings;
    }

    /**
     * Return the string being bound
     *
     * @return String into which Binding objects are bound.
     */
    public String getString() {
        return this.sb.toString();
    }

    /**
     * Add a binding
     *
     * @param type class of object to bind.
     * @param obj  object to bind.
     */
    public <T> void addBinding(Class<T> type, T obj) {
        this.bindings.add(new Binding<T>(type, obj));
    }

    public void addBinding(UncheckedBinding binding) {
        this.bindings.add(binding);
    }

    /**
     * Add a collection of bindings.
     *
     * @param bindings Bindings to apply
     */
    public void addBindings(Collection<UncheckedBinding> bindings) {
        this.bindings.addAll(bindings);
    }

    /**
     * Clear both string and bindings.
     */
    public void clear() {
        this.sb.setLength(0);
        this.bindings.clear();
    }

    /**
     * Verify placeholder count against bindings count.
     *
     * @return True if placeholder count mismatches binding count.
     */
    public boolean isImbalanced() {
        int placeholders = 0;
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == PLACEHOLDER) {
                placeholders++;
            }
        }
        return placeholders != this.bindings.size();
    }

    /**
     * Chop last ct characters off of string
     *
     * @param ct Number of characters to excise
     */
    public void chop(int ct) {
        this.sb.setLength(Math.max(0, this.sb.length() - ct));
    }

    /**
     * Display an unbound equivalent of this BoundString with String versions of each bound
     * value represented in-line in place of the markers.
     *
     * @return String with toString versions of bound values inserted back into the String.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int marker = 0;
        char quot = '\'';
        char[] sql = this.getString().toCharArray();
        for (char c : sql) {
            if (c == '?') {
                UncheckedBinding binding = getBindings().get(marker++);
                if (binding.getObj() == null) {
                    sb.append("null");
                } else {
                    Object obj = binding.getObj();
                    Class<?> type = binding.getType();
                    if (type == Integer.class || type == int.class || type == Long.class
                            || type == long.class) {
                        sb.append(obj.toString());
                    } else if (DateTime.class == obj.getClass()) {
                        sb.append(formatDate((DateTime) obj));
                    } else if (Date.class.isAssignableFrom(obj.getClass())) {
                        sb.append(formatDate(new DateTime(((Date) obj).getTime())));
                    } else {
                        sb.append(quot);
                        sb.append(binding.getObj().toString());
                        sb.append(quot);
                    }
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Format a date, chopping off zeros.
     *
     * @param dt Date to format
     * @return a string version of a date with zeros truncated.
     */
    private String formatDate(DateTime dt) {
        StringBuilder sb = new StringBuilder();
        sb.append(QUOT);
        sb.append(DateTimeFormat.format(BoundString.date2ms, dt));
        if (sb.substring(11).equals(" 00:00:00.000")) {
            sb.setLength(11);
        } else if (sb.substring(20).equals(".000")) {
            sb.setLength(20);
        }
        sb.append(QUOT);
        return sb.toString();
    }

}
