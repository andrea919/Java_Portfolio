package edu.monmouth.CS205.library;

public class LibraryNode{
	
		LibraryItem info;
		LibraryNode link;
		
	  public LibraryNode(LibraryItem info) {
		  setInfo(info);
	  }
	  public void setInfo(LibraryItem info) {
		  this.info=info;
	  }
	  public LibraryItem getInfo() {
		  return info;
	  }
	  public void setNext(LibraryNode link) {
		  this.link=link;
	  }
	  public LibraryNode getNext() {
		  return link;
	  }
	  @Override
	  public String toString() {
		return "LibraryNode [info=" + info + ", link=" + link + "]";
	  }
	  
	}