package edu.monmouth.CS205L.boggle;
import java.util.*;

public class Boggle {

	public static void main(String[] args) {
		
		int n = getNumberN(args);
		
		char[][] boggleBoard = new char[n][n];
		boolean[][] isExplored = new boolean[n][n];
		generateBoard(n, boggleBoard);
		
		// Create Dictionary thru Tree
		Tree dictionaryTree = new Tree();
		
		Set<String> foundWords = new HashSet<String>();
		startSearch(n, boggleBoard, isExplored, dictionaryTree, foundWords);
		countPoints(foundWords);
		
	
	} // main
	
	public static int countPoints(Set<String> words) {
		if(words.size()==0) {return 0;}
		int length, points, totalPoints=0;
		for (String word: words) {
			length = word.length();
			if(length > 7) {points = 11;}
			else if(length > 6) {points = 5;}
			else if(length > 5) {points = 3;}
			else if(length > 4) {points = 2;}
			else if(length > 2) {points = 1;}
			else {points = 0;}
			totalPoints += points;
			System.out.println("The word "+word+" is worth "+points+" points. Total points collected: "+ totalPoints);
		}
		System.out.println("Total words collected: "+words.size()+"\nTotal points collected: "+totalPoints);
		return totalPoints;
	}
	
	public static void startSearch(int n, char[][] board, boolean[][] isExplored, Tree tree, Set<String> foundWords) {
		for (int i = 0; i < n; i++) {
		    for (int j = 0; j < n; j++) {
				recursiveSearch("", i, j, board, isExplored, tree, foundWords );
		    }
		}
	}
	
	public static void recursiveSearch(String currentWord,int row, int col, char[][] board, boolean[][] isExplored, Tree tree, Set<String> foundWords) {
		
		if(isOutOfBounds(board, row, col) || 
				isVisited(isExplored, row, col)){
			return;
		} 
		
		String nextWord = currentWord + board[row][col];
		isExplored[row][col] = true;
		if (tree.isPrefixValid(nextWord)) {
			if(nextWord.length() > BoggleConstants.MINWORDCHARACTERS && tree.isWord(nextWord)) {
				foundWords.add(nextWord);
			}
		
			// call recursion for next cells
			int[] dRow = {-1, -1, -1,  0, 0,  1, 1, 1};
			int[] dCol = {-1,  0,  1, -1, 1, -1, 0, 1};
			
			for(int i=0; i<dRow.length; i++) {
				int nextRow = row + dRow[i];
				int nextCol = col + dCol[i];
				recursiveSearch(nextWord, nextRow, nextCol, board, isExplored, tree, foundWords);
			}
		}
		// reset for other combinations
		isExplored[row][col] = false;
	} // recursiveSearch
	
	
	
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
		System.out.println();
	} // printBoard
	
	public static boolean isOutOfBounds(char[][] board, int row, int col) {
		if(row < 0 || row > (board.length-1)) {
			// System.out.println("Debugging: row out of bounds");
			return true;
		}  else if ( col < 0 || col > board.length-1) {
			// System.out.println("Debugging: col out of bounds");
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
	
	
}
