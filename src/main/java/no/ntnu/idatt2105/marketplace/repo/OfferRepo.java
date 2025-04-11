package no.ntnu.idatt2105.marketplace.repo;

import java.util.List;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.negotiation.Offer;
import no.ntnu.idatt2105.marketplace.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepo  extends JpaRepository<Offer, Integer> {
    Offer findById(int id);
    List<Offer> findByBuyerAndListingId(User buyer, int listing_id);
    List<Offer> findByCreatorAndListingId(User creator, int listing_id);
    List<Offer> findByListingId(int listingId);
}
