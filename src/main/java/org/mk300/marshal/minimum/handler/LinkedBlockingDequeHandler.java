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
import java.util.concurrent.LinkedBlockingDeque;

import org.mk300.marshal.minimum.MarshalHandler;
import org.mk300.marshal.minimum.io.NaturalNumberIoHelper;
import org.mk300.marshal.minimum.io.OInput;
import org.mk300.marshal.minimum.io.OOutput;

/**
 * 
 * @author mkobayas@redhat.com
 *
 */
@SuppressWarnings("rawtypes")
public class LinkedBlockingDequeHandler implements MarshalHandler<LinkedBlockingDeque> {
	
	@Override
	public void writeObject(OOutput out, LinkedBlockingDeque queue) throws IOException {
		// LinkedBlockingDequeHandlerの内部ReentrantLockを利用する必要があるか？
		
		// capacity
		int capacity = queue.remainingCapacity() + queue.size();
		NaturalNumberIoHelper.writeNaturalNumber(out, capacity);
		
		// element num
		NaturalNumberIoHelper.writeNaturalNumber(out, queue.size());
		
		for(Object element : queue) {
			out.writeObject(element);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedBlockingDeque readObject(OInput in, Class<LinkedBlockingDeque> clazz) throws IOException {
		int capacity = NaturalNumberIoHelper.readNaturalNumber(in);
		LinkedBlockingDeque queue = new LinkedBlockingDeque(capacity);
		
		int size = NaturalNumberIoHelper.readNaturalNumber(in);
		for(int i=0 ; i<size ; i++) {
			queue.add(in.readObject());
		}
		
		return queue;
	}

}
