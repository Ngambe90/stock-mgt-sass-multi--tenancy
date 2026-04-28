package com.ngambe.sass_stock.security;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.ngambe.sass_stock.config.JwtProperties;
import com.ngambe.sass_stock.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenService {

	private final JwtProperties jwtProperties;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	@PostConstruct
	public void init() {
		try {
			this.privateKey = loadPrivateKey(this.jwtProperties.getPrivateKeyPath());
			this.publicKey = loadPublicKey(this.jwtProperties.getPublicKeyPath());
		} catch ( final Exception e) {
			log.error("Error loading private key");
			throw new RuntimeException("Error loading private key",e);
		}
	}
	private PrivateKey loadPrivateKey(final String privateKeyPath) throws Exception{
		
		try (final InputStream is = JwtTokenService.class.getClassLoader().getResourceAsStream(privateKeyPath)){
			
			if(is==null) {
				throw new RuntimeException("private key not found");
			}
			 final String key = new String(is.readAllBytes());
			 final String privateKeyPem = key
					 .replace("-----BEGIN PRIVATE KEY-----", "")
					 .replace("-----END PRIVATE KEY-----", "")
					 .replaceAll("\\s", "");
			 final byte[] encoded = Base64.getDecoder().decode(privateKeyPem);
			 final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
			 return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
			 
		}
		
	}
	
	 public String generateAccessToken(final String tenantId, final String userId,final String role) {
		 final Date now= new Date();
		 final Date expiration = new Date(System.currentTimeMillis() + this.jwtProperties.getAccessTokenExpiration());
		 return Jwts.builder()
				 .subject(userId)
		 		 .claim("tenant_id", tenantId)
		 		 .claim("role", role)
		 		 .issuedAt(now)
		 		 .expiration(expiration)
		 		 .issuer("stock-saas-app")
		 		 .signWith(this.privateKey,Jwts.SIG.RS256)
		 		 .compact();
	 }
	 public String getUserIdFromToken(String token) {
		 final Claims claims = getClaimsFromToken(token);
		 return claims.getSubject();
	 }
	private Claims getClaimsFromToken(String token) {
		
		return Jwts.parser()
				.verifyWith(this.publicKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public String getTenantIdFromToken(final String token) {
		final Claims claims = getClaimsFromToken(token);
		return claims.get("tenant_id", String.class);
	}
	public String getRoleFromToken(final String token) {
		final Claims claims = getClaimsFromToken(token);
		return claims.get("role", String.class);
	}
	
	public boolean validateToken(final String token) {
		try {
			Jwts.parser()
				.verifyWith(this.publicKey)
				.build()
				.parseSignedClaims(token);
			return true;
		} catch (final ExpiredJwtException e) {
			throw new UnauthorizedException("Token has expire");
		}catch(final UnsupportedOperationException e) {
			throw new UnauthorizedException("Token is not signed");
		}catch (final MalformedJwtException e) {
			throw new UnauthorizedException("Token is malformed");
		}catch (final SecurityException e) {
			throw new UnauthorizedException("Invalid JWT Signature");
		}catch (final IllegalArgumentException e) {
			throw new UnauthorizedException(" JWT Claims String is empty");
		}
	}
	private PublicKey loadPublicKey(String publicKeyPath) throws Exception{
			try (final InputStream is = JwtTokenService.class.getClassLoader().getResourceAsStream(publicKeyPath)){
			
			if(is==null) {
				throw new RuntimeException("public key not found");
			}
			 final String key = new String(is.readAllBytes());
			 final String publicKeyPem = key
					 .replace("-----BEGIN PUBLIC KEY-----", "")
					 .replace("-----END PUBLIC KEY-----", "")
					 .replaceAll("\\s", "");
			 final byte[] encoded = Base64.getDecoder().decode(publicKeyPem);
			 final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
			 return KeyFactory.getInstance("RSA").generatePublic(keySpec);
			 
		}
	}

	
}
