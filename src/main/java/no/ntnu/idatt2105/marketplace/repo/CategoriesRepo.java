package no.ntnu.idatt2105.marketplace.repo;

import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepo extends JpaRepository<Categories, Integer> {
    Optional<Categories> findById(int id);
    Optional<Categories> findByName(String name);
}