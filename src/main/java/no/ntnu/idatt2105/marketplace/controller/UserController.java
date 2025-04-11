package no.ntnu.idatt2105.marketplace.controller;

import java.util.Map;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idatt2105.marketplace.exception.EmailNotAvailibleException;
import no.ntnu.idatt2105.marketplace.exception.IncorrectPasswordException;
import no.ntnu.idatt2105.marketplace.exception.PhonenumberNotAvailibleException;
import no.ntnu.idatt2105.marketplace.exception.TokenExpiredException;
import no.ntnu.idatt2105.marketplace.exception.UserNotFoundException;

import no.ntnu.idatt2105.marketplace.dto.user.*;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.images.ImagesService;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import no.ntnu.idatt2105.marketplace.service.user.UserService;
import no.ntnu.idatt2105.marketplace.dto.other.TokenResponseObject;
import no.ntnu.idatt2105.marketplace.dto.user.UserResponseObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import no.ntnu.idatt2105.marketplace.model.user.User;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Operations related to user accounts")
public class UserController {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private UserService userService;

  @Autowired
  private JWT_token jwt;

  @Autowired
  private ImagesService imagesService;

  @Autowired
  private ListingRepo listingRepo;

  private static final Logger LOGGER = LogManager.getLogger(ListingController.class);
  
  @PostMapping("/register")
  @Operation(
          summary = "User register",
          description = "Registers a new user with given credentials"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "User registered successfully",
                  content = @Content(
                          schema = @Schema(implementation = ApiResponse.class)
                  )
          ),
          @ApiResponse(
                  responseCode = "400",
                  description = "Invalid user data"
          ),
          @ApiResponse(
                  responseCode = "409",
                  description = "Email and/or phonenumber already in use"
          )
  })
  public ResponseEntity<String> registerUser(
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                  description = "User register data",
                  required = true,
                  content = @Content(
                          schema = @Schema(implementation = UserRegister.class)
                  )
          ) @RequestBody UserRegister userRegister) {
    try {
      User user = new User(
              userRegister.getEmail(),
              userRegister.getPassword(),
              userRegister.getFirstname(),
              userRegister.getSurname(),
              userRegister.getPhonenumber(),
              null);
      userService.register(user);
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
  @Operation(
          summary = "User login",
          description = "Validates user credentials and returns a JWT token on success"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "JWT token returned",
                  content = @Content(
                          schema = @Schema(implementation = TokenResponseObject.class)
                  )
          ),
          @ApiResponse(
                  responseCode = "401",
                  description = "Incorrect password for given email",
                  content = @Content(
                          schema = @Schema(implementation = String.class)
                  )
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "No user found with given email and password",
                  content = @Content(
                          schema = @Schema(implementation = String.class)
                  )
          ),
          @ApiResponse(
                  responseCode = "400",
                  description = "Invalid user data",
                  content = @Content(
                          schema = @Schema(implementation = String.class)
                  )
          )
  })
  public ResponseEntity<?> login(
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User login data",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = UserLogin.class)
            )
          )
          @RequestBody UserLogin userLogin)
  {
    LOGGER.info("Logging in user with: " + userLogin.getEmail() + " " + userLogin.getPassword());
    TokenResponseObject token;
    try {
      token = userService.authenticate(userLogin.getEmail(), userLogin.getPassword());
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
  @Operation(
          summary = "JWT validation",
          description = "Returns true upon receiving a valid JWT"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "401",
                  description = "Token is invalid, has expired or authorization header is invalid"
          ),
          @ApiResponse(
                  responseCode = "200",
                  description = "Token is valid",
                  content = @Content(schema = @Schema(implementation = Boolean.class))
          )
  })
  public ResponseEntity<Boolean> validate(
          @Parameter(
                  name = "Authorization",
                  description = "JWT token prefixed with `Bearer `",
                  required = true
          )

          @RequestHeader("Authorization") String authorizationHeader) {
    try {
      if (!authorizationHeader.startsWith("Bearer ")) {
        LOGGER.info("Invalid Authorization header");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
      }
      String sessionToken = authorizationHeader.substring(7);
      jwt.validateJwtToken(sessionToken);
      return ResponseEntity.ok(true);
    } catch (TokenExpiredException e) {
      LOGGER.error("Token has expired");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
    catch (IllegalArgumentException e) {
      LOGGER.error("Invalid token");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }




  @PutMapping("/update")
  @Operation(
          summary = "Update user credentials",
          description = "Updates the logged-in user's information such as name, email, phone number, and profile picture."
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "401",
                  description = "Invalid Authorization header"
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "User not found or error while updating user"
          ),
          @ApiResponse(
                  responseCode = "200",
                  description = "Successfully updated user credentials",
                  content = @Content(
                          schema = @Schema(implementation = String.class)
                  )
          )
  })
  public ResponseEntity<?> update(
          @Parameter(
                  name = "Authorization",
                  description = "JWT token prefixed with `Bearer `",
                  required = true
          )
          @RequestHeader("Authorization") String authorizationHeader,
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                  description = "Updated user data",
                  required = true,
                  content = @Content(
                          schema = @Schema(implementation = UserUpdate.class)
                  )
          ) @RequestBody UserUpdate userUpdate) {
    try {
      if (!authorizationHeader.startsWith("Bearer ")) {
        LOGGER.info("Invalid Authorization header");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      String token = authorizationHeader.substring(7);

      int user_id = Integer.parseInt(jwt.extractIdFromJwt(token));

      userService.updateUser(user_id, userUpdate);
      return ResponseEntity.ok("Successfully updated the user with id: " + user_id);

    } catch (Exception e) {
      LOGGER.error("Error: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }




  @GetMapping("/my_account")
  @Operation(
          summary = "Get account credentials",
          description = "Returns the logged in users credentials"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "401",
                  description = "Invalid Authorization header"
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "No User found with given email"
          ),
          @ApiResponse(
                  responseCode = "200",
                  description = "User Credentials Returned",
                  content = @Content(
                          schema = @Schema(implementation = UserResponseObject.class)
                  )
          )
  })
  public ResponseEntity<UserResponseObject> getUserInfo(
          @Parameter(
                  name = "Authorization",
                  description = "JWT token prefixed with `Bearer `",
                  required = true
          ) @RequestHeader("Authorization") String authorizationHeader) {
    if (!authorizationHeader.startsWith("Bearer ")) {
      LOGGER.info("Invalid Authorization header");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String token = authorizationHeader.substring(7);

    int user_id = Integer.parseInt(jwt.extractIdFromJwt(token));

    Optional<User> user = userRepo.findById(user_id);

    if (user.isEmpty()) {
      LOGGER.info("No user found with given id:" + user_id);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    LOGGER.info("User found with id: " + user_id);
    UserResponseObject response = new UserResponseObject(user.get(), true);

    LOGGER.info(response);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}/info")
  @Operation(
          summary = "Get user information",
          description = "Returns information about a user with the provided id")
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "401",
                  description = "Invalid Authorization header and/or token"
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "User not found"
          ),
          @ApiResponse(
                  responseCode = "200",
                  description = "Returned user information",
                  content = @Content(
                          schema = @Schema(implementation = UserResponseObject.class)
                  )
          ),
  })
  public ResponseEntity<UserResponseObject> getUserInfo(
          @Parameter(
                  name = "Authorization",
                  description = "JWT token prefixed with `Bearer `",
                  required = true
          ) @RequestHeader("Authorization") String authorizationHeader,
          @Parameter(
                  name = "id",
                  description = "Integer value representing the id of the user",
                  required = true,
                  example = "4"
          ) @PathVariable String id) {
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
      LOGGER.error("Token has expired");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (IllegalArgumentException e) {
      LOGGER.error("Invalid token");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @PostMapping("/favorites/toggle/{listingId}")
  @Operation(
          summary = "Toggle favorite for listing",
          description = "Adds a listing to user's favorites if it's not already favorited, removes it if it is"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "Favorite status toggled",
                  content = @Content(
                          schema = @Schema(example = "{\"isFavorited\": true}"))
          ),
          @ApiResponse(
                  responseCode = "401",
                  description = "Unauthorized"
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "User or listing not found"
          )})
  public ResponseEntity<Map<String, Boolean>> toggleFavorite(
          @Parameter(
                  name = "Authorization",
                  description = "Bearer token in the format `Bearer <JWT>`",
                  required = true,
                  example = "Bearer eyJhbGciOiJIUzI1N..."
          ) @RequestHeader("Authorization") String authorizationHeader,
          @Parameter(
                  name = "listingId",
                  description = "ID of the listing to toggle favorite status for",
                  required = true,
                  example = "3"
          ) @PathVariable int listingId) {

    if (!authorizationHeader.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String token = authorizationHeader.substring(7);
    int userId = Integer.parseInt(jwt.extractIdFromJwt(token));

    Optional<User> userOpt = userRepo.findById(userId);
    Optional<Listing> listingOpt = listingRepo.findById(listingId);

    if (userOpt.isEmpty() || listingOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    User user = userOpt.get();
    Listing listing = listingOpt.get();

    boolean wasFavorited = user.getFavorites().contains(listing);
    if (wasFavorited) {
      user.getFavorites().remove(listing);
    } else {
      user.getFavorites().add(listing);
    }

    userRepo.save(user);

    // Returner ny favorittstatus som JSON
    return ResponseEntity.ok(Map.of("isFavorited", !wasFavorited));
  }


  @GetMapping("/favorites/check/{listingId}")
  @Operation(
          summary = "Check if listing is favorited",
          description = "Returns true/false if current user has this listing as favorite"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "Favorite status returned",
                  content = @Content(
                          schema = @Schema(implementation = boolean.class)
                  )
          ),
          @ApiResponse(
                  responseCode = "401",
                  description = "Unauthorized"
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "User or listing not found"
          )
  })
  public ResponseEntity<Boolean> isFavorite(
          @Parameter(
                  name = "Authorization",
                  description = "Bearer token in the format `Bearer <JWT>`",
                  required = true,
                  example = "Bearer eyJhbGciOiJIUzI1N..."
          )@RequestHeader("Authorization") String authorizationHeader,
          @Parameter(
                  name = "listingId",
                  description = "ID of the listing to toggle favorite status for",
                  required = true,
                  example = "3"
          )@PathVariable int listingId) {

    if (!authorizationHeader.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String token = authorizationHeader.substring(7);
    int userId = Integer.parseInt(jwt.extractIdFromJwt(token));

    Optional<User> userOpt = userRepo.findById(userId);
    Optional<Listing> listingOpt = listingRepo.findById(listingId);

    if (userOpt.isEmpty() || listingOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    boolean isFav = userOpt.get().getFavorites().contains(listingOpt.get());
    return ResponseEntity.ok(isFav);
  }

  @GetMapping("/owns/{listingId}")
  @Operation(
          summary = "Sjekk om innlogget bruker eier en gitt listing",
          description = "Returnerer true hvis den innloggede brukeren (utledet fra JWT) eier annonsen med oppgitt ID"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "Returnerer true hvis bruker eier annonsen, ellers false",
                  content = @Content(schema = @Schema(implementation = Boolean.class))
          ),
          @ApiResponse(
                  responseCode = "401",
                  description = "Ugyldig eller manglende JWT-token"
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "Bruker ikke funnet"
          )
  })
  public ResponseEntity<Boolean> checkIfUserOwnsListing(
          @Parameter(
                  name = "Authorization",
                  description = "JWT i formatet `Bearer <token>`",
                  required = true,
                  example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
          )
          @RequestHeader("Authorization") String authorizationHeader,

          @Parameter(
                  name = "listingId",
                  description = "ID til annonsen som sjekkes",
                  required = true,
                  example = "3"
          )
          @PathVariable int listingId
  ) {
    if (!authorizationHeader.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

    String token = authorizationHeader.substring(7);
    int userId = Integer.parseInt(jwt.extractIdFromJwt(token));
    Optional<User> userOpt = userRepo.findById(userId);

    if (userOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }

    User user = userOpt.get();
    boolean owns = user.getMy_listings().stream()
            .anyMatch(listing -> listing.getId() == listingId);

    return ResponseEntity.ok(owns);
  }
}
