package no.ntnu.idatt2105.marketplace.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.other.Images;

/**
 * Data Transfer Object for updating user profile information.
 * Used when a user wants to modify personal details such as name, email, phone number,
 * or profile picture.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 */
@Schema(description = "Data transfer object representing user update data")
public class UserUpdate {

  @Schema(description = "Updated firstname", example = "Foo")
  private String firstname;

  @Schema(description = "Updated surname", example = "Boo")
  private String surname;

  @Schema(description = "Updated email", example = "new.example@email.com")
  private String email;

  @Schema(description = "Updated phonenumber", example = "12345678")
  private String phonenumber;

  /**
   * Gets the updated first name.
   * @return the updated first name.
   */
  public String getFirstname() {
    return firstname;
  }

  /**
   * Sets the updated first name.
   * @param firstname the new first name.
   */
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  /**
   * Gets the updated surname.
   * @return the updated surname.
   */
  public String getSurname() {
    return surname;
  }

  /**
   * Sets the updated surname.
   * @param surname the new surname.
   */
  public void setSurname(String surname) {
    this.surname = surname;
  }

  /**
   * Gets the updated email address.
   * @return the updated email address.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the updated email address.
   * @param email the new email address.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the updated phone number.
   * @return the updated phone number.
   */
  public String getPhonenumber() {
    return phonenumber;
  }

  /**
   * Sets the updated phone number.
   * @param phonenumber the new phone number.
   */
  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }

}