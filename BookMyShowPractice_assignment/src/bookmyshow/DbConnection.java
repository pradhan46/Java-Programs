package bookmyshow;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// initialize database connection with MySQL 
public class DbConnection {
	static Connection getConnection() throws SQLException ,ClassNotFoundException  {
		//using jdbc driver class to connect to database
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		//using getConnection method from DriverManager to establish connection with database 
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bms", "root", "root");
		return con;
		
	}

}
