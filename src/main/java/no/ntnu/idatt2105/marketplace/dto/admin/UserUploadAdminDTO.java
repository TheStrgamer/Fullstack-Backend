package no.ntnu.idatt2105.marketplace.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;

/**
 * Data Transfer Object (DTO) used by administrators to update or manage user information.
 * Includes both user profile data and role designation.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 */
@Schema(description = "Gives all information about a user relevant for admin operations")
public class UserUploadAdminDTO {

  @Schema(description = "User's first name", example = "Foo")
  private String firstname;

  @Schema(description = "User's surname", example = "Boo")
  private String surname;

  @Schema(description = "User's email", example = "new.example@email.com")
  private String email;

  @Schema(description = "User's phone number", example = "12345678")
  private String phonenumber;

  @Schema(description = "User's role (e.g., ADMIN, USER)", example = "USER")
  private String role;

  /**
   * Default no-argument constructor.
   */
  public UserUploadAdminDTO() {}

  /**
   * Constructor to create DTO from basic user field values.
   *
   * @param firstname    First name of the user
   * @param surname      Surname of the user
   * @param email        Email address of the user
   * @param phonenumber  Phone number of the user
   * @param role         Role name (e.g., "USER", "ADMIN")
   */
  public UserUploadAdminDTO(String firstname, String surname, String email, String phonenumber, String role) {
    this.firstname = firstname;
    this.surname = surname;
    this.email = email;
    this.phonenumber = phonenumber;
    this.role = role;
  }

  /**
   * Constructor to map from a {@link User} entity.
   *
   * @param user the user entity to map from
   */
  public UserUploadAdminDTO(User user) {
    this.firstname = user.getFirstname();
    this.surname = user.getSurname();
    this.email = user.getEmail();
    this.phonenumber = user.getPhonenumber();
    this.role = user.getRole().toString();
  }

  /**
   * Gets the user's first name.
   *
   * @return the first name
   */
  public String getFirstname() {
    return firstname;
  }

  /**
   * Sets the user's first name.
   *
   * @param firstname the new first name
   */
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  /**
   * Gets the user's surname.
   *
   * @return the surname
   */
  public String getSurname() {
    return surname;
  }

  /**
   * Sets the user's surname.
   *
   * @param surname the new surname
   */
  public void setSurname(String surname) {
    this.surname = surname;
  }

  /**
   * Gets the user's email address.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the user's email address.
   *
   * @param email the new email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the user's phone number.
   *
   * @return the phone number
   */
  public String getPhonenumber() {
    return phonenumber;
  }

  /**
   * Sets the user's phone number.
   *
   * @param phonenumber the new phone number
   */
  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }

  /**
   * Gets the user's role (e.g., "USER", "ADMIN").
   *
   * @return the role name
   */
  public String getRole() {
    return role;
  }

  /**
   * Sets the user's role (e.g., "USER", "ADMIN").
   *
   * @param role the new role
   */
  public void setRole(String role) {
    this.role = role;
  }
}