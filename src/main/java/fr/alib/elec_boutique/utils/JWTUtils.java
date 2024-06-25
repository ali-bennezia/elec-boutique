package fr.alib.elec_boutique.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
@ConfigurationProperties(prefix="jwt")
@ConfigurationPropertiesScan
public class JWTUtils {
	@Value("${jwt.secret-key}")
	private String secretKey;
	private final long expirationTime = 864_000_000;
	private final long longExpirationTime = 864_000_000*3;
	
	
	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(String username, Boolean rememberMe) {
		return Jwts.builder()
				.subject(username)
				.expiration(new Date(System.currentTimeMillis() + (rememberMe ? longExpirationTime : expirationTime)))
				.signWith(getSigningKey())
				.compact();
	}
	
	public String extractUsername(String token) {
		try {
			return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
		} catch (JwtException jwtEx) {
			return null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
	}

	public long getExpirationTime() {
		return expirationTime;
	}
	public long getLongExpirationTime() {
		return longExpirationTime;
	}
}
