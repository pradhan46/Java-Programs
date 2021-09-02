package bookmyshow;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//class admin
public class Admin
{
	static int addMovie(int id, String title, String movieLength,String adminRating)
	{
		int status = 0;
		try {
		Connection conn= DbConnection.getConnection();
		
		//stores the new movies into database
		PreparedStatement fetcherSt = conn.prepareStatement("insert into movies(id,title,movieLength,adminRating,userRating, usersRated) values(?,?,?,?,'0','0')");
		fetcherSt.setInt(1, id);
		fetcherSt.setString(2, title);
		fetcherSt.setString(3, movieLength);
		fetcherSt.setString(4, adminRating);
		status=fetcherSt.executeUpdate();
		fetcherSt.close();                	 //close the connection
		conn.close();
		}
		catch (Exception e) {
		
		}
		return status;
	}

	static void editMovie(int id, String newTitle)
	{
		try {
			Connection conn = DbConnection.getConnection();
			
			//updates movie title in the database
			PreparedStatement updateSt = conn.prepareStatement("UPDATE movies SET title = ? WHERE id = ?");	
			
			updateSt.setString(1, newTitle);
			updateSt.setInt(2, id);
			
			int edit = updateSt.executeUpdate();
			
			if(edit == 1)		
				System.out.println("Movie title updated successfuly");
			
			else
				System.out.println("No movie found for the given ID");	
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	static int deleteMovie(int id)
	{
		try {
		Connection conn = DbConnection.getConnection();
		
		//deletes movie from the database
		PreparedStatement deleteSt = conn.prepareStatement("DELETE FROM movies where id = ?");	
		deleteSt.setInt(1, id);
		int delete = deleteSt.executeUpdate();    	 
		
		if(delete == 1)
		{
			System.out.println("Movie deleted successfully");
		}
		else
		{
			System.out.println("No movie found for the given ID");
		}
		}
		catch (Exception e) {
			System.out.println(e);
		}

		return 0;
	}

	
	
}
