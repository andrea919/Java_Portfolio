package edu.monmouth.CS205.hw6;

import java.util.Comparator;

public class JournalEditor implements Comparator<Journal>{

	@Override
	public int compare(Journal journal1, Journal journal2) {
		return journal1.getEditor().compareTo(journal2.getEditor());
	}

}
