package selenium;
public class NegativeValueException extends Exception { //for negative exception " -1 ,-2.."
	public NegativeValueException() {
	}

	public String getMessage() {
		return "Negative values cannot be inputted for this function - input natural numbers only ";
	}

}