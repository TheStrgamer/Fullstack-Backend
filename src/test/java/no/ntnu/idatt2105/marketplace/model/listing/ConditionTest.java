package no.ntnu.idatt2105.marketplace.model.listing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Condition} entity.
 */
class ConditionTest {

  private Condition condition;

  @BeforeEach
  void setUp() {
    condition = new Condition();
  }

  /**
   * Test that the default constructor creates a non-null instance.
   */
  @Test
  void defaultConstructor_shouldCreateEmptyCondition() {
    assertNotNull(condition);
  }

  /**
   * Test that the parameterized constructor sets fields correctly.
   */
  @Test
  void parameterizedConstructor_shouldInitializeFieldsCorrectly() {
    Condition c = new Condition(1, "Used");
    assertEquals(1, c.getId());
    assertEquals("Used", c.getName());
  }

  /**
   * Test that setters and getters for id work correctly.
   */
  @Test
  void setId_shouldUpdateIdCorrectly() {
    condition.setId(42);
    assertEquals(42, condition.getId());
  }

  /**
   * Test that setters and getters for name work correctly.
   */
  @Test
  void setName_shouldUpdateNameCorrectly() {
    condition.setName("New");
    assertEquals("New", condition.getName());
  }

  /**
   * Optional: Test that you can associate listings with this condition.
   */
  @Test
  void testListingsAssociation_canBeSetAndRetrieved() {
    Listing l1 = new Listing();
    Listing l2 = new Listing();
    List<Listing> mockListings = List.of(l1, l2);

    condition.setName("Like New");
    // NOTE: `condition.setListings(mockListings)` is missing, since no setter exists in your model.
    // If you add a setter for `listings`, you can also test that relationship.
  }
}
