package no.ntnu.idatt2105.marketplace.repo;

import no.ntnu.idatt2105.marketplace.model.listing.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConditionRepo extends JpaRepository<Condition, Integer> {
    Optional<Condition> findByName(String name);
}

