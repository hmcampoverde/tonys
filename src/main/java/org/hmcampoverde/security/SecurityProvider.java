package org.hmcampoverde.security;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.text.ParseException;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecurityProvider {

	@Value("${application.security.jwt.secret-key}")
	private String secretKey;

	@Value("${application.security.jwt.time.expiration}")
	private int expirationTime;

	public String buildToken(Authentication authentication) {
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

		String role = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("");

		log.info("generated token");

		return Jwts.builder()
			.subject(user.getUsername())
			.claim("role", role)
			.issuedAt(new Date())
			.expiration(new Date(new Date().getTime() + (expirationTime * 1000)))
			.signWith(getSecretKey(secretKey))
			.compact();
	}

	public String refreshToken(String token) throws ParseException {
		try {
			Jwts.parser().verifyWith(getSecretKey(secretKey)).build().parseSignedClaims(token);
		} catch (ExpiredJwtException e) {
			log.error("token se refresco");

			JWT jwt = JWTParser.parse(token);
			JWTClaimsSet claims = jwt.getJWTClaimsSet();

			return Jwts.builder()
				.subject(claims.getSubject())
				.claim("role", claims.getClaim("role"))
				.issuedAt(new Date())
				.expiration(new Date(new Date().getTime() + (expirationTime * 1000)))
				.signWith(getSecretKey(secretKey))
				.compact();
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(getSecretKey(secretKey)).build().parseSignedClaims(token);
			return true;
		} catch (MalformedJwtException e) {
			log.error("token mal formado");
		} catch (UnsupportedJwtException e) {
			log.error("token no soportado");
		} catch (ExpiredJwtException e) {
			log.error("token expirado");
		} catch (IllegalArgumentException e) {
			log.error("token vacío");
		} catch (SignatureException e) {
			log.error("fail en la firma");
		}
		return false;
	}

	public String getUsername(String token) {
		return Jwts.parser().verifyWith(getSecretKey(secretKey)).build().parseSignedClaims(token).getPayload().getSubject();
	}

	private SecretKey getSecretKey(String secretKey) {
		byte[] bytes = Decoders.BASE64URL.decode(secretKey);
		return Keys.hmacShaKeyFor(bytes);
	}
}
