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

import org.pojava.lang.Binding;

/**
 * The BooleanIntegerAdaptor transforms a Boolean true/false value to an Integer 1/0 value.
 *
 * @author John Pile
 */
public class BooleanIntegerAdaptor extends BindingAdaptor<Boolean, Integer> {

    public Class<Boolean> inboundType() {
        return Boolean.class;
    }

    public Class<Integer> outboundType() {
        return Integer.class;
    }

    public Binding<Boolean> inbound(Binding<Integer> fromBinding) {
        Binding<Boolean> toBinding = new Binding<Boolean>(Boolean.class, null);
        if (fromBinding == null || fromBinding.getObj() == null) {
            return toBinding;
        }
        toBinding.setObj(fromBinding.getValue() != 0);
        return toBinding;
    }

    public Binding<Integer> outbound(Binding<Boolean> fromBinding) {
        Binding<Integer> toBinding = new Binding<Integer>(Integer.class, null);
        if (fromBinding == null || fromBinding.getObj() == null) {
            return toBinding;
        }
        toBinding.setObj(fromBinding.getValue().equals(Boolean.TRUE) ? 1 : 0);
        return toBinding;
    }

}
