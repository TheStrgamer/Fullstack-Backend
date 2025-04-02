package no.ntnu.idatt2105.marketplace.controller;

import java.util.Optional;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.responseobjects.UserResponseObject;
import no.ntnu.idatt2105.marketplace.service.BCryptHasher;
import no.ntnu.idatt2105.marketplace.service.JWT_token;
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

import no.ntnu.idatt2105.marketplace.model.user.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserRepo userRepo;

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
    System.out.println("User with id " + user.get().getIdAsString() + " found with given email and password");
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

  @GetMapping("/") //TODO: remove this endpoint, for testing purposes only
  public Iterable<User> getAllUsers() {
    return userRepo.findAll();
  }
  @GetMapping("/{id}/info")
  public ResponseEntity<UserResponseObject> getUserInfo(
      @RequestHeader("Authorization") String authorizationHeader,
      @PathVariable String id) {

    if (!authorizationHeader.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String sessionToken = authorizationHeader.substring(7);

    boolean validToken = jwt.validateJwtToken(sessionToken);
    if (!validToken) {
      System.out.println("Invalid token");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String requesterId = jwt.extractIdFromJwt(sessionToken);
    boolean userRequestingSelf = requesterId.equals(id);

    Optional<User> user = userRepo.findById(Integer.parseInt(id));
    if (user.isEmpty()) {
      System.out.println("No user found with given email");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    UserResponseObject response = new UserResponseObject(user.get(), userRequestingSelf);
    return ResponseEntity.ok(response);
  }




}
