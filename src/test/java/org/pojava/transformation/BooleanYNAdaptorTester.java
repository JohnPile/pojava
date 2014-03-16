package org.pojava.transformation;

import junit.framework.TestCase;
import org.pojava.lang.Binding;

public class BooleanYNAdaptorTester extends TestCase {

    public void testCleanCase() {
        BindingAdaptor<Boolean, String> adaptor = new BooleanYNAdaptor();
        Binding<Boolean> local = new Binding<Boolean>(Boolean.class, Boolean.TRUE);
        Binding<String> remote = new Binding<String>(String.class, "Y");
        Binding<Boolean> adaptedIn = adaptor.inbound(remote);
        Binding<String> adaptedOut = adaptor.outbound(local);
        assertEquals(local.getValue(), adaptedIn.getValue());
        assertEquals(remote.getValue(), adaptedOut.getValue());
        assertEquals(local.getObj(), adaptedIn.getObj());
        assertEquals(remote.getObj(), adaptedOut.getObj());
    }

    /**
     * How will the system cope if type-checking is suppressed so an invalid type can be
     * inserted?
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void testDirtyCase() {
        BindingAdaptor adaptor = new BooleanIntegerAdaptor();
        try {
            adaptor.inbound(new Binding(String.class, "invalid"));
            fail("Expecting ClassCastException.");
        } catch (ClassCastException ex) {
            assertTrue(ex.getMessage().startsWith("java.lang.String"));
        }
        try {
            adaptor.outbound(new Binding(String.class, "other"));
            fail("Expecting ClassCastException.");
        } catch (ClassCastException ex) {
            assertTrue(ex.getMessage().startsWith("java.lang.String"));
        }
    }

    public void testNullCase() {
        BindingAdaptor<Boolean, Integer> adaptor = new BooleanIntegerAdaptor();
        assertEquals(null, adaptor.inbound(null).getObj());
        assertEquals(null, adaptor.outbound(null).getObj());
    }
}
