package edu.monmouth.CS205.hw5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;

public class HW5 {
	
	public static class HW5Constants{
		private HW5Constants() {}
		public static final int EXIT_VALUE=20;
		public static final int EXIT_VALUE_COMM_LINE=19;
		public static final int EXIT_VALUE_KEY_NOT_FOUND=18;
		public static final int EXIT_VALUE_CREATING_FILE=17;
		public static final int EXIT_VALUE_FILE_NOT_FOUND=16;
		public static final int EXIT_VALUE_READING_FILE=15;
		public static final int EXIT_VALUE_INVALID_RANGE=14;
		
		public static final int EXPECTED_COMM_LINE=1;
		public static final int START_INDEX=0;
		public static final String EXPECTED_FILE_NAME="log_file_name";
		public static final String EXPECTED_RANGE_KEY="number_range";
		public static final String DELIMITER=",";
	}

	public static void main(String[] args) {
		initialization(args);
		Properties p = new Properties();
		String stdOut = retrieveOutput(args, p);
		redirect(stdOut);
		Map<String, String[]> map = parseValues(p);
		palindromeWords(map);
		palindromeNumbers(p);
		
	}
	
	public static void initialization(String[] args) {
		if(args.length != HW5Constants.EXPECTED_COMM_LINE) {
			System.err.println("You must input one command line. Exiting...");
			System.exit(HW5Constants.EXIT_VALUE_COMM_LINE);
		}
	} // initialization
	
	public static String retrieveOutput(String[] args, Properties p) {
		String readFile = args[HW5Constants.START_INDEX]; 
		try {
			BufferedReader in = new BufferedReader(new FileReader(readFile));
			p.load(in);
			p.list(System.out);
			isKeyInSet(HW5Constants.EXPECTED_FILE_NAME, p);
			String stdOut = p.getProperty(HW5Constants.EXPECTED_FILE_NAME);
			if(stdOut == null) {
				System.err.println("Key not found. Exiting...");
				System.exit(HW5Constants.EXIT_VALUE_KEY_NOT_FOUND); 
			}
			return stdOut;
		// If it catches an exception, I should still close p.close()
		} catch (FileNotFoundException e) {
			System.err.println("File not found. Exiting...");
			System.exit(HW5Constants.EXIT_VALUE_FILE_NOT_FOUND);
		}catch (IOException e) {
			System.err.println("Error while reading file. Exiting...");
			System.exit(HW5Constants.EXIT_VALUE_READING_FILE); 
		}
		return null;
	} // retrieveOutput
	
	public static void isKeyInSet(String keyName, Properties p) {
		Set<String> keys = p.stringPropertyNames();
		if(keys.contains(keyName)) { 
			return;
		}
		System.err.println("Key does not exist. Exiting");
		System.exit(HW5Constants.EXIT_VALUE_KEY_NOT_FOUND); 
		
 	} // isKeyInSet
	
	public static void redirect(String destinationPath) {
		try {
			PrintStream out = new PrintStream(destinationPath);
			System.setOut(out);
			System.setErr(out);
			
		} catch (FileNotFoundException e) {
			System.err.println("Error while creating the file. Exiting...");
			System.exit(HW5Constants.EXIT_VALUE_CREATING_FILE); 
		}
	} // redirect
	
	public static Map<String, String[]> parseValues(Properties p) {
		Set<String> keys = p.stringPropertyNames();
		Map<String, String[]> map = new HashMap<>();
		String temp;
		String[] arr;
		for (String s : keys) {
			if (s.equals(HW5Constants.EXPECTED_FILE_NAME) || s.equals(HW5Constants.EXPECTED_RANGE_KEY)) {
				continue;
			}
			temp = p.getProperty(s);
			if(temp==null) continue;
			arr = temp.trim().split(HW5Constants.DELIMITER); 
			map.put(s, arr);
		}
		return map;
	} // parseValues
	
	public static void palindromeWords(Map<String, String[]> map){
		Set<String> keys = map.keySet();
		for(String keyS: keys) {
			String[] values = map.get(keyS);
			for(String valueS: values ) {
				if(isWordPalindrome(valueS)) {
					System.out.println("Word " +valueS+" is palindrome.");
				} else {
					System.out.println("Word " +valueS+" is not palindrome.");
				}
			}
		}
	} //accessSingleWords
	
	public static Boolean isWordPalindrome(String word) {
		List<Character> list = new LinkedList<>();
		Stack<Character> stack = new Stack<>();
		
		for(int i=0; i<word.length(); i++) {
			list.add(word.charAt(i));
			stack.push(word.charAt(i));
		}
		
		StringBuilder reverseWord = new StringBuilder();
		int counter = HW5Constants.START_INDEX;
		while(!stack.isEmpty()) {
			char lChar = list.get(counter++);
			char sChar = stack.pop();
			if(lChar != sChar) return false;
			reverseWord.append(sChar);
		}
		return word.equals(reverseWord.toString());
		
	} // isWordPalindrome
	
	public static void palindromeNumbers(Properties p) {
		isKeyInSet(HW5Constants.EXPECTED_RANGE_KEY, p);
		String rawRange = p.getProperty(HW5Constants.EXPECTED_RANGE_KEY);
		if (rawRange != null) {
		    String[] parts = rawRange.split(HW5Constants.DELIMITER);
		    if (parts.length == 2) {
		        int start = Integer.parseInt(parts[0].trim());
		        int end = Integer.parseInt(parts[1].trim());
		        printPalindromeNumbers(start, end);
		    } else {
		    		System.out.println("Invalid range format.");
		    		System.exit(HW5Constants.EXIT_VALUE_INVALID_RANGE); 
		    }
		}
		
	} // palindromeNumbers
	
	public static void printPalindromeNumbers(int num, int end) {
		String numString;
		System.out.println("Printing palindrome Numbers... ");
		while(num <= end) {
			numString = String.valueOf(num);
			if(isWordPalindrome(numString)) {
				System.out.print(num+" ");
			} 
			num++;
		}
	} // printPalindromeNumbers
}
