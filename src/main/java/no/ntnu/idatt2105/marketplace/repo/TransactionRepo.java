package no.ntnu.idatt2105.marketplace.repo;

import java.util.List;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.other.Transaction;
import no.ntnu.idatt2105.marketplace.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

}
