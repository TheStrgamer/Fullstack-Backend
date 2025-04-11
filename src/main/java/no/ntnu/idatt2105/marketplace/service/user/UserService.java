package no.ntnu.idatt2105.marketplace.service.user;

import java.util.List;
import java.util.Optional;

import no.ntnu.idatt2105.marketplace.controller.ListingController;
import no.ntnu.idatt2105.marketplace.dto.negotiation.NegotiationChatsDTO;
import no.ntnu.idatt2105.marketplace.dto.user.UserUpdate;
import no.ntnu.idatt2105.marketplace.exception.EmailNotAvailibleException;
import no.ntnu.idatt2105.marketplace.exception.IncorrectPasswordException;
import no.ntnu.idatt2105.marketplace.exception.PhonenumberNotAvailibleException;
import no.ntnu.idatt2105.marketplace.exception.UserNotFoundException;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.Role;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.repo.RoleRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.dto.other.TokenResponseObject;
import no.ntnu.idatt2105.marketplace.service.images.ImagesService;
import no.ntnu.idatt2105.marketplace.service.security.BCryptHasher;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Service class responsible for managing operations related to users.
 * This includes user authentication, registration, updating profile data,
 * validation of credentials, favorites handling, and user-related listing data.
 * <p>
 * Uses repositories to access and manipulate user and listing information in the database.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 *
 * @see User
 */
@Service
public class UserService {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private RoleRepo roleRepo;

  @Autowired
  private ListingRepo listingRepo;

  @Autowired
  private ImagesService imagesService;

  @Autowired
  private JWT_token jwt;

  private final BCryptHasher hasher = new BCryptHasher();

  private static final Logger LOGGER = LogManager.getLogger(ListingController.class);



  /**
   * Sets the {@link JWT_token} instance used by this service.
   *
   * @param jwt The new {@code JWT_token} instance to be used.
   */
  public void setJwt(JWT_token jwt) {
    this.jwt = jwt;
  }



  /**
   * Updates the user with the given ID using the provided data transfer object.
   *
   * @param id The ID of the user to update.
   * @param dto The user update data.
   * @throws Exception if the user is not found.
   */
  public void updateUser(int id, UserUpdate dto) throws Exception{
    User user = userRepo.findById(id).orElseThrow(() -> new Exception("User not found"));

    LOGGER.info("Updating user: " + user.getId() + " update info: " + dto.toString());

    user.setFirstname(      dto.getFirstname()            != null   ? dto.getFirstname()        : user.getFirstname());
    user.setSurname(        dto.getSurname()              != null   ? dto.getSurname()          : user.getSurname());
    user.setEmail(          dto.getEmail()                != null   ? dto.getEmail()            : user.getEmail());
    user.setPhonenumber(    dto.getPhonenumber()          != null   ? dto.getPhonenumber()      : user.getPhonenumber());

    LOGGER.info("Updated user");
    userRepo.save(user);
  }



  /**
   * Retrieves active negotiation chat summaries for the provided user.
   *
   * @param user The user for whom to fetch active negotiations.
   * @return A list of negotiation chat summaries.
   */
  public List<NegotiationChatsDTO> getActiveChats(User user) {
    return user.getActiveNegotiations().stream()
        .map(negotiation -> new NegotiationChatsDTO(
            negotiation.getId(),
            negotiation.getBuyer() == user ? negotiation.getSeller() : negotiation.getBuyer(),
            negotiation.getLatestMessage(),
            negotiation.getUpdatedAt()
            )).toList();
  }



  /**
   * Validates the format of an email address.
   *
   * @param email The email to validate.
   * @return true if the email format is valid, false otherwise.
   */
  public boolean validateEmail(String email) {
    return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
  }



  /**
   * Checks if the provided email is not already registered.
   *
   * @param email The email to check.
   * @return true if the email is available, false otherwise.
   */
  public boolean verifyEmailNotInUse(String email) {
    return userRepo.findByEmail(email).isEmpty();
  }



  /**
   * Validates that a password is not empty.
   *
   * @param password The password to validate.
   * @return true if the password is not empty, false otherwise.
   */
  public boolean validatePassword(String password) {
    return !password.isEmpty();
  }



  /**
   * Validates that the phone number is exactly 8 digits.
   *
   * @param phoneNumber The phone number to validate.
   * @return true if valid, false otherwise.
   */
  public boolean validatePhoneNumber(String phoneNumber) {
    return phoneNumber.matches("^\\d{8}$");
  }



  /**
   * Checks if the phone number is not already registered.
   *
   * @param phoneNumber The phone number to check.
   * @return true if the phone number is available, false otherwise.
   */
  public boolean verifyPhoneNumberNotInUse(String phoneNumber) {
    return userRepo.findByPhonenumber(phoneNumber).isEmpty();
  }



  /**
   * Validates that the provided name contains only valid name characters.
   *
   * @param name The name to validate.
   * @return true if valid, false otherwise.
   */
  public boolean validateName(String name) {
    return name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
  }



  /**
   * Validates that all user information fields are valid.
   *
   * @param user The user to validate.
   * @return true if all fields are valid, false otherwise.
   */
  public boolean validateUser(User user) {
    return validateEmail(user.getEmail()) && validatePassword(user.getPassword()) && validatePhoneNumber(user.getPhonenumber()) && validateName(user.getFirstname()) && validateName(user.getSurname());
  }



  /**
   * Authenticates a user using the provided email and password.
   *
   * @param email The user's email.
   * @param password The user's password.
   * @return A token response object on successful authentication.
   * @throws UserNotFoundException if no user is found.
   * @throws IncorrectPasswordException if the password does not match.
   */
  public TokenResponseObject authenticate(String email, String password) {
    Optional<User> user = userRepo.findByEmail(email);
    if (user.isEmpty()) {
      throw new UserNotFoundException("No user found with given email and password");
    }
    if (!hasher.checkPassword(password, user.get().getPassword())) {
      throw new IncorrectPasswordException("Incorrect password for given email");
    }
    LOGGER.info("User with id " + user.get().getIdAsString() + " found with given email and password");
    return jwt.generateJwtToken(user.get());
  }



  /**
   * Registers a new user if the email and phone number are not in use and the data is valid.
   *
   * @param user The user to register.
   * @return 0 if successful.
   * @throws EmailNotAvailibleException if the email is already in use.
   * @throws PhonenumberNotAvailibleException if the phone number is already in use.
   * @throws IllegalArgumentException if user data is invalid.
   */
  public int register(User user) {
    if (!verifyEmailNotInUse(user.getEmail())) {
      LOGGER.info("Email is already in use");
      throw new EmailNotAvailibleException("Email is already in use");
    }
    if (!verifyPhoneNumberNotInUse(user.getPhonenumber())) {
      LOGGER.info("Phone number is already in use");
      throw new PhonenumberNotAvailibleException("Phone number is already in use");
    }
    if (!validateUser(user)) {
      LOGGER.info("Invalid user data");
      throw new IllegalArgumentException("Invalid user data");
    }
    user.setPassword(hasher.hashPassword(user.getPassword()));
    Role role = roleRepo.findByName("USER").orElseThrow();
    user.setRole(role);
    userRepo.save(user);
    return 0;
  }



  /**
   * Gets the number of listings created by the user.
   *
   * @param user The user whose listings to count.
   * @return The number of listings.
   */
  public int getListingCount(User user) {
    return listingRepo.findAllByCreator(user).size();
  }



  /**
   * Toggles a listing in the user's favorites. Adds it if not present, removes it if already favorited.
   *
   * @param userId The user's ID.
   * @param listingId The listing's ID.
   * @return true if the listing was added to favorites, false if removed.
   * @throws UserNotFoundException if the user is not found.
   * @throws IllegalArgumentException if the listing is not found.
   */
  public boolean toggleFavorite(int userId, int listingId) {
    Optional<User> userOpt = userRepo.findById(userId);
    Optional<Listing> listingOpt = listingRepo.findById(listingId);

    if (userOpt.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    if (listingOpt.isEmpty()) {
      throw new IllegalArgumentException("Listing not found");
    }

    User user = userOpt.get();
    Listing listing = listingOpt.get();
    boolean isNowFavorite;

    if (user.getFavorites().contains(listing)) {
      user.removeFavorite(listing);
      isNowFavorite = false;
    } else {
      user.addFavorite(listing);
      isNowFavorite = true;
    }

    userRepo.save(user);
    return isNowFavorite;
  }

}
