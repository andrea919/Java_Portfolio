package edu.monmouth.CS205.library;

public class Journal implements LibraryItem{
	
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
			System.out.println("This book is not available.");
			return;
		}
		setStatus(StatusType.BORROWED);
	}

	@Override
	public void returnIitem() {
		if(!(getStatus().equals(StatusType.ONSHELF))){
			System.out.println("Journal already on shelf.");
			return;
		}
		setStatus(StatusType.ONSHELF);
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) throws JournalException {
		if(editor == null || editor.length() < JournalConstants.MINTITLELENGTH) {
			throw new JournalException("Editor cannot be null or have no characters.");
		}
		this.editor = editor;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) throws JournalException {
		if(volume < JournalConstants.MINJOURNALVOLUME) {
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
		if(title == null || title.length() < JournalConstants.MINTITLELENGTH) {
			throw new JournalException("Title cannot be null or have no characters.");
		}
		this.title = title;
	}

	@Override
	public String toString() {
		return "Journal [title=" + title + ", editor=" + editor + ", volume=" + volume + ", status=" + status + "]";
	}
	
	
}
