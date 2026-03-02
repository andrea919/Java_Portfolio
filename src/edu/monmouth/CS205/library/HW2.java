package edu.monmouth.CS205.library;

import edu.monmouth.CS205.library.Book;
import edu.monmouth.CS205.library.BookException;
import edu.monmouth.CS205.library.Journal;
import edu.monmouth.CS205.library.JournalException;
import edu.monmouth.CS205.library.LibraryItem;
import edu.monmouth.CS205.library.ListLibraryNode;
import edu.monmouth.CS205.library.StatusType;

public class HW2 {

	public static void main(String[] args) {
		ListLibraryNode library = new ListLibraryNode();
		// verify these methods work on an empty list
		System.out.println("First: " + library.first());
		System.out.println("Removed first: " + library.removeFirst());
		System.out.println("Last: " + library.last());
		System.out.println("Entire list: \n" + library);
		
		// instantiate Book and Journal objects and insert them into the list
		LibraryItem journal1=null, journal2=null;
		LibraryItem book1=null, book2=null, book3=null, book4=null;
		try {
			
			book1 = new Book("To Kill a Mockingbird", "Harper Lee", 320, StatusType.ONSHELF);
			book2 = new Book("For Whom the Bell Tolls","Ernest Hemingway", 155, StatusType.ONSHELF);
			book3 = new Book("Undaunted Courage", "Stephen Ambrose", 498, StatusType.ONSHELF);
			book4 = new Book("A Christmas Carol", "Charles Dickens", 198, StatusType.MISSING);
			Book badBook = new Book("", null, -198, StatusType.MISSING);  // will throw BookException
		} catch(BookException be) {
			System.out.println(be.getMessage());
		}
		try {
			journal1 = new Journal("Science", "American Association for the Advancement of Science", 1, StatusType.ONSHELF);
			journal2 = new Journal("Proceedings of the National Academy of Sciences", "National Academy of Sciences", 2, StatusType.ONSHELF);
		} catch(JournalException je) {
			System.out.println(je.getMessage());
		}
		
		library.insert(journal1);
		library.insert(book1);
		library.insert(book2);
		library.insert(book3);
			
		System.out.println("After inserting LibraryItems: \n" + library);
		
		library.insertEnd(book4);
		 
		System.out.println("After adding another book to end: \n" + library);
		
		System.out.println("First: " + library.first());
		
		System.out.println("Last: " + library.last());

		System.out.println("Removed first: " + library.removeFirst());
		System.out.println("list after removing first: \n" + library);

		library.insert(journal2);
		System.out.println("list after adding another journal: \n" + library);
	}
}
