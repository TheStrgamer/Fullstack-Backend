package no.ntnu.idatt2105.marketplace.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import no.ntnu.idatt2105.marketplace.dto.negotiation.ConversationDTO;
import no.ntnu.idatt2105.marketplace.dto.negotiation.MessageDTO;
import no.ntnu.idatt2105.marketplace.dto.negotiation.NegotiationChatsDTO;
import no.ntnu.idatt2105.marketplace.model.negotiation.Conversation;
import no.ntnu.idatt2105.marketplace.model.negotiation.Message;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.ConversationRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import no.ntnu.idatt2105.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/negotiation/chat")
public class ChatController {
  @Autowired
  private UserRepo userRepo;

  @Autowired
  private ConversationRepo conversationRepo;

  @Autowired
  private UserService userService;

  private final JWT_token jwt;

  @Autowired
  public ChatController(JWT_token jwt) {
    this.jwt = jwt;
  }

  @GetMapping("/my_chats")
  public ResponseEntity<List<NegotiationChatsDTO>> getActiveChatsList(@RequestHeader("Authorization") String authorizationHeader) {
    if (!authorizationHeader.startsWith("Bearer ")) {
      System.out.println("Invalid Authorization header");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String token = authorizationHeader.substring(7);
    int user_id = Integer.parseInt(jwt.extractIdFromJwt(token));
    Optional<User> user = userRepo.findById(user_id);
    if (user.isEmpty()) {
      System.out.println("No user found with given email");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    List<NegotiationChatsDTO> conversations = userService.getActiveChats(user.get());
    if (conversations.isEmpty()) {
      System.out.println("No active chats found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(conversations);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ConversationDTO> getConversation(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
    if (!authorizationHeader.startsWith("Bearer ")) {
      System.out.println("Invalid Authorization header");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String token = authorizationHeader.substring(7);
    int user_id = Integer.parseInt(jwt.extractIdFromJwt(token));
    Optional<User> user = userRepo.findById(user_id);
    if (user.isEmpty()) {
      System.out.println("No user found with given email");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    Optional<Conversation> conversation = conversationRepo.findById(id);
    if (conversation.isEmpty()) {
      System.out.println("No conversation found with given id");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    if (conversation.get().getBuyer() != user.get() && conversation.get().getSeller() != user.get()) {
      System.out.println("User is not part of the conversation");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    Conversation conv = conversation.get();
    boolean isSeller = conv.getSeller() == user.get();
    Images otherUserPicture = isSeller ? conv.getBuyer().getProfile_picture() : conv.getSeller().getProfile_picture();
    String otherUserPicturePath = "";
    if (otherUserPicture != null) {
      otherUserPicturePath = otherUserPicture.getFilepath_to_image();
    }
    String otherUserName = isSeller ? conv.getBuyer().toString() : conv.getSeller().toString();
    String lastUpdate = conv.getUpdatedAt().toString();

    List<MessageDTO> messages = new ArrayList<>();
    for (Message message : conv.getMessages()) {
      boolean sentBySelf = message.getSender() == user.get();
      MessageDTO messageDTO = new MessageDTO(
          message.getId(),
          message.getText(),
          sentBySelf,
          message.getCreatedAt().toString()
      );
      messages.add(messageDTO);
    }
    int status = conv.getStatus();
    ConversationDTO conversationDTO = new ConversationDTO(
        conv.getId(),
        otherUserPicturePath,
        otherUserName,
        lastUpdate,
        messages,
        status
    );
    return ResponseEntity.ok(conversationDTO);
  }

}
