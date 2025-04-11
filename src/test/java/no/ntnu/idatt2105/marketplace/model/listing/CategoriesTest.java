package no.ntnu.idatt2105.marketplace.model.listing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Categories} entity.
 */
class CategoriesTest {

  private Categories category;

  @BeforeEach
  void setUp() {
    category = new Categories();
  }

  /**
   * Test that the default constructor creates a non-null instance.
   */
  @Test
  void defaultConstructor_shouldCreateEmptyCategory() {
    assertNotNull(category);
  }

  /**
   * Test that the parameterized constructor sets fields correctly.
   */
  @Test
  void parameterizedConstructor_shouldInitializeFieldsCorrectly() {
    Categories parent = new Categories("Electronics", "All electronics", null);
    Categories c = new Categories("Laptops", "Portable computers", parent);

    assertEquals("Laptops", c.getName());
    assertEquals("Portable computers", c.getDescription());
    assertEquals(parent, c.getParent_category());
  }

  /**
   * Test that setters and getters for ID work as expected.
   */
  @Test
  void setId_shouldUpdateIdCorrectly() {
    category.setId(10);
    assertEquals(10, category.getId());
  }

  /**
   * Test name field access.
   */
  @Test
  void setName_shouldUpdateNameCorrectly() {
    category.setName("Books");
    assertEquals("Books", category.getName());
  }

  /**
   * Test description field access.
   */
  @Test
  void setDescription_shouldUpdateDescriptionCorrectly() {
    category.setDescription("All kinds of books");
    assertEquals("All kinds of books", category.getDescription());
  }

  /**
   * Test setting and getting parent category.
   */
  @Test
  void setParentCategory_shouldSetParentCorrectly() {
    Categories parent = new Categories("Home", "Home items", null);
    category.setParent_category(parent);
    assertEquals(parent, category.getParent_category());
  }

  /**
   * Test toString method returns the name.
   */
  @Test
  void toString_shouldReturnCategoryName() {
    category.setName("Fashion");
    assertEquals("Fashion", category.toString());
  }

  /**
   * Test getting listings returns correct list reference.
   * Note: setter is not defined, so this just ensures no NPE on get.
   */
  @Test
  void getListings_shouldReturnNullByDefault() {
    assertNull(category.getListings()); // JPA usually sets this
  }
}
