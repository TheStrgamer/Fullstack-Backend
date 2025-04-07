package no.ntnu.idatt2105.marketplace.responseobjects;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.user.Role;
import no.ntnu.idatt2105.marketplace.model.user.User;

import java.util.List;

@Schema(description = "Response object containing non-sensitive user information")
public class UserResponseObject {

  @Schema(description = "User email address", example = "user@example.com")
  private String email;

  @Schema(description = "User's first name", example = "Alice")
  private String firstname;

  @Schema(description = "User's surname", example = "Andersen")
  private String surname;

  @Schema(description = "User's phone number", example = "12345678")
  private String phonenumber;

  @Schema(description = "User's profile picture object")
  private Images profile_picture;

  @Schema(description = "List of roles the user has (only included for self-requests)", example = "[{\"name\": \"ROLE_USER\"}]")
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
    this.roles = includeRoles ? user.getRoles() : null;
  }

  // Getters and setters

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
