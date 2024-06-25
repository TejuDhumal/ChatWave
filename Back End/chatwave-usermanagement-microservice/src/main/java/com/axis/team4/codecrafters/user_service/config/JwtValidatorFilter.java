// JWT token validator filter class

package com.axis.team4.codecrafters.user_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtValidatorFilter extends OncePerRequestFilter {
  private static final Logger logger =
      LoggerFactory.getLogger(JwtValidatorFilter.class);

  private final SecretKey key =
      Keys.hmacShaKeyFor(SecurityConstant.JWT_KEY.getBytes());

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String jwt = getJwtFromRequest(request);

    if (jwt != null && !jwt.isEmpty()) {
      try {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(jwt)
                            .getBody();

        String username = claims.getSubject();
        String authorities = (String) claims.get("authorities");

        List<GrantedAuthority> auths =
            AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
        Authentication authentication =
            new UsernamePasswordAuthenticationToken(username, null, auths);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.info("JWT validated for user: {}", username);
      } catch (Exception e) {
        logger.error("Invalid JWT token: {}", e.getMessage());
        throw new BadCredentialsException("Invalid token", e);
      }
    } else {
      logger.warn("JWT token is null or empty");
    }

    filterChain.doFilter(request, response);
  }
//This method is used to get the JWT token from the request.
  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(SecurityConstant.HEADER);
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return request.getServletPath().equals("/signin");
  }
}
