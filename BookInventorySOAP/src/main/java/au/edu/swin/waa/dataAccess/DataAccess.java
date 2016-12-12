package au.edu.swin.waa.dataAccess;

import java.sql.*;
import java.util.ArrayList;

import au.edu.swin.waa.domain.*;

public class DataAccess {

	private Connection connection;
	private String url = "jdbc:mysql://localhost:3306/book_db";

	public DataAccess() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, "root", "1234");
		} catch (Exception e) {
			// TODO implement logging
			e.printStackTrace();
		}
	}

	public void closeConnection() throws Exception {
		connection.close();
	}

	public ArrayList<Book> getAllBooks() throws Exception {
		ArrayList<Book> books = new ArrayList<Book>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM book");

		while (resultSet.next()) {
			Book book = new Book();
			book.setBookId(resultSet.getString("book_id"));
			book.setBookTitle(resultSet.getString("book_title"));
			book.setAuthorList(resultSet.getString("author_list"));
			book.setIsbn10(resultSet.getString("isbn_10"));
			book.setIsbn13(resultSet.getString("isbn_13"));
			book.setPublishDate(resultSet.getString("publish_date"));
			book.setPublisherName(resultSet.getString("publisher_name"));
			book.setBookStatus(resultSet.getString("book_status"));

			books.add(book);
		}

		return books;
	}

	public ArrayList<StudentRecord> getStudentRecords(Integer studentId)
			throws Exception {
		ArrayList<StudentRecord> studentRecords = new ArrayList<StudentRecord>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT isbn_10,book_title," + "borrow_status "
						+ "FROM book,borrow_details "
						+ "WHERE book.book_id=borrow_details.book_id "
						+ "AND borrow_details.student_id=" + studentId);

		while (resultSet.next()) {
			StudentRecord studentRecord = new StudentRecord();
			studentRecord.setBookTitle(resultSet.getString("book_title"));
			studentRecord.setIsbn10(resultSet.getString("isbn_10"));
			studentRecord.setBorrowStatus(resultSet.getString("borrow_status"));

			studentRecords.add(studentRecord);
		}

		return studentRecords;
	}

	public Book getBook(Integer bookId) throws Exception {
		Book book = new Book();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * FROM book WHERE book_id=" + bookId);

		while (resultSet.next()) {

			book.setBookId(resultSet.getString("book_id"));
			book.setBookTitle(resultSet.getString("book_title"));
			book.setAuthorList(resultSet.getString("author_list"));
			book.setIsbn10(resultSet.getString("isbn_10"));
			book.setIsbn13(resultSet.getString("isbn_13"));
			book.setPublisherName(resultSet.getString("publisher_name"));
			book.setPublishDate(resultSet.getString("publish_date"));
			book.setBookStatus(resultSet.getString("book_status"));
		}

		return book;
	}

	public void  addBook(String bookTitle, 
			String authorList,
			String isbn10,
			String isbn13,
			String publisherName,
			String publishDate,
			String bookStatus) throws Exception {
		try {
			connection.setAutoCommit(false);
			
			//TODO -change this to prepared statement
			Statement statement = connection.createStatement();
			String values = "'" + bookTitle + "' , " + "'" + authorList
					+ "' , " + "'" + isbn10 + "' , " + "'"
					+ isbn13 + "' , " + "'" + publisherName
					+ "' , " + "'" + publishDate + "' , " + "'"
					+ bookStatus + "'";

			System.out.println(values);
			// insert new book
			statement.executeUpdate("INSERT INTO "
					+ "book(book_title,author_list,"
					+ "isbn_10,isbn_13,publisher_name,"
					+ "publish_date,book_status) VALUES(" + values + ")");

			connection.commit();

		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}
	
	public Book updateBook(Book book) throws Exception {

		try {
			connection.setAutoCommit(false);
			
			//TODO -change this to prepared statement
			Statement statement = connection.createStatement();
			String values = "'" + book.getBookId() + "' , " + "'"
					+ book.getBookTitle() + "' , " + "'" + book.getAuthorList()
					+ "' , " + "'" + book.getIsbn10() + "' , " + "'"
					+ book.getIsbn13() + "' , " + "'" + book.getPublisherName()
					+ "' , " + "'" + book.getPublishDate() + "' , " + "'"
					+ book.getBookStatus() + "'";

			System.out.println(values);
			// insert new book
			String value = "update book set book_title='"+book.getBookTitle()+"', "
					+ "author_list='"+book.getAuthorList()+"',"
					+ "isbn_10='"+book.getIsbn10()+"',"
					+ "isbn_13='"+book.getIsbn13()+"',"
					+ "publisher_name='"+book.getPublisherName()+"',"
					+ "publish_date='"+book.getPublishDate()+"',"
					+ "book_status='"+book.getBookStatus()+"'"
					+ "where book_id='"+book.getBookId()+"'";
			
			System.out.println("Upadte sql value"+value);
			statement.executeUpdate(value);

			connection.commit();
			
			Book result = getBook(new Integer(book.getBookId()));
			
			return result;

		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}
	
	/**
	 * It checks book's validity.  If book is invalid (book id not proper or book is not available then appropriate message is 
	 *  returned)
	 *  If book is valid then it updates book's and borowDetails' records.
	 * @param bookId
	 * @param studentId
	 * @return Validation message (successful or error message)
	 */
	public String borrowBook(int bookId, int studentId)throws Exception {
		String responce="";
		
		Statement stmt = connection.createStatement();
		//check whether book id is valid or not
		ResultSet rs=stmt.executeQuery("select count(*) from book where book_id='"+bookId+"'");
		
		rs.next();
		int count=rs.getInt(1);
		
		if(count==0)
			responce="Book Id not valid, Please enter again";
		else
		{
			//check whether book status is available
			rs=stmt.executeQuery("select count(*) from book where book_id='"+bookId+"' and book_status='available'");
			
			rs.next();
			count=rs.getInt(1);
			
			if(count!=1)//not available
				responce="Requested book is not available.";
			else{ 
				try{
					
					//start transaction
					connection.setAutoCommit(false);
				   
					//update book status
					stmt.executeUpdate("update book set book_status='on loan' where book_id='"+bookId+"'");
				   
					//insert borrow details (insert borrowed in status)
					stmt.executeUpdate("insert into borrow_details(student_id,book_id,borrow_status) values("+studentId+", '"+bookId+"' , 'borrowed')");
                    
					connection.commit(); //commit transaction
					
				}
				catch(Exception e)
				{
					connection.rollback(); //rollback transaction

					throw e;//throw exception
				}
				
				responce="You have successfully borrowed the book";
			}
		}
		return responce;
	}
	
	/**
	 * It checks whether book is already there in book table (any status)
	 * @return negative if book exists in database else positive (so that new book can be requested/ordered)
	 * @param isbn
	 */
	public String isValidNewBookRequest(String isbn)throws Exception {
		ResultSet rs;
		String validationResponse="";		
		Statement stmt = connection.createStatement();
		//check whether book id is a valid or not
		//isbn10
		if(isbn.length()==10){ 
			rs=stmt.executeQuery("select * from book where isbn_10="+isbn);
		}else {
			rs=stmt.executeQuery("select * from book where isbn_13="+isbn);
		}
		
		if(rs.next()){  
			String status=rs.getString("book_status");		
			//back order
			if(status.contains("order")){
				//do not order new book
				validationResponse="Negative,The book has already been orderd.";
			}else{ 
				//Book is already present in library.
				validationResponse="Negative,The library contains requested book.";//do not order new book	
			}
		}else{ 
			//empty set
			validationResponse="Positive";  //order new book
		}
		return validationResponse;
	}
	
	/**
	 * It add new book in book table.
	 * @param book
	 * @param borrowDetails
	 */
	public void placeNewBookOrder(NewBookRequest newBookRequest)throws Exception {

		//start transaction
		try{
		connection.setAutoCommit(false);   
		Statement stmt = connection.createStatement();
		
		String values="'"+newBookRequest.getBook().getBookId()+"' , "+
					"'"+newBookRequest.getBook().getBookTitle()+"' , "+
					"'"+newBookRequest.getBook().getAuthorList()+"' , "+
					"'"+newBookRequest.getBook().getIsbn10()+"' , "+
					"'"+newBookRequest.getBook().getIsbn13()+"' , "+
					"'"+newBookRequest.getBook().getPublisherName()+"' , "+
					"'"+newBookRequest.getBook().getPublishDate()+"' , "+
					"'"+newBookRequest.getBook().getBookStatus()+"'";
		//insert new book
		System.out.println(values);		
		stmt.executeUpdate("insert into book(book_id,book_title,author_list,isbn_10,isbn_13,publisher_name,publish_date,book_status) values("+values+")");
						   
		//insert borrow details (insert borrowed in status)
		String status = "requested";
		stmt.executeUpdate("insert into borrow_details(student_id,book_id,borrow_status) values("+newBookRequest.getStudentId()+",'"+newBookRequest.getBook().getBookId()+"','"+ status +"')");
        
		//commit transaction
		connection.commit(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			connection.rollback();
			throw e;
		}
	}

}
