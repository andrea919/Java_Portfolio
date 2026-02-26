package edu.monmouth.CS205L.general;
import java.util.LinkedList;

public class LinkedListStack<E> implements Stack<E> {
	int capacity = 7;
	private LinkedList<E> list = new LinkedList<>();
	
	@Override
	public int size() {
		if(list.isEmpty()) {
			return 0;
		}
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public void push(E e) {
		list.addFirst(e);
	}

	@Override
	public E pop() {
		if (isEmpty()) return null;
		return list.removeFirst();
	}

	@Override
	public E top() {
		if (isEmpty()) return null;
		return list.getFirst();
		
	}

}
