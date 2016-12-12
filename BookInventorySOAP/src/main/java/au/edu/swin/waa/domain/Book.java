package au.edu.swin.waa.domain;

public class Book {
	
	private String bookId;
	private String bookTitle;
	private String authorList;
	private String isbn10;
	private String isbn13;
	private String publisherName;
	private String publishDate;
	private String bookStatus;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getAuthorList() {
		return authorList;
	}

	public void setAuthorList(String authorList) {
		this.authorList = authorList;
	}

	public String getIsbn10() {
		return isbn10;
	}

	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

}
