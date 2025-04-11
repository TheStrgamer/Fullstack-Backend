package no.ntnu.idatt2105.marketplace.dto.negotiation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link MessageSendDTO} class.
 */
class MessageSendDTOTest {

  /**
   * Test the no-argument constructor and default field values.
   */
  @Test
  void testNoArgConstructor_shouldCreateEmptyObject() {
    MessageSendDTO dto = new MessageSendDTO();
    assertNull(dto.getMessage());
    assertEquals(0, dto.getConversationId());
    assertNull(dto.getToken());
  }

  /**
   * Test the all-arguments constructor to ensure fields are initialized correctly.
   */
  @Test
  void testAllArgsConstructor_shouldAssignValuesCorrectly() {
    MessageSendDTO dto = new MessageSendDTO("Hello", 42, "jwt-token-123");
    assertEquals("Hello", dto.getMessage());
    assertEquals(42, dto.getConversationId());
    assertEquals("jwt-token-123", dto.getToken());
  }

  /**
   * Test setters and getters for message, conversation ID, and token.
   */
  @Test
  void testSettersAndGetters_shouldUpdateFieldsCorrectly() {
    MessageSendDTO dto = new MessageSendDTO();

    dto.setMessage("Test message");
    dto.setConversationId(10);
    dto.setToken("token-456");

    assertEquals("Test message", dto.getMessage());
    assertEquals(10, dto.getConversationId());
    assertEquals("token-456", dto.getToken());
  }

  /**
   * Test the toString method to ensure it returns a proper representation.
   */
  @Test
  void testToString_shouldContainAllFields() {
    MessageSendDTO dto = new MessageSendDTO("Hi!", 5, "abc.def.jwt");
    String toString = dto.toString();

    assertTrue(toString.contains("message='Hi!'"));
    assertTrue(toString.contains("conversationId=5"));
    assertFalse(toString.contains("token")); // token is not included in toString
  }
}
