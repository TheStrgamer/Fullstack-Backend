package no.ntnu.idatt2105.marketplace.security;

import java.util.Optional;
import no.ntnu.idatt2105.marketplace.model.negotiation.Conversation;
import no.ntnu.idatt2105.marketplace.repo.ConversationRepo;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
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

@Service
public class WebSocketAuthorization implements HandshakeInterceptor {

  private ConversationRepo conversationRepo;

  private JWT_token jwt;
  @Autowired
  public WebSocketAuthorization(JWT_token jwt,
      ConversationRepo conversationRepo) {
    this.jwt = jwt;
    this.conversationRepo = conversationRepo;
  }


  /**
   * Handles the WebSocket handshake process. Validates the JWT token and checks if the user is authorized to access the chat.
   * @param request
   * @param response
   * @param wsHandler the WebSocket handler
   * @param attributes the map of attributes
   * @return true if the handshake is successful, false otherwise
   * @throws Exception
   */
  @Transactional
  @Override
  public boolean beforeHandshake(ServerHttpRequest request,
      ServerHttpResponse response,
      WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    try {
      System.out.println("=== WebSocket Handshake Started ===");
      System.out.println("Request URI: " + request.getURI());
      System.out.println("Headers: " + request.getHeaders());

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
          System.out.println("User with id " + userId + " authorized for chat");
          attributes.put("chatId", chatId);
          attributes.put("userId", userId);
          return true;
        }
      }
      System.out.println("Authorization failed");
      response.setStatusCode(HttpStatus.UNAUTHORIZED);
      return false;
    } catch (Exception e) {
      System.out.println("Error during WebSocket handshake: " + e.getMessage());
      response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
      return false;
    }
  }

  /**
   * Handles the WebSocket handshake completion process. This method is called after the handshake is completed.
   * @param request
   * @param response
   * @param wsHandler
   * @param ex the exception that occurred during the handshake, if any
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
   */
  private String validateJwtToken(String jwtToken) {
    try {
      jwt.validateJwtToken(jwtToken);
      System.out.println("ws Token is valid");
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
