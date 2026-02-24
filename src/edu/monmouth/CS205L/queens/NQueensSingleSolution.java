package edu.monmouth.CS205L.queens;
import java.util.ArrayList;

public class NQueensSingleSolution {

	public static void main(String[] args){
		
		if (args.length == 0) {
	        System.out.println("Error: Please provide the number of queens.");
	        return;
	    }
		
		int n;
	    try {
	        n = Integer.parseInt(args[0]); 
	    } catch (NumberFormatException e) {
	        System.out.println("Error: Argument must be an integer.");
	        return;
	    }
	    
		// chessBoard with the actual queens stored
		int [][] chessBoard = new int[n][n];
		// chessBoard with all the squares not available
		int [][] badSquares = new int[n][n];
		// List of type int[][] to store the solutions 
		ArrayList<int[][]> solutions = new ArrayList<int[][]>();
		// counter to see queens placed
		int placed = constants.START_ITERATION_CYCLE;
			
		if(playGame(n, placed,chessBoard, badSquares, solutions) && solutions.size() > 0) {
			System.out.println("\n--- SOLUTION FOUND ---");
			printBoard(chessBoard);
		} else {
			System.out.println("No solution exists for " + n + " queens.");
		}

		
	} // main
	
	public static boolean playGame(int n, int placed,int[][] chessBoard, int[][] badSquares, ArrayList<int[][]> solutions){
		System.out.println("Current recursion depth: " + placed);
		
		if(placed==n) {
			int[][] finalSolution = copyBoard(chessBoard);
			
			solutions.add(finalSolution);
			System.out.println("Solution added to solutions array.");
			return true;
		}
		
		System.out.println("Placed "+placed+" queens.");
		printBoard(chessBoard);
		printBoard(badSquares);
		
		int row = placed;		
		// for each column of the row I try to input
		for (int col=0; col < chessBoard.length; col++) {
			if(!isPositionTaken(placed, col, badSquares)) {
				int[][] nextBadSquares = copyBoard(badSquares);
				chessBoard[row][col] = constants.QUEEN;
				collectBadSquares(placed, col, nextBadSquares);
				
				printBoard(chessBoard);
				
				if(playGame(n, placed+1, chessBoard, nextBadSquares, solutions)) {
					return true;
				}
				// reset the current slot and go to the next col with for loop
				chessBoard[row][col] = 0;
			}
		}
		
		return false;
		
	}
	
	public static int[][] copyBoard(int[][] ogBadSquares) {
	    int[][] copy = new int[ogBadSquares.length][ogBadSquares.length];
	    for (int i = 0; i < ogBadSquares.length; i++) {
	        copy[i] = ogBadSquares[i].clone();
	    }
	    return copy;
	}
		
	
	public static void printBoard(int[][] chessBoard) {
		int counter = constants.START_ITERATION_CYCLE+1;
		for (int i = 0; i < chessBoard.length; i++) {
			System.out.println();
			
			for(int j = 0; j < chessBoard.length; j++) {
				if (chessBoard[i][j] == constants.QUEEN) {
					System.out.print("|  "+(constants.QUEEN)+"  ");
					counter++;
				} else if (chessBoard[i][j] == constants.BAD_SQUARES) {
					System.out.print("|  "+(constants.BAD_SQUARES)+"  ");
					counter++;
				} else if (counter < 10){
					System.out.print("|  "+(counter)+"  ");
					counter++;
				} else if (counter < 100) {
					System.out.print("|  "+(counter)+" ");
					counter++;
				} else {
					System.out.print("| "+(counter)+" ");
					counter++;
				}
				if (j == chessBoard.length-1){
					System.out.print("|");
				}
			}
		} // for
		System.out.println();
	} // printBoard
	

	public static boolean placeQueen(int row, int col, int[][] chessBoard, int[][] badSquares) {
		
		if(isPositionTaken(row, col, chessBoard) || isPositionTaken(row, col, badSquares)){
			System.out.println("Position already taken or not safe, try again.");
			return false;
		} else {
			chessBoard[row][col] = constants.QUEEN;	
			collectBadSquares(row, col, badSquares);
			return true;
		}
	}
	
	public static void collectBadSquares(int row, int col, int[][] badSquares) {
		// After successfully placing a queen
		badSquares[row][col] = constants.BAD_SQUARES;
		
		// Start with the horizontal/vertical
		for (int i = 0; i < badSquares.length; i++) {
			for (int j = 0; j < badSquares.length; j++) {
				if (i==row || j==col) {
					badSquares[i][j] = constants.BAD_SQUARES;
				}
			}
		}
		
		// New method for checking just diagonals
		diagonalBadSquares(row, col, badSquares);
		
	}
	
	public static int[][] diagonalBadSquares(int row, int col,int[][] badSquares){
		int d1, d2;
		
		// Diagonal Top Right
		d1=row; d2=col;
		while(d2 < badSquares.length && d1 > -1) {
			badSquares[d1][d2] = constants.BAD_SQUARES;
			d1--;
			d2++;
		}
		
		// Diagonal Top Left
		d1=row; d2=col;
		while(d2 > -1 && d1 > -1) {
			badSquares[d1][d2] = constants.BAD_SQUARES;
			d1--;
			d2--;
		}
		
		// Diagonal Bottom Left
		d1=row; d2=col;
		while(d2 > -1 && d1 < badSquares.length) {
			badSquares[d1][d2] = constants.BAD_SQUARES;
			d1++;
			d2--;
		}
			
		// Diagonal Bottom Right
		d1=row; d2=col;
		while(d2 < badSquares.length && d1 < badSquares.length) {
			badSquares[d1][d2] = constants.BAD_SQUARES;
			d1++;
			d2++;
		}
		
		return badSquares;
	}
	
	
	public static boolean isPositionTaken(int row, int col, int[][] chessBoard) {	
		return (chessBoard[row][col] == constants.QUEEN || chessBoard[row][col] == constants.BAD_SQUARES);
	}
	
		
} // class
