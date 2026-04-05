package edu.monmouth.CS205L.boggle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Boggle {

	public static void main(String[] args) {
		
		int n = getNumberN(args);
		
		char[][] boggleBoard = new char[n][n];
		boolean[][] isExplored = new boolean[n][n];
		generateBoard(n, boggleBoard);
		
		// Create Dictionary thru Tree
		Tree dictionaryTree = new Tree();
		dictionaryTree.loadDictionary(BoggleConstants.INPUTFILE);
		
		ArrayList<String> foundWords = new ArrayList<String>();
		recursiveSearch("", 0, 0, 0, boggleBoard, isExplored, dictionaryTree, foundWords );
		
		
	
	} // main
	
	public static void recursiveSearch(String currentWord,int row, int col, char[][] board, boolean[][] isExplored, Tree tree, ArrayList<String> foundWords) {
		
		if(isOutOfBounds(board, row, col) || 
				isVisited(isExplored, row, col)){
			return;
		} 
		
		String nextWord = currentWord + board[row][col];
		isExplored[row][col] = true;
		if (!(tree.isPrefixValid(nextWord))) {
			return;
		} else if(tree.isWord(nextWord)) {
			foundWords.add(nextWord);
		}
		
		// call recursion for next cells
		
		// reset for other combinations
		isExplored[row][col] = false;
	} // recursiveSearch
	
	public static void populateHashMap(HashSet<String> dictionary) {
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(BoggleConstants.INPUTFILE));
			String line;
			while((line = in.readLine()) != null) {
				String word = line.trim().toLowerCase();
				dictionary.add(word);
			}
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static int getNumberN(String[] args) {
		int n;
		try {
			n = Integer.parseInt(args[0]);
			if(n<BoggleConstants.EXPECTEDLINES) {
				System.out.println("Invalid command line. The default board will be 5x5!");
				n = BoggleConstants.DEFAULTBOARD;
			}
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("No command line. The default board will be 5x5!");
			n = BoggleConstants.DEFAULTBOARD;
		}catch(NumberFormatException e) {
			System.out.println("Invalid command line. The default board will be 5x5!");
			n = BoggleConstants.DEFAULTBOARD;
		}
		return n;
	} // getNumberN
	
	public static void generateBoard(int n, char[][] board) {
		Random r = new Random();
		// r.nextInt(26); first invalid number is 26
		
		for(int i=0; i<n; i++) { // rows
			for(int j=0; j<n; j++) { // columns
				board[i][j] = (char) (r.nextInt(BoggleConstants.ALPHABETNUM)+BoggleConstants.ASCIIADDITION);
			} // inner for 
		} // outer for
		printBoard(n, board);
	} // generateBoard
	
	public static void printBoard(int n, char[][] board) {
		for(int i=0; i<n; i++) { // rows
			System.out.println();
			for(int j=0; j<n; j++) { // columns
				
				System.out.print("| "+board[i][j]+" ");
				
				if (j == board.length-1){
					System.out.print("|");
				} // if
			} // inner for 
		} // outer for
	} // printBoard
	
	public static boolean isOutOfBounds(char[][] board, int row, int col) {
		if(row < 0 || row > (board.length-1)) {
			System.out.println("Debugging: row out of bounds");
			return true;
		}  else if ( col < 0 || col > board.length-1) {
			System.out.println("Debugging: col out of bounds");
			return true;
		}
		return false;
	} // outOfBounds
	
	public static boolean isVisited(boolean[][] isExplored, int row, int col) {
		if(isExplored[row][col] == true) {
			return true;
		}
		return false;
	} // isVisited
	
	public static boolean isPossibleWord(String word, HashSet<String> dict) {
		// if is in list of possible words -> true
		if(dict.contains(word)) {
			return true;
		}
		return false;
	}
	
}
