package cohelp;				//shopping console application java

import java.util.HashMap;
import java.io.File;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Character;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Home{

	static HashMap <String, Integer> productName = new HashMap<String, Integer>() {{put("oxygen", 1000); put("medicine", 10000);}}; //using hashmap to store the quantity and price 

	static HashMap <String, Integer> productPrice = new HashMap<String,Integer>() {{put("oxygen", 250); put("medicine", 20);}};;

	public static boolean verify(String pass) {						//verifying  the password with conditions

		int count = 0;
		boolean digit = false, uppercase = false, lowercase = false, special = false; 

		if(pass.length() < 5 || pass.length() > 10)						//check for password length 5-10
			return false;

		int i = 0;

		while(i < pass.length() && count < 4) {
			if(Character.isDigit(pass.charAt(i))) {
				if(!digit) {
					digit = true;
					count++;
				}
			}
			else if(Character.isLetter(pass.charAt(i))) {			//checking for uppercase 
				if(Character.isUpperCase(pass.charAt(i))) {
					if(!uppercase) {
						uppercase = true;
						count++;
					}
				}
				else {
					if(!lowercase) {									//checking for lowercase 
						lowercase = true;
						count++;
					}	
				}
			}
			else {
				if(!special) {									 //checking for special characters
					special = true;
					count++;
				}
			}
			i++;
		}

		if(count == 4)
			return true;
		return false;
	}

	public static boolean authenticate() {								//authentication of login credentials
		Scanner in = new Scanner(System.in);

		boolean isRight;
		System.out.println("Enter you username:");
		String uname = in.next();

		System.out.println("Enter your password:");
		String pw = in.next();

		isRight = verify(pw);

		return isRight;
	}

	public static void login() {										//login method
		System.out.println("******Login to Co-Help****** ");			
		boolean successful = authenticate();

		if(successful) 
			listings();
		else {
			System.out.println("Please enter valid credentials");
			login();
		}
	}

	private static void register() {									//register method the credentials is not storing

		System.out.println("****Create new Account*****");

		boolean successful = authenticate();

		if(successful) 
			listings();
		else {
			System.out.println("Please enter valid password");
			register();
		}
	}

	public static void welcome() {											//welcome method (screen )

		Scanner sc = new Scanner(System.in);
		System.out.println("******Welcome to Co-Help*******");
		System.out.println("1.Login");
		System.out.println("2.Register");
		System.out.println("Enter your choice:");
		int choice = sc.nextInt();

		switch(choice) {												//checking for the user input

		case 1 : login(); break;

		case 2 : register(); break;								 

		default : welcome();
		}
	}

	public static void listings() {										// list method (list of option selected by the user)

		Scanner sc = new Scanner(System.in);

		System.out.println("3.Product");
		System.out.println("4.Order");
		System.out.println("5.Exit");

		System.out.println("Enter your choice:");
		int choice = sc.nextInt();

		switch(choice) {												//based on user input output will be displayed

		case 3 : search(); break;

		case 4 : order(); break;

		case 5 : welcome(); break;

		default : listings();
		}
	}

	public static void search() {									//search method

		Scanner sc = new Scanner(System.in);
		System.out.println("****Search Product***** ");				// based on user input product will be searched
		System.out.println("Product Name: ");						//product only oxygen and medicine added
		String prod = sc.next();

		System.out.println("Product Name: " + prod);
		System.out.println("Quantity Available: " + productName.get(prod));		//display product with available quantity and price
		System.out.println("Price: " + productPrice.get(prod));
		listings();
	}

	public static void order() {									//order method

		Scanner sc = new Scanner(System.in);
		System.out.println("*****Book Order*****");

		System.out.println("Please confirm the Product Name: ");			//confirms the product 
		String prod = sc.next();
		
		
		System.out.println("Please confirm the Quantity : ");				//input the quantity 
		int quantity = sc.nextInt();

		int availableQuantity = productName.get(prod);

		if(availableQuantity == 0)					//checking for availability of quantity 
			System.out.println("Out of stock");

		else if(availableQuantity < quantity) {
			System.out.println(quantity + " number of units are not available");
			order();
		}

		else {
			productName.put(prod, availableQuantity - quantity);
			System.out.println("Your order has been successfully placed!");		//after purchase success message is displayed


			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			String str = prod + "	" + quantity + "	" + productPrice.get(prod) + "	" + (quantity * productPrice.get(prod)) + "		" + dtf.format(now);                 
			System.out.print("\n\n\t *********************************    Co-Help Invoice   ******************************************");	//generating invoice in console
			 
			System.out.print("\n\t ___________________________________________________________________________________________________\n\n");
			System.out.format("\n\n\t   %-20s%-20s%-20s%-20s","Product Name"," Qty ","Price Rs","Date of purchase" );
		
				
			
			System.out.format("\n\n\t    %-20s%-20s%-20s%-20s",prod,quantity,(quantity * productPrice.get(prod)), dtf.format(now) );
			
			
			System.out.print("\n\t ___________________________________________________________________________________________________\n\n");
			System.out.print("\n\n\t *********************************    Thank You   ******************************************");
			
			System.out.println("\n\t......Generating invoice as file..... ");
			System.out.println("......Invoice saved in the given location.....");
			invoice(str);		//generating invoice as text file
			System.out.println("*****Logging off***** ");
			System.out.println("Thanks for choosing Co-Help ");
			System.out.println("Redirecting...");
		}
		listings();
	}

	
	public static void invoice(String str) {
		try {
			FileWriter myWriter = new FileWriter("C:\\Users\\pradhp\\Desktop\\test.txt");		// the invoice is generated as text file and saved in the given location
			myWriter.write("……………….. Invoice ………………………  \n");
			myWriter.write("|ProductName\tQuantity \tPrice\t\tDate of purchase \t");
			
			myWriter.write(str);
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");			//exception thrown if invoice is not generated properly
			e.printStackTrace();
		}
	}


	
	


	public static void main(String[] args) {		//main method

		welcome();			//return main welcome method
	}
}
