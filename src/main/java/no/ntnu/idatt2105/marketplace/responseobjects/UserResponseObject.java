package no.ntnu.idatt2105.marketplace.responseobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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

  @Schema(description = "User's profile picture url")
  @JsonProperty("profilePicture")
  private String profilePicture;

  @Schema(description = "The role the user has (only included for self-requests)", example = "1")
  private int role;

  public UserResponseObject(String email, String firstname, String surname, String phonenumber, String profilePicture, int role) {
    this.email = email;
    this.firstname = firstname;
    this.surname = surname;
    this.phonenumber = phonenumber;
    this.profilePicture = profilePicture;
    this.role = role;
  }

  public UserResponseObject(String email, String firstname, String surname, String phonenumber, String profilePicture) {
    this.email = email;
    this.firstname = firstname;
    this.surname = surname;
    this.phonenumber = phonenumber;
    this.profilePicture = profilePicture;
    this.role = -1;
  }

  public UserResponseObject(User user, boolean includeRoles) {
    this.email = user.getEmail();
    this.firstname = user.getFirstname();
    this.surname = user.getSurname();
    this.phonenumber = user.getPhonenumber();
    this.profilePicture = user.getProfile_picture() != null ? user.getProfile_picture().getFilepath_to_image() : null;
    this.role = includeRoles ? user.getRole().getId() : -1;
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

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profile_picture_url) {
    this.profilePicture = profile_picture_url;
  }

  public int getRole() {
    return role;
  }

  public void setRole(int role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "Email: " + this.email
            + "\nFirstName: " + this.firstname
            + "\nLastname: " + this.surname
            + "\nPhonenumber: " + this.phonenumber
            + "\nProfile Picture URL : " + this.profilePicture
            + "\nRole: " + this.role;
  }
}
