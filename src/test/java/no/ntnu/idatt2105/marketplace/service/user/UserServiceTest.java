package no.ntnu.idatt2105.marketplace.service.user;

// Mockito
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

// utility
import java.util.Date;
import java.util.Optional;
import java.util.List;

// models
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.model.user.Role;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;

// DTOs
import no.ntnu.idatt2105.marketplace.dto.user.UserUpdate;
import no.ntnu.idatt2105.marketplace.dto.other.TokenResponseObject;

// repos
import no.ntnu.idatt2105.marketplace.repo.*;

// services
import no.ntnu.idatt2105.marketplace.service.security.BCryptHasher;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;

// JUnit tests
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// exceptions
import no.ntnu.idatt2105.marketplace.exception.EmailNotAvailibleException;
import no.ntnu.idatt2105.marketplace.exception.IncorrectPasswordException;
import no.ntnu.idatt2105.marketplace.exception.PhonenumberNotAvailibleException;
import no.ntnu.idatt2105.marketplace.exception.UserNotFoundException;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

  @Mock private UserRepo userRepo;
  @Mock private RoleRepo roleRepo;
  @Mock private ListingRepo listingRepo;
  @Mock private JWT_token jwt;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Tests validateEmail with valid input.
   */
  @Test
  void validateEmail_shouldReturnTrueForValidEmail() {
    assertTrue(userService.validateEmail("valid@example.com"));
  }

  /**
   * Tests validateEmail with invalid input.
   */
  @Test
  void validateEmail_shouldReturnFalseForInvalidEmail() {
    assertFalse(userService.validateEmail("invalid"));
  }

  /**
   * Tests password validation with empty input.
   */
  @Test
  void validatePassword_shouldReturnFalseIfEmpty() {
    assertFalse(userService.validatePassword(""));
  }

  /**
   * Tests password validation with a proper password.
   */
  @Test
  void validatePassword_shouldReturnTrueIfNotEmpty() {
    assertTrue(userService.validatePassword("secure123"));
  }

  /**
   * Tests phone number validation with correct and incorrect inputs.
   */
  @Test
  void validatePhoneNumber_shouldHandleValidAndInvalidNumbers() {
    assertTrue(userService.validatePhoneNumber("12345678"));
    assertFalse(userService.validatePhoneNumber("12345abc"));
  }

  /**
   * Tests name validation.
   */
  @Test
  void validateName_shouldAcceptValidAndRejectInvalidNames() {
    assertTrue(userService.validateName("Jonas"));
    assertFalse(userService.validateName("Jonas@123"));
  }

  /**
   * Tests validateUser with valid data.
   */
  @Test
  void validateUser_shouldReturnTrueWhenUserIsValid() {
    User user = new User("valid@email.com", "password", "Test", "User", "12345678", null);
    assertTrue(userService.validateUser(user));
  }

  /**
   * Tests email uniqueness check.
   */
  @Test
  void verifyEmailNotInUse_shouldReturnTrueWhenNotFound() {
    when(userRepo.findByEmail("new@example.com")).thenReturn(Optional.empty());
    assertTrue(userService.verifyEmailNotInUse("new@example.com"));
  }

  /**
   * Tests phone number uniqueness check.
   */
  @Test
  void verifyPhoneNumberNotInUse_shouldReturnTrueWhenNotFound() {
    when(userRepo.findByPhonenumber("12345678")).thenReturn(Optional.empty());
    assertTrue(userService.verifyPhoneNumberNotInUse("12345678"));
  }

  /**
   * Tests toggleFavorite adds and removes a listing.
   */
  @Test
  void toggleFavorite_shouldAddAndRemoveListing() {
    User user = new User();
    Listing listing = new Listing();
    listing.setId(1);

    when(userRepo.findById(1)).thenReturn(Optional.of(user));
    when(listingRepo.findById(1)).thenReturn(Optional.of(listing));

    assertTrue(userService.toggleFavorite(1, 1));
    assertFalse(userService.toggleFavorite(1, 1));
  }

  /**
   * Tests registration throws exception if email is in use.
   */
  @Test
  void register_shouldThrowIfEmailInUse() {
    User user = new User("email@example.com", "pass", "f", "l", "12345678", null);
    when(userRepo.findByEmail("email@example.com")).thenReturn(Optional.of(user));

    assertThrows(EmailNotAvailibleException.class, () -> userService.register(user));
  }

  /**
   * Tests registration throws exception if phone number is in use.
   */
  @Test
  void register_shouldThrowIfPhoneInUse() {
    User user = new User("x@y.com", "pass", "f", "l", "12345678", null);
    when(userRepo.findByEmail("x@y.com")).thenReturn(Optional.empty());
    when(userRepo.findByPhonenumber("12345678")).thenReturn(Optional.of(user));

    assertThrows(PhonenumberNotAvailibleException.class, () -> userService.register(user));
  }

  /**
   * Tests authentication with valid credentials.
   */
  @Test
  void authenticate_shouldReturnToken() {
    User user = new User("test@test.com", new BCryptHasher().hashPassword("pass"), "f", "l", "12345678", null);
    when(userRepo.findByEmail("test@test.com")).thenReturn(Optional.of(user));
    when(jwt.generateJwtToken(any())).thenReturn(new TokenResponseObject("mock.token", new Date().getTime()));

    TokenResponseObject token = userService.authenticate("test@test.com", "pass");
    assertEquals("mock.token", token.getToken());
  }

  /**
   * Tests authentication with unknown email.
   */
  @Test
  void authenticate_shouldThrowIfUserNotFound() {
    when(userRepo.findByEmail("notfound@test.com")).thenReturn(Optional.empty());
    assertThrows(UserNotFoundException.class, () -> userService.authenticate("notfound@test.com", "pass"));
  }

  /**
   * Tests authentication with wrong password.
   */
  @Test
  void authenticate_shouldThrowIfWrongPassword() {
    User user = new User("user@test.com", new BCryptHasher().hashPassword("correct"), "f", "l", "12345678", null);
    when(userRepo.findByEmail("user@test.com")).thenReturn(Optional.of(user));

    assertThrows(IncorrectPasswordException.class, () -> userService.authenticate("user@test.com", "wrong"));
  }

  /**
   * Tests counting listings created by a user.
   */
  @Test
  void getListingCount_shouldReturnCorrectCount() {
    User user = new User();
    when(listingRepo.findAllByCreator(user)).thenReturn(List.of(new Listing(), new Listing()));
    assertEquals(2, userService.getListingCount(user));
  }

  /**
   * Tests updating user fields from DTO.
   */
  @Test
  void updateUser_shouldApplyDtoChanges() throws Exception {
    User user = new User("x@y.com", "pass", "first", "last", "12345678", null);
    user.setId(1);
    when(userRepo.findById(1)).thenReturn(Optional.of(user));

    UserUpdate dto = new UserUpdate();
    dto.setFirstname("Updated");
    dto.setSurname("User");

    userService.updateUser(1, dto);
    assertEquals("Updated", user.getFirstname());
    assertEquals("User", user.getSurname());
  }
}