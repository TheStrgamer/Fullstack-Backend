package no.ntnu.idatt2105.marketplace.dto.listing;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ListingDTO}.
 */
class ListingDTOTest {

  @Test
  void constructor_shouldInitializeAllFieldsCorrectly() {
    Date now = new Date();

    ListingDTO dto = new ListingDTO(
            101,
            "Vintage bicycle for sale",
            "Lightly used vintage bike",
            "This vintage bicycle is in great condition...",
            1500,
            0,
            "Medium",
            63.4305,
            10.3951,
            "Bicycles",
            "Used - Good",
            221,
            now,
            now,
            "/images/listings/101.jpg"
    );

    assertEquals(101, dto.getId());
    assertEquals("Vintage bicycle for sale", dto.getTitle());
    assertEquals("Lightly used vintage bike", dto.getBriefDescription());
    assertEquals("This vintage bicycle is in great condition...", dto.getFullDescription());
    assertEquals(1500, dto.getPrice());
    assertEquals(0, dto.getSaleStatus());
    assertEquals("Medium", dto.getSize());
    assertEquals(63.4305, dto.getLatitude());
    assertEquals(10.3951, dto.getLongitude());
    assertEquals("Bicycles", dto.getCategoryName());
    assertEquals("Used - Good", dto.getConditionName());
    assertEquals(221, dto.getCreatorId());
    assertEquals(now, dto.getCreatedAt());
    assertEquals(now, dto.getUpdatedAt());
    assertEquals("/images/listings/101.jpg", dto.getImagePath());
  }

  @Test
  void setters_shouldSetImageUrlsAndFavoritedCorrectly() {
    ListingDTO dto = new ListingDTO(
            1, "Title", "Brief", "Full", 100, 0,
            "S", 1.0, 1.0, "Cat", "Cond", 2, new Date(), new Date(), "/img.jpg");

    List<String> images = List.of("/img1.jpg", "/img2.jpg");
    dto.setImageUrls(images);
    dto.setFavorited(true);

    assertEquals(images, dto.getImageUrls());
    assertTrue(dto.isFavorited());
  }

  @Test
  void favorited_shouldBeFalseByDefault() {
    ListingDTO dto = new ListingDTO(
            1, "Title", "Brief", "Full", 100, 0,
            "S", 1.0, 1.0, "Cat", "Cond", 2, new Date(), new Date(), "/img.jpg");

    assertFalse(dto.isFavorited());
  }
}
