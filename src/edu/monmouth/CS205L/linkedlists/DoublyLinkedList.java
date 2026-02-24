package edu.monmouth.CS205L.linkedlists;

import java.util.Scanner;

public class DoublyLinkedList {

	public static void main(String[] args) {

		DoublyLinkedList list = new DoublyLinkedList();
		Scanner sc = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
			System.out.println("\n" + list.displayList());
	        
	        System.out.println("Select an option:");
	        System.out.println("1: Add to Top"
	        		+ "\n2: Add to End"
	        		+ "\n3: Add After Current");
	        System.out.println("4: Remove Current"
	        		+ "\n5: Move Forward"
	        		+ "\n6: Move Backward"
	        		+ "\n7: Exit");
	        
	        try {
	        		int choice = Integer.parseInt(sc.nextLine());
	        		switch (choice) {
	                case 1:
	                    System.out.print("Enter item: ");
	                    list.addFirst(sc.nextLine());
	                    break;
	                case 2:
	                    System.out.print("Enter item: ");
	                    list.addLast(sc.nextLine());
	                    break;
	                case 3:
	                    System.out.print("Enter item: ");
	                    list.addCurrent(sc.nextLine());
	                    break;
	                case 4:
	                    list.removeCurrent();
	                    break;
	                case 5:
	                    list.moveForward();
	                    break;
	                case 6:
	                    list.moveBackwards();
	                    break;
	                case 7:
	                    running = false;
	                    System.out.println("Exiting Program 3...");
	                    break;
	                default:
	                    System.out.println("Invalid option.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: Please enter a valid number.");
	        }
        } // while
        
	} // main
	
	private NodeDouble head;
    private NodeDouble tail;
    private NodeDouble currentView;
    
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.currentView = null;
    }
    
    // methods
   
    public String displayList() {
    	if (head == null) return "Empty";
        
        String output = "NULL <-> ";
        NodeDouble temp = head;
        
        while (temp != null) {
        		if (temp == currentView) {
        			// To make the current view bright
        			output += "[" + temp.getData() + "]";
        			} else {
        				output += temp.getData();
        			} // if
        		temp = temp.getNext();
        		
        		if (temp != null) {
        			output += " <-> ";
        		} // if
        		
        	} // while
     	
    		output += " <-> NULL";
		return output;
    } // displayList
    
    public void addLast(String item) {
    		NodeDouble newNode = new NodeDouble(item);
    		
    		if (head == null) {
    			head = newNode;
    			tail = newNode;
    			currentView = newNode;
    		}else {
    			tail.setNext(newNode);
    			newNode.setPrev(tail);
    			
    			// Update the tail
    			tail = newNode;
    		}
    		
    		currentView = newNode;
    		System.out.println("Last item: "+tail.getData());
    		
    } // addLast
    
    public void addFirst(String item) {
    	NodeDouble newNode = new NodeDouble(item);
    	
    		if (head == null ) {
    			head = newNode;
    			tail = newNode;
    			currentView = newNode;
    		} else {
    			newNode.setNext(head);
    			head.setPrev(newNode);
    			
    			head = newNode;
    		}

        	currentView = newNode;
        	System.out.println("First item: "+head.getData());
    		
    } // addFirst
    
    public void addCurrent(String item) {
    		NodeDouble newNode = new NodeDouble(item);
    		
    		if(currentView == null) {
    			addFirst(item);
    		} else if (currentView.getNext() == null ) {
    			addLast(item);
    		} else {
    			NodeDouble nextNode = currentView.getNext();
    			NodeDouble prevNode = currentView;
    			
    			newNode.setPrev(prevNode);
    			newNode.setNext(nextNode);
    			
    			prevNode.setNext(newNode);
    			nextNode.setPrev(newNode);
    			
    			currentView = newNode;
    		}
    }
    
    
    public void removeCurrent() {
    	if (currentView == null) {
            System.out.println("Nothing to remove.");
            return;
        }

        NodeDouble nodeToRemove = currentView;
        
        // To remove the head
        if (nodeToRemove == head) {
        		head = nodeToRemove.getNext();
        		if(head != null) {
        			head.setPrev(null);
        		} else {
        			tail = null;
        		}
        		currentView = head;
        }
        
        // To remove the tail
        else if (nodeToRemove == tail) {
        		tail = nodeToRemove.getPrev();
	        if (tail != null) {
	        		tail.setNext(null);
	        } else {
	        		head = null;
	        }
	        currentView = tail;
        }
        
        else {
        		nodeToRemove.getPrev().setNext(nodeToRemove.getNext());
        		nodeToRemove.getNext().setPrev(nodeToRemove.getPrev());
        		
        		currentView = nodeToRemove.getNext();
        }

        System.out.println("Removed item: " + nodeToRemove.getData());
    }
    
    public void moveForward() {
    		if (currentView != null && currentView.getNext() != null) {
    			currentView = currentView.getNext();
    			System.out.println("Moved forward to: "+currentView.getData());
    		} else {
        		System.out.println("Cannot move forward!");
    		}
    }
    
    public void moveBackwards() {
    	if (currentView != null && currentView.getPrev() != null) {
			currentView = currentView.getPrev();
			System.out.println("Moved backwards to: "+currentView.getData());
		} else {
    		System.out.println("Cannot move backwards!");
		}
    }
    
} // class
