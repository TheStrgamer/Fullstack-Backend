package no.ntnu.idatt2105.marketplace.service.user;

import java.util.Optional;
import no.ntnu.idatt2105.marketplace.exception.UserNotAdminException;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.repo.RoleRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private JWT_token jwt;

  public boolean validateAdminPrivileges(String authorizationHeader) {
    if (!authorizationHeader.startsWith("Bearer ")) {
      System.out.println("Invalid Authorization header");
      throw new UserNotAdminException("Invalid Authorization header");
    }
    String token = authorizationHeader.substring(7);
    int user_id = Integer.parseInt(jwt.extractIdFromJwt(token));
    Optional<User> user = userRepo.findById(user_id);

    if (user.isEmpty()) {
      System.out.println("No user found with given id:" + user_id);
      throw new UserNotAdminException("User not found with given id:" + user_id);
    }
    User adminUser = user.get();
    if (!adminUser.isAdmin()) {
      System.out.println("User is not an admin");
      throw new UserNotAdminException("User is not an admin");
    }
    return true;
  }
}
