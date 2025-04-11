package no.ntnu.idatt2105.marketplace.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object used for registering a new user.
 * Contains essential user information like email, password, and contact details.
 */
@Schema(description = "Data transfer object representing user registration data")
public class UserRegister {

  @Schema(description = "New user email address", example = "example@email.com")
  private String email;

  @Schema(description = "New user password", example = "password123")
  private String password;

  @Schema(description = "New user's first name", example = "Foo")
  private String firstname;

  @Schema(description = "New user's last name", example = "Boo")
  private String surname;

  @Schema(description = "New user's phone number", example = "12345678")
  private String phonenumber;

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

  /**
   * Gets the user's first name.
   * @return the first name.
   */
  public String getFirstname() {
    return firstname;
  }

  /**
   * Sets the user's first name.
   * @param firstname the first name to set.
   */
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  /**
   * Gets the user's last name (surname).
   * @return the surname.
   */
  public String getSurname() {
    return surname;
  }

  /**
   * Sets the user's last name (surname).
   * @param surname the surname to set.
   */
  public void setSurname(String surname) {
    this.surname = surname;
  }

  /**
   * Gets the user's phone number.
   * @return the phone number.
   */
  public String getPhonenumber() {
    return phonenumber;
  }

  /**
   * Sets the user's phone number.
   * @param phonenumber the phone number to set.
   */
  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }
}