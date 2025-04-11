package no.ntnu.idatt2105.marketplace.dto.negotiation;

import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.user.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link NegotiationChatsDTO}.
 */
class NegotiationChatsDTOTest {

  /**
   * Test the constructor using an {@link Images} object.
   */
  @Test
  void constructor_withImage_shouldSetFieldsCorrectly() {
    Images image = new Images(1, "/images/user.jpg");
    Date now = new Date();

    NegotiationChatsDTO dto = new NegotiationChatsDTO(
            101,
            image,
            "John Doe",
            "Hey there!",
            now
    );

    assertEquals(101, dto.getId());
    assertEquals("/images/user.jpg", dto.getOther_user_picture());
    assertEquals("John Doe", dto.getOther_user_name());
    assertEquals("Hey there!", dto.getLast_message());
    assertEquals(now, dto.getLast_update());
  }

  /**
   * Test the constructor using a {@link User} object.
   */
  @Test
  void constructor_withUser_shouldSetFieldsCorrectly() {
    User user = new User();
    user.setFirstname("Alice");
    user.setSurname("Smith");
    user.setProfile_picture(new Images(2, "/images/alice.png"));

    Date now = new Date();

    NegotiationChatsDTO dto = new NegotiationChatsDTO(
            202,
            user,
            "Hello!",
            now
    );

    assertEquals(202, dto.getId());
    assertEquals("/images/alice.png", dto.getOther_user_picture());
    assertEquals("Alice Smith", dto.getOther_user_name());
    assertEquals("Hello!", dto.getLast_message());
    assertEquals(now, dto.getLast_update());
  }

  /**
   * Test when the user's image is null.
   */
  @Test
  void constructor_withNullImage_shouldDefaultToEmptyString() {
    Images image = new Images(1, null);
    Date now = new Date();

    NegotiationChatsDTO dto = new NegotiationChatsDTO(
            303,
            image,
            "Unknown",
            "No image available",
            now
    );

    assertEquals("", dto.getOther_user_picture());
  }

  /**
   * Test when user's profile picture is null.
   */
  @Test
  void constructor_withUserAndNullPicture_shouldDefaultToEmptyString() {
    User user = new User();
    user.setFirstname("Bob");
    user.setSurname("Jones");
    user.setProfile_picture(null); // null profile pic

    Date now = new Date();

    NegotiationChatsDTO dto = new NegotiationChatsDTO(
            404,
            user,
            "Missing profile pic",
            now
    );

    assertEquals("Bob Jones", dto.getOther_user_name());
    assertEquals("", dto.getOther_user_picture());
  }

  /**
   * Test getters and setters.
   */
  @Test
  void settersAndGetters_shouldUpdateAndReturnCorrectValues() {
    NegotiationChatsDTO dto = new NegotiationChatsDTO(0, (Images) null, "", "", new Date());

    dto.setId(1);
    dto.setOther_user_picture("/new/path.jpg");
    dto.setOther_user_name("Jane Doe");
    dto.setLast_message("Updated message");
    Date date = new Date();
    dto.setLast_update(date);

    assertEquals(1, dto.getId());
    assertEquals("/new/path.jpg", dto.getOther_user_picture());
    assertEquals("Jane Doe", dto.getOther_user_name());
    assertEquals("Updated message", dto.getLast_message());
    assertEquals(date, dto.getLast_update());
  }
}
