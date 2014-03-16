package org.pojava.transformation;

import junit.framework.TestCase;
import org.pojava.lang.Binding;

public class BooleanIntegerAdaptorTester extends TestCase {

    public void testCleanCase() {
        BindingAdaptor<Boolean, Integer> adaptor = new BooleanIntegerAdaptor();
        Binding<Boolean> local = new Binding<Boolean>(Boolean.class, Boolean.TRUE);
        Binding<Integer> remote = new Binding<Integer>(Integer.class, 1);
        Binding<Boolean> adaptedIn = adaptor.inbound(remote);
        Binding<Integer> adaptedOut = adaptor.outbound(local);
        assertEquals(local.getObj(), adaptedIn.getObj());
        assertEquals(remote.getObj(), adaptedOut.getObj());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void testDirtyCase() {
        BindingAdaptor<Boolean, Integer> adaptor = new BooleanIntegerAdaptor();
        try {
            // Force an incompatibly-typed binding into the adaptor.
            adaptor.inbound(new Binding(String.class, "invalid"));
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
