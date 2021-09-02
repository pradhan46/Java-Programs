package bookmyshow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

//register class  
public class Registration {
	
	static boolean register() {
		Scanner in = new Scanner (System.in);
		System.out.println("Enter username:");
		String uname = in.next();
		
		boolean registrationStatus = false;
		try {
	    	Connection conn = DbConnection.getConnection();
	    	
	    	//fetcher statment to check whether the username already exist
	    	PreparedStatement fetcherSt = conn.prepareStatement("select role from users where username = ?");
        	fetcherSt.setString(1, uname);
        	ResultSet row = fetcherSt.executeQuery();
        	if(!row.next()) {					//if no username found already then user is registered
	        	fetcherSt.close();
	        	System.out.println("Enter password:");
	        	String pass = in.next();
	        	PreparedStatement inserterSt = conn.prepareStatement("insert into users(username, password, role) values(?, ?, 'user')");
	        	inserterSt.setString(1, uname);
	        	inserterSt.setString(2, pass);
	        	inserterSt.executeUpdate(); 	//registering user 
	        	registrationStatus = true;
	        	inserterSt.close();
	        	conn.close();
        	}
		}
		catch(Exception e) {
			System.out.println("Unable to reach the server.");
		}
		return registrationStatus;
	}
}
