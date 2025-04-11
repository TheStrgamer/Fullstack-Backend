package no.ntnu.idatt2105.marketplace.security;

import java.util.Optional;

import no.ntnu.idatt2105.marketplace.controller.ListingController;
import no.ntnu.idatt2105.marketplace.model.negotiation.Conversation;
import no.ntnu.idatt2105.marketplace.repo.ConversationRepo;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.http.HttpHeaders;

import java.util.Map;

/**
 * Service that implements WebSocket authorization by intercepting the handshake process.
 * This interceptor validates JWT tokens and ensures that only authorized users
 * can establish WebSocket connections to specific chat conversations.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 * @see HandshakeInterceptor
 * @see JWT_token
 * @see Conversation
 */
@Service
public class WebSocketAuthorization implements HandshakeInterceptor {

  private ConversationRepo conversationRepo;

  private JWT_token jwt;
  
  private static final Logger LOGGER = LogManager.getLogger(ListingController.class);

  /**
   * Constructs a WebSocketAuthorization with the required dependencies.
   *
   * @param jwt the JWT token service for validating tokens
   * @param conversationRepo the repository for accessing conversation data
   * @since 1.0
   */
  @Autowired
  public WebSocketAuthorization(JWT_token jwt,
      ConversationRepo conversationRepo) {
    this.jwt = jwt;
    this.conversationRepo = conversationRepo;
  }


  /**
   * Handles the WebSocket handshake process. Validates the JWT token and checks if the user is authorized to access the chat.
   *
   * @param request the HTTP request during the handshake
   * @param response the HTTP response during the handshake
   * @param wsHandler the WebSocket handler
   * @param attributes the map of attributes
   * @return true if the handshake is successful, false otherwise
   * @throws Exception if an error occurs during the handshake process
   * @since 1.0
   */
  @Transactional
  @Override
  public boolean beforeHandshake(ServerHttpRequest request,
      ServerHttpResponse response,
      WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    try {
      LOGGER.info("=== WebSocket Handshake Started ===");
      LOGGER.info("Request URI: " + request.getURI());
      LOGGER.info("Headers: " + request.getHeaders());

      String path = request.getURI().getPath();

      String chatId = path.substring(path.lastIndexOf('/') + 1);

      String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

      if (token == null || !token.startsWith("Bearer ")) {
        if (request instanceof ServletServerHttpRequest) {
          token = ((ServletServerHttpRequest) request).getServletRequest().getParameter("token");
          if (token != null) {
            token = "Bearer " + token;
          }
        }
      }

      if (token != null && token.startsWith("Bearer ")) {
        String jwtToken = token.substring(7);

        String userId = validateJwtToken(jwtToken);

        if (userId != null && isUserInChat(userId, chatId)) {
          LOGGER.info("User with id " + userId + " authorized for chat");
          attributes.put("chatId", chatId);
          attributes.put("userId", userId);
          return true;
        }
      }
      LOGGER.info("Authorization failed");
      response.setStatusCode(HttpStatus.UNAUTHORIZED);
      return false;
    } catch (Exception e) {
      LOGGER.info("Error during WebSocket handshake: " + e.getMessage());
      response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
      return false;
    }
  }

  /**
   * Handles the WebSocket handshake completion process. This method is called after the handshake is completed.
   *
   * @param request the HTTP request after the handshake
   * @param response the HTTP response after the handshake
   * @param wsHandler the WebSocket handler
   * @param ex the exception that occurred during the handshake, if any
   * @since 1.0
   */
  @Override
  public void afterHandshake(
      org.springframework.http.server.ServerHttpRequest request,
      org.springframework.http.server.ServerHttpResponse response,
      WebSocketHandler wsHandler, Exception ex) {
  }

  /**
   * Validates the JWT token and extracts the user ID from it.
   * @param jwtToken the token to validate
   * @return the user ID if the token is valid, null otherwise
   * @since 1.0
   */
  private String validateJwtToken(String jwtToken) {
    try {
      jwt.validateJwtToken(jwtToken);
      LOGGER.info("ws Token is valid");
    } catch (IllegalArgumentException e) {
      return null;
    }
    String userId = jwt.extractIdFromJwt(jwtToken);
    if (userId == null) {
      return null;
    }
    return userId;
  }

  /**
   * Checks if the user is a participant in the chat. Only participants are allowed to join.
   * @param userId the user ID to check
   * @param chatId the chat the user should be in
   * @return true if the user is in the chat, false otherwise
   * @since 1.0
   */
  @Transactional
  public boolean isUserInChat(String userId, String chatId) {
    try {
      int chatIdInt = Integer.parseInt(chatId);
      Optional<Conversation> conv = conversationRepo.findByIdWithListingAndCreator(chatIdInt);
      if (conv.isEmpty()) {
        return false;
      }
      int userIdInt = Integer.parseInt(userId);
      return conv.get().getBuyer().getId() == userIdInt ||
             conv.get().getSeller().getId() == userIdInt;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
