package no.ntnu.idatt2105.marketplace.repo;

import no.ntnu.idatt2105.marketplace.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
