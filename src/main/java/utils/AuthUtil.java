package utils;

import java.awt.RenderingHints.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthUtil {
	public static String hashPassword(String password) {
		String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
		return hashedPassword;
	}

	public static boolean comparePasswords(String candidatePassword, String hashedPassword) {
		BCrypt.Result result = BCrypt.verifyer().verify(candidatePassword.toCharArray(), hashedPassword);
		return result.verified == true;
	}

	public static String generateToken() {
		SignatureAlgorithm sa = SignatureAlgorithm.HS256;
		Date now = new Date(System.currentTimeMillis());
		long expiresIn = 3600000;
		Date expirationDate = new Date(expiresIn);

		// Sign token with ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("MAKE A SECRET KEY");
		SecretKeySpec signingKey = new SecretKeySpec(apiKeySecretBytes, sa.getJcaName());

		JwtBuilder builder = Jwts.builder().setIssuedAt(now).setSubject("Some subject").setIssuer("Some issuer")
				.setExpiration(expirationDate).signWith(sa, signingKey);

		// Build token and serialize it to a compact, URL-safe string
		return builder.compact();
	}

	public static Claims verifyToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("MAKE A SECRET KEY"))
				.parseClaimsJws(token).getBody();
		return claims;
	}
}
