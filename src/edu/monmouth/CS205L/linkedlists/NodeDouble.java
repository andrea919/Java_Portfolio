package edu.monmouth.CS205L.linkedlists;

public class NodeDouble {
	String data;
	NodeDouble prev, next;
	
	public NodeDouble(String data) {
		this.data = data;
		this.next = null;
		this.prev = null;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public NodeDouble getNext() {
		return next;
	}

	public void setNext(NodeDouble next) {
		this.next = next;
	}

	public NodeDouble getPrev() {
		return prev;
	}

	public void setPrev(NodeDouble prev) {
		this.prev = prev;
	}

	
	
}
