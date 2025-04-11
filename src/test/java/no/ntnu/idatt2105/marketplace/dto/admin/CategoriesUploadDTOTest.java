package no.ntnu.idatt2105.marketplace.dto.admin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CategoriesUploadDTO}.
 */
class CategoriesUploadDTOTest {

  /**
   * Test constructor with parameters.
   */
  @Test
  void constructorWithParams_shouldInitializeFields() {
    CategoriesUploadDTO dto = new CategoriesUploadDTO("Books", "Used and new books");

    assertEquals("Books", dto.getName());
    assertEquals("Used and new books", dto.getDescription());
  }

  /**
   * Test default constructor and setters/getters.
   */
  @Test
  void defaultConstructorAndSetters_shouldUpdateFields() {
    CategoriesUploadDTO dto = new CategoriesUploadDTO();

    dto.setName("Electronics");
    dto.setDescription("All electronic devices");

    assertEquals("Electronics", dto.getName());
    assertEquals("All electronic devices", dto.getDescription());
  }
}
