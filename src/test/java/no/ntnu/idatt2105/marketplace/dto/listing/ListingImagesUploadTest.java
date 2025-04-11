package no.ntnu.idatt2105.marketplace.dto.listing;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ListingImagesUpload}.
 */
class ListingImagesUploadTest {

  /**
   * Tests that setters correctly assign values and getters retrieve them.
   */
  @Test
  void testSettersAndGetters_shouldReturnCorrectValues() {
    ListingImagesUpload dto = new ListingImagesUpload();
    MultipartFile file1 = new MockMultipartFile("file1", "file1.jpg", "image/jpeg", new byte[]{1, 2, 3});
    MultipartFile file2 = new MockMultipartFile("file2", "file2.jpg", "image/jpeg", new byte[]{4, 5, 6});

    dto.setId(42);
    dto.setImages(List.of(file1, file2));

    assertEquals(42, dto.getId());
    assertNotNull(dto.getImages());
    assertEquals(2, dto.getImages().size());
    assertEquals("file1.jpg", dto.getImages().get(0).getOriginalFilename());
    assertEquals("file2.jpg", dto.getImages().get(1).getOriginalFilename());
  }

  /**
   * Tests that the image list can be set to null safely.
   */
  @Test
  void testSetImagesNull_shouldHandleNullGracefully() {
    ListingImagesUpload dto = new ListingImagesUpload();
    dto.setImages(null);
    assertNull(dto.getImages());
  }
}
