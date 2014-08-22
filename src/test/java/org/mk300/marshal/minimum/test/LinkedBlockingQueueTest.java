package org.mk300.marshal.minimum.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.io.HexDump;
import org.junit.Test;
import org.mk300.marshal.minimum.MinimumMarshaller;

@SuppressWarnings({"rawtypes", "unchecked"})
public class LinkedBlockingQueueTest {
	@Test
	public void testLinkedBlockingQueue() throws Exception {
		LinkedBlockingQueue data = new LinkedBlockingQueue(10);

		data.add(new Date(0));
		data.add(new Date(3));
		data.add(new Date(4));
		data.put(new Date(5));
		data.put(new Date(6));
		data.put(new Date(7));
		data.poll();
		testAndPrintHexAndCheck(data);
	}

	// LinkedBlockingQueue は equalsメソッドを実装していない・・・
	private void testAndPrintHexAndCheck(LinkedBlockingQueue<Date> target) throws Exception{
		try {

			byte[] bytes = MinimumMarshaller.marshal(target);
			
			System.out.println(target.getClass().getSimpleName() + " binary size is " + bytes.length);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			HexDump.dump(bytes, 0, os, 0);
			System.out.println(os.toString());
			System.out.println("");
			
			LinkedBlockingQueue<Date> o = (LinkedBlockingQueue<Date>)MinimumMarshaller.unmarshal(bytes);
			
			// 正確に復元されていることの検証
			if( o.size() != target.size()) {
				throw new RuntimeException("オブジェクトが異なります。target=" + target + ", desr=" + o);
			}
			
			if( o.remainingCapacity() != target.remainingCapacity()) {
				throw new RuntimeException("オブジェクトが異なります。target=" + target + ", desr=" + o);
			}
	
			Date[] desr = o.toArray(new Date[0]);
			Date[] origin = target.toArray(new Date[0]);
			
			for(int i=0; i<desr.length ; i++) {
				if(desr[i] == null && origin[i] == null) {
					continue;
				}
				
				if(desr[i] == null || origin[i] == null) {
					throw new RuntimeException("オブジェクトが異なります。target=" + target + ", desr=" + o);
				}
				
				if( ! desr[i].equals(origin[i])) {
					throw new RuntimeException("オブジェクトが異なります。target=" + target + ", desr=" + o);
					
				}
			}
		} finally {
		}
		
		// おまけ 普通のByteArray*Streamも使えるか？
		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			MinimumMarshaller.marshal(target, baos);
			
			byte[] bytes = baos.toByteArray();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			
			LinkedBlockingQueue<Date> o = (LinkedBlockingQueue<Date>)MinimumMarshaller.unmarshal(bais);
			
			
			// 正確に復元されていることの検証
			if( o.size() != target.size()) {
				throw new RuntimeException("オブジェクトが異なります。target=" + target + ", desr=" + o);
			}
			
			if( o.remainingCapacity() != target.remainingCapacity()) {
				throw new RuntimeException("オブジェクトが異なります。target=" + target + ", desr=" + o);
			}
	
			Date[] desr = o.toArray(new Date[0]);
			Date[] origin = target.toArray(new Date[0]);
			
			for(int i=0; i<desr.length ; i++) {
				if(desr[i] == null && origin[i] == null) {
					continue;
				}
				
				if(desr[i] == null || origin[i] == null) {
					throw new RuntimeException("オブジェクトが異なります。target=" + target + ", desr=" + o);
				}
				
				if( ! desr[i].equals(origin[i])) {
					throw new RuntimeException("オブジェクトが異なります。target=" + target + ", desr=" + o);
					
				}
			}
			
		} finally {
		}
	}
}
