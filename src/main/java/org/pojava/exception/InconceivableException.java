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
 * The InconceivableException was conceived for the purpose of covering cases where the
 * developer doesn't want to pass on the joy of handling exceptions he believes can't happen to
 * callers of his code. The benefit is that if they do happen, they aren't just swallowed by an
 * empty catch block he will have a clue about where something unusually improbable must have
 * occurred.
 */
public class InconceivableException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public InconceivableException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
