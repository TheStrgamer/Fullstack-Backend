package no.ntnu.idatt2105.marketplace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2105.marketplace.dto.negotiation.MessageSendDTO;
import no.ntnu.idatt2105.marketplace.dto.negotiation.OfferDTO;
import no.ntnu.idatt2105.marketplace.model.negotiation.Conversation;
import no.ntnu.idatt2105.marketplace.model.negotiation.Message;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.ConversationRepo;
import no.ntnu.idatt2105.marketplace.repo.MessageRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controls sending and recieving messages form the websocket connections
 */
@Component
public class ChatSocketController extends TextWebSocketHandler {

  private static final Map<String, Map<String, WebSocketSession>> chatSessions = new HashMap<>();

  private ConversationRepo conversationRepo;
  private UserRepo userRepo;
  private JWT_token jwt;
  private MessageRepo messageRepo;


  @Autowired
  public ChatSocketController(ConversationRepo conversationRepo, UserRepo userRepo, JWT_token jwt, MessageRepo messageRepo) {
    this.conversationRepo = conversationRepo;
    this.userRepo = userRepo;
    this.jwt = jwt;
    this.messageRepo = messageRepo;
  }
  /**
   * Handles the establishment of a WebSocket connection. Adds the session to the chat sessions map.
   * @param session the WebSocket session
   * @throws Exception if an error occurs while establishing the connection
   */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    System.out.println("Connection established: " + session.getId());
    String chatId = (String) session.getAttributes().get("chatId");
    String userId = (String) session.getAttributes().get("userId");

    if (!chatSessions.containsKey(chatId)) {
      chatSessions.put(chatId, new HashMap<>());
    }
    chatSessions.get(chatId).put(userId, session);
  }

  /**
   * Handles incoming text messages from WebSocket clients, saves the message to the database,
   * and sends it to all other connected clients in the same chat.
   * @param session the WebSocket session
   * @param message the incoming text message
   * @throws Exception if an error occurs while processing the message
   */
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    System.out.println("Received message: " + message.getPayload());
    String chatId = (String) session.getAttributes().get("chatId");

    ObjectMapper objectMapper = new ObjectMapper();
    MessageSendDTO payload = objectMapper.readValue(message.getPayload(), MessageSendDTO.class);
    //System.out.println("Payload: " + payload);
    //System.out.println("Payload message: " + payload.getMessage());
    //System.out.println("Payload convId: " + payload.getConversationId());
    //System.out.println("Payload token: " + payload.getToken());

    Conversation conversation = conversationRepo.findById(Integer.parseInt(chatId)).orElse(null);
    if (conversation == null) {
      session.sendMessage(new TextMessage("Chat not found"));
      return;
    }
    String userId = jwt.extractIdFromJwt(payload.getToken());
    User user = userRepo.findById(Integer.parseInt(userId)).orElse(null);
    Message newMessage = new Message(user, conversation, payload.getMessage());
    conversation.addMessage(newMessage);
    conversation.setUpdatedAt(newMessage.getCreatedAt());

    messageRepo.save(newMessage);
    conversationRepo.save(conversation);

    String messagePayload = objectMapper.writeValueAsString(new MessageSendDTO(
        payload.getMessage(),
        payload.getConversationId(),
        "Yeah you ain't getting this"
    ));

    for (WebSocketSession webSocketSession : chatSessions.get(chatId).values()) {
      String userID = (String) webSocketSession.getAttributes().get("userId");
      try {
        if (userID.equals(userId)) { //don't want to send the message to the sender
          continue;
        }
        System.out.println("Sending message to session: " + webSocketSession.getId());
        webSocketSession.sendMessage(new TextMessage("ADD MESSAGE "+messagePayload));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Handles the closing of a WebSocket connection. Removes the session from the chat sessions map.
   * @param session the WebSocket session that was closed
   * @param status the close status
   * @throws Exception if an error occurs while closing the connection
   */
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    try {
      System.out.println("Connection closed: " + session.getId());
      String chatId = (String) session.getAttributes().get("chatId");
      String userId = (String) session.getAttributes().get("userId");

      if (chatSessions.containsKey(chatId)) {
        chatSessions.get(chatId).remove(userId);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Function to send offer message to all clients in a chat
   * Called by other controllers
   * @param chatId the id of the chat
   * @param offer the offer to send
   */
  public void sendOfferMessage(String chatId, OfferDTO offer) {
    System.out.println("Available chat id sessions: " + chatSessions.keySet());
    if (chatSessions.containsKey(chatId)) {
      for (WebSocketSession webSocketSession : chatSessions.get(chatId).values()) {
        try {
          String userID = (String) webSocketSession.getAttributes().get("userId");
          boolean isSender = userID.equals(String.valueOf(offer.getCreatedById()));
          User sender = userRepo.findById(offer.getCreatedById()).orElse(null);
          String senderName = sender != null ? sender.getFirstname() + " " + sender.getSurname() : "Unknown";
          String payload = offer.toJsonString(isSender, senderName);
          System.out.println("Sending offer message to session: " + webSocketSession.getId());
          webSocketSession.sendMessage(new TextMessage("CREATE OFFER " + payload));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Function to change the status of an offer to all clients in a chat
   * Called by other controllers
   * @param chatId the id of the chat
   * @param offerId the id of the offer to update
   * @param status the new status of the offer
   */
  public void updateOfferMessage(String chatId, int offerId, int status) {
    if (chatSessions.containsKey(chatId)) {
      for (WebSocketSession webSocketSession : chatSessions.get(chatId).values()) {
        try {
          System.out.println("Sending offer update message to session: " + webSocketSession.getId());
          webSocketSession.sendMessage(
              new TextMessage("UPDATE STATUS " + offerId + " TO " + status));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
