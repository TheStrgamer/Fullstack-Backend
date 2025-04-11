package no.ntnu.idatt2105.marketplace.dto.admin;

import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CategoriesAdminDTO}.
 */
class CategoriesAdminDTOTest {

  /**
   * Test constructor with primitive fields.
   */
  @Test
  void constructorWithFields_shouldInitializeFields() {
    CategoriesAdminDTO dto = new CategoriesAdminDTO(1, "Electronics", "Electronic gadgets", 10);

    assertEquals(1, dto.getId());
    assertEquals("Electronics", dto.getName());
    assertEquals("Electronic gadgets", dto.getDescription());
    assertEquals(10, dto.getListings());
  }

  /**
   * Test constructor with Categories object.
   */
  @Test
  void constructorWithCategoryObject_shouldInitializeFromCategory() {
    Categories category = new Categories();
    category.setId(2);
    category.setName("Books");
    category.setDescription("Various books");

    CategoriesAdminDTO dto = new CategoriesAdminDTO(category, 5);

    assertEquals(2, dto.getId());
    assertEquals("Books", dto.getName());
    assertEquals("Various books", dto.getDescription());
    assertEquals(5, dto.getListings());
  }
}
