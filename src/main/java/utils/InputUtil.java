package utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

public class InputUtil {
	private static final String USERNAME_PATTERN = "[a-zA-Z0-9\\\\._\\\\-]{2,20}";
	private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d).{7,50}$";
	private static final int saltRounds = 10;
	
	public static boolean isValidAmount(BigDecimal amount) {
		// Check if amount input is greater than 0
		if (amount.compareTo(new BigDecimal(0)) == 1)
			return true;
		return false;
	}
	
	public static boolean isValidUsername(String username) {
		boolean isMatch = getMatch(USERNAME_PATTERN, username);
		return isMatch;
	}
	
	public static boolean isValidPassword(String password) {
		boolean isMatch = getMatch(PASSWORD_PATTERN, password);
		return isMatch;
	}
	
	public static String hashPassword(String password) {
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(saltRounds));
		return hashedPassword;
	}
	
	public static boolean comparePasswords(String candidatePassword, String hashedPassword) {
		boolean isMatch = BCrypt.checkpw(candidatePassword, hashedPassword);
		return isMatch;
	}
	
	private static boolean getMatch(String inputPattern, String input) {
		Pattern pattern = Pattern.compile(inputPattern);
		Matcher matcher = pattern.matcher(input);
		boolean isMatch = matcher.matches();
		
		return isMatch;
	}
}
