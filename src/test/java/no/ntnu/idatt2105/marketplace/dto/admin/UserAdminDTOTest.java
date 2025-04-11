package no.ntnu.idatt2105.marketplace.dto.admin;

import no.ntnu.idatt2105.marketplace.model.user.Role;
import no.ntnu.idatt2105.marketplace.model.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UserAdminDTO}
 */
class UserAdminDTOTest {

  /**
   * Test the all-args constructor to ensure it sets all fields correctly.
   */
  @Test
  void constructor_shouldAssignFieldsCorrectly() {
    UserAdminDTO dto = new UserAdminDTO(
            1, "John", "Doe", "john@example.com", "12345678", "ADMIN", 5
    );

    assertEquals(1, dto.getId());
    assertEquals("John", dto.getFirstname());
    assertEquals("Doe", dto.getSurname());
    assertEquals("john@example.com", dto.getEmail());
    assertEquals("12345678", dto.getPhonenumber());
    assertEquals("ADMIN", dto.getRole());
    assertEquals(5, dto.getListings());
  }

  /**
   * Test the constructor that builds a DTO from a User entity.
   */
  @Test
  void constructorFromUser_shouldMapFieldsCorrectly() {
    User user = new User();
    user.setId(2);
    user.setFirstname("Alice");
    user.setSurname("Smith");
    user.setEmail("alice@example.com");
    user.setPhonenumber("87654321");

    Role role = new Role();
    role.setName("USER");
    user.setRole(role);

    UserAdminDTO dto = new UserAdminDTO(user, 3);

    assertEquals(2, dto.getId());
    assertEquals("Alice", dto.getFirstname());
    assertEquals("Smith", dto.getSurname());
    assertEquals("alice@example.com", dto.getEmail());
    assertEquals("87654321", dto.getPhonenumber());
    assertEquals("USER", dto.getRole());
    assertEquals(3, dto.getListings());
  }
}
