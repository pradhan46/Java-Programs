package bookmyshow;

import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import bookmyshow.Login;

//class with main method
public class Home {

	public static void welcome() {
		//display menu options
		System.out.println("******Welcome to BookMyShow******* \n");
		System.out.println("1. Login \n");
		System.out.println("2. Register new account \n");
		System.out.println("3. Exit \n");

	}

	public static void entryPage() {
		welcome();			//calls welcome method
	
		Scanner in = new Scanner(System.in);
		
		boolean loggedIn = false, exit = false;
		while(true && !exit) {
			int choice = in.nextInt();
			
			//using switch for options to be selected by user
			switch(choice) {
			
			
			case 1: 	//for login
					int loginStatus = Login.verify();		//0:Fail 1:UserLogin 2:AdminLogin
				
					if(loginStatus == 0) {
						System.out.println("Invalid credentials. Press 1 to try logging in again");
					}
					else if (loginStatus == 1) {
						welcomeUser();
						loggedIn = true;
					}
					else {
						welcomeAdmin();
						loggedIn = true;
					}
				break; //break out of switch
			
			
			case 2:     //for register
				if(loggedIn)
					System.out.println("You have already registered");
				else {
					boolean registrationStatus = Registration.register();
					if(registrationStatus) {
						System.out.println("You are registered successfully");
						welcomeUser();
					}
					else {
						System.out.println("Cannot register you with the given credentials. Press 2 to register again.");
					}	
				}
				break;
				
			
			case 3: //Exit
				exit = true;
				loggedIn = false;
				System.out.println("You are logged out successfully. Thank you for using BookMyShow");
				System.exit(0);
				break;
				
			
			default: //invalid input
				
				System.out.println("Invalid option chosen.");
				
				//calls home method
				entryPage();
				break;
			}

		}

	}

	private static void welcomeAdmin() 
	{	// display admin page
		System.out.println("******Welcome Admin******* \r\n"
				+ "1. Add New Movie Info \r\n"
				+ "2. Edit Movie Title \r\n"
				+ "3. Delete Movies \r\n"
				+ "4. Logout \r\n"
				+ "");
		Scanner sc = new Scanner(System.in);
		int option = sc.nextInt();
		
		while(true)
		{	//using switch for selecting options
			switch (option) {
			
			//To add a movie
			case 1:{
				int id =0;
				System.out.println("Enter movie ID:");
				id = sc.nextInt();
				while(true)
				{	
					if(id<1 || id>4)                                  //checks for valid id
					{
						System.out.println("Invalid movie id. Press 0 to go back or enter valid movie ID.");
						id = sc.nextInt();
						if(id == 0)
							welcomeAdmin();
					}
					else
					{
						break;
					}
				}
				
				//movie details to be added
				System.out.println("Enter movie title: ");
				sc.nextLine();
				String title=sc.nextLine();
				System.out.println("Enter movie length: ");
				String length=sc.nextLine();
				System.out.println("Enter movie admin rating: ");
				String adminRating = sc.nextLine();
				
				//adds the details to the database
				Admin.addMovie(id, title, length,adminRating);
				
				//call welcome admin method after add method completes
				welcomeAdmin();
				break;
			}
			
			//To edit a movie
			case 2: 
				{
				int id;
				System.out.println("Enter movie ID:");
				id = sc.nextInt();
				while(true)
				{	
					//checks for valid id have given only 4id's
					if(id<1 || id>4)
					{
						System.out.println("Invalid movie id. Press 0 to go back or enter valid movie ID.");
						id = sc.nextInt();
						if(id == 0)
							welcomeAdmin();
					}
					else
					{
						break;
					}
				}
				//adds the details to the database
				System.out.println("Enter new title:");
				sc.nextLine();
				String title = sc.nextLine();
				Admin.editMovie(id, title);
				
				//calls welcome admin method after edit process is completed
				welcomeAdmin();
				break;
				}
			
			//To delete a movie
			case 3: {                                   
				int id;
				System.out.println("Enter movie ID:");
				id = sc.nextInt();
				while(true)
				{	
					//checks for valid id
					if(id<1 || id>4)
					{
						System.out.println("Invalid movie id. Press 0 to go back or enter valid movie ID.");
						id = sc.nextInt();
						if(id == 0) 
							welcomeAdmin();
					}
					else
					{
						break;
					}
				}
				Admin.deleteMovie(id);
				
				//calls welcome page of admin after delete process is complete
				welcomeAdmin();
				break;
			}
			
			//logout
			case 4: 
				entryPage();
				break;
				
			//if input is invalid from admin	
			default:
			
				System.out.println("Invalid option chosen.");
				welcomeAdmin();
				break;
			}
		}

	}

	private static void welcomeUser()
	{
		System.out.println("******Welcome User******* \r\n"
				+ "1. Movies \r\n"
				+ "0. Logout \r\n"
			);
		Scanner sc = new Scanner(System.in);
		
		
		ArrayList<Integer> ids = new ArrayList<Integer>();
		System.out.println("Enter your option:");
		int option = sc.nextInt();
		
		if(option == 0)
			entryPage();
		else if(option == 1) {
			ids = User.displayMovies();				//stores currently available movie ids in arraylist
			int choice = sc.nextInt();
			
			if(choice == 0);						//redirect to admin page
			else if(ids.contains(choice)) {
				System.out.println("Press 1 to rate this movie.");
				int rate = sc.nextInt();
				if(rate == 1) {
					System.out.println("Give your rating:");
					int rating = sc.nextInt();
					User.ratingUpdate(choice,rating);		//calls rating method
				}
			}
			else {
				System.out.println("Invalid option chosen");
			}
			welcomeUser();
		}
	}

	public static void main(String[] args) {	
		entryPage(); //displays home page
	}
}