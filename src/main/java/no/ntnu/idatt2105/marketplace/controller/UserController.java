package no.ntnu.idatt2105.marketplace.controller;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import jdk.jshell.spi.ExecutionControlProvider;
import no.ntnu.idatt2105.marketplace.dto.user.*;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.security.BCryptHasher;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import no.ntnu.idatt2105.marketplace.service.user.UserService;
import no.ntnu.idatt2105.marketplace.responseobjects.UserResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import no.ntnu.idatt2105.marketplace.model.user.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private UserService userService;

  private final BCryptHasher hasher = new BCryptHasher();

  private final JWT_token jwt;

  @Autowired
  public UserController(JWT_token jwt) {
    this.jwt = jwt;
  }

  private boolean validateEmail(String email) {
    return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
  }
  private boolean validatePassword(String password) {
    return !password.isEmpty();
  }
  private boolean validatePhoneNumber(String phoneNumber) {
    return phoneNumber.matches("^\\d{8}$");
  }
  private boolean validateName(String name) {
    return name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
  }
  private boolean validateUser(User user) {
    return validateEmail(user.getEmail()) && validatePassword(user.getPassword()) && validatePhoneNumber(user.getPhonenumber()) && validateName(user.getFirstname()) && validateName(user.getSurname());
  }


  public String authenticate(String email, String password) {
    Optional<User> user = userRepo.findByEmail(email);
    if (user.isEmpty() || !hasher.checkPassword(password, user.get().getPassword())) {
      System.out.println("No user found with given email and password");
      return null;
    }
    System.out.println("User found with given email and password");
    return jwt.generateJwtToken(user.get());
  }

  public int register(User user) {
    if (!validateUser(user)) {
      System.out.println("Invalid user data");
      return 1;
    }
    user.setPassword(hasher.hashPassword(user.getPassword()));
    if (userRepo.findByEmail(user.getEmail()).isPresent()) { //TODO add check for other unique fields
      System.out.println("User already exists");
      return 1;
    }
    userRepo.save(user);
    return 0;
  }

  @PostMapping("/register")
  public int registerUser(@RequestBody User user) {
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

  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(@PathVariable int id, @RequestBody UserUpdate userUpdate) {
    try {
      User updatedUser = userService.updateUser(id, userUpdate);
      return ResponseEntity.status(HttpStatus.OK).build();

    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  /**
   * getUserInfo function that is mapped to the /my_account endpoint.
   * goal: return userinfo to display on profile
   * @param authorizationHeader header field containing the token used for verifying the user
   * @return user info as an UserResponseObject
   */
  @PostMapping("/my_account")
  public ResponseEntity<UserResponseObject> getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
    if (!authorizationHeader.startsWith("Bearer ")) {
      System.out.println("Invalid Authorization header");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String token = authorizationHeader.substring(7);

    String email = jwt.extractEmailFromJwt(token);

    Optional<User> user = userRepo.findByEmail(email);

    if (user.isEmpty()) {
      System.out.println("No user found with given email");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    UserResponseObject response = new UserResponseObject(user.get(), true);

    return ResponseEntity.ok(response);
  }

  // TODO: REMOVE, ONLY FOR DEBUG
  @GetMapping("")
  public ResponseEntity<UserResponseObject> getUserInfoByEmail(@RequestParam String email) {
    Optional<User> user = userRepo.findByEmail(email);

    if (user.isEmpty()) {
      System.out.println("No user found with given email");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    user.get().setPassword("");

    UserResponseObject response = new UserResponseObject(user.get(), true);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/") //TODO: remove this endpoint, for testing purposes only
  public Iterable<User> getAllUsers() {
    return userRepo.findAll();
  }
  @GetMapping("/{email}/info")
  public ResponseEntity<UserResponseObject> getUserInfo(
      @RequestHeader("Authorization") String authorizationHeader,
      @PathVariable String email) {

    if (!authorizationHeader.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String sessionToken = authorizationHeader.substring(7);

    boolean validToken = jwt.validateJwtToken(sessionToken);
    if (!validToken) {
      System.out.println("Invalid token");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String requesterEmail = jwt.extractEmailFromJwt(sessionToken);
    boolean userRequestingSelf = requesterEmail.equals(email);

    Optional<User> user = userRepo.findByEmail(email);
    if (user.isEmpty()) {
      System.out.println("No user found with given email");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    UserResponseObject response = new UserResponseObject(user.get(), userRequestingSelf);
    return ResponseEntity.ok(response);
  }




}
