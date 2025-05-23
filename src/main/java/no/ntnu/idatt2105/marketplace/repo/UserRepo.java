package no.ntnu.idatt2105.marketplace.repo;

import no.ntnu.idatt2105.marketplace.model.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

  // get user by email
  Optional<User> findByEmail(String email);

  Optional<User> findByPhonenumber(String phonenumber);

  // save function to save user in bd is included by default
  // usage: UserRepo.save(userObj)

  // get user by id
  Optional<User> findById(int id);

  @EntityGraph(attributePaths = {"favorites"})
  Optional<User> findWithFavoritesByEmail(String email);

  @EntityGraph(attributePaths = {"history"})
  Optional<User> findWithHistoryByEmail(String email);
}
