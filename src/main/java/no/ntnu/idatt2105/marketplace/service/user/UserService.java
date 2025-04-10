package no.ntnu.idatt2105.marketplace.service.user;

import java.util.List;
import java.util.Optional;

import no.ntnu.idatt2105.marketplace.dto.negotiation.NegotiationChatsDTO;
import no.ntnu.idatt2105.marketplace.dto.user.UserUpdate;
import no.ntnu.idatt2105.marketplace.exception.EmailNotAvailibleException;
import no.ntnu.idatt2105.marketplace.exception.IncorrectPasswordException;
import no.ntnu.idatt2105.marketplace.exception.PhonenumberNotAvailibleException;
import no.ntnu.idatt2105.marketplace.exception.UserNotFoundException;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.Role;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.repo.RoleRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.dto.other.TokenResponseObject;
import no.ntnu.idatt2105.marketplace.service.security.BCryptHasher;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class UserService {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private RoleRepo roleRepo;

  @Autowired
  private ListingRepo listingRepo;

  @Autowired
  private JWT_token jwt;

  private final BCryptHasher hasher = new BCryptHasher();

  public void setJwt(JWT_token jwt) {
    this.jwt = jwt;
  }

  public void updateUser(int id, UserUpdate dto) throws Exception{
    User user = userRepo.findById(id).orElseThrow(() -> new Exception("User not found"));


    // TODO: implement logger
    System.out.println("Updating user: " + user.getId() + " update info: " + dto.toString());

    user.setFirstname(      dto.getFirstname()            != null   ? dto.getFirstname()        : user.getFirstname());
    user.setSurname(        dto.getSurname()              != null   ? dto.getSurname()          : user.getSurname());
    user.setEmail(          dto.getEmail()                != null   ? dto.getEmail()            : user.getEmail());
    user.setPhonenumber(    dto.getPhonenumber()          != null   ? dto.getPhonenumber()      : user.getPhonenumber());
    user.setProfile_picture(dto.getProfile_picture()      != null   ? dto.getProfile_picture()  : user.getProfile_picture());

    System.out.println("Updated user");
    userRepo.save(user);
  }

  public List<NegotiationChatsDTO> getActiveChats(User user) {
    return user.getActiveNegotiations().stream()
        .map(negotiation -> new NegotiationChatsDTO(
            negotiation.getId(),
            negotiation.getBuyer() == user ? negotiation.getSeller() : negotiation.getBuyer(),
            negotiation.getLatestMessage(),
            negotiation.getUpdatedAt()
        )).toList();
  }


  public boolean validateEmail(String email) {
    return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
  }
  public boolean verifyEmailNotInUse(String email) {
    return userRepo.findByEmail(email).isEmpty();
  }
  public boolean validatePassword(String password) {
    return !password.isEmpty();
  }
  public boolean validatePhoneNumber(String phoneNumber) {
    return phoneNumber.matches("^\\d{8}$");
  }
  public boolean verifyPhoneNumberNotInUse(String phoneNumber) {
    return userRepo.findByPhonenumber(phoneNumber).isEmpty();
  }
  public boolean validateName(String name) {
    return name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
  }
  public boolean validateUser(User user) {
    return validateEmail(user.getEmail()) && validatePassword(user.getPassword()) && validatePhoneNumber(user.getPhonenumber()) && validateName(user.getFirstname()) && validateName(user.getSurname());
  }

  public TokenResponseObject authenticate(String email, String password) {
    Optional<User> user = userRepo.findByEmail(email);
    if (user.isEmpty()) {
      throw new UserNotFoundException("No user found with given email and password");
    }
    if (!hasher.checkPassword(password, user.get().getPassword())) {
      throw new IncorrectPasswordException("Incorrect password for given email");
    }
    System.out.println("User with id " + user.get().getIdAsString() + " found with given email and password");
    return jwt.generateJwtToken(user.get());
  }

  public int register(User user) {
    if (!verifyEmailNotInUse(user.getEmail())) {
      System.out.println("Email is already in use");
      throw new EmailNotAvailibleException("Email is already in use");
    }
    if (!verifyPhoneNumberNotInUse(user.getPhonenumber())) {
      System.out.println("Phone number is already in use");
      throw new PhonenumberNotAvailibleException("Phone number is already in use");
    }
    if (!validateUser(user)) {
      System.out.println("Invalid user data");
      throw new IllegalArgumentException("Invalid user data");
    }
    user.setPassword(hasher.hashPassword(user.getPassword()));
    Role role = roleRepo.findByName("USER").orElseThrow();
    user.setRole(role);
    userRepo.save(user);
    return 0;
  }
  public int getListingCount(User user) {
    return listingRepo.findAllByCreator(user).size();
  }

  public boolean toggleFavorite(int userId, int listingId) {
    Optional<User> userOpt = userRepo.findById(userId);
    Optional<Listing> listingOpt = listingRepo.findById(listingId);

    if (userOpt.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    if (listingOpt.isEmpty()) {
      throw new IllegalArgumentException("Listing not found");
    }

    User user = userOpt.get();
    Listing listing = listingOpt.get();
    boolean isNowFavorite;

    if (user.getFavorites().contains(listing)) {
      user.removeFavorite(listing);
      isNowFavorite = false;
    } else {
      user.addFavorite(listing);
      isNowFavorite = true;
    }

    userRepo.save(user);
    return isNowFavorite;
  }

}
