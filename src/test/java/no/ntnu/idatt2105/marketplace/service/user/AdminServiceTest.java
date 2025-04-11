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
import no.ntnu.idatt2105.marketplace.exception.UserNotAdminException;


/**
 * Unit tests for {@link AdminService}.
 * This tests admin privilege validation from a JWT Bearer token.
 *
 * @author Jonas Reiher
 */
@SpringBootTest
@ActiveProfiles("test")
class AdminServiceTest {

  @Mock
  private UserRepo userRepo;

  @Mock
  private JWT_token jwt;

  @InjectMocks
  private AdminService adminService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Tests that an exception is thrown for a malformed Authorization header.
   */
  @Test
  void validateAdminPrivileges_shouldThrowIfHeaderInvalid() {
    String invalidHeader = "InvalidTokenFormat";
    assertThrows(UserNotAdminException.class, () -> adminService.validateAdminPrivileges(invalidHeader));
  }

  /**
   * Tests that an exception is thrown when no user is found for the ID.
   */
  @Test
  void validateAdminPrivileges_shouldThrowIfUserNotFound() {
    String token = "Bearer mocked.jwt.token";
    when(jwt.extractIdFromJwt("mocked.jwt.token")).thenReturn("42");
    when(userRepo.findById(42)).thenReturn(Optional.empty());

    assertThrows(UserNotAdminException.class, () -> adminService.validateAdminPrivileges(token));
  }

  /**
   * Tests that an exception is thrown when the user is not an admin.
   */
  @Test
  void validateAdminPrivileges_shouldThrowIfUserNotAdmin() {
    String token = "Bearer mocked.jwt.token";
    User user = new User();
    user.setRole(new Role(1, "USER")); // Not admin

    when(jwt.extractIdFromJwt("mocked.jwt.token")).thenReturn("1");
    when(userRepo.findById(1)).thenReturn(Optional.of(user));

    assertThrows(UserNotAdminException.class, () -> adminService.validateAdminPrivileges(token));
  }

  /**
   * Tests that the method returns true for a valid admin user.
   */
  @Test
  void validateAdminPrivileges_shouldReturnTrueIfAdmin() {
    String token = "Bearer valid.jwt.token";
    Role adminRole = new Role(2, "ADMIN");
    User user = new User();
    user.setRole(adminRole);

    when(jwt.extractIdFromJwt("valid.jwt.token")).thenReturn("99");
    when(userRepo.findById(99)).thenReturn(Optional.of(user));

    assertTrue(adminService.validateAdminPrivileges(token));
  }
}
