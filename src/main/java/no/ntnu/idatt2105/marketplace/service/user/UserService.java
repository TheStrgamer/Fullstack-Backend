package no.ntnu.idatt2105.marketplace.service.user;

import no.ntnu.idatt2105.marketplace.dto.user.UserUpdate;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepo userRepo;

  public void updateUser(int id, UserUpdate dto) throws Exception{
    User user = userRepo.findById(id).orElseThrow(() -> new Exception("User not found"));


    // TODO: implement logger
    System.out.println("Updating user: " + user.getId() + " update info: " + dto.toString());

    user.setFirstname(dto.getFirstname() != null              ? dto.getFirstname()        : user.getFirstname());
    user.setSurname(dto.getSurname() != null                  ? dto.getSurname()          : user.getSurname());
    user.setPhonenumber(dto.getPhonenumber() != null          ? dto.getPhonenumber()      : user.getPhonenumber());
    user.setProfile_picture(dto.getProfile_picture() != null  ? dto.getProfile_picture()  : user.getProfile_picture());

    System.out.println("Updated user");
    userRepo.save(user);
  }
}
