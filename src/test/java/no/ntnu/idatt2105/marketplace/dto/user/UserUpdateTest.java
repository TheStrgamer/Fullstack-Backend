package no.ntnu.idatt2105.marketplace.dto.user;

import no.ntnu.idatt2105.marketplace.model.other.Images;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UserUpdate} DTO.
 */
class UserUpdateTest {

  /**
   * Test that all fields can be set and retrieved correctly.
   */
  @Test
  void shouldSetAndGetAllFields() {
    UserUpdate dto = new UserUpdate();

    dto.setFirstname("John");
    dto.setSurname("Doe");
    dto.setEmail("john.doe@example.com");
    dto.setPhonenumber("12345678");

    assertEquals("John", dto.getFirstname());
    assertEquals("Doe", dto.getSurname());
    assertEquals("john.doe@example.com", dto.getEmail());
    assertEquals("12345678", dto.getPhonenumber());
  }
}
