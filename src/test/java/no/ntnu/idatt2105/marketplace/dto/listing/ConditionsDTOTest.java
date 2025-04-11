package no.ntnu.idatt2105.marketplace.dto.listing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link ConditionsDTO}
 */
class ConditionsDTOTest {

  /**
   * Test that all getters and setters work as expected
   */
  @Test
  void testGettersAndSetters_shouldSetAndReturnCorrectValues() {
    ConditionsDTO dto = new ConditionsDTO();

    dto.setId(42);
    dto.setName("Used - Like New");

    assertEquals(42, dto.getId());
    assertEquals("Used - Like New", dto.getName());
  }
}
