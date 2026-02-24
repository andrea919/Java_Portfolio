package edu.monmouth.CS205L.linkedlists;

import java.util.Scanner;

public class SinglyLinkedList {
	private Node head;
	Node current;
	Node currentView;
		
	
	public SinglyLinkedList() {
		this.head = null;
	}
	
	public static void main(String[] args) {
		SinglyLinkedList list = new SinglyLinkedList();
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		
		while(flag) {
			if (list.currentView != null) {
                System.out.println("CURRENT VIEW: [" + list.currentView.data + "]");
            } else {
                System.out.println("CURRENT VIEW: [Empty List]");
            }
            
            System.out.println("Select an option:");
            System.out.println("1: Add to Top"
            		+ "\n2: Add to End"
            		+ "\n3: Remove Top"
            		+ "\n4: Remove End");
            System.out.println("5: Move Forward"
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
                        list.removeFirst();
                        break;
                    case 4:
                        list.removeLast();
                        break;
                    case 5:
                        list.moveForward();
                        break;
                    case 6:
                        list.moveBackward();
                        break;
                    case 7:
                        flag = false;
                        System.out.println("Exiting Program 2...");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
            }
        } // while
        sc.close();
	} // main
	
	public void addFirst(String item) {
		// Create a new node containing the item
		Node newNode = new Node(item);
		newNode.next = head;
		head = newNode;
		
		if (currentView == null) {
			currentView = head;
		}
		
		System.out.println("First item: "+head.data);
		
	}
	
	public void addLast(String item) {
		Node newNode = new Node(item);
		
		if (head == null) {
			head = newNode;
			return;
		}
		
		current = head;
		while (current.next != null) {
			current = current.next;
		} 
		
		current.next = newNode;
		System.out.println("Last item: "+newNode.data);
		currentView = newNode;

	}
	
	public void removeFirst() {
		if(head == null) {
			System.out.println("List is empty");
			return;
		}
		
		head = head.next;
		// Garbage collection

		currentView = head;
		System.out.println("First Item: "+ currentView.data);
		
		// Check now the list for the pointer 
		if (head == null) {
			currentView = null;
		} else {
			currentView = head;
		}
		
	}
	
	public void removeLast() {
		
		// Empty list
		if(head== null) {
			System.out.println("List is empty");
			return;
			
		// Single item check
		} else if (head.next == null) {
			System.out.println("Removed: "+head.data);
			head = null;
			return;
		}
		
		// Check the nodes
		current = head;
		while (current.next.next != null) {
			current = current.next;
		} 
		System.out.println("Removed: "+current.next.data);
		current.next = null;
		// Garbage collection
		
		currentView = current;
		System.out.println("Last item: "+current.data);
		
	}
	
	public void moveForward() {
		if (currentView != null && currentView.next != null) {
	        currentView = currentView.next;
	        System.out.println("Moved forward to: " + currentView.data);
	    } else {
	        System.out.println("Cannot move forward.");
	    }
	}
	
	public void moveBackward() {
		if (currentView == head || head == null) {
			System.out.println("Cannot move backwards.");
			return;
		}
		
		current = head;	
		while (current != null && current.next != currentView) {
			current = current.next;
	    }
		if (current != null) {
	        currentView = current;
	     // Output the current view
	        System.out.println("Moved backwards to: " + currentView.data);
	    }

	}
	
}

