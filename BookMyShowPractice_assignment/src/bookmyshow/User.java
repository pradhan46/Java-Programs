package bookmyshow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;


//class user
public class User {
	
	static ArrayList<Integer> displayMovies() {
		
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ArrayList<ArrayList<String>> movies = new ArrayList<ArrayList<String>>();
		try {
			Connection conn= DbConnection.getConnection();
			Statement fetcherSt = conn.createStatement();
			ResultSet rows = fetcherSt.executeQuery("SELECT id, title, adminRating FROM movies");
			while(rows.next()) {                 //to store available movies in arraylist<arraylist>
				ArrayList<String> movie = new ArrayList<String>();
				String id = String.valueOf(rows.getInt(1));
				ids.add(rows.getInt(1));
				String title = rows.getString(2);
				String adminRating = rows.getString(3);
				movie.add(id);
				movie.add(title);
				movie.add(adminRating);
				movies.add(movie);				//add 1 movie details
			}
			for(int i = 0; i < movies.size(); i++) {		//to print movie details
				for(int j = 0; j < movies.get(i).size(); j++) {
					System.out.print(movies.get(i).get(j) + "    ");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("Press 0 to go back");
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return ids;				//returns IDs of all available movies
	}
	
	static void ratingUpdate(int id, int rating) {
		if(rating < 0 || rating > 10) {		//checks validity of rating
			System.out.println("Please enter value between 0 and 10");
			return;
		}
		try {
			Connection conn= DbConnection.getConnection();
			PreparedStatement fetcherSt = conn.prepareStatement("SELECT userRating,usersRated FROM movies WHERE id = ?");
			fetcherSt.setInt(1, id);
			ResultSet rows = fetcherSt.executeQuery();
			int users = 0;
			float userRating = 0;
			if(rows.next()) {
				userRating = Float.parseFloat(rows.getString(1));	//current average user rating
				users = Integer.parseInt(rows.getString(2));		//no. of users rated 
			}
			userRating = (userRating * users + rating) / (users + 1);	//new avg user rating
			users = users + 1;										//updated no of users who have rated the movie
			PreparedStatement updateSt = conn.prepareStatement("UPDATE movies SET userRating = ?, usersRated = ? WHERE id = ?");	
			updateSt.setString(1, String.valueOf(userRating));
			updateSt.setString(2, String.valueOf(users));
			updateSt.setInt(3, id);
			int count = updateSt.executeUpdate();
			if(count == 1) {
				System.out.println("Ratings updated successfully");
			}
			else {
				System.out.println("Unable to reach the server");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
