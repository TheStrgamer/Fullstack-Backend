package no.ntnu.idatt2105.marketplace.dto.listing;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ListingUpdate}.
 */
class ListingUpdateTest {

  /**
   * Test all setters and getters to verify correct assignment and retrieval.
   */
  @Test
  void testSettersAndGetters_shouldReturnCorrectValues() {
    ListingUpdate update = new ListingUpdate();

    update.setTitle("Test Title");
    update.setCategory_id(10);
    update.setCondition_id(5);
    update.setSale_status(1);
    update.setPrice(999);
    update.setBrief_description("Short desc");
    update.setFull_description("Long description here.");
    update.setSize("L");
    update.setLatitude(12.345);
    update.setLongitude(67.890);
    update.setUpdatedAt("2025-04-11T12:00:00Z");
    update.setImages(List.of("/img/1.jpg", "/img/2.jpg"));

    assertEquals("Test Title", update.getTitle());
    assertEquals(10, update.getCategory_id());
    assertEquals(5, update.getCondition_id());
    assertEquals(1, update.getSale_status());
    assertEquals(999, update.getPrice());
    assertEquals("Short desc", update.getBrief_description());
    assertEquals("Long description here.", update.getFull_description());
    assertEquals("L", update.getSize());
    assertEquals(12.345, update.getLatitude());
    assertEquals(67.890, update.getLongitude());
    assertEquals("2025-04-11T12:00:00Z", update.getUpdatedAt());
    assertEquals(2, update.getImages().size());
    assertEquals("/img/1.jpg", update.getImages().get(0));
  }

  /**
   * Test the toString() method returns a readable format with all fields present.
   */
  @Test
  void testToString_shouldIncludeAllFields() {
    ListingUpdate update = new ListingUpdate();
    update.setTitle("Test");
    update.setCategory_id(1);
    update.setCondition_id(2);
    update.setSale_status(1);
    update.setPrice(500);
    update.setBrief_description("Brief");
    update.setFull_description("Full");
    update.setSize("M");
    update.setLatitude(11.11);
    update.setLongitude(22.22);
    update.setUpdatedAt("2025-04-11");
    update.setImages(List.of("/img1.jpg", "/img2.jpg"));

    String result = update.toString();
    assertTrue(result.contains("title='Test'"));
    assertTrue(result.contains("category_id=1"));
    assertTrue(result.contains("condition_id=2"));
    assertTrue(result.contains("sale_status=1"));
    assertTrue(result.contains("price=500"));
    assertTrue(result.contains("brief_description='Brief'"));
    assertTrue(result.contains("full_description='Full'"));
    assertTrue(result.contains("size='M'"));
    assertTrue(result.contains("latitude=11.11"));
    assertTrue(result.contains("longitude=22.22"));
    assertTrue(result.contains("images=2"));
  }

  /**
   * Test that null image list does not cause issues in toString().
   */
  @Test
  void testToString_shouldHandleNullImagesGracefully() {
    ListingUpdate update = new ListingUpdate();
    update.setImages(null);

    String result = update.toString();
    assertTrue(result.contains("images=null"));
  }
}
