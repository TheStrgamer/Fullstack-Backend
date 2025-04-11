package no.ntnu.idatt2105.marketplace.dto.admin;

import no.ntnu.idatt2105.marketplace.model.user.Role;
import no.ntnu.idatt2105.marketplace.model.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UserUploadAdminDTO}
 */
class UserUploadAdminDTOTest {

  /**
   * Test that the all-args constructor sets fields correctly
   */
  @Test
  void constructor_shouldSetAllFields() {
    UserUploadAdminDTO dto = new UserUploadAdminDTO("Alice", "Smith", "alice@example.com", "12345678", "ADMIN");

    assertEquals("Alice", dto.getFirstname());
    assertEquals("Smith", dto.getSurname());
    assertEquals("alice@example.com", dto.getEmail());
    assertEquals("12345678", dto.getPhonenumber());
    assertEquals("ADMIN", dto.getRole());
  }

  /**
   * Test that the no-args constructor and setters work as expected
   */
  @Test
  void settersAndGetters_shouldSetAndReturnValues() {
    UserUploadAdminDTO dto = new UserUploadAdminDTO();
    dto.setFirstname("Bob");
    dto.setSurname("Jones");
    dto.setEmail("bob@example.com");
    dto.setPhonenumber("98765432");
    dto.setRole("USER");

    assertEquals("Bob", dto.getFirstname());
    assertEquals("Jones", dto.getSurname());
    assertEquals("bob@example.com", dto.getEmail());
    assertEquals("98765432", dto.getPhonenumber());
    assertEquals("USER", dto.getRole());
  }

  /**
   * Test that the User constructor maps values correctly from a User model
   */
  @Test
  void constructorFromUser_shouldMapFieldsCorrectly() {
    User user = new User();
    user.setFirstname("Charlie");
    user.setSurname("Anderson");
    user.setEmail("charlie@example.com");
    user.setPhonenumber("11223344");

    Role role = new Role();
    role.setName("MODERATOR");
    user.setRole(role);

    UserUploadAdminDTO dto = new UserUploadAdminDTO(user);

    assertEquals("Charlie", dto.getFirstname());
    assertEquals("Anderson", dto.getSurname());
    assertEquals("charlie@example.com", dto.getEmail());
    assertEquals("11223344", dto.getPhonenumber());
    assertEquals("MODERATOR", dto.getRole());
  }
}
