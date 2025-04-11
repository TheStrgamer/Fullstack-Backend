package no.ntnu.idatt2105.marketplace.dto.user;

import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.user.Role;
import no.ntnu.idatt2105.marketplace.model.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UserResponseObject}.
 */
class UserResponseObjectTest {

  /**
   * Test the constructor that includes role information.
   */
  @Test
  void testConstructorWithRole() {
    UserResponseObject obj = new UserResponseObject(
            "test@example.com", "Alice", "Andersen", "12345678", "/images/profile.jpg", 1
    );

    assertEquals("test@example.com", obj.getEmail());
    assertEquals("Alice", obj.getFirstname());
    assertEquals("Andersen", obj.getSurname());
    assertEquals("12345678", obj.getPhonenumber());
    assertEquals("/images/profile.jpg", obj.getProfilePicture());
    assertEquals(1, obj.getRole());
  }

  /**
   * Test the constructor without role (role defaults to -1).
   */
  @Test
  void testConstructorWithoutRole() {
    UserResponseObject obj = new UserResponseObject(
            "test@example.com", "Bob", "Baker", "87654321", "/images/default.jpg"
    );

    assertEquals("test@example.com", obj.getEmail());
    assertEquals("Bob", obj.getFirstname());
    assertEquals("Baker", obj.getSurname());
    assertEquals("87654321", obj.getPhonenumber());
    assertEquals("/images/default.jpg", obj.getProfilePicture());
    assertEquals(-1, obj.getRole());
  }

  /**
   * Test the constructor from a {@link User} instance with includeRoles true.
   */
  @Test
  void testConstructorFromUser_withRole() {
    User user = new User();
    user.setEmail("user@example.com");
    user.setFirstname("Eve");
    user.setSurname("Evans");
    user.setPhonenumber("55512345");
    user.setProfile_picture(new Images(1, "/images/pfp.jpg"));
    user.setRole(new Role(2, "ADMIN"));

    UserResponseObject obj = new UserResponseObject(user, true);

    assertEquals("user@example.com", obj.getEmail());
    assertEquals("Eve", obj.getFirstname());
    assertEquals("Evans", obj.getSurname());
    assertEquals("55512345", obj.getPhonenumber());
    assertEquals("/images/pfp.jpg", obj.getProfilePicture());
    assertEquals(2, obj.getRole());
  }

  /**
   * Test the constructor from a {@link User} instance with includeRoles false.
   */
  @Test
  void testConstructorFromUser_withoutRole() {
    User user = new User();
    user.setEmail("someone@nowhere.com");
    user.setFirstname("Sam");
    user.setSurname("Smith");
    user.setPhonenumber("00001111");
    user.setProfile_picture(new Images(2, "/images/anon.jpg"));
    user.setRole(new Role(3, "USER"));

    UserResponseObject obj = new UserResponseObject(user, false);

    assertEquals("someone@nowhere.com", obj.getEmail());
    assertEquals("Sam", obj.getFirstname());
    assertEquals("Smith", obj.getSurname());
    assertEquals("00001111", obj.getPhonenumber());
    assertEquals("/images/anon.jpg", obj.getProfilePicture());
    assertEquals(-1, obj.getRole());
  }

  /**
   * Test setters and getters individually.
   */
  @Test
  void testSettersAndGetters() {
    UserResponseObject obj = new UserResponseObject("","","","","", -1);

    obj.setEmail("new@mail.com");
    obj.setFirstname("Liam");
    obj.setSurname("Lee");
    obj.setPhonenumber("11223344");
    obj.setProfilePicture("/images/updated.jpg");
    obj.setRole(5);

    assertEquals("new@mail.com", obj.getEmail());
    assertEquals("Liam", obj.getFirstname());
    assertEquals("Lee", obj.getSurname());
    assertEquals("11223344", obj.getPhonenumber());
    assertEquals("/images/updated.jpg", obj.getProfilePicture());
    assertEquals(5, obj.getRole());
  }

  /**
   * Test the toString method returns a descriptive string.
   */
  @Test
  void testToString_shouldContainFieldValues() {
    UserResponseObject obj = new UserResponseObject(
            "sample@mail.com", "Zoe", "Zimmerman", "44445555", "/img/zoe.jpg", 9
    );

    String str = obj.toString();
    assertTrue(str.contains("sample@mail.com"));
    assertTrue(str.contains("Zoe"));
    assertTrue(str.contains("Zimmerman"));
    assertTrue(str.contains("44445555"));
    assertTrue(str.contains("/img/zoe.jpg"));
    assertTrue(str.contains("9"));
  }
}
