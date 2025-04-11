package no.ntnu.idatt2105.marketplace.model.other;

import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Transaction} entity.
 */
public class TransactionTest {

  /**
   * Test the default constructor and all setters/getters.
   */
  @Test
  void testDefaultConstructorAndSetters() {
    Transaction transaction = new Transaction();

    User buyer = new User();
    Listing listing = new Listing();
    Date now = new Date();

    transaction.setId(1);
    transaction.setBuyer(buyer);
    transaction.setListing(listing);
    transaction.setFinal_price(499);
    transaction.setUpdated_at(now);
    transaction.setStatus("COMPLETED");

    assertEquals(1, transaction.getId());
    assertEquals(buyer, transaction.getBuyer());
    assertEquals(listing, transaction.getListing());
    assertEquals(499, transaction.getFinal_price());
    assertEquals(now, transaction.getUpdated_at());
    assertEquals("COMPLETED", transaction.getStatus());
    assertNull(transaction.getCreated_at()); // still null unless explicitly set
  }

  /**
   * Test the all-arguments constructor sets fields correctly.
   */
  @Test
  void testAllArgsConstructor() {
    User buyer = new User();
    Listing listing = new Listing();
    Date created = new Date();
    Date updated = new Date();

    Transaction transaction = new Transaction(
            buyer,
            listing,
            899,
            created,
            updated,
            "PENDING"
    );

    assertEquals(2, transaction.getId());
    assertEquals(buyer, transaction.getBuyer());
    assertEquals(listing, transaction.getListing());
    assertEquals(899, transaction.getFinal_price());
    assertEquals(created, transaction.getCreated_at());
    assertEquals(updated, transaction.getUpdated_at());
    assertEquals("PENDING", transaction.getStatus());
  }
}
