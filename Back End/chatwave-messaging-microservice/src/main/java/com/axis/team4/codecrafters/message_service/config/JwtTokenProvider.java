// The JwtTokenProvider class is responsible for generating and validating JWT tokens. It uses the Jwts class from the io.jsonwebtoken package to generate and validate tokens. The generateJwtToken method generates a JWT token using the email address from the authentication object and signs it with a secret key. The getEmailFromToken method extracts the email address from a JWT token. The validateToken method validates a JWT token by parsing it and checking for any exceptions. The populateAuthorities method extracts the authorities from the authentication object and returns them as a string. The JwtTokenProvider class is used by the JwtTokenFilter class to generate and validate JWT tokens for authentication and authorization purposes.

package com.axis.team4.codecrafters.message_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
  private final SecretKey key =
      Keys.hmacShaKeyFor(SecurityConstant.JWT_KEY.getBytes());

  public String generateJwtToken(Authentication authentication) {
    return Jwts.builder()
        .setIssuer("Chat Wave")
        .setIssuedAt(new Date())
        .setExpiration(
            new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
        .claim("email", authentication.getName())
        .signWith(key)
        .compact();
  }

  public String getEmailFromToken(String token) {
    try {
      System.out.println("Before claims: token received - " + token);

      Claims claims = Jwts.parserBuilder()
                          .setSigningKey(key)
                          .build()
                          .parseClaimsJws(token)
                          .getBody();

      return claims.get("email", String.class);
    } catch (Exception e) {
      System.err.println("Error parsing JWT token: " + e.getMessage());
      return null;
    }
  }

  public String populateAuthorities(
      Collection<? extends GrantedAuthority> authorities) {
    Set<String> authoritySet = new HashSet<>();
    for (GrantedAuthority authority : authorities) {
      authoritySet.add(authority.getAuthority());
    }
    return String.join(",", authoritySet);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
