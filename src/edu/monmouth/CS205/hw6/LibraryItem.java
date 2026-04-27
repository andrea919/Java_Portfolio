package edu.monmouth.CS205.hw6;

public interface LibraryItem {

	abstract public String getTitle();
	abstract public boolean isAvailable();
	abstract public void borrowItem();
	abstract public void returnItem();

}
