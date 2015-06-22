package zhmt.dawn.util;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestSimpleLinkedList {
	@Test
	public void testAdd(){
		SimpleLinkedList<Integer> list = new SimpleLinkedList<Integer>();
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
		
		assertTrue(list.removeFirst()==2);
		assertTrue(list.size()==2);
		assertTrue(list.getFirst()==3);
		assertTrue(list.removeFirst()==3);
		assertTrue(list.size()==1);
		assertTrue(list.getFirst()==4);
		assertTrue(list.removeFirst()==4);
		assertTrue(list.size()==0);
		assertTrue(list.getFirst()==null);
	}
	
	@Test
	public void testRemove(){
		SimpleLinkedList<Integer> list = new SimpleLinkedList<Integer>();
		assertTrue(list.size()==0);
		list.add(1);
		assertTrue(list.size()==1);
		list.remove(1);
		assertTrue(list.size()==0);
		
		list.add(2);
		assertTrue(list.size()==1);
		list.add(3);
		assertTrue(list.size()==2);
		list.add(4);
		assertTrue(list.size()==3);
		
		list.remove(2);
		assertTrue(list.size()==2);
		list.remove(3);
		assertTrue(list.size()==1);
		list.remove(4);
		assertTrue(list.size()==0);
	}
}
