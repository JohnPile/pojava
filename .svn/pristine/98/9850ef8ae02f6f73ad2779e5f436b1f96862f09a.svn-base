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

/*
 * The BooleanYNAdaptor transforms a Boolean true/false value to a
 * character Y/N value.
 */
public class BooleanYNAdaptor extends BindingAdaptor<Boolean, String> {

    public Class<Boolean> inboundType() {
        return Boolean.class;
    }

    public Class<String> outboundType() {
        return String.class;
    }

    public Binding<Boolean> inbound(Binding<String> fromBinding) {
        Binding<Boolean> toBinding = new Binding<Boolean>(Boolean.class, null);
        if (fromBinding == null || fromBinding.getObj() == null) {
            return toBinding;
        }
        String fromStr = fromBinding.getObj().toString();
        if (fromStr.length() == 0) {
            return toBinding;
        }
        char fromChar = fromStr.charAt(0);
        if (Character.toUpperCase(fromChar) == 'Y') {
            toBinding.setObj(Boolean.TRUE);
        } else if (Character.toUpperCase(fromChar) == 'N') {
            toBinding.setObj(Boolean.FALSE);
        }
        return toBinding;
    }

    public Binding<String> outbound(Binding<Boolean> fromBinding) {
        Binding<String> toBinding = new Binding<String>(String.class, null);
        if (fromBinding == null || fromBinding.getObj() == null) {
            return toBinding;
        }
        toBinding.setValue(fromBinding.getValue().equals(Boolean.TRUE) ? "Y" : "N");
        return toBinding;
    }

}
