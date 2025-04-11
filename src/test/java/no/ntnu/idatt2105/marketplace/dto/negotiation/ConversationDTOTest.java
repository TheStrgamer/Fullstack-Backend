package no.ntnu.idatt2105.marketplace.dto.negotiation;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ConversationDTO}.
 */
class ConversationDTOTest {

  /**
   * Test that the constructor correctly assigns all fields.
   */
  @Test
  void constructor_shouldInitializeAllFields() {
    MessageDTO message1 = new MessageDTO(1, "Hello", true, "2024-01-01T10:00:00Z");
    MessageDTO message2 = new MessageDTO(2, "Hi", false, "2024-01-01T10:05:00Z");
    List<MessageDTO> messages = List.of(message1, message2);

    ConversationDTO dto = new ConversationDTO(
            101,
            "/images/user.jpg",
            "John Doe",
            "2024-01-01T11:00:00Z",
            messages,
            0
    );

    assertEquals(101, dto.getId());
    assertEquals("/images/user.jpg", dto.getOther_user_picture());
    assertEquals("John Doe", dto.getOther_user_name());
    assertEquals("2024-01-01T11:00:00Z", dto.getLast_update());
    assertEquals(messages, dto.getMessages());
    assertEquals(0, dto.getStatus());
  }

  /**
   * Test that all setters and getters work correctly.
   */
  @Test
  void settersAndGetters_shouldUpdateAndReturnCorrectValues() {
    ConversationDTO dto = new ConversationDTO(0, null, null, null, null, 0);

    MessageDTO msg = new MessageDTO(5, "Updated", false, "2025-04-10T10:10:00Z");
    dto.setId(123);
    dto.setOther_user_picture("/img.jpg");
    dto.setOther_user_name("Jane Doe");
    dto.setLast_message("Latest message");
    dto.setLast_update("2025-04-10T10:10:00Z");
    dto.setMessages(List.of(msg));
    dto.setStatus(1);

    assertEquals(123, dto.getId());
    assertEquals("/img.jpg", dto.getOther_user_picture());
    assertEquals("Jane Doe", dto.getOther_user_name());
    assertEquals("Latest message", dto.getLast_message());
    assertEquals("2025-04-10T10:10:00Z", dto.getLast_update());
    assertEquals(1, dto.getStatus());
    assertEquals(1, dto.getMessages().size());
    assertEquals("Updated", dto.getMessages().get(0).getText());
  }

  /**
   * Test the toString method includes key info.
   */
  @Test
  void toString_shouldContainRelevantFields() {
    ConversationDTO dto = new ConversationDTO(1, "/pic.png", "Name", "2025-04-10", List.of(), 0);
    dto.setLast_message("Hello!");

    String result = dto.toString();
    assertTrue(result.contains("ConversationDTO"));
    assertTrue(result.contains("Name"));
    assertTrue(result.contains("Hello!"));
  }
}
