package org.pojava.transformation;

import org.pojava.lang.UncheckedBinding;

public interface UncheckedAdaptor {

    public Class<?> inboundType();

    public Class<?> outboundType();

    public UncheckedBinding inbound(UncheckedBinding outBinding);

    public UncheckedBinding outbound(UncheckedBinding inBinding);

}
