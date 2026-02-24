package edu.monmouth.CS205L.linkedlists;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MyLinkedList {
    public static void main(String[] args) {
    	
        LinkedList<String> list = new LinkedList<String>();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while(running) {
            System.out.println("Select an option:"
                    + "\n1:Add Item to the List"
                    + "\n2:Remove Item from the List"
                    + "\n3:Print The List"
                    + "\n4:Exit the Program");

            String s = sc.nextLine();
            int answer = Integer.parseInt(s);

            switch(answer) {
                case 1:
                    System.out.println("Enter the item to add to the list: ");
                    String item = sc.nextLine();
                    list.add(item);
                    break;
                case 2:
                    try {
                        list.removeLast();
                    } catch(NoSuchElementException e) {
                        System.out.println("List is empty");
                    }
                    break;
                case 3:
                    System.out.println(list);
                    break;
                default:
                    running = false;
            } // switch
        }
    }
}