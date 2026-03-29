package edu.monmouth.CS205.hw4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;


public class HW4 {
	public static void main(String[] args) {
		verifyFiles(args);
		
		String output = args[0];
		String data = args[1];
		
		initializeOutput(output);
		verifyEquality();
		
		Set<LibraryItem> librarySet = new HashSet<LibraryItem>();
		int totalAttempts = readLines(data, librarySet);
		
		iteratorMethod(librarySet);
		areAllObjectAdded(librarySet, totalAttempts);
		
		validation(librarySet);
		
	} // main
	
	public static void verifyFiles(String[] args) {
		try {
			String stdFile = args[0];
			String data = args[1];
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("No command line. Try again!");
			System.exit(20);
		}
	} // getFiles
	
	public static void initializeOutput(String output) {
		try {
			PrintStream out = new PrintStream(output);
			System.setErr(out);
			System.setOut(out);
		} catch(FileNotFoundException e) {
			System.out.println("Could not create output file: "+output);
			e.printStackTrace();
			System.exit(20);
		}
	} // initializeOutput
	
	public static void verifyEquality() {
		try {
			// Equivalent Books
			Book b1 = new Book("F. Scott Fitzgerald", "The Great Gatsby", 180, StatusType.ONSHELF);
			Book b2 = new Book("F. Scott Fitzgerald", "The Great Gatsby", 180, StatusType.BORROWED);
			System.out.println("Are the books equal? " + b1.equals(b2));
			System.out.println("b1 HashCode: " + b1.hashCode());
			System.out.println("b2 HashCode: " + b2.hashCode());
			
			// Non-Equivalent Books
			Book b3 = new Book("F. Scott Fitz", "Gatsby", 180, StatusType.ONSHELF);
			Book b4 = new Book("Scott Fitzgerald", "Great", 180, StatusType.BORROWED);
			System.out.println("\nAre the books equal? " + b3.equals(b4));
			System.out.println("b3 HashCode: " + b3.hashCode());
			System.out.println("b4 HashCode: " + b4.hashCode());
			
			// Equivalent Journals
			Journal j1 = new Journal("Nature", "Nature Publishing", 500, StatusType.ONSHELF);
			Journal j2 = new Journal("Nature", "Nature Publishing", 500, StatusType.BORROWED);
			System.out.println("\nAre the journals equal? " + j1.equals(j1));
			System.out.println("j1 HashCode: " + j1.hashCode());
			System.out.println("j2 HashCode: " + j2.hashCode());
			
			// Non-Equivalent Books
			Journal j3 = new Journal("Nature", "Nature PublishingS", 45, StatusType.MISSING);
			Journal j4 = new Journal("Movies", "Movies 2026", 55, StatusType.BORROWED);
			System.out.println("\nAre the journals equal? " + j3.equals(j4));
			System.out.println("j3 HashCode: " + j3.hashCode());
			System.out.println("j4 HashCode: " + j4.hashCode());
			
			
		} catch (BookException e) {
			System.out.println("Could not create book.");
			e.printStackTrace();
		}
		
		catch(JournalException e) {
			System.out.println("Could not create journal.");
			e.printStackTrace();
		}
	} // verifyEquality
	
	public static int readLines(String file, Set<LibraryItem> set) {
		try {
			BufferedReader in = new BufferedReader( new FileReader(file));
			
			int countItems=0;
			String text;
			while((text = in.readLine()) != null) {
				try {
					String[] result = text.split(HW4Constants.DELIMITER);
					if(result.length == HW4Constants.MINREQUIRED) {
						// If it is a Journal
						if (result[0].equalsIgnoreCase(HW4Constants.JOURNALITEM)) {
							int volume = Integer.valueOf(result[3]);
							StatusType status = StatusType.valueOf(result[4]);
							Journal j = new Journal(result[1], result[2], volume, status);
							insertItem(j, set);// Add Journal to set
							countItems++;
							
						} else if (result[0].equalsIgnoreCase(HW4Constants.BOOKITEM)) {
							int pages = Integer.valueOf(result[3]);
							StatusType status = StatusType.valueOf(result[4]);
							Book b = new Book(result[2], result[1], pages, status);
							insertItem(b, set); // Add Book to set
							countItems++;
						}
					} 
				} catch(NumberFormatException e) {
					System.out.println("Cannot convert the volume. "+ e);
				} catch(IllegalArgumentException e) {
					System.out.println("Cannot convert the status. "+ e);
				} catch (JournalException e) {
					System.out.println("Cannot create the Journal. "+ e);
					e.printStackTrace();
				} catch (BookException e) {
					System.out.println("Cannot create the Book. "+ e);
				e.printStackTrace();
				}
			} // while loop
			
			return countItems;
			
		}catch(IOException e) {
			System.out.println("Failed to read lines.");
			e.printStackTrace();
			System.exit(20);
    		} 
		return 0;
	} // readLines
	
	public static void insertItem(LibraryItem item, Set<LibraryItem> set) {
		boolean added = set.add(item);
		System.out.println("Adding item: " + item.getTitle() + " | Added: " + added);
	} // insertItem
	
	public static void iteratorMethod(Set<LibraryItem> set) {
		Iterator <LibraryItem> it = set.iterator();
		
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	} // iteratorMethod
	
	public static boolean areAllObjectAdded(Set<LibraryItem> set, int totalAttempts) {
		System.out.println("Total valid objects read from file: " + totalAttempts);
		System.out.println("Actual objects in HashSet (unique): " + set.size());
		
		if (totalAttempts != set.size()) {
		    System.out.println("Some items were not added because they were duplicates.");
		}
		
		return totalAttempts == set.size();
	}
	
	public static void validation(Set<LibraryItem> set) {
		try {
			// Create 1 Book object you KNOW exists in the HashSet
			Book bookInSet = new Book("Harper Lee", "To Kill a Mockingbird", 320, StatusType.ONSHELF);
			// Create 1 Journal object you KNOW exists in the HashSet 
		    Journal journalInSet = new Journal("Science", "American Association for the Advancement of Science", 1, StatusType.ONSHELF);
		    // Create 1 Book object you KNOW IS NOT in the HashSet 
		    Book bookNotInSet = new Book("Dante Alighieri", "La Divina Commedia", 500, StatusType.ONSHELF);
		    // Create 1 Journal object you KNOW IS NOT in the HashSet
		    Journal journalNotInSet = new Journal("Focus", "Mondadori", 100, StatusType.ONSHELF);
		    
		    System.out.println("\nTESTING CONTAINS: ");
		    System.out.println("Contains Book (exists): " + set.contains(bookInSet)); // true
		    System.out.println("Contains Journal (exists): " + set.contains(journalInSet)); // true
		    System.out.println("Contains Book (not exists): " + set.contains(bookNotInSet)); // false
		    System.out.println("Contains Journal (not exists): " + set.contains(journalNotInSet)); // false
		    
		    System.out.println("\nTESTING REMOVE: ");
		    System.out.println("Removing Book (exists): " + set.remove(bookInSet)); // true
		    System.out.println("Removing Journal (exists): " + set.remove(journalInSet)); // true
		    System.out.println("Removing Book (not exists): " + set.remove(bookNotInSet)); // false
		    System.out.println("Removing Journal (not exists): " + set.remove(journalNotInSet)); // false
		    
		    System.out.println("\nFINAL INSPECTION: ");
		    iteratorMethod(set);
		    
		} catch (BookException e) {
		    System.err.println("Error creating book objects: " + e.getMessage());
		} catch (JournalException e) {
		    System.err.println("Error creating journal objects: " + e.getMessage());
		}
	 } // validation
	
}
