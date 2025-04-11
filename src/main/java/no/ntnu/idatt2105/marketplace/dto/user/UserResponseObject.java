package no.ntnu.idatt2105.marketplace.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.user.User;

/**
 * Response object containing non-sensitive user information,
 * typically used for frontend rendering or profile viewing.
 */
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

  @Schema(description = "User's profile picture URL")
  @JsonProperty("profilePicture")
  private String profilePicture;

  @Schema(description = "The role the user has (only included for self-requests)", example = "1")
  private int role;

  /**
   * Full constructor including role.
   */
  public UserResponseObject(String email, String firstname, String surname, String phonenumber, String profilePicture, int role) {
    this.email = email;
    this.firstname = firstname;
    this.surname = surname;
    this.phonenumber = phonenumber;
    this.profilePicture = profilePicture;
    this.role = role;
  }

  /**
   * Constructor excluding role. Defaults role to -1 (not shown).
   */
  public UserResponseObject(String email, String firstname, String surname, String phonenumber, String profilePicture) {
    this(email, firstname, surname, phonenumber, profilePicture, -1);
  }

  /**
   * Constructor that creates a response object from a User entity.
   * Role is optionally included.
   *
   * @param user The User entity.
   * @param includeRoles Whether to include the user's role.
   */
  public UserResponseObject(User user, boolean includeRoles) {
    this.email = user.getEmail();
    this.firstname = user.getFirstname();
    this.surname = user.getSurname();
    this.phonenumber = user.getPhonenumber();
    this.profilePicture = user.getProfile_picture() != null ? user.getProfile_picture().getFilepath_to_image() : null;
    this.role = includeRoles ? user.getRole().getId() : -1;
  }

  /** @return the email of the user */
  public String getEmail() {
    return email;
  }

  /** @param email sets the email of the user */
  public void setEmail(String email) {
    this.email = email;
  }

  /** @return the first name of the user */
  public String getFirstname() {
    return firstname;
  }

  /** @param firstname sets the first name of the user */
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  /** @return the surname of the user */
  public String getSurname() {
    return surname;
  }

  /** @param surname sets the surname of the user */
  public void setSurname(String surname) {
    this.surname = surname;
  }

  /** @return the phone number of the user */
  public String getPhonenumber() {
    return phonenumber;
  }

  /** @param phonenumber sets the phone number of the user */
  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }

  /** @return the profile picture URL */
  public String getProfilePicture() {
    return profilePicture;
  }

  /** @param profile_picture_url sets the profile picture URL */
  public void setProfilePicture(String profile_picture_url) {
    this.profilePicture = profile_picture_url;
  }

  /** @return the role id of the user */
  public int getRole() {
    return role;
  }

  /** @param role sets the role id */
  public void setRole(int role) {
    this.role = role;
  }

  /**
   * @return string representation of this user response object
   */
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