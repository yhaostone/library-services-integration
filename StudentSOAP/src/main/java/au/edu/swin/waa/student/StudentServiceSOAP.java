package au.edu.swin.waa.student;

import au.edu.swin.waa.dataAccess.DataAccess;

public class StudentServiceSOAP {

	private DataAccess dataAccess;

	public String authenticate(int studentId, int pin) {
		String response = "";
		dataAccess = new DataAccess();
		
		try {
			int studentCount = dataAccess.getStudentCount(studentId, pin);

			if (studentCount == 0) {
				response = " Error: Authentication failed, Please try again.";
			} else {
				response = "Authentication Successful";
			}	
		} catch (Exception e) {
			e.printStackTrace();
			response = "Exception: Something went wrong when communicating with database";
		}

		return response;

	}

}