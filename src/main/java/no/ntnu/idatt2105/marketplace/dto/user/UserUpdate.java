package no.ntnu.idatt2105.marketplace.dto.user;

import no.ntnu.idatt2105.marketplace.model.other.Images;

public class UserUpdate {

  private String firstname;
  private String surname;
  private String email;
  private String phonenumber;
  private Images profile_picture;

  // getters

  public String getFirstname() {
    return firstname;
  }

  public String getSurname() {
    return surname;
  }

  public String getEmail() {
    return email;
  }

  public String getPhonenumber() {
    return phonenumber;
  }

  public Images getProfile_picture() {
    return profile_picture;
  }
}
