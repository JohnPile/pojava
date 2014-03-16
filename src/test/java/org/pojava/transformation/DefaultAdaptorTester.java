package org.pojava.transformation;

import junit.framework.TestCase;
import org.pojava.datetime.DateTime;
import org.pojava.lang.Binding;
import org.pojava.lang.UncheckedBinding;

import java.sql.Timestamp;
import java.util.Date;

public class DefaultAdaptorTester extends TestCase {

    public void testDateTimeCase() {
        UncheckedAdaptor adaptor = new DefaultAdaptor();
        Binding<DateTime> local = new Binding<DateTime>(DateTime.class, new DateTime(123));
        Binding<Timestamp> remote = new Binding<Timestamp>(Timestamp.class, new Timestamp(123));
        UncheckedBinding adapted = adaptor.inbound(remote);
        assertEquals(local.getObj(), adapted.getObj());
        adapted = adaptor.outbound(local);
        assertEquals(remote.getObj(), adapted.getObj());
    }

    public void testUtilDateCase() {
        UncheckedAdaptor adaptor = new DefaultAdaptor();
        UncheckedBinding local = new UncheckedBinding(Date.class, new Date(123));
        Binding<Timestamp> remote = new Binding<Timestamp>(Timestamp.class, new Timestamp(123));
        UncheckedBinding adapted = adaptor.inbound(remote);
        assertEquals(123, ((DateTime) adapted.getObj()).toMillis());
        adapted = adaptor.outbound(local);
        assertEquals(123, ((Timestamp) adapted.getObj()).getTime());
    }

    public void testBooleanCase() {
        UncheckedAdaptor adaptor = new DefaultAdaptor();
        Binding<Boolean> local = new Binding<Boolean>(Boolean.class, Boolean.TRUE);
        UncheckedBinding remote = new UncheckedBinding(Boolean.class, Boolean.TRUE);
        UncheckedBinding adapted = adaptor.inbound(remote);
        assertEquals(local.getObj(), adapted.getObj());
        adapted = adaptor.outbound(local);
        assertEquals(remote.getObj(), adapted.getObj());
    }

    public void testNullCase() {
        UncheckedAdaptor adaptor = new DefaultAdaptor();
        assertEquals(null, adaptor.inbound(new Binding<String>(String.class, null)).getObj());
        // Look out... if we can't determine type, we just return null.
        assertEquals(null, adaptor.outbound(null));
    }
}
