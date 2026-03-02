package edu.monmouth.CS205.library;

public interface LibraryItem {
	abstract public String getTitle();
	abstract public boolean isAvailable();
	abstract public void borrowItem();
	public void returnIitem();
}
