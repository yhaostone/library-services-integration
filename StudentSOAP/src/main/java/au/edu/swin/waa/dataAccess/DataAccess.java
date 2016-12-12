package au.edu.swin.waa.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataAccess {
	
	private Connection connection;
	private String url =  "jdbc:mysql://localhost:3306/student_db";
	
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
	
	public int getStudentCount(int studentId, int pin) throws Exception {
		Statement stmt = connection.createStatement();
		ResultSet rs=stmt.executeQuery("select count(*) "
				+ "from student "
				+ "where student_id="+studentId+" "
				+ "and pin="+pin);
	
		rs.next();
		return rs.getInt(1); 

	}

}
