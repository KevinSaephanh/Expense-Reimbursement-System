package utils;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Claims;
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
		Date issuedAt = new Date(System.currentTimeMillis());
		long expiresIn = System.currentTimeMillis() + 3600000;
		Date expirationDate = new Date(expiresIn);

		// Sign token with API Key secret
		SignatureAlgorithm sa = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
		SecretKeySpec signingKey = new SecretKeySpec(apiKeySecretBytes, sa.getJcaName());

		// Build token and serialize it to a compact, URL-safe string
		String jwt = Jwts.builder().setIssuedAt(issuedAt).setSubject(user.getUsername()).setExpiration(expirationDate)
				.signWith(sa, signingKey).claim("role", user.getRoleId()).claim("id", user.getId()).compact();
		return jwt;
	}

	public static Claims verifyToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET)).parseClaimsJws(token)
				.getBody();
		return claims;
	}
}
