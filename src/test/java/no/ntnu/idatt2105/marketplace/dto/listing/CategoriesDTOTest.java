package no.ntnu.idatt2105.marketplace.dto.listing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CategoriesDTO}
 */
class CategoriesDTOTest {

  /**
   * Tests that all fields can be correctly set and retrieved using getters and setters
   */
  @Test
  void testGettersAndSetters_shouldSetAndReturnCorrectValues() {
    CategoriesDTO dto = new CategoriesDTO();

    dto.setId(101);
    dto.setName("Electronics");
    dto.setDescription("Devices and gadgets");
    dto.setParent_category(0); // root category

    assertEquals(101, dto.getId());
    assertEquals("Electronics", dto.getName());
    assertEquals("Devices and gadgets", dto.getDescription());
    assertEquals(0, dto.getParent_category());
  }
}
