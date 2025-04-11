package no.ntnu.idatt2105.marketplace.model.negotiation;

import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Conversation} entity.
 */
class ConversationTest {

  private User buyer;
  private User seller;
  private Listing listing;

  /**
   * Setup method for test users and listings.
   */
  @BeforeEach
  void setUp() {
    buyer = new User();
    buyer.setId(1);
    buyer.setFirstname("Buyer");

    seller = new User();
    seller.setId(2);
    seller.setFirstname("Seller");

    listing = new Listing();
    listing.setCreator(seller);
  }

  /**
   * Test that constructor sets fields and timestamps correctly.
   */
  @Test
  void constructor_shouldSetFieldsCorrectly() {
    Conversation conversation = new Conversation(buyer, listing);

    assertEquals(buyer, conversation.getBuyer());
    assertEquals(seller, conversation.getSeller());
    assertEquals(listing, conversation.getListing());
    assertEquals(0, conversation.getStatus());
    assertNotNull(conversation.getCreatedAt());
    assertNotNull(conversation.getUpdatedAt());
  }

  /**
   * Test that constructor throws exception when buyer is the seller.
   */
  @Test
  void constructor_shouldThrowWhenBuyerIsSeller() {
    listing.setCreator(buyer); // make buyer same as creator
    assertThrows(IllegalArgumentException.class, () -> new Conversation(buyer, listing));
  }

  /**
   * Test that getLatestMessage returns null when no messages exist.
   */
  @Test
  void getLatestMessage_shouldReturnNullWhenEmpty() {
    Conversation conversation = new Conversation(buyer, listing);
    assertNull(conversation.getLatestMessage());
  }

  /**
   * Test that getLatestMessage returns the most recent message.
   */
  @Test
  void getLatestMessage_shouldReturnMostRecentMessage() {
    Conversation conversation = new Conversation(buyer, listing);
    Message msg1 = new Message();
    msg1.setText("First");
    Message msg2 = new Message();
    msg2.setText("Last");

    List<Message> messages = new ArrayList<>();
    messages.add(msg1);
    messages.add(msg2);

    // using reflection to set private messages list
    setMessages(conversation, messages);

    assertEquals("Last", conversation.getLatestMessage());
  }

  /**
   * Test that updateDate updates the timestamp.
   */
  @Test
  void updateDate_shouldUpdateTimestamp() {
    Conversation conversation = new Conversation(buyer, listing);
    Date before = conversation.getUpdatedAt();

    try {
      Thread.sleep(10);
    } catch (InterruptedException ignored) {}

    conversation.updateDate();
    assertTrue(conversation.getUpdatedAt().after(before));
  }

  /**
   * Test that setStatus works.
   */
  @Test
  void setStatus_shouldUpdateStatus() {
    Conversation conversation = new Conversation(buyer, listing);
    conversation.setStatus(1);
    assertEquals(1, conversation.getStatus());
  }

  /**
   * Helper method to inject message list into a conversation.
   */
  private void setMessages(Conversation conversation, List<Message> messages) {
    try {
      var field = Conversation.class.getDeclaredField("messages");
      field.setAccessible(true);
      field.set(conversation, messages);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
