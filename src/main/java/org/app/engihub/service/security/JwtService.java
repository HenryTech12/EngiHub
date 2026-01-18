package org.app.engihub.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

public class JwtService {

    private String secretKey;
    public JwtService() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String createAccessToken(String email) {
        Map<String,String> claims = new HashMap<>();
        long expiration = 5 * 60 * 1000;
        claims.put("type","access");
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public String createRefreshToken(String email) {
        Map<String,String> claims = new HashMap<>();
        long expiration = 5 * 60 * 1000;
        claims.put("type","refresh");
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    public String getType() {
        return "access";
    }

    public <T>T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractEmail(String token) {
        return extractClaims(token,Claims::getSubject);
    }

    public Date getExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean isExpired(String token) {
        Date expiration = getExpiration(token);
        return (new Date(System.currentTimeMillis()).before(expiration));
    }

    public boolean verifyUser(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return Objects.equals(email,userDetails.getUsername());
    }
}

