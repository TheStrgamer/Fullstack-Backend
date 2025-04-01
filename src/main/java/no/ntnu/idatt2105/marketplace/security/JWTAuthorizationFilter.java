package no.ntnu.idatt2105.marketplace.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;

import no.ntnu.idatt2105.marketplace.service.JWT_token;
import no.ntnu.idatt2105.marketplace.model.user.User;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

  private static final Logger LOGGER = LogManager.getLogger(JWTAuthorizationFilter.class);

  private final JWT_token jwtTokenService = new JWT_token();

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    LOGGER.info("Processing request: {}", request.getRequestURI());

    String token = extractToken(request);
    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }

    String userEmail = validateTokenAndGetUserEmail(token);
    if (userEmail == null) {
      LOGGER.warn("Invalid or expired token, user not authenticated.");
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      return;
    }

    LOGGER.info("Token validated for user: {}", userEmail);

    setAuthentication(userEmail);

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null || !header.startsWith("Bearer ")) {
      LOGGER.warn("No Authorization header found or invalid format.");
      return null;
    }
    return header.substring(7);
  }

  private String validateTokenAndGetUserEmail(final String token) {
    if (jwtTokenService.validateJwtToken(token)) {
      return jwtTokenService.extractEmailFromJwt(token);
    } else {
      LOGGER.warn("Token is invalid or expired.");
      return null;
    }
  }

  private void setAuthentication(String userEmail) {
    User user = jwtTokenService.getUserByToken(userEmail);
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
        userEmail, null, Collections.emptyList()
    );
    SecurityContextHolder.getContext().setAuthentication(auth);

    LOGGER.info("Authentication set for user: {}", userEmail);
  }
}
