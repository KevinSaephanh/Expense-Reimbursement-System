package utils;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.User;

public class AuthUtil {
	public static final String SECRET = System.getenv("SECRET");
	
	public static String hashPassword(String password) {
		String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
		return hashedPassword;
	}

	public static boolean comparePasswords(String candidatePassword, String hashedPassword) {
		BCrypt.Result result = BCrypt.verifyer().verify(candidatePassword.toCharArray(), hashedPassword);
		return result.verified == true;
	}

	public static String generateToken(User user) {
		SignatureAlgorithm sa = SignatureAlgorithm.HS256;
		Date now = new Date(System.currentTimeMillis());
		long expiresIn = 3600000;
		Date expirationDate = new Date(expiresIn);

		// Sign token with ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
		SecretKeySpec signingKey = new SecretKeySpec(apiKeySecretBytes, sa.getJcaName());

		// Build token and serialize it to a compact, URL-safe string
		JwtBuilder builder = Jwts.builder().setIssuedAt(now).setSubject(user.getUsername())
				.setExpiration(expirationDate).signWith(sa, signingKey);
		return builder.compact();
	}

	public static Claims verifyToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
				.parseClaimsJws(token).getBody();
		return claims;
	}
}
