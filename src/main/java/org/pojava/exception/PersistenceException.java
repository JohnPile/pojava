package org.pojava.exception;

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
 * This wraps a number of exceptions that generally indicate a problem relating to the task of
 * persisting an object to an external medium, such as JDBC, a file, or a service.
 * <p/>
 * The general purpose is to allow the business end of your code to be insulated from changes to
 * a particular storage medium, so you can more easily switch, for example, from direct SQL to
 * an ESB or other service.
 *
 * @author John Pile
 */
public class PersistenceException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public PersistenceException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
