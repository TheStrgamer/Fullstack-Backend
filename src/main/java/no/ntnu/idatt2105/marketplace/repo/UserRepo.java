package no.ntnu.idatt2105.marketplace.repo;

import no.ntnu.idatt2105.marketplace.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
  Optional<User> findUserByEmail(String email);

  // save function to save user in bd is included by default
  // usage: UserRepo.save(userObj)

}
