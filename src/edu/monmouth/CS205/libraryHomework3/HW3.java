package edu.monmouth.CS205.libraryHomework3;

import java.io.*;
import java.util.*;

// import edu.monmouth.CS205.libraryHomework3.StatusType;

public class HW3 {

	public static void main(String[] args) {
		if(args.length != 3) {
			System.out.println("Usage: HW3 <outputFile> <stringFile> <libraryItemFile>");
			System.exit(20);
		} 
		String outputFileName = args[0];
        String stringFileName = args[1];
        String libraryFileName = args[2];
        
        initializedOutput(outputFileName);
        System.out.println("Standard out and error redirected successfully.");
        
        List<String> stringList = new LinkedList<>();
        List<LibraryItem> libraryItemList = new LinkedList<>();
               
        loadStringList(stringFileName, stringList);
        loadLibraryList(libraryFileName, libraryItemList);
         
        runAssignmentTestString(stringList);
        runAssignmentTestLibraryItem(libraryItemList);
        
	}
	
	private static void runAssignmentTestString(List<String> list) {
		System.out.println("\n--- TESTING STRING LIST ---");
		System.out.println("isEmpty method:" + list.isEmpty());
		
		// Standard Iterator
		System.out.println("\nremove method:" + list.remove(2));
		for(String string: list) {
			System.out.println(string);
		}
		
		System.out.println("\nsize method: " + list.size());
		
		System.out.println("add method: "+ list.add("March")); 
		
		// List Iterator
		ListIterator<String> listIter = list.listIterator();
		System.out.println("List Iterator:");
		System.out.println("\nForward:");
		while (listIter.hasNext()) {
		    System.out.println(listIter.next());
		}
		
		System.out.println("\nBackwards:");
		while (listIter.hasPrevious()) {
		    System.out.println(listIter.previous());
		}
		
		System.out.println("contains method:" + list.contains("March"));
		System.out.println("contains method:" + list.contains("February")); // removed
		
	}
	
	private static void runAssignmentTestLibraryItem(List<LibraryItem> list) {
		System.out.println("\n--- TESTING LIBRARY ITEM LIST ---");
		
		System.out.println("isEmpty: " + list.isEmpty());
		
		System.out.println("size: " + list.size());
		
		System.out.println("Removing item at index 1: " + list.remove(1));
        System.out.println("\nProving removal:");
        
        for (LibraryItem item : list) {
            System.out.println(item);
        }
        
        try {
        		Book newBook = new Book("J.K. Rowling", "Harry Potter", 300, StatusType.ONSHELF);
            list.add(newBook);
            System.out.println("\nAdded book: "+ newBook.toString());
        } catch (BookException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
        
        ListIterator<LibraryItem> iter = list.listIterator();
        System.out.println("\nForward: ");
        while(iter.hasNext()) {
        		System.out.println(iter.next());
        }
        System.out.println("\nBackwards: ");
        while(iter.hasPrevious()) {
        		System.out.println(iter.previous());
        }
        
        Book existingBook = null;
        try {
            existingBook = new Book("Harper Lee", "To Kill a Mockingbird", 320, StatusType.ONSHELF);
        } catch (BookException e) { 
        	e.printStackTrace(); 
        	}
        System.out.println("\nContains existing book? " + list.contains(existingBook));
        
        Book fakeBook = null;
        try {
            fakeBook = new Book("Fake", "Fake", 10, StatusType.ONSHELF);
        } catch (BookException e) { e.printStackTrace(); }
        
        System.out.println("Contains fake book? " + list.contains(fakeBook));
        
        Journal existingJournal = null;
        try {
            existingJournal = new Journal("Science", "American Association for the Advancement of Science", 1, StatusType.ONSHELF);
        } catch (JournalException e) { e.printStackTrace(); }
        
        System.out.println("\nRemoving existing Journal: " + list.remove(existingJournal));
        
        Journal fakeJournal = null;
        try {
            fakeJournal = new Journal("Vogue", "Someone", 99, StatusType.ONSHELF);
        } catch (JournalException e) { e.printStackTrace(); }
        
        System.out.println("Removing non-existing Journal: " + list.remove(fakeJournal));
        
        // Final proof
        System.out.println("\nFinal List Proof:");
        for(LibraryItem item : list) {
        		System.out.println(item);
        }
	}
	
	private static void loadLibraryList(String libraryFileName, List<LibraryItem> libraryItemList) {
		try {
    		BufferedReader in = new BufferedReader(new FileReader(libraryFileName));
    		
    		try {
    			String text;
    			while((text = in.readLine())!= null) {
    				String[] results = text.split(LibraryItemConstants.DELIMITER);
    				if(results.length == LibraryItemConstants.REQUIREDNUM) {
    					
        				if(results[0].equals(LibraryItemConstants.JOURNALITEM)) {
        					// Journal(String title, String editor, int volume, StatusType status)
        					try {
        						int volume = Integer.valueOf(results[3]);
        						StatusType status = StatusType.valueOf(results[4]);
							Journal j = new Journal(results[1], results[2], volume, status);
							libraryItemList.add(j);
							
	        					} catch(NumberFormatException e) {
	        						System.out.println("Cannot convert the volume. "+ e);
	        					} catch(IllegalArgumentException e) {
	        						System.out.println("Cannot convert the status. "+ e);
	        					} catch (JournalException e) {
									System.out.println("Cannot create the Journal. "+ e);
									e.printStackTrace();
							}
        					
        				} else if (results[0].equals(LibraryItemConstants.BOOKITEM)) {
        					// Book(String author, String title, int pages, StatusType status)
        					try {
        						int pages = Integer.valueOf(results[3]);
        						StatusType status = StatusType.valueOf(results[4]);
        						Book b = new Book(results[2], results[1], pages, status);
        						libraryItemList.add(b);
        						
        					} catch(NumberFormatException e) {
        						System.out.println("Cannot convert the pages. "+ e);
        					} catch(IllegalArgumentException e) {
        						System.out.println("Cannot convert the status. "+ e);
        					} catch (BookException e) {
        						System.out.println("Cannot create the Book. "+ e);
							e.printStackTrace();
        					}
        				}
    				}
    			}
 
    		}catch(IOException e) {
    			System.out.println("failed");
    			e.printStackTrace();
    			System.exit(20);
    		} 
    		
	    }catch(FileNotFoundException e) {
	    		System.out.println("Cannot find "+libraryFileName);
	    }
	} //loadLibraryList
	
	
	private static void loadStringList(String stringFileName, List<String> stringList) {
		try {
        	
    		BufferedReader in = new BufferedReader(new FileReader(stringFileName));
    		String line;
			try {
				while((line = in.readLine())!= null) {
					stringList.add(line);
				}
			} catch (IOException e) {
				System.out.println("failed");
    				e.printStackTrace();
    				System.exit(20);
    			}
        }catch(FileNotFoundException e) {
    		System.out.println("Cannot find "+stringFileName);
        }
	} //loadStringList
	
	private static void initializedOutput(String file) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream(file));
			
			System.setErr(out);
			System.setOut(out);
		} catch(FileNotFoundException e) {
			System.out.println("Could not create output file: "+file);
			e.printStackTrace();
			System.exit(20);
		}
	}
}
