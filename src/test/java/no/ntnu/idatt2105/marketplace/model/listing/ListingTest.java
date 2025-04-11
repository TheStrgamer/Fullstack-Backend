package no.ntnu.idatt2105.marketplace.model.listing;

import no.ntnu.idatt2105.marketplace.model.negotiation.Conversation;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ListingTest {

  private Listing listing;

  @BeforeEach
  void setUp() {
    listing = new Listing();
    listing.setId(1);
  }

  /**
   * Test basic setters and getters
   */
  @Test
  void testSettersAndGetters_shouldAssignAndReturnCorrectValues() {
    User creator = new User();
    Categories category = new Categories();
    Condition condition = new Condition();
    Date now = new Date();

    listing.setCreator(creator);
    listing.setCategory(category);
    listing.setCondition(condition);
    listing.setTitle("Test Title");
    listing.setSale_status(1);
    listing.setPrice(100);
    listing.setBrief_description("Brief");
    listing.setFull_description("Full");
    listing.setSize("L");
    listing.setCreated_at(now);
    listing.setUpdated_at(now);
    listing.setLatitude(12.34);
    listing.setLongitude(56.78);

    assertEquals(creator, listing.getCreator());
    assertEquals(category, listing.getCategory());
    assertEquals(condition, listing.getCondition());
    assertEquals("Test Title", listing.getTitle());
    assertEquals(1, listing.getSale_status());
    assertEquals(100, listing.getPrice());
    assertEquals("Brief", listing.getBrief_description());
    assertEquals("Full", listing.getFull_description());
    assertEquals("L", listing.getSize());
    assertEquals(now, listing.getCreated_at());
    assertEquals(now, listing.getUpdated_at());
    assertEquals(12.34, listing.getLatitude());
    assertEquals(56.78, listing.getLongitude());
  }

  /**
   * Test adding and removing images
   */
  @Test
  void testAddImage_shouldAddImageToList() {
    Images img = new Images();
    listing.addImage(img);
    assertTrue(listing.getImages().contains(img));
  }

  @Test
  void testRemoveImage_shouldRemoveImageFromList() {
    Images img = new Images();
    listing.addImage(img);
    listing.removeImage(img);
    assertFalse(listing.getImages().contains(img));
  }

  @Test
  void testSetImages_shouldReplaceImageList() {
    Images img1 = new Images();
    listing.setImages(List.of(img1));
    assertEquals(1, listing.getImages().size());
    assertTrue(listing.getImages().contains(img1));
  }

  /**
   * Test conversations
   */
  @Test
  void testAddConversation_shouldAddConversationToList() {
    Conversation conv = new Conversation();
    listing.addConversation(conv);
    assertTrue(listing.getConversations().contains(conv));
  }

  @Test
  void testCloseConversation_shouldSetStatusToClosed() {
    Conversation conv = new Conversation();
    listing.addConversation(conv);
    listing.closeConversation(conv);
    assertEquals(1, conv.getStatus());
  }

  @Test
  void testCloseAllConversations_shouldCloseAll() {
    Conversation conv1 = new Conversation();
    Conversation conv2 = new Conversation();
    listing.addConversation(conv1);
    listing.addConversation(conv2);
    listing.closeAllConversations();
    assertEquals(1, conv1.getStatus());
    assertEquals(1, conv2.getStatus());
  }

  /**
   * Tests that removeUser_favorites removes this listing from all users who favorited it
   */
  @Test
  void testRemoveUserFavorites_shouldClearFavorites() {
    // Arrange
    Listing listing = new Listing();
    listing.setId(1);

    User user = new User();
    user.addFavorite(listing); // this links user to listing
    assertTrue(user.getFavorites().contains(listing));

    // Reflect this favorite on the listing side too
    // use the public method to get the list and add the user manually
    listing.getUser_favorites().add(user);

    // Act
    listing.removeUser_favorites();

    // Assert
    assertFalse(user.getFavorites().contains(listing));
  }



  /**
   * Test offers
   */
  @Test
  void testRemoveOffers_shouldClearOffersList() {
    listing.removeOffers();
    assertTrue(listing.getOffers().isEmpty());
  }

  /**
   * Test equals and hashCode
   */
  @Test
  void testEquals_shouldReturnTrueForSameId() {
    Listing other = new Listing();
    other.setId(1);
    assertEquals(listing, other);
  }

  @Test
  void testEquals_shouldReturnFalseForDifferentId() {
    Listing other = new Listing();
    other.setId(2);
    assertNotEquals(listing, other);
  }

  @Test
  void testHashCode_shouldBeConsistentForSameId() {
    Listing other = new Listing();
    other.setId(1);
    assertEquals(listing.hashCode(), other.hashCode());
  }
}
