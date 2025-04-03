package no.ntnu.idatt2105.marketplace.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.ntnu.idatt2105.marketplace.exception.TokenExpiredException;
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

    String userId = validateTokenAndGetUserId(token);
    if (userId == null) {
      LOGGER.warn("Invalid or expired token, user not authenticated.");
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      return;
    }

    LOGGER.info("Token validated for user: {}", userId);

    setAuthentication(userId);
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

  private String validateTokenAndGetUserId(final String token) {
    try {
      jwtTokenService.validateJwtToken(token);
      return jwtTokenService.extractIdFromJwt(token);
    } catch (TokenExpiredException e) {
      LOGGER.warn("Token has expired.");
      return null;
    } catch (IllegalArgumentException e) {
      LOGGER.warn("Token is invalid.");
      return null;
    } catch (Exception e) {
      LOGGER.warn("Token is empty.");
      return null;
    }
  }

  private void setAuthentication(String userId) {
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
        userId, null, Collections.emptyList()
    );
    SecurityContextHolder.getContext().setAuthentication(auth);

    LOGGER.info("Authentication set for user with id: {}", userId);
  }
}
