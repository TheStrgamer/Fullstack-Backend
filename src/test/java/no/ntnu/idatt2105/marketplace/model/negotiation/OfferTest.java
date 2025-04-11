package no.ntnu.idatt2105.marketplace.model.negotiation;

import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Offer} entity.
 */
public class OfferTest {

  /**
   * Test the default constructor and all setters/getters.
   */
  @Test
  void testDefaultConstructorAndSetters() {
    Offer offer = new Offer();

    User buyer = new User();
    Listing listing = new Listing();
    Date created = new Date();
    Date updated = new Date();

    offer.setId(1);
    offer.setListing_id(listing);
    offer.setCurrent_offer(999.99f);
    offer.setStatus(1);
    offer.setUpdated_at(updated);

    // Use reflection or field assignment to simulate created_at if needed.
    // For this example we validate updated_at only
    assertEquals(1, offer.getId());
    assertEquals(listing, offer.getListing_id());
    assertEquals(999.99f, offer.getCurrent_offer());
    assertEquals(1, offer.getStatus());
    assertEquals(updated, offer.getUpdated_at());
  }

  /**
   * Test the all-args constructor sets fields correctly.
   */
  @Test
  void testAllArgsConstructor() {
    int id = 2;
    User buyer = new User();
    Listing listing = new Listing();
    float currentOffer = 500.50f;
    int status = 1;
    Date created = new Date();
    Date updated = new Date();

    Offer offer = new Offer(id, buyer, listing, currentOffer, status, created, updated);

    assertEquals(id, offer.getId());
    assertEquals(buyer, offer.getBuyer_id());
    assertEquals(listing, offer.getListing_id());
    assertEquals(currentOffer, offer.getCurrent_offer());
    assertEquals(status, offer.getStatus());
    assertEquals(created, offer.getCreated_at());
    assertEquals(updated, offer.getUpdated_at());
  }
}
