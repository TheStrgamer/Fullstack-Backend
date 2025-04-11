package no.ntnu.idatt2105.marketplace.dto.listing;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ListingCreate}
 */
class ListingCreateTest {

  @Test
  void constructor_shouldInitializeAllFieldsCorrectly() {
    Date created = new Date();
    Date updated = new Date();
    ListingCreate listing = new ListingCreate(
            "Test Title",
            "Brief",
            "Full description",
            1500,
            0,
            "Large",
            63.0,
            10.0,
            5,
            2,
            42,
            created,
            updated,
            "/images/path.jpg"
    );

    assertEquals("Test Title", listing.getTitle());
    assertEquals("Brief", listing.getBriefDescription());
    assertEquals("Full description", listing.getFullDescription());
    assertEquals(1500, listing.getPrice());
    assertEquals(0, listing.getSaleStatus());
    assertEquals("Large", listing.getSize());
    assertEquals(63.0, listing.getLatitude());
    assertEquals(10.0, listing.getLongitude());
    assertEquals(5, listing.getCategory());
    assertEquals(2, listing.getCondition());
    assertEquals(42, listing.getCreatorId());
    assertEquals(created, listing.getCreatedAt());
    assertEquals(updated, listing.getUpdatedAt());
    assertEquals("/images/path.jpg", listing.getImagePath());
  }

  @Test
  void setters_shouldUpdateFieldsCorrectly() {
    ListingCreate listing = new ListingCreate(
            "", "", "", 0, 0, "", 0.0, 0.0, 0, 0, 0, null, null, null);

    Date newCreated = new Date();
    Date newUpdated = new Date();
    List<String> images = List.of("/img1.jpg", "/img2.jpg");

    listing.setTitle("New Title");
    listing.setBriefDescription("Brief New");
    listing.setFullDescription("Updated Description");
    listing.setPrice(999);
    listing.setSaleStatus(1);
    listing.setSize("Small");
    listing.setLatitude(64.5);
    listing.setLongitude(11.2);
    listing.setCategory(7);
    listing.setCondition(3);
    listing.setCreatorId(99);
    listing.setCreatedAt(newCreated);
    listing.setUpdatedAt(newUpdated);
    listing.setImagePath("/new/path.jpg");
    listing.setImageUrls(images);

    assertEquals("New Title", listing.getTitle());
    assertEquals("Brief New", listing.getBriefDescription());
    assertEquals("Updated Description", listing.getFullDescription());
    assertEquals(999, listing.getPrice());
    assertEquals(1, listing.getSaleStatus());
    assertEquals("Small", listing.getSize());
    assertEquals(64.5, listing.getLatitude());
    assertEquals(11.2, listing.getLongitude());
    assertEquals(7, listing.getCategory());
    assertEquals(3, listing.getCondition());
    assertEquals(99, listing.getCreatorId());
    assertEquals(newCreated, listing.getCreatedAt());
    assertEquals(newUpdated, listing.getUpdatedAt());
    assertEquals("/new/path.jpg", listing.getImagePath());
    assertEquals(images, listing.getImageUrls());
  }

  @Test
  void toString_shouldIncludeAllFields() {
    ListingCreate listing = new ListingCreate(
            "Title", "Brief", "Full", 1000, 0,
            "M", 1.0, 2.0, 3, 4, 5, new Date(), new Date(), "/path.jpg"
    );
    listing.setImageUrls(List.of("/img1.jpg", "/img2.jpg"));

    String result = listing.toString();
    assertTrue(result.contains("Title"));
    assertTrue(result.contains("Brief"));
    assertTrue(result.contains("/img1.jpg"));
    assertTrue(result.contains("creatorId=5"));
  }
}
