// JWT filter to generate JWT token and set it in response header

package com.axis.team4.codecrafters.user_service.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtGenratorFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		SecretKey key = Keys.hmacShaKeyFor(SecurityConstant.JWT_KEY.getBytes());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String jwt = Jwts.builder().setIssuer("chatwave").setSubject("jwt_token")
				.claim("username", authentication.getName())
				.claim("authorities", populateAuthorities(authentication.getAuthorities())).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 48900000)) // This is the expiration time.
				.signWith(key).compact(); // This is the JWT token.

		response.setHeader(SecurityConstant.HEADER, jwt);

		filterChain.doFilter(request, response);
	}

//This method populates the authorities.
	
	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> authoritiesSet = new HashSet<>();

		for (GrantedAuthority authority : collection) {
			authoritiesSet.add(authority.getAuthority());
		}
		return String.join(",", authoritiesSet);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getServletPath().equals("/signin");
		// return false;
	}
}
