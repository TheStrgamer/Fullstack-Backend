package no.ntnu.idatt2105.marketplace.responseobjects;

import java.util.List;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.user.Role;
import no.ntnu.idatt2105.marketplace.model.user.User;

/**
UserResponseObject is a class that is used to create a response object that is sent back to the client when a user is requested.
This is done to avoid sending sensitive information about the user, such as password, to the client.
What information is availible depends on if the user is requesting their own information or someone elses.

*/
public class UserResponseObject {
  private String email;
  private String firstname;
  private String surname;
  private String phonenumber;
  private Images profile_picture;

  private List<Role> roles;

  public UserResponseObject(String email, String firstname, String surname, String phonenumber, Images profile_picture, List<Role> roles) {
    this.email = email;
    this.firstname = firstname;
    this.surname = surname;
    this.phonenumber = phonenumber;
    this.profile_picture = profile_picture;
    this.roles = roles;
  }
  public UserResponseObject(String email, String firstname, String surname, String phonenumber, Images profile_picture) {
    this.email = email;
    this.firstname = firstname;
    this.surname = surname;
    this.phonenumber = phonenumber;
    this.profile_picture = profile_picture;
    this.roles = null;
  }

  public UserResponseObject(User user, boolean includeRoles) {
    this.email = user.getEmail();
    this.firstname = user.getFirstname();
    this.surname = user.getSurname();
    this.phonenumber = user.getPhonenumber();
    this.profile_picture = user.getProfile_picture();
    if(includeRoles) {
      this.roles = user.getRoles();
    } else {
      this.roles = null;
    }
  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getFirstname() {
    return firstname;
  }
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }
  public String getSurname() {
    return surname;
  }
  public void setSurname(String surname) {
    this.surname = surname;
  }
  public String getPhonenumber() {
    return phonenumber;
  }
  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }
  public Images getProfile_picture() {
    return profile_picture;
  }
  public void setProfile_picture(Images profile_picture) {
    this.profile_picture = profile_picture;
  }
  public List<Role> getRoles() {
    return roles;
  }
  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

}
