package edu.monmouth.CS205L.general;

import java.util.Scanner;

public class TicTacToe {
	

	public static void main(String[] args) {
		
		char[][] board = new char[3][3];	
		char currentPlayer = 'X';
		boolean gameEnded = false;
		int turnCount = 0;
		
		initializeBoard(board);
		Scanner scanner = new Scanner(System.in);
		
		while(!gameEnded) {
			
			// Print the actual board 
			printBoard(board);
			
			
			System.out.println("Player "+currentPlayer+" is your turn!");
			System.out.println("Insert the line (0-2): ");
			int row = scanner.nextInt();
			
			System.out.println("Insert the column (0-2): ");
			int column = scanner.nextInt();
			
			// Input validation
			if (row >= 0 && row < 3 && column >= 0 && column < 3) {
				// Change validation
				if (board[row][column] == '-') {
					
					// We can update the board & Count
					board[row][column] = currentPlayer;
					turnCount++;
					
					// Check if the move was winning
					if (checkWinner(board, currentPlayer)) {
						gameEnded = true;
						printBoard(board); // Print the final result
						System.out.println("Player "+currentPlayer+" won!");
						
					// Or if it was the last move
					} else if (checkCount(turnCount)) {
						gameEnded = true;
						printBoard(board);
						System.out.println("Nobody wins!");
						
					// Or if we can keep playing
					} else {

						currentPlayer = changeTurn(currentPlayer);
						
					} // checkWinner
					
					
					
					
			} else { // Place occupied
				System.out.println("The row "+row+" and the column "+column+" is already taken");
				System.out.println("Please try again!");
			}
			
		} else { // Invalid input
			System.out.println("The input is invalid, please try again!");
		}

	} // while
		
	scanner.close();
	
	} // main
	
	public static void printBoard (char[][] board) {
		System.out.println("Print Board");
		
		for (int i=0;i<3;i++) {
			System.out.print("| ");
			
			for (int j=0; j<3; j++) {
				System.out.print(board[i][j] + " | ");
			}
			
			// When the row is over print line
			System.out.println();
		}
		
		
		
		
	}
	
	public static boolean checkWinner(char[][] board, char player) {
		
		// Check all the lines to see if somebody won
		for (int i=0; i<3; i++) {

			// Check the rows first
			if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
	            return true;
	        }
			
			// Check the columns
			if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
	            return true;
	        }
			
		} // for cycle
		
		// Check the diagonals
		if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
	        return true;
	    }
		
		if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
	        return true;
	    }
		
		// If nothing is true, nobody wins
		return false;
	}
	
	public static boolean checkCount(int count) {
		if (count == 9) {
			return true;
		}
		return false;
	}
	
	public static char changeTurn(char current) {
		// Change the turn
		if (current == 'X') {
			return 'O';
		} else {
			return 'X';
		} // Change turn
	}
	
	public static void initializeBoard(char[][] board) {
		// Initialize the board
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						board [i][j] = '-';
					} // j cycle
				} // i cycle
	}

}
