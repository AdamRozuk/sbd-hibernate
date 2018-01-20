package sbd.adam.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String jdbcUrl="jdbc:mysql://localhost:3306/adam_tracker?useSSL=false";
		String user="adam";
		String pass="adam";
		
		try {
			System.out.println("Connecting to database: "+ jdbcUrl);
			
			Connection myConn=
					DriverManager.getConnection(jdbcUrl,user,pass);
			
			System.out.println("Connection succesfull!");
		}
		catch (Exception exc){
			exc.printStackTrace();
		}

	}

}
