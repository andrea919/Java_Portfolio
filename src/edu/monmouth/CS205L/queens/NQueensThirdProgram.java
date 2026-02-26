package edu.monmouth.CS205L.queens;

import java.util.ArrayList;

public class NQueensThirdProgram {
	public static void main(String[] args){
		
		if (args.length == 0) {
	        System.out.println("Error: Please provide 3 integers.");
	        return;
	    }
		
		int q, m, n;
	    try {
	        q = Integer.parseInt(args[0]); // queens
	        m = Integer.parseInt(args[1]); // rows
	        n = Integer.parseInt(args[2]); // columns
	    } catch (NumberFormatException e) {
	        System.out.println("Error: Arguments must be integers.");
	        return;
	    }
	    
		// chessBoard with the actual queens stored
		int [][] chessBoard = new int[m][n];
		// chessBoard with all the squares not available
		int [][] badSquares = new int[m][n];
		// List of type int[][] to store the solutions 
		ArrayList<int[][]> solutions = new ArrayList<int[][]>();
		// counter to see queens placed & currentRow
		int placed = constants.START_ITERATION_CYCLE;
		int currentRow = constants.START_ITERATION_CYCLE;
		
		if(!(q > m || q > n)) {
			playGame(q, m, n, currentRow, placed, chessBoard, badSquares, solutions);
		} else {
			System.out.println("There is no solution, more queens than rows or columns.");
		}
		
		System.out.println("Total solutions: "+solutions.size());
		
	} // main
	
	public static void playGame(int queens, int rows, int columns, int currentRow, int placed,int[][] chessBoard, int[][] badSquares, ArrayList<int[][]> solutions){
		
		System.out.println("Current recursion depth: " + placed);
		
		if(placed==queens) {
			int[][] finalSolution = copyBoard(rows, columns, chessBoard);
			solutions.add(finalSolution);
			
			return;
		}
		
		if(currentRow==rows) {
			return;
		}
		
		int row = currentRow;		
		// for each column of the row I try to input
		for (int col=0; col < columns; col++) {
			if(!isPositionTaken(currentRow, col, badSquares)) {
				int[][] nextBadSquares = copyBoard(rows, columns, badSquares);
				chessBoard[row][col] = constants.QUEEN;
				collectBadSquares(row, col, rows, columns, nextBadSquares);
				
				
				playGame(queens, rows, columns, currentRow + 1, placed + 1, chessBoard, nextBadSquares, solutions);
				chessBoard[row][col] = 0;
			}
		}
		playGame(queens, rows, columns, currentRow + 1, placed, chessBoard, badSquares, solutions);
	}
	
	public static int[][] copyBoard(int rows, int columns, int[][] ogBadSquares) {
	    int[][] copy = new int[rows][columns];
	    for (int i = 0; i < rows; i++) {
	        copy[i] = ogBadSquares[i].clone();
	    }
	    return copy;
	}
		
	
	public static void printBoard(int rows, int columns, int[][] chessBoard) {
		int counter = constants.START_ITERATION_CYCLE+1;
		for (int i = 0; i < rows; i++) {
			System.out.println();
			
			for(int j = 0; j < columns; j++) {
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
				if (j == columns-1){
					System.out.print("|");
				}
			}
		} // for
		System.out.println();
	} // printBoard
	

	public static boolean placeQueen(int row, int col, int rows, int columns, int[][] chessBoard, int[][] badSquares) {
		
		if(isPositionTaken(row, col, chessBoard) || isPositionTaken(row, col, badSquares)){
			System.out.println("Position already taken or not safe, try again.");
			return false;
		} else {
			chessBoard[row][col] = constants.QUEEN;	
			collectBadSquares(row, col,rows, columns, badSquares);
			return true;
		}
	}
	
	public static void collectBadSquares(int row, int col, int rows, int columns, int[][] badSquares) {
		// After successfully placing a queen
		badSquares[row][col] = constants.BAD_SQUARES;
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (i==row || j==col) {
					badSquares[i][j] = constants.BAD_SQUARES;
				}
			}
		}
		
		// New method for checking just diagonals
		diagonalBadSquares(row, col, rows, columns, badSquares);
		
	}
	
	public static int[][] diagonalBadSquares(int row, int col, int rows, int columns,int[][] badSquares){
		int d1, d2;
		
		// Diagonal Top Right
		d1=row; d2=col;
		while(d2 < columns && d1 > -1) {
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
		while(d2 > -1 && d1 < rows) {
			badSquares[d1][d2] = constants.BAD_SQUARES;
			d1++;
			d2--;
		}
			
		// Diagonal Bottom Right
		d1=row; d2=col;
		while(d2 < columns && d1 < rows) {
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
