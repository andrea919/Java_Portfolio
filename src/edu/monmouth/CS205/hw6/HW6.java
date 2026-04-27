package edu.monmouth.CS205.hw6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;


public class HW6 {

	public static void main(String[] args) {
		verifyCommLine(args);
		String propFile = extractFileName(args);
		
		Properties p = new Properties();
		String redirectFileName = extractRedirectFile(p, propFile);
		// redirectFile(redirectFileName);
		
		String libraryItemFileName = parseStringInProperty(p, HW6Constants.LIBRARY_ITEM_FILE_NAME);
		// char delimiter = parseCharInProperty(p, HW6Constants.DELIMITER_NAME);
		String delimiter = parseStringInProperty(p, HW6Constants.DELIMITER_NAME);
		
		Set<Book> bookTreeSet = new TreeSet<>();
		Set<Journal> journalTreeSet = new TreeSet<>();
		populateTreeSet(bookTreeSet, journalTreeSet, libraryItemFileName, delimiter);
		System.out.println("Book count: " + bookTreeSet.size());
	
		printTreeSet(bookTreeSet);
		
	}

	

	public static void verifyCommLine(String[] args) {
		if(args.length == 1) return;
		// if not return
		System.err.println("Command line must be exactly 1.");
		System.exit(HW6Constants.EXIT_INVALID_ARGS); 
	} // verifyCommLine
	
	public static String extractFileName(String[] args) {
		return args[0];
	} // extractFileName
	
	public static String extractRedirectFile(Properties p,String propFile) {
		try(BufferedReader in = new BufferedReader(new FileReader(propFile))) {
			p.load(in);
			isKeyInPropertiesFile(p, HW6Constants.LOG_FILE_NAME);
			String fileName = p.getProperty(HW6Constants.LOG_FILE_NAME);
			return fileName;
			
		} catch (FileNotFoundException e) {
			System.err.println("File not found. Exiting...");
			System.exit(HW6Constants.EXIT_VALUE_FILE_NOT_FOUND);
		}catch (IOException e) {
			System.err.println("Error while reading file. Exiting...");
			System.exit(HW6Constants.EXIT_VALUE_READING_FILE); 
		}
		return null;
	} // extractRedirectFile
	
	private static void isKeyInPropertiesFile(Properties p, String expectedKeyName) {
		if(p.getProperty(expectedKeyName) == null) {
			System.err.println("Property name not found. Exiting...");
			System.exit(HW6Constants.EXIT_VALUE_PROPERTY_NOT_FOUND); 
		}
	} // isKeyInPropertiesFile

	public static void redirectFile(String name) {
		try {
			PrintStream out = new PrintStream(name);
			System.setOut(out);
			System.setErr(out);
			
		} catch (FileNotFoundException e) {
			System.err.println("Error while creating the file. Exiting...");
			System.exit(HW6Constants.EXIT_VALUE_CREATING_FILE); 
		}
	} // redirectFile
	
	private static String parseStringInProperty(Properties p, String keyName) {
		isKeyInPropertiesFile(p, keyName);
		return p.getProperty(keyName);
		
	} // parseStringInProperty
	
	private static char parseCharInProperty(Properties p, String keyName) {
	    isKeyInPropertiesFile(p, keyName);
	    String value = p.getProperty(keyName);
	    if (value.isEmpty()) {
	        System.err.println("Property " + keyName + " is empty. Exiting...");
	        System.exit(HW6Constants.EXIT_VALUE_PROPERTY_NOT_FOUND);
	    }
	    return value.charAt(0);
	} // parseCharInProperty
	
	private static void populateTreeSet(Set<Book> bookTreeSet, Set<Journal> journalTreeSet, String fileName, String delimiter) {
		try(BufferedReader in = new BufferedReader(new FileReader(fileName))) {
			String line;
			while((line = in.readLine())!= null) {
				String[] parts = line.strip().split(delimiter);
			    // System.out.println("Parts length: " + parts.length + " | Parts: " + java.util.Arrays.toString(parts));  // debug
				if(validateParts(parts)) {
					// initialize only if the letter is correct
					if((parts[0].equals(HW6Constants.EXPECTED_BOOK_INITIAL)) || 
							(parts[0].equals(HW6Constants.EXPECTED_JOURNAL_INITIAL))) {
						initializeObject(parts, bookTreeSet, journalTreeSet);
					}
				}
			}
		} catch (FileNotFoundException e) {
		    System.err.println("File not found. Exiting...");
		    System.exit(HW6Constants.EXIT_VALUE_FILE_NOT_FOUND);
		} catch (IOException e) {
		    System.err.println("Error reading file. Exiting...");
		    System.exit(HW6Constants.EXIT_VALUE_READING_FILE);
		}
		
	} // populateTreeSet

	private static void initializeObject(String[] parts, Set<Book> bookTreeSet, Set<Journal> journalTreeSet) {
		try {
			if(parts[0].equals(HW6Constants.EXPECTED_BOOK_INITIAL)) {
				Book b = new Book(parts[2], parts[1], Integer.parseInt(parts[3]), StatusType.valueOf(parts[4]));
				bookTreeSet.add(b);
			} else if (parts[0].equals(HW6Constants.EXPECTED_JOURNAL_INITIAL)){
				Journal j = new Journal(parts[1], parts[2], Integer.parseInt(parts[3]), StatusType.valueOf(parts[4]));
				journalTreeSet.add(j);
			}
		} catch (BookException e) {
	        System.err.println("Invalid book data: " + e.getMessage());
	    } catch (JournalException e) {
	        System.err.println("Invalid journal data: " + e.getMessage());
	    } catch (NumberFormatException e) {
	        System.err.println("Invalid number format: " + e.getMessage());
	    } catch (IllegalArgumentException e) {
	        System.err.println("Invalid status value: " + e.getMessage());
	    }
	} // initializeObject

	private static boolean validateParts(String[] parts) {
		if(parts.length == HW6Constants.EXPECTED_PARTS_OBJECT) return true;
		return false;
	} // validateParts
	
	private static void printTreeSet(Set<Book> bookTreeSet) {
		
		for(LibraryItem item: bookTreeSet) {
			System.out.println(item.toString());
		}
	} // printTreeSet
	
}
