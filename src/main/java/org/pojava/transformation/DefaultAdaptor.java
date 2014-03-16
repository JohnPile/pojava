package org.pojava.transformation;

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
import org.pojava.lang.Binding;
import org.pojava.lang.UncheckedBinding;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The DefaultAdaptor passes most data through directly, but performs some specialized
 * transformations on date types.
 *
 * @author John Pile
 */
public class DefaultAdaptor implements UncheckedAdaptor {

    public Class<?> inboundType() {
        return Object.class;
    }

    public Class<?> outboundType() {
        return Object.class;
    }

    /**
     * Inbound is typically converting JDBC values to local POJO values.
     */
    public UncheckedBinding inbound(UncheckedBinding inBinding) {
        UncheckedBinding outBinding = inBinding;
        if (inBinding == null)
            return null;
        if (inBinding.getObj() == null) {
            return outBinding;
        }
        if (Time.class.equals(inBinding.getType())) {
            return outBinding;
        }
        if (Date.class.isAssignableFrom(inBinding.getObj().getClass())) {
            outBinding = new Binding<DateTime>(DateTime.class, new DateTime(((Date) inBinding
                    .getObj()).getTime()));
        }
        return outBinding;
    }

    /**
     * This default outbound adaptor caters to the currently wide JDBC support for the Timestamp
     * object. As JDBC evolves over time, this may need to be adjusted or replaced.
     */
    public UncheckedBinding outbound(UncheckedBinding outBinding) {
        UncheckedBinding inBinding = outBinding;
        if (outBinding == null)
            return null;
        if (outBinding.getObj() == null) {
            inBinding = new Binding<Timestamp>(Timestamp.class, null);
        }
        if (Time.class == outBinding.getType()) {
            return inBinding;
        }
        if (outBinding.getType().equals(DateTime.class)) {
            inBinding = new Binding<Timestamp>(Timestamp.class,
                    ((DateTime) outBinding.getObj()).toTimestamp());
        }
        if (Date.class.isAssignableFrom(outBinding.getType())) {
            inBinding = new Binding<Timestamp>(Timestamp.class, new Timestamp(
                    ((Date) outBinding.getObj()).getTime()));
        }
        return inBinding;
    }

}
