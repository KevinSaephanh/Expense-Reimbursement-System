package utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
	private static final String USERNAME_PATTERN = "[a-zA-Z0-9\\\\._\\\\-]{2,20}";
	private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d).{7,50}$";
	
	public static boolean isValidAmount(BigDecimal amount) {
		// Check if amount input is greater than 0
		if (amount.compareTo(new BigDecimal(0)) == 1)
			return true;
		return false;
	}
	
	public static boolean isValidUsername(String username) {
		// Check if username matches a specific pattern
		if (getMatch(USERNAME_PATTERN, username))
			return true;
		return false;
	}
	
	public static boolean isValidPassword(String password) {
		// Check if password matches a specific pattern
		if (getMatch(PASSWORD_PATTERN, password))
			return true;
		return false;
	}
	
	public static boolean comparePasswords(String input, String password) {
		return false;
	}
	
	private static boolean getMatch(String inputPattern, String input) {
		Pattern pattern = Pattern.compile(inputPattern);
		Matcher matcher = pattern.matcher(input);
		boolean match = matcher.matches();
		
		return match;
	}
}
