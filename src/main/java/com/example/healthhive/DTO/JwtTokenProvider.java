package com.example.healthhive.DTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenProvider {

    private final String jwtSecret = "f8d4ff6f1cfdd69bc8d625e0a1e845b937d295e6e4568a98dff9bdfb3fc51e3764b15d07fd34f4a4778b1f7ba1ed758f39abdd5d951c5eb1b3a85b82b1f7bcbde";
    // Secret key for signing the JWT
    private final int jwtExpirationInMs = 604800000; // 7 days expiration time




    // Generate JWT token
    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        System.out.println(email);
        return Jwts.builder()
                .setSubject(email) // Set the subject (user)
                .setIssuedAt(new Date()) // Set the issue date
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationInMs)) // Set expiration date (7 days)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)  // Sign with HS512 algorithm and secret key
                .compact(); // Compact to generate the JWT
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser() // Updated to use parserBuilder() instead of deprecated parser()
                .setSigningKey(jwtSecret) // Set the signing key
                .build() // Build the JwtParser
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Get the body (claims) from the parsed token
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token);  // If the token is invalid or expired, this will throw an exception
            return true;
        } catch (Exception e) {
            // Log or handle the exception (expired or malformed token)
            return false;
        }
    }


    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();  // Extract the username (subject) from the token
    }
}
