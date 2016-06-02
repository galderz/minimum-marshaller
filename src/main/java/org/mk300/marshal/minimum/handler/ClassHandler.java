/*
 * Copyright 2014 Masazumi Kobayashi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mk300.marshal.minimum.handler;


import java.io.IOException;

import org.mk300.marshal.minimum.MarshalHandler;
import org.mk300.marshal.minimum.io.OInput;
import org.mk300.marshal.minimum.io.OOutput;

/**
 *  
 *  @author mkobayas@redhat.com
 *
 */
@SuppressWarnings("rawtypes")
public class ClassHandler implements MarshalHandler<Class> {

	@Override
	public void writeObject(OOutput out, Class clazz) throws IOException {
		out.writeString(clazz.getName());
	}

	@Override
	public Class readObject(OInput in, Class<Class> clazz) throws IOException {
		String classStr = in.readString();
		Class c;
        try {
            c = Class.forName(classStr);
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
		return c;
	}

}
