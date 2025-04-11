package no.ntnu.idatt2105.marketplace.model.negotiation;

import no.ntnu.idatt2105.marketplace.model.user.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Message} entity.
 */
public class MessageTest {

  /**
   * Test the default constructor and basic field assignment.
   */
  @Test
  void testDefaultConstructor() {
    Message message = new Message();

    assertEquals(0, message.getId());
    assertNull(message.getSender());
    assertNull(message.getConversation());
    assertNull(message.getText());
    assertNull(message.getCreatedAt());
  }

  /**
   * Test the parameterized constructor sets fields correctly and sets timestamp.
   */
  @Test
  void testConstructorSetsFieldsCorrectly() {
    User sender = new User();
    Conversation conversation = new Conversation();
    String text = "Hello world";

    Message message = new Message(sender, conversation, text);

    assertEquals(sender, message.getSender());
    assertEquals(conversation, message.getConversation());
    assertEquals(text, message.getText());
    assertNotNull(message.getCreatedAt());

    // Timestamp is close to "now"
    Date now = new Date();
    assertTrue(Math.abs(now.getTime() - message.getCreatedAt().getTime()) < 1000);
  }

  /**
   * Test setting the text message using the setter.
   */
  @Test
  void testSetText() {
    Message message = new Message();
    message.setText("Updated message");

    assertEquals("Updated message", message.getText());
  }

  /**
   * Test the toString method contains expected data.
   */
  @Test
  void testToStringContainsText() {
    User sender = new User();
    Conversation conversation = new Conversation();
    String text = "Test message";

    Message message = new Message(sender, conversation, text);

    String str = message.toString();
    assertTrue(str.contains("Test message"));
    assertTrue(str.contains("Message{"));
  }
}
