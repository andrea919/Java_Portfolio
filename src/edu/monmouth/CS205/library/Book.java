package edu.monmouth.CS205.library;

public class Book implements LibraryItem {
	private String title;
	private String author;
	private int pages;
	private StatusType status;
	
	public Book(String author, String title, int pages, StatusType status ) throws BookException {
		setAuthor(author);
		setTitle(title);
		setPages(pages);
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
			System.out.println("Book already on shelf.");
			return;
		}
		setStatus(StatusType.ONSHELF);
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) throws BookException {
		if(author == null || author.length() < BookConstants.MINAUTHORLENGTH) {
			throw new BookException("Author cannot be null or have no characters.");
		}
		this.author = author;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) throws BookException {
		if(pages < BookConstants.MINPAGES) {
			throw new BookException("Pages cannot be negative.");
		}
		this.pages = pages;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public void setTitle(String title) throws BookException {
		if(title == null || title.length() < BookConstants.MINTITLELENGTH) {
			throw new BookException("Title cannot be null or have no characters.");
		}
		this.title = title;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", pages=" + pages + ", status=" + status + "]";
	}
	
	

}
