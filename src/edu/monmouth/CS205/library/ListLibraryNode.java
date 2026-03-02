package edu.monmouth.CS205.library;

public class ListLibraryNode {
	/* add a reference to a LibraryNode named head */
	private LibraryNode head;
	private int size;
	
	public ListLibraryNode() {
	this.head = null;
	this.size = 0;
	}
	
	public LibraryNode removeFirst() {
		if(head == null) {
			return null;
		}
		LibraryNode oldHead = head;
		head = head.getNext();
		size--;	
		
		return oldHead;
	}
	public LibraryNode first() {
		if(head == null) {
			return null;
		}
		return head;
	}
  	public LibraryNode last() {
  		if(head==null) {
  			return null;
  		}
  		LibraryNode current = head;
  		while(current.getNext() != null) {
  			current = current.getNext();
  		}
  		return current;
	}
	public void insert(LibraryItem element) {
		LibraryNode newNode = new LibraryNode(element);
		newNode.setNext(head);
		head = newNode;
		size++;
	}
	public void insertEnd(LibraryItem element) {
		if(head==null) {
			LibraryNode newNode = new LibraryNode(element);
			head = newNode;
			return;
  		}
  		LibraryNode current = head;
  		LibraryNode newNode = new LibraryNode(element);
  		
  		while(current.getNext() != null) {
  			current = current.getNext();
  		}
  		current.setNext(newNode);
  		size++;
	}
	public boolean isEmpty() { 
		return head==null;
	}
	public int size() {
		return size;
	}
	public void clear() {
		head = null;
	}
	@Override
  	public String toString() {
		StringBuilder result = new StringBuilder();
		if(head==null) return "";
		
		LibraryNode current = head;
		result.append("\nNode: "+current.info);
		
		while(current.getNext() != null) {
			current = current.getNext();
			result.append("\nNode: "+current.info);
  			
  		}
		
		return result.toString();
	}
}
