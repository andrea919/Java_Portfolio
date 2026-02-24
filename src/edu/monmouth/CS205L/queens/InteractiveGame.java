package edu.monmouth.CS205L.queens;
import java.util.ArrayList;
import java.util.Scanner;

public class InteractiveGame {

	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Welcome, Input the number of queens you want to try to place: ");
			
			int n = constants.START_ITERATION_CYCLE;
			boolean valid = constants.BOOL_FLAG;

			while (!valid) {
			    while (!sc.hasNextInt()) {
			        System.out.println("Not a number! Try again:");
			        sc.next();
			    }
			    n = sc.nextInt();
			    
			    if (n >= 1 && n <= 100) {
			        valid = true;
			    } else {
			        System.out.println("Not valid! Enter a number between 1 and 100.");
			    }
			}
			
			// chessBoard with the actual queens stored
			int [][] chessBoard = new int[n][n];
			// chessBoard with all the squares not available
			int [][] badSquares = new int[n][n];
			// List of type int[][] to store the solutions 
			ArrayList<int[][]> solutions = new ArrayList<int[][]>();
			// counter to see queens placed
			int placed = constants.START_ITERATION_CYCLE;
			
			playGame(placed,chessBoard, badSquares,sc, solutions);
			
			
			
		} catch(Exception e) {
			System.out.println("Critical error: " +e.getMessage());
		}
		
	} // main
	
	public static void playGame(int placed,int[][] chessBoard, int[][] badSquares, Scanner sc, ArrayList<int[][]> solutions){
		System.out.println("Current recursion depth: " + placed);
		
		if (countAvailable(badSquares) == 0 && placed != chessBoard.length)	{
			System.out.println("You lost, you ran out of queens!");
			System.out.println("You have placed "+placed+" queens out of "+chessBoard.length+".");
			printBoard(chessBoard);
			return;
		}
		if (placed == chessBoard.length) {
			System.out.println("You won, you fond a solution!");
			// attach it to the solutions array
			printBoard(chessBoard);
			solutions.add(chessBoard);
			
			return;
		}
		
		System.out.println("You have placed "+placed+" queens.");
		printBoard(chessBoard);
		printBoard(badSquares);
				
		System.out.println("Where do you want to place the next queen: ");
		
		int position = constants.START_ITERATION_CYCLE-1;
		boolean valid = constants.BOOL_FLAG;

		while (!valid) {
		    while (!sc.hasNextInt()) {
		        System.out.println("Not a number! Try again:");
		        sc.next();
		    }
		    position = sc.nextInt();
		    
		    if (position >= 1 && position <= (chessBoard.length * chessBoard.length)) {
		        valid = true;
		    } else {
		        System.out.println("Out of bounds! Enter a number between 1 and " + (chessBoard.length * chessBoard.length));
		    }
		}
		
		int row = calculateRow(position, chessBoard);
		int col = calculateCol(position, chessBoard);
				
				
		if (placeQueen(row, col, chessBoard, badSquares)) {
			// If it is true, add the next one
			placed++;
			// Recursive call with updated placed, chessBoard, etc
			playGame( placed, chessBoard, badSquares, sc, solutions);
		} else {
			// If returns false, placeQueen method will output and return
			// Repeat the same call with the same placed depth
			playGame( placed, chessBoard, badSquares, sc, solutions);
		}
			
	}
	
	public static int countAvailable(int[][] badSquares) {
		int counter = constants.START_ITERATION_CYCLE;
		
		for (int i = 0; i < badSquares.length; i++) {
			for(int j = 0; j < badSquares.length; j++) {
				if (badSquares[i][j] != constants.QUEEN && badSquares[i][j] != constants.BAD_SQUARES) {
					counter++;
				}
			}
		}
		
		return counter;
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
	
	public static int calculateRow(int position, int[][] chessBoard) {
		int row = (position - 1)/ chessBoard.length;
		return row;
	}
	
	public static int calculateCol(int position, int[][] chessBoard) {
		int col = (position - 1)% chessBoard.length;
		return col;
	}
		
} // class
