package no.ntnu.idatt2105.marketplace.dto.negotiation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MessageDTO}.
 */
class MessageDTOTest {

  /**
   * Test constructor assigns all fields correctly.
   */
  @Test
  void constructor_shouldInitializeAllFields() {
    MessageDTO dto = new MessageDTO(1, "Hello", true, "2024-04-10T12:00:00Z");

    assertEquals(1, dto.getId());
    assertEquals("Hello", dto.getText());
    assertTrue(dto.isSendtBySelf());
    assertEquals("2024-04-10T12:00:00Z", dto.getSendtAt());
  }

  /**
   * Test setters and getters update and return correct values.
   */
  @Test
  void settersAndGetters_shouldUpdateFieldsCorrectly() {
    MessageDTO dto = new MessageDTO(0, "", false, "");

    dto.setId(42);
    dto.setText("Updated text");
    dto.setSendtBySelf(true);
    dto.setSendtAt("2025-01-01T00:00:00Z");

    assertEquals(42, dto.getId());
    assertEquals("Updated text", dto.getText());
    assertTrue(dto.isSendtBySelf());
    assertEquals("2025-01-01T00:00:00Z", dto.getSendtAt());
  }
}
