package no.ntnu.idatt2105.marketplace.service;

import no.ntnu.idatt2105.marketplace.controller.ChatSocketController;
import no.ntnu.idatt2105.marketplace.repo.ConversationRepo;
import no.ntnu.idatt2105.marketplace.repo.MessageRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.security.WebSocketAuthorization;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  ConversationRepo conversationRepo;
  UserRepo userRepo;
  MessageRepo messageRepo;
  JWT_token jwt;
  @Autowired
  public WebSocketConfig(JWT_token jwt,
      ConversationRepo conversationRepo,
      UserRepo userRepo,
      MessageRepo messageRepo) {
    this.jwt = jwt;
    this.conversationRepo = conversationRepo;
    this.userRepo = userRepo;
    this.messageRepo = messageRepo;
  }

  /**
   * Configures the WebSocket handler registry. Registers the ChatSocketController and adds the WebSocketAuthorization interceptor.
   * Also gives the controller all repost it needs.
   * @param registry the WebSocket handler registry
   */
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    System.out.println("=== Registering WebSocket Handlers ===");
    System.out.println("Registering ChatSocketController at /chat/{chatId}");

    registry.addHandler(new ChatSocketController(conversationRepo,userRepo,jwt,messageRepo), "/ws/chat/{chatId}")
        .addInterceptors(new WebSocketAuthorization(jwt, conversationRepo))
        .setAllowedOrigins("*");

    System.out.println("WebSocket handler registration complete");
  }
}
