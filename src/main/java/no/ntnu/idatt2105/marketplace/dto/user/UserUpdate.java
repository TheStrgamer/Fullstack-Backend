package no.ntnu.idatt2105.marketplace.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.other.Images;

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

  @Schema(description = "Updated profile picture metadata")
  private Images profile_picture;

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

  public Images getProfile_picture() {
    return profile_picture;
  }
}
