package no.ntnu.idatt2105.marketplace.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object used for registering a new user.
 * Contains essential user information like email, password, and contact details.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 */
@Schema(description = "Data transfer object representing user login data")
public class UserLogin {

  @Schema(description = "User email address", example = "example@email.com")
  private String email;

  @Schema(description = "User password", example = "password123")
  private String password;


  /**
   * Gets the user's email.
   * @return the email address.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the user's email.
   * @param email the email address to set.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the user's password.
   * @return the password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the user's password.
   * @param password the password to set.
   */
  public void setPassword(String password) {
    this.password = password;
  }
}