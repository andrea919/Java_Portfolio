package edu.monmouth.CS205L.boggle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Tree {
	
	private class Node<E> {
		E data;
		Node[] children;
		boolean isWord;
		
		private Node(E data){
			this.data = data;
			this.children=new Node[BoggleConstants.ALPHABETNUM];
			this.isWord=false;
		}
		
	} // Node class
	
	private Node root;
	
	public Tree() {
		root = new Node(BoggleConstants.DEFAULTROOT);
	}
	
	public void insertWordTree(String word) {
		Node currentNode = root;
		int characterInt;
		char character;
		for(int c=0; c<word.length(); c++) {
			character = word.charAt(c);
			characterInt = (int) (word.charAt(c) - BoggleConstants.ASCIIADDITION);
			if(currentNode.children[characterInt] == null) {
				currentNode.children[characterInt] = new Node(character);
				currentNode.children[characterInt].data = character;
			}
			currentNode = currentNode.children[characterInt];
			} // for loop
			currentNode.isWord=true;
		} // insertWordTree
	
	public boolean isPrefixValid(String prefix) {
		Node currentNode = root;
		int characterInt;
		char character;
		for (int c = 0; c < prefix.length(); c++) {
			character = prefix.charAt(c);
			characterInt = (int) (prefix.charAt(c) - BoggleConstants.ASCIIADDITION);
			// path in the tree does not exist = Prefix is not valid
			if(currentNode.children[characterInt] == null) {
				return false;
			}
			currentNode = currentNode.children[characterInt];
		}
		// path in the tree exists = prefix is valid
		return true;
		
	} // isPrefixValid
	
	public boolean isWord(String word) {
		Node currentNode = root;
		int characterInt;
		char character;
		for (int c = 0; c < word.length(); c++) {
			character = word.charAt(c);
			characterInt = (int) (word.charAt(c) - BoggleConstants.ASCIIADDITION);
			if(currentNode.children[characterInt]==null) {
				return false;
			}
			currentNode = currentNode.children[characterInt];
		}
		return currentNode.isWord;
	} // isWord
	
	public void loadDictionary(String filePath) {
	    try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = in.readLine()) != null) {
	            String word = line.trim().toLowerCase();
	            if (!word.isEmpty()) {
	                insertWordTree(word);
	            }
	        }
	    } catch (IOException e) {
	        System.err.println("Errore nel caricamento del dizionario: " + e.getMessage());
	    }
	} //loadDictionary
	
}
	
