package no.ntnu.idatt2105.marketplace.dto.admin;

import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import no.ntnu.idatt2105.marketplace.model.listing.Condition;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ListingAdminDTO}.
 */
class ListingAdminDTOTest {

  /**
   * Test the full constructor to ensure all fields are correctly assigned and retrieved.
   */
  @Test
  void constructor_shouldAssignFieldsCorrectly() {
    ListingAdminDTO dto = new ListingAdminDTO(
            1, "Test Title", "Short Desc", 500,
            "Electronics", "Used", 1, "(1) Alice Doe",
            "2024-01-01 00:00:00", "2024-01-02 00:00:00",
            10.12, 63.42, "Full Description"
    );

    assertEquals(1, dto.getId());
    assertEquals("Test Title", dto.getTitle());
    assertEquals("Short Desc", dto.getShortDescription());
    assertEquals(500, dto.getPrice());
    assertEquals("Electronics", dto.getCategory());
    assertEquals("Used", dto.getCondition());
    assertEquals(1, dto.getStatus());
    assertEquals("(1) Alice Doe", dto.getCreatorName());
    assertEquals("2024-01-01 00:00:00", dto.getCreatedDate());
    assertEquals("2024-01-02 00:00:00", dto.getUpdatedDate());
    assertEquals(10.12, dto.getLongitude());
    assertEquals(63.42, dto.getLatitude());
    assertEquals("Full Description", dto.getLongDescription());
  }

  /**
   * Test the constructor that takes a {@link Listing} object.
   */
  @Test
  void constructorFromListing_shouldMapFieldsCorrectly() {
    User user = new User();
    user.setId(1);
    user.setFirstname("Alice");
    user.setSurname("Doe");

    Categories cat = new Categories();
    cat.setName("Electronics");
    cat.setDescription("Tech");

    Condition condition = new Condition();
    condition.setName("Used");

    Date now = new Date();

    Listing listing = new Listing();
    listing.setId(100);
    listing.setTitle("Laptop for sale");
    listing.setBrief_description("Good condition");
    listing.setPrice(1500);
    listing.setCategory(cat);
    listing.setCondition(condition);
    listing.setSale_status(0);
    listing.setCreator(user);
    listing.setCreated_at(now);
    listing.setUpdated_at(now);
    listing.setLongitude(10.5);
    listing.setLatitude(63.1);
    listing.setFull_description("A well-maintained laptop");

    ListingAdminDTO dto = new ListingAdminDTO(listing);

    assertEquals(100, dto.getId());
    assertEquals("Laptop for sale", dto.getTitle());
    assertEquals("Good condition", dto.getShortDescription());
    assertEquals(1500, dto.getPrice());
    assertEquals("Electronics", dto.getCategory());
    assertEquals("Used", dto.getCondition());
    assertEquals(0, dto.getStatus());
    assertEquals("(1) Alice Doe", dto.getCreatorName());
    assertEquals(now.toString(), dto.getCreatedDate());
    assertEquals(now.toString(), dto.getUpdatedDate());
    assertEquals(10.5, dto.getLongitude());
    assertEquals(63.1, dto.getLatitude());
    assertEquals("A well-maintained laptop", dto.getLongDescription());
  }
}
