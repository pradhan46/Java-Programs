package selenium;

import java.util.Scanner;

public class BinomialCoefficients {

	
	public static void main(String[] args) {
		try {
			int n = 0 , r=0;
			Scanner sc = new Scanner(System.in);
			System.out.println("enter value of n and r");
			
			n=sc.nextInt();
			r=sc.nextInt();
			
			System.out.println("binomial coeffient is "+ c(n,r));
			
		}
		
		catch (NegativeValueException e) {  // Catch any exception
			System.err.println(e.getMessage());
		}
	}

	public static int c(int n, int r) throws NegativeValueException {
		if (n < 0 || r < 0) {
			throw new NegativeValueException();
		}
	
		if (r == 0) {					//  r = 0, nCr = (n!)/r!(n-r)! = n!/(1*n!) = 1
			return 1;
		}
		
		else if (r == n) {				// r = n, nCr = (n!)/r!(n-r)! = n!/(n!*1) = 1
			return 1;
		}
		
		else if (r > n) {				//  r > n, return 0
			return 0;
		} else {
			return c(n - 1, r - 1) + c(n - 1, r);
		}
	}
	

}