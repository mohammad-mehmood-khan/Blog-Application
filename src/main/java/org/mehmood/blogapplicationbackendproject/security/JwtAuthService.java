package org.mehmood.blogapplicationbackendproject.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtAuthService {
    private static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60;
    @Value("${jwt.secret}")
    private String SECRET;

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder().subject(username).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getKey()).compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith((SecretKey) getKey())
                .build().parseSignedClaims(token).getPayload().getSubject();
    }

    // validate
    public boolean validate(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }

}
