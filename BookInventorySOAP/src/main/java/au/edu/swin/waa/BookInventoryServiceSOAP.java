package au.edu.swin.waa;

import au.edu.swin.waa.dataAccess.DataAccess;
import au.edu.swin.waa.domain.Book;

public class BookInventoryServiceSOAP{
	
	private DataAccess dataAccess;

	
	public String addBook(String bookTitle, 
			String authorList,
			String isbn10,
			String isbn13,
			String publisherName,
			String publishDate,
			String bookStatus) throws Exception {
		String addBookResponse = "";
		try {
			
			System.out.println("Inside AddBook to add a book for bookTitle" + bookTitle);
			dataAccess = new DataAccess();

			dataAccess.addBook(bookTitle, 
					authorList,
					isbn10,
					isbn13,
					publisherName,
					publishDate,
					bookStatus);

			// TODO - move this to dataAccess class
			dataAccess.closeConnection();
			return addBookResponse = "Positive";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return addBookResponse = "Error: "+e.getMessage();
		}
	}
	
	
	public String validateBookOrder(String isbn) throws Exception {
		String response = "";

		try {
			System.out.println("Inside validateBookOrder for Isbn" + isbn);
			dataAccess = new DataAccess();

			response = dataAccess.isValidNewBookRequest(isbn);
			// TODO - move this to dataAccess class
			dataAccess.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			response = "Something went wrong when processing request, Please try again.";
		}
		System.out.println("validateBookOrder response" + response);
		return response;
	}

}