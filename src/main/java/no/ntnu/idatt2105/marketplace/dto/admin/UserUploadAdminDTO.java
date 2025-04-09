package no.ntnu.idatt2105.marketplace.dto.admin;


import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.user.User;


@Schema(description = "Gives all information about a user relevant for admin operations")
public class UserUploadAdminDTO {

  @Schema(description = "Users firstname", example = "Foo")
  private String firstname;

  @Schema(description = "Users surname", example = "Boo")
  private String surname;

  @Schema(description = "Users email", example = "new.example@email.com")
  private String email;

  @Schema(description = "Users phonenumber", example = "12345678")
  private String phonenumber;

  @Schema(description = "Users role", example = "USER")
  private String role;

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

  public String getRole() { return role; }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }
  public void setSurname(String surname) {
    this.surname = surname;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }
  public void setRole(String role) {
    this.role = role;
  }
  public UserUploadAdminDTO() {
  }

  public UserUploadAdminDTO(String firstname, String surname, String email, String phonenumber, String role) {
    this.firstname = firstname;
    this.surname = surname;
    this.email = email;
    this.phonenumber = phonenumber;
    this.role = role;
  }
  public UserUploadAdminDTO(User user) {
    this.firstname = user.getFirstname();
    this.surname = user.getSurname();
    this.email = user.getEmail();
    this.phonenumber = user.getPhonenumber();
    this.role = user.getRole().toString();
  }

}
