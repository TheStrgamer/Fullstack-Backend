package no.ntnu.idatt2105.marketplace.model.user;

import static org.junit.jupiter.api.Assertions.*;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.negotiation.Conversation;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
  /**
   * Unit tests for the {@link User} model class.
   */
  public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
      user = new User("test@example.com", "password", "John", "Doe", "12345678", null);
    }

    /**
     * Test user basic getters and setters.
     */
    @Test
    void userFields_shouldSetAndGetCorrectly() {
      user.setId(1);
      user.setEmail("john@example.com");
      user.setPassword("newpassword");
      user.setFirstname("Johnny");
      user.setSurname("Bravo");
      user.setPhonenumber("87654321");

      assertEquals(1, user.getId());
      assertEquals("john@example.com", user.getEmail());
      assertEquals("newpassword", user.getPassword());
      assertEquals("Johnny", user.getFirstname());
      assertEquals("Bravo", user.getSurname());
      assertEquals("87654321", user.getPhonenumber());
    }

    /**
     * Test adding and removing favorites.
     */
    @Test
    void favorites_shouldAddAndRemoveCorrectly() {
      Listing listing = new Listing();
      user.addFavorite(listing);
      assertTrue(user.getFavorites().contains(listing));
      user.removeFavorite(listing);
      assertFalse(user.getFavorites().contains(listing));
    }

    /**
     * Test adding and clearing history.
     */
    @Test
    void history_shouldRespectMaxSizeAndAddCorrectly() {
      for (int i = 0; i < 15; i++) {
        Listing listing = new Listing();
        listing.setId(i);
        user.addHistory(listing);
      }
      assertEquals(10, user.getHistory().size());
    }

    /**
     * Test role admin check.
     */
    @Test
    void isAdmin_shouldReturnTrueIfRoleIsAdmin() {
      Role role = new Role();
      role.setName("ADMIN");
      user.setRole(role);
      assertTrue(user.isAdmin());
    }

    /**
     * Test getIdAsString.
     */
    @Test
    void getIdAsString_shouldReturnStringId() {
      user.setId(42);
      assertEquals("42", user.getIdAsString());
    }

    /**
     * Test toString method.
     */
    @Test
    void toString_shouldReturnFullName() {
      assertEquals("John Doe", user.toString());
    }
}