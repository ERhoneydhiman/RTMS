package in.rtms.main.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

//	private String secret = "gfyU2H2sfjdu47gH2ksDf092kfSjghD28ksLsaLKJH23qXjhGhfjLkfGh27";
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expirationTime;

	// Generate token with given user name
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

	@SuppressWarnings("deprecation")
	public String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(getSignKey())
				.compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Retrieves all claims from the token using the updated parser
	@SuppressWarnings("deprecation")
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();

	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {

		final Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);

	}

	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);

	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);

	}

	public Boolean isTokenExpired(String token) {
		final Date expirationDate = getExpirationDateFromToken(token);
		return expirationDate.before(new Date());
	}

	// validate token
	public Boolean validateToken(String token, String usernameFromUserDetails) {
		final String username = getUserNameFromToken(token);
		return (username.equals(usernameFromUserDetails) && !isTokenExpired(token));
	}

}
