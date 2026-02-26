package edu.monmouth.CS205L.general;

public class ArrayStack<E> implements Stack<E> {
	private int capacity = 7;
	private int location = -1;
	private E[] data;
	
	
	
	public ArrayStack(int capacity, int location, E[] data) {
		this.capacity = capacity;
		this.data = (E[]) new Object[capacity];
	}

	@Override 
	public boolean isEmpty() {
		return location == -1;
	}
	
	@Override 
	public int size() {
		return location+1;
	}
	
	@Override 
	public void push(E e) {
		if (size()== capacity) {
			System.out.println("Stack Full!");
		}
		data[++location] = e; // arithmetic first and then index
	}
	
	@Override 
	public E top() {
		if(isEmpty()) {
			return null;
		}
		return data[location];
	}
	
	@Override 
	public E pop() {
		if(isEmpty()) {
			return null;
		}
		E answer = data[location];
		data[location] = null;
		location--; 
		return answer;
	}
	
	
}
