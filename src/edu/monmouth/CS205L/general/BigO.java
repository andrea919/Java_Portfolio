package edu.monmouth.CS205L.general;

import java.util.Arrays;
import java.util.Scanner;

public class BigO {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int[] data = {5, 2, 9, 1, 5, 6};
        int n = data.length;
        int target = 9;
        boolean flag = true;
        
        while(flag) {
        	System.out.println("Select an option:"
        	        + "\n1:O(1) Constant: "
        	        + "\n2:O(log n) Logarithmic: "
        	        + "\n3:O(n) Linear:"
        	        + "\n4:O(n log n) Linearithmic:"
        	        + "\n5: O(n^2) Quadratic"
                    + "\n6: O(n^3) Cubic"
                    + "\n7: O(2^n) Exponential"
                    + "\n8: Exit the Program");

        	        String s = sc.nextLine();
        	        int answer = Integer.parseInt(s);

        	        switch(answer) {
        	        case 1:
        	        	System.out.println(BigOConstant(1, 2, 3, 4));
        	        	break;
        	        case 2:
        	        	int[] sorted = {10, 20, 30, 40, 50};
        	        	System.out.println("Result index: " + BigOLog(sorted, 40));
        	        	break;
        	        case 3:
        	        	System.out.println("Result index: " + BigOLinear(data, 40));
        	        	break;
        	        case 4:
        	        	BigOnLogn(data);
        	        	System.out.println("Array sorted.");
        	        	break;
        	        case 5:
        	        	bigOBubble(data);
        	        	System.out.println("Array sorted via Bubble Sort.");
        	        	break;
        	        case 6:
        	        	int[][] A = {{1, 2}, {3, 4}};
        	        	int[][] B = {{5, 6}, {7, 8}};
        	        	BigOCubic(A, B, 2);
        	        	System.out.println("Triple Nested Array solved.");
        	        	break;
        	        case 7:
        	        	System.out.println("Recursive result: " + BigOExponential(10));
        	        	break;
        	        case 8:
        	        	System.out.println("Exiting...");
        	        	flag = false;
        	        	break;
        	        } // switch	
        }
        sc.close();
	}
	
	// no matter how many input, it takes the same time
	public static int BigOConstant(int a, int b, int c, int d) {
		return a+b;
	}
	
	// remaining work is divided in every step
	// means it grows at logarithmic rate -> 1000 - 500 - 250...
	public static int BigOLog(int[] sortedArr, int target) {
		int low = 0;
		int high = sortedArr.length -1;
		
		while (low<= high) {
			int mid = low + (high - low) / 2;
			
			if (sortedArr[mid] == target) {
				return mid;
			}
			
			if (sortedArr[mid] < target) {
	            low = mid + 1; // Target is in the right half
	        } else {
	            high = mid - 1; // Target is in the left half
	        }
			
		} // while
		
		// target not found
		return -1;
    }
	
	// The amount of time depends directly on the array size
	// If the array is bigger, it takes more time -> linear
	public static int BigOLinear(int [] arr, int target) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == target) {
				return i;
			}
		}
		return -1;
	}
	
	// Sorting algorithm like Quicksort -> Divide and Conquer
	// Divid is log n as we split it until is individual size
	// Conquer is n as we merge pieces back together (linear)
	
	public static void BigOnLogn(int [] array) {
		Arrays.sort(array);
		System.out.println("Sorted array: "+ Arrays.toString(array));
	}
	
	// Sorting algorithm like Bubble -> Quadratic growth
	// If the number of input grows (example n)
	// The number of operations performed is n^2
	public static void bigOBubble(int[] array) {
		int n = array.length;
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - i - 1; j++) {
				if (array[j] > array[j + 1]) {
					// temp to store primitive for swap
					int temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}
	}
	
	// The growth is cubic 
	// If we have 10 input (n=10), the operations are 10^3
	public static int[][] BigOCubic(int[][] A, int[][] B, int n) {
	    int[][] C = new int[n][n];

	    // Loop 1: Iterate each row of A
	    for (int i = 0; i < n; i++) {
	        // Iterate each column of B
	        for (int j = 0; j < n; j++) {
	            C[i][j] = 0;
	            for (int k = 0; k < n; k++) {
	                C[i][j] += A[i][k] * B[k][j];
	            }
	        }
	    }
	    return C;
	}
	
	// Exponential time complexity - O(2^n)
	// This algorithm creates a binary tree of calls (depth n)
	// Simple recursion = high computational costs
	public static int BigOExponential(int n) {
		if (n<=1) {
			return n;
		}
		
		return BigOExponential(n-1) + BigOExponential(n-2);
	}
	
	
	

} // class
