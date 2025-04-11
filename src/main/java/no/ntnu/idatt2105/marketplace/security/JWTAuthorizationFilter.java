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
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;

/**
 * Filter for validating JWT tokens in incoming HTTP requests.
 * Extracts JWT tokens from Authorization headers, validates them,
 * and sets appropriate authentication in the Spring Security context.
 * This filter runs once per request, except for WebSocket connections.
 *
 * @author Konrad Seime
 * @version 1.0
 * @since 1.0
 * @see JWT_token
 * @see OncePerRequestFilter
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {

  private static final Logger LOGGER = LogManager.getLogger(JWTAuthorizationFilter.class);

  private final JWT_token jwtTokenService = new JWT_token();

  /**
   * Makes it so that the filter does not run for WebSocket requests.
   *
   * @param request the current HTTP request
   * @return true if the filter should not be applied, false otherwise
   * @throws ServletException if an exception occurs that interrupts the filter chain
   * @since 1.0
   */
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    return path.startsWith("/ws/chat/");
  }

  /**
   * Main filter method that processes each HTTP request.
   * Extracts the JWT token, validates it, and sets up authentication if valid.
   *
   * @param request the HTTP request being processed
   * @param response the HTTP response of the request
   * @param filterChain the filter chain for invoking the next filter
   * @throws ServletException if an exception occurs that interrupts the filter chain
   * @throws IOException if an I/O exception occurs during the processing
   * @since 1.0
   */
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

  /**
   * Extracts the JWT token from the Authorization header of the request.
   *
   * @param request the HTTP request containing the Authorization header
   * @return the extracted JWT token, or null if no valid token is found
   * @since 1.0
   */
  private String extractToken(HttpServletRequest request) {
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null || !header.startsWith("Bearer ")) {
      LOGGER.warn("No Authorization header found or invalid format.");
      return null;
    }
    return header.substring(7);
  }

  /**
   * Validates the JWT token and extracts the user ID from it.
   *
   * @param token the JWT token to validate
   * @return the user ID extracted from the token, or null if the token is invalid or expired
   * @since 1.0
   */
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

  /**
   * Sets up authentication in the Spring Security context for the given user ID.
   *
   * @param userId the ID of the authenticated user
   * @since 1.0
   */
  private void setAuthentication(String userId) {
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
        userId, null, Collections.emptyList()
    );
    SecurityContextHolder.getContext().setAuthentication(auth);

    LOGGER.info("Authentication set for user with id: {}", userId);
  }
}
