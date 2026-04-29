package edu.monmouth.CS205L.fileSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FileSystem {
	
	public static void main(String[] args) {
		FileSystem fs = new FileSystem();

	    fs.addFile("/home/docs/notes.txt");
	    fs.addFile("/home/docs/hw.pdf");
	    fs.addFile("/home/pictures/photo.png");

	    System.out.println("notes.txt exists? " + fs.fileExist("/home/docs/notes.txt"));
	    System.out.println("fake.txt exists? " + fs.fileExist("/home/docs/fake.txt"));

	    System.out.println("Total size of /home/docs: " + fs.getTotalSize("/home/docs"));
	    System.out.println("Total size of /home: " + fs.getTotalSize("/home"));

	    fs.removeFile("/home/docs/notes.txt");

	    System.out.println("notes.txt exists after removal? " + fs.fileExist("/home/docs/notes.txt"));
	    System.out.println("Total size of /home/docs after removal: " + fs.getTotalSize("/home/docs"));

	}
	
	private Directory root;
	private Random random; 	
	
	public FileSystem() {
		root = new Directory();
		random = new Random();
	}
	
	private String[] cleanPath(String path) {
		if(path == null || path.strip().isEmpty()) {
			System.out.println("Cannot clean a null path.");
			System.exit(FileSystemConstants.NO_PATH_EXIT);
		}
		path = path.strip();
		if(path.startsWith(FileSystemConstants.DELIMITER)) {
			path = path.substring(1); 	// start at 1 and removes the element at 0
		}
		return path.split(FileSystemConstants.DELIMITER);
	}
	
	public void addFile(String path) {
		String parts[] = cleanPath(path);
		Directory current = root;
		
		for(int i=0; i<parts.length-1; i++) {
			if(!current.getSubdirectories().containsKey(parts[i])) {
				current.getSubdirectories().put(parts[i], new Directory());
			}
			current = current.getSubdirectories().get(parts[i]);
		}
		String fileName = parts[parts.length-1];
		int size = random.nextInt(FileSystemConstants.FIXED_FILE_SIZE);
		
		current.getFiles().put(fileName, new FileEntry(fileName, size));
		System.out.println("Added file: "+fileName+" with size "+size);
	}
	
	public boolean fileExist(String path) {
		String parts[] = cleanPath(path);
		Directory current = root;
		
		for(int i=0; i<parts.length-1; i++) {
			if(!current.getSubdirectories().containsKey(parts[i])) {
				return false;
			}
			current = current.getSubdirectories().get(parts[i]);
		}
		
		String fileName = parts[parts.length-1];
		return current.getFiles().containsKey(fileName);
	} // fileExist
	
	public void removeFile(String path) {
		String parts[] = cleanPath(path);
		Directory current = root;
		
		for(int i=0; i<parts.length-1; i++) {
			if(!current.getSubdirectories().containsKey(parts[i])) {
				System.out.println("Path does not exist.");
				System.exit(FileSystemConstants.NO_PATH_EXIT);
			}
			current = current.getSubdirectories().get(parts[i]);
		}
		String fileName = parts[parts.length-1];
		if(!current.getFiles().containsKey(fileName)) {
			System.out.println("Path does not exist.");
			System.exit(FileSystemConstants.NO_FILE_EXIT);
		}
		current.getFiles().remove(fileName);
		System.out.println("Removed file: "+ fileName);
	} // removeFile
	
	public int getTotalSize(String path) {
		String parts[] = cleanPath(path);
		Directory current = root;
		
		for(int i=0; i<parts.length; i++) {
			if(parts[i].isEmpty()) continue;
			
			if(!current.getSubdirectories().containsKey(parts[i])) {
				System.out.println("Directory "+parts[i]+" does not exist.");
				return 0;
			}
			current = current.getSubdirectories().get(parts[i]);
		}
		return calculateSize(current);
		
	} // getTotalSize
	
	private int calculateSize(Directory dir) {
		int total = 0;
		for (FileEntry file : dir.getFiles().values()) {
			total += file.getSize();
		}
		for(Directory subDir : dir.getSubdirectories().values()) {
			total += calculateSize(subDir);
		}
		return total;
	} // calculateSize
	
	class Directory{
		// each directory represents a possible path and a node in a tree
		Map<String, FileEntry> files;			// each directory has possible files
		Map<String, Directory> subdirectories;	// each directory has possible subdirectories
		
		public Directory() {
			files = new HashMap<>();
			subdirectories = new HashMap<>();
		}

		public Map<String, FileEntry> getFiles() {
			return files;
		}

		public Map<String, Directory> getSubdirectories() {
			return subdirectories;
		}
	}
	
	class FileEntry{
		// FileEntry represents one single file with a fixed size
		String name;
		int size;
		
		public FileEntry(String name, int size) {
			setName(name);
			setSize(size);
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}

		@Override
		public String toString() {
			return "FileEntry [name=" + name + ", size=" + size + "]";
		}
		
	}

	

} // FileSystem
