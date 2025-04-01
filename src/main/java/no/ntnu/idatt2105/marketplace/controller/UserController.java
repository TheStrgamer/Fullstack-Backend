package no.ntnu.idatt2105.marketplace.controller;

import java.util.Optional;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.BCryptHasher;
import no.ntnu.idatt2105.marketplace.service.JWT_token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.idatt2105.marketplace.model.user.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserRepo userRepo;

  private final BCryptHasher hasher = new BCryptHasher();
  private final JWT_token jwt = new JWT_token();

  public String authenticate(String email, String password) {
    Optional<User> user = userRepo.findByEmail(email);
    if (user.isEmpty() || !hasher.checkPassword(password, user.get().getPassword())) {
      return null;
    }

    return jwt.generateJwtToken(user.get());
  }

  public int register(User user) {
    user.setPassword(hasher.hashPassword(user.getPassword()));
    if (userRepo.findByEmail(user.getEmail()).isPresent()) { //TODO add check for other unique fields
      System.out.println("User already exists");
      return 1;
    }
    userRepo.save(user);
    return 0;
  }

  @PostMapping("/add")
  public int addUser(@RequestBody User user) {
    return register(user);
  }

  @PostMapping("/login")
  public String login(@RequestBody User user) {
    System.out.println("Logging in user: " + user.getEmail() + " " + user.getPassword());
    return authenticate(user.getEmail(), user.getPassword());
  }

  @GetMapping("/validate")
  public boolean validate(@RequestHeader("Authorization") String authorizationHeader) {
    if (!authorizationHeader.startsWith("Bearer ")) {
      System.out.println("Invalid Authorization header");

      return false;
    }
    String sessionToken = authorizationHeader.substring(7);
    return jwt.validateJwtToken(sessionToken);
  }



}
