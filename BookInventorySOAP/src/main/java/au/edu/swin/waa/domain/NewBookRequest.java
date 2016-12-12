package au.edu.swin.waa.domain;

public class NewBookRequest {
	
	private String studentId;
	private Book book;
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	

	
}
