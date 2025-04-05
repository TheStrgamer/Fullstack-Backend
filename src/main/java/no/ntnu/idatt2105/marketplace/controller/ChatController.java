package no.ntnu.idatt2105.marketplace.controller;

import java.util.List;
import java.util.Optional;
import no.ntnu.idatt2105.marketplace.dto.negotiation.NegotiationChatsDTO;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.security.BCryptHasher;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import no.ntnu.idatt2105.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/negotiation/chat/")
public class ChatController {
  @Autowired
  private UserRepo userRepo;

  @Autowired
  private UserService userService;

  private final BCryptHasher hasher = new BCryptHasher();

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

}
