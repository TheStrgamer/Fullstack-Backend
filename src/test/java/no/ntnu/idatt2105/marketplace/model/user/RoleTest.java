package no.ntnu.idatt2105.marketplace.model.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Role} class.
 */
public class RoleTest {

  /**
   * Test default constructor and setters.
   */
  @Test
  void testSettersAndGetters() {
    Role role = new Role();

    role.setId(1);
    role.setName("ADMIN");

    assertEquals(1, role.getId());
    assertEquals("ADMIN", role.getName());
  }

  /**
   * Test the all-args constructor.
   */
  @Test
  void testConstructorWithArgs() {
    Role role = new Role(2, "USER");

    assertEquals(2, role.getId());
    assertEquals("USER", role.getName());
  }

  /**
   * Test the toString method returns the name.
   */
  @Test
  void testToString() {
    Role role = new Role(3, "MODERATOR");

    assertEquals("MODERATOR", role.toString());
  }
}
