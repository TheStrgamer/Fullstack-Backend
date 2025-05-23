package no.ntnu.idatt2105.marketplace.repo;

import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import no.ntnu.idatt2105.marketplace.model.listing.Condition;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepo extends JpaRepository<Listing, Integer> {
    // get all listings by category
    List<Listing> findAllByCategory(Categories category);

    // get all listings by condition
    List<Listing> findAllByCondition(Condition condition);

    // get all listings by price
    List<Listing> findAllByPrice(int price);

    // get all listings by title
    List<Listing> findAllByTitle(String title);

    List<Listing> findAllByCreator(User creator);

    List<Listing> findByCategory_Name(String categoryName);

    List<Listing> findByTitleContainingIgnoreCase(String title);


}