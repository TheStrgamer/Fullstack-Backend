package no.ntnu.idatt2105.marketplace.dto.admin;


import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.user.User;


@Schema(description = "Gives all information about a user relevant for admin operations")
public class UserAdminDTO {

    @Schema(description = "Users id", example = "123456789")
    private int id;
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

    @Schema(description = "How many listings the user has", example = "5")
    private int listings;


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

    public int getId() { return id; }
    public String getRole() { return role; }
    public int getListings() { return listings; }

    public UserAdminDTO(int id, String firstname, String surname, String email, String phonenumber, String role, int listings) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.role = role;
        this.listings = listings;
    }
    public UserAdminDTO(User user, int listings) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.phonenumber = user.getPhonenumber();
        this.role = user.getRole().toString();
        this.listings = listings;
    }

}
