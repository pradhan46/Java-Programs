package bookmyshow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

//login class
public class Login {
	//validation of username and password
	static int verify() {
		Scanner in = new Scanner(System.in);
	    System.out.println("Enter username:");
	    String username = in.next();
	    System.out.println("Enter password:");
	    String pass = in.next();
	    int status = 0;
	   
	    //exception handling for database 
	    try {
	    	
	    	//initialization of connection 
	    	Connection conn = DbConnection.getConnection();
	    	PreparedStatement st = conn.prepareStatement("select role from users where username = ? and password = ?");
        	st.setString(1, username);
        	st.setString(2, pass);
        	ResultSet row = st.executeQuery();
        	
        	//checks usernmae and password exits
        	if(row.next())
        	{	
        		String role = row.getString(1);		
        		if(role.equals("user"))
        			status = 1;
        		else
        			status = 2;
        	}
        	
        	//closes the connection 
        	st.close();
        	conn.close();
	    }
	    catch(Exception e) {
	    	
	    	System.out.println("Unable to establish connection with the server.");
	    }
	    return status;               		//0:loginFail,1:userLogin,2:adminLogin
	}
}
