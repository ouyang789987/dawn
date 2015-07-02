package zhmt.dawn.util;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestCycleArrayQueue {
	@Test
	public void test() {
		CycleArrayQueue<Integer> list = new CycleArrayQueue<Integer>(4);
		assertTrue(list.size()==0);
		list.add(1);
		assertTrue(list.getFirst()==1);
		assertTrue(list.size()==1);
		assertTrue(list.removeFirst()==1);
		assertTrue(list.size()==0);
		
		
		list.add(2);
		assertTrue(list.size()==1);
		assertTrue(list.getFirst()==2);
		list.add(3);
		assertTrue(list.size()==2);
		assertTrue(list.getFirst()==2);
		list.add(4);
		assertTrue(list.size()==3);
		assertTrue(list.getFirst()==2);
		list.add(5);
		assertTrue(list.size()==4);
		assertTrue(list.getFirst()==2);
		list.add(6);
		assertTrue(list.size()==5);
		assertTrue(list.getFirst()==2);
		list.add(7);
		assertTrue(list.size()==6);
		assertTrue(list.getFirst()==2);
		list.add(8);
		assertTrue(list.size()==7);
		assertTrue(list.getFirst()==2);
		
		
		
		assertTrue(list.removeFirst()==2);
		assertTrue(list.size()==6);
		
		assertTrue(list.getFirst()==3);
		assertTrue(list.removeFirst()==3);
		assertTrue(list.size()==5);
		
		assertTrue(list.getFirst()==4);
		assertTrue(list.removeFirst()==4);
		assertTrue(list.size()==4);
		
		assertTrue(list.getFirst()==5);
		assertTrue(list.removeFirst()==5);
		assertTrue(list.size()==3);
		
		assertTrue(list.getFirst()==6);
		assertTrue(list.removeFirst()==6);
		assertTrue(list.size()==2);
		
		assertTrue(list.getFirst()==7);
		assertTrue(list.removeFirst()==7);
		assertTrue(list.size()==1);
		
		assertTrue(list.getFirst()==8);
		assertTrue(list.removeFirst()==8);
		assertTrue(list.size()==0);
		
		assertTrue(list.getFirst()==null);
		
		
		
		
		
		
		list.add(1);
		assertTrue(list.size()==1);
		assertTrue(list.getFirst()==1);
		assertTrue(list.removeFirst()==1);
		assertTrue(list.size()==0);
		assertTrue(list.getFirst()==null);
	}
}
