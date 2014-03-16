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
 * A BindingAdaptor is a two-way data transformer used for translating a single typed value with
 * an external representation.
 * <p/>
 * It is responsible for translating both the data and the expected class in both directions,
 * even for null values.
 *
 * @author John Pile
 */
public abstract class BindingAdaptor<I, O> {

    public abstract Class<I> inboundType();

    public abstract Class<O> outboundType();

    /**
     * Inbound example might be from the persistence layer to the business layer.
     *
     * @param obj Inbound object
     * @return a new binding translated from the old
     */
    public abstract Binding<I> inbound(Binding<O> obj);

    /**
     * Outbound is typically originating from the business layer to the persistence layer.
     *
     * @param obj Outbound object
     * @return a new binding translated from the old
     */
    public abstract Binding<O> outbound(Binding<I> obj);

}
