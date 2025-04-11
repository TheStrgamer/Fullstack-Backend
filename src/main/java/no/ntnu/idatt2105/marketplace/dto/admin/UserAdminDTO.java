package no.ntnu.idatt2105.marketplace.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.user.User;

/**
 * Data Transfer Object (DTO) for exposing administrative-level details about users,
 * including role and the number of listings they have.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 * @see User
 */
@Schema(description = "Gives all information about a user relevant for admin operations")
public class UserAdminDTO {

    @Schema(description = "User's unique identifier", example = "123456789")
    private int id;

    @Schema(description = "User's first name", example = "Foo")
    private String firstname;

    @Schema(description = "User's surname", example = "Boo")
    private String surname;

    @Schema(description = "User's email address", example = "new.example@email.com")
    private String email;

    @Schema(description = "User's phone number", example = "12345678")
    private String phonenumber;

    @Schema(description = "User's role (e.g., ADMIN, USER)", example = "USER")
    private String role;

    @Schema(description = "Total number of listings associated with the user", example = "5")
    private int listings;

    /**
     * Constructs a {@code UserAdminDTO} using all individual fields.
     *
     * @param id          the user's ID
     * @param firstname   the user's first name
     * @param surname     the user's surname
     * @param email       the user's email
     * @param phonenumber the user's phone number
     * @param role        the user's role name
     * @param listings    the number of listings the user has
     */
    public UserAdminDTO(int id, String firstname, String surname, String email,
                        String phonenumber, String role, int listings) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.role = role;
        this.listings = listings;
    }

    /**
     * Constructs a {@code UserAdminDTO} from a {@link User} entity and number of listings.
     *
     * @param user     the user entity
     * @param listings the number of listings associated with the user
     */
    public UserAdminDTO(User user, int listings) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.phonenumber = user.getPhonenumber();
        this.role = user.getRole().toString();
        this.listings = listings;
    }

    /**
     * Returns the user's ID.
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the user's first name.
     *
     * @return the first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Returns the user's surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Returns the user's email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the user's phone number.
     *
     * @return the phone number
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * Returns the user's role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Returns the number of listings the user has.
     *
     * @return number of listings
     */
    public int getListings() {
        return listings;
    }
}