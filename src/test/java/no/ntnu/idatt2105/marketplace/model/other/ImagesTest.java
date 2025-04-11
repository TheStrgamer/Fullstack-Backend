package no.ntnu.idatt2105.marketplace.model.other;

import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Images} class.
 */
public class ImagesTest {

  /**
   * Tests the default constructor and setters.
   */
  @Test
  void testDefaultConstructorAndSetters() {
    Images image = new Images();

    image.setId(1);
    image.setFilepath_to_image("/images/listing/phone.png");

    Listing mockListing = new Listing();
    image.setListing(mockListing);

    assertEquals(1, image.getId());
    assertEquals("/images/listing/phone.png", image.getFilepath_to_image());
    assertEquals(mockListing, image.getListing());
  }

  /**
   * Tests the all-arguments constructor.
   */
  @Test
  void testAllArgsConstructor() {
    Images image = new Images(2, "/images/listing/laptop.png");

    assertEquals(2, image.getId());
    assertEquals("/images/listing/laptop.png", image.getFilepath_to_image());
    assertNull(image.getListing());
  }
}
