package no.ntnu.idatt2105.marketplace.dto.user;

import no.ntnu.idatt2105.marketplace.model.other.Images;

import javax.print.DocFlavor;

public class UserUpdate {

  private String firstname;
  private String surname;
  private String phonenumber;
  private Images profile_picture;

  // getters

  public String getFirstname() {
    return firstname;
  }

  public String getSurname() {
    return surname;
  }

  public String getPhonenumber() {
    return phonenumber;
  }

  public Images getProfile_picture() {
    return profile_picture;
  }
}
