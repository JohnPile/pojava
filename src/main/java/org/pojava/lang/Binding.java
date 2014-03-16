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

/**
 * A Binding is representation of an object with its class. Its class can be inferred even when
 * the object is null.
 *
 * @author John Pile
 */
public class Binding<T> extends UncheckedBinding {

    /**
     * Construct a Binding from an object.
     *
     * @param type Class of bound object
     * @param obj Bound object
     */
    public Binding(Class<T> type, T obj) {
        super(type, obj);
    }

    /**
     * Return the type represented by this binding.
     */
    @SuppressWarnings("unchecked")
    public Class<T> getType() {
        return (Class<T>) super.getType();
    }

    /**
     * Retrieve typed value of object.
     *
     * @return Typed value of object.
     */
    @SuppressWarnings("unchecked")
    public T getValue() {
        return (T) super.getObj();
    }

    /**
     * Store a new value into this object.
     *
     * @param obj Bound object
     */
    public void setValue(T obj) {
        super.setObj(obj);
    }

}
