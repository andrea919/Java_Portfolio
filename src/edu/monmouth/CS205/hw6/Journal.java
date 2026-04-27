package edu.monmouth.CS205.hw6;

public class Journal implements LibraryItem, Comparable<Journal>{
	
	private String title, editor;
	private int volume;
	private StatusType status;
	
	public Journal(String title, String editor, int volume, StatusType status) throws JournalException {
		setTitle(title);
		setEditor(editor);
		setVolume(volume);
		setStatus(status);
	}
	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public boolean isAvailable() {
		return getStatus().equals(StatusType.ONSHELF);
	}

	@Override
	public void borrowItem() {
		if(!(getStatus().equals(StatusType.ONSHELF))) {
			System.out.println("This journal is not available.");
			return;
		}
		setStatus(StatusType.BORROWED);
	}

	@Override
	public void returnItem() {
		if(getStatus().equals(StatusType.ONSHELF)){
			System.out.println("Journal already on shelf.");
			return;
		}
		setStatus(StatusType.ONSHELF);
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) throws JournalException {
		if(editor == null || editor.length() < LibraryItemConstants.MINJOURNALTITLELENGTH) {
			throw new JournalException("Editor cannot be null or have no characters.");
		}
		this.editor = editor;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) throws JournalException {
		if(volume < LibraryItemConstants.MINJOURNALVOLUME) {
			throw new JournalException("Volume cannot be negative.");
		}
		this.volume = volume;	
		}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public void setTitle(String title) throws JournalException{
		if(title == null || title.length() < LibraryItemConstants.MINJOURNALTITLELENGTH) {
			throw new JournalException("Title cannot be null or have no characters.");
		}
		this.title = title;
	}

	@Override
	public String toString() {
		return "Journal [title=" + title + ", editor=" + editor + ", volume=" + volume + ", status=" + status + "]";
	}
	
	@Override
	public boolean equals(Object o) {
			if(this == o) return true;
			if(o == null || !(o instanceof Journal)) return false;
			// if it does not return yet
			Journal other = (Journal) o;
			return this.getTitle().equals(other.getTitle()) &&
					this.getEditor().equals(other.getEditor()) &&
					this.getVolume() == other.getVolume();
	}
	
	@Override
	public int hashCode() {
	    return title.hashCode() + editor.hashCode() + volume;
	}

	@Override
	public int compareTo(Journal o) {
		System.out.println("compareTo method called for: "+ this.title+ " and "+o.title);
		if(!this.title.equals(o.title)) return this.title.compareTo(o.title);
		return this.volume - o.volume;
	}
}