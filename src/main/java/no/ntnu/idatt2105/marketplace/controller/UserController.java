package no.ntnu.idatt2105.marketplace.controller;

import java.util.Optional;
import no.ntnu.idatt2105.marketplace.exception.EmailNotAvailibleException;
import no.ntnu.idatt2105.marketplace.exception.IncorrectPasswordException;
import no.ntnu.idatt2105.marketplace.exception.PhonenumberNotAvailibleException;
import no.ntnu.idatt2105.marketplace.exception.TokenExpiredException;
import no.ntnu.idatt2105.marketplace.exception.UserNotFoundException;
import java.util.concurrent.ExecutionException;

import jdk.jshell.spi.ExecutionControlProvider;
import no.ntnu.idatt2105.marketplace.dto.user.*;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.security.BCryptHasher;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import no.ntnu.idatt2105.marketplace.service.user.UserService;
import no.ntnu.idatt2105.marketplace.responseobjects.TokenResponseObject;
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
  private boolean verifyEmailNotInUse(String email) {
    return userRepo.findByEmail(email).isEmpty();
  }
  private boolean validatePassword(String password) {
    return !password.isEmpty();
  }
  private boolean validatePhoneNumber(String phoneNumber) {
    return phoneNumber.matches("^\\d{8}$");
  }
  private boolean verifyPhoneNumberNotInUse(String phoneNumber) {
    return userRepo.findByPhonenumber(phoneNumber).isEmpty();
  }
  private boolean validateName(String name) {
    return name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
  }
  private boolean validateUser(User user) {
    return validateEmail(user.getEmail()) && validatePassword(user.getPassword()) && validatePhoneNumber(user.getPhonenumber()) && validateName(user.getFirstname()) && validateName(user.getSurname());
  }

  public TokenResponseObject authenticate(String email, String password) {
    Optional<User> user = userRepo.findByEmail(email);
    if (user.isEmpty()) {
      throw new UserNotFoundException("No user found with given email and password");
    }
    if (!hasher.checkPassword(password, user.get().getPassword())) {
      throw new IncorrectPasswordException("Incorrect password for given email");
    }
    System.out.println("User with id " + user.get().getIdAsString() + " found with given email and password");
    return jwt.generateJwtToken(user.get());
  }

  public int register(User user) {
    if (!verifyEmailNotInUse(user.getEmail())) {
      System.out.println("Email is already in use");
      throw new EmailNotAvailibleException("Email is already in use");
    }
    if (!verifyPhoneNumberNotInUse(user.getPhonenumber())) {
      System.out.println("Phone number is already in use");
      throw new PhonenumberNotAvailibleException("Phone number is already in use");
    }
    if (!validateUser(user)) {
      System.out.println("Invalid user data");
      throw new IllegalArgumentException("Invalid user data");
    }
    user.setPassword(hasher.hashPassword(user.getPassword()));
    userRepo.save(user);
    return 0;
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody User user) {
    try {
      register(user);
      return ResponseEntity.ok("User registered successfully");
    }
    catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user data");
    } catch (EmailNotAvailibleException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already in use");
    } catch (PhonenumberNotAvailibleException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number is already in use");
    }

  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User user) {
    System.out.println("Logging in user with: " + user.getEmail() + " " + user.getPassword());
    TokenResponseObject token;
    try {
      token = authenticate(user.getEmail(), user.getPassword());
    }
    catch (IncorrectPasswordException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password for given email");
    }
    catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with given email and password");
    }
    catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user data");
    }
    return ResponseEntity.ok(token);
  }

  @GetMapping("/validate")
  public ResponseEntity<Boolean> validate(@RequestHeader("Authorization") String authorizationHeader) {
    try {
      if (!authorizationHeader.startsWith("Bearer ")) {
        System.out.println("Invalid Authorization header");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
      String sessionToken = authorizationHeader.substring(7);
      jwt.validateJwtToken(sessionToken);
      return ResponseEntity.ok(true);
    } catch (TokenExpiredException e) {
      System.out.println("Token has expired");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    catch (IllegalArgumentException e) {
      System.out.println("Invalid token");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @PostMapping("/update")
  public ResponseEntity<?> update(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UserUpdate userUpdate) {
    try {
      if (!authorizationHeader.startsWith("Bearer ")) {
        System.out.println("Invalid Authorization header");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      String token = authorizationHeader.substring(7);

      int user_id = Integer.parseInt(jwt.extractIdFromJwt(token));

      userService.updateUser(user_id, userUpdate);
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

    int user_id = Integer.parseInt(jwt.extractIdFromJwt(token));

    Optional<User> user = userRepo.findById(user_id);

    if (user.isEmpty()) {
      System.out.println("No user found with given email");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    UserResponseObject response = new UserResponseObject(user.get(), true);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/") //TODO: remove this endpoint, for testing purposes only
  public Iterable<User> getAllUsers() {
    return userRepo.findAll();
  }
  @GetMapping("/{id}/info")
  public ResponseEntity<UserResponseObject> getUserInfo(
      @RequestHeader("Authorization") String authorizationHeader,
      @PathVariable String id) {
    try {
      if (!authorizationHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      String sessionToken = authorizationHeader.substring(7);

      jwt.validateJwtToken(sessionToken);

    String requesterId = jwt.extractIdFromJwt(sessionToken);
    boolean userRequestingSelf = requesterId.equals(id);

      Optional<User> user = userRepo.findById(Integer.parseInt(id));
      if (user.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
      UserResponseObject response = new UserResponseObject(user.get(), userRequestingSelf);
      return ResponseEntity.ok(response);
    }
    catch (TokenExpiredException e) {
      System.out.println("Token has expired");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid token");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }




}
