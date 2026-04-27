package edu.monmouth.CS205L.symbolTable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* An HashMap was chosen as the underlying data structures
 * because it guarantees O(1) average time complexity for 
 * the main operations that a Symbol Table needs to execute
 * often such as insertion, get and deletion.
 * 
 * Additionally, I opted to create an internal static Symbol
 * class to define the metadata and keep each entry
 * compact and consistent. I could have used built-in structures
 * like an internal HashTable or Properties class but having a 
 * custom class gives full control of the code, making the 
 * structure smoother and easier to extend in the future.
*/
public class SymbolTable {
	public static class Symbol{
		String name, type;
		Object value;
		
		public Symbol(String name, String type, Object value){
			setName(name);
			setType(type);
			setValue(value);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "Symbol [name=" + name + ", type=" + type + ", value=" + value + "]";
		}
		
	} // Symbol class
	
	public static void main(String[] args) {
		SymbolTable table = new SymbolTable();

		// insert
	    table.insert("x", "int", 42);
	    table.insert("y", "String", "hello");
	    table.insert("x", "int", 99); // duplicate

	    // printTable
	    System.out.println("\n--- Table after inserts ---");
	    table.printTable();

	    // getValue
	    System.out.println("\n--- getValue ---");
	    System.out.println(table.getValue("x"));
	    System.out.println(table.getValue("z")); // doesn't exist, returns null

	    // contains
	    System.out.println("\n--- contains ---");
	    System.out.println("Contains y? " + table.contains("y"));
	    System.out.println("Contains z? " + table.contains("z"));

	    // update
	    System.out.println("\n--- update ---");
	    table.update("y", "String", "world"); 
	    table.update("x", "double", 3.14);  
	    table.update("z", "int", 0);          
	    table.printTable();

	    // remove
	    System.out.println("\n--- remove ---");
	    table.remove("y");
	    table.remove("y"); // already removed
	    table.printTable();
	}
	
	private Map<String, Symbol> symbolMap = new HashMap<>();
	
	public void insert(String name, String type, Object value) {
		if(symbolMap.containsKey(name)) {
			System.out.println("Name already associated to another value.");
			return;
		}
		symbolMap.put(name, new Symbol(name, type, value));
		System.out.println("Name " +name+" associated to its value "+value);
	}
	
	public String getValue(String name) {
		if(!symbolMap.containsKey(name)) {
			return null;
		}
		return symbolMap.get(name).toString();
	}
	
	public void update(String name, String newType, Object newValue) {
		if(!symbolMap.containsKey(name)) {
			System.out.println("Cannot update values for a key that does not exist.");
			return;
		}
		if(!newType.equals(symbolMap.get(name).getType())){
			symbolMap.get(name).setType(newType);
			System.out.println("Updated type to: "+newType);
		}
		if(!newValue.equals(symbolMap.get(name).getValue())){
			symbolMap.get(name).setValue(newValue);
			System.out.println("Updated value to: "+newValue);
		}
	}
	
	public void remove(String name) {
		if(!symbolMap.containsKey(name)) {
			System.out.println("Cannot remove a key that does not exist.");
			return;
		}
		symbolMap.remove(name);
	    System.out.println("Symbol '" + name + "' removed.");
	}
	
	public boolean contains(String name) {
		return symbolMap.containsKey(name);
	}
	
	public void printTable() {
		Set<String> keys = symbolMap.keySet();
		for(String k : keys) {
			System.out.println("Key: "+k+" - Value: "+symbolMap.get(k));
		}
	}
	
}
