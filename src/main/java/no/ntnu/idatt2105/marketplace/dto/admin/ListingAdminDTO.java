package no.ntnu.idatt2105.marketplace.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;

/**
 * Data Transfer Object (DTO) used by administrators to view detailed information
 * about a specific listing. This includes metadata, creator info, geolocation, and
 * other relevant details needed for admin operations.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 * @see Listing
 */
@Schema(description = "Gives all information about a listing relevant for admin operations")
public class ListingAdminDTO {

  /**
   * The unique identifier of the listing.
   */
  @Schema(description = "Listing id", example = "123456789")
  private int id;

  /**
   * The title of the listing.
   */
  @Schema(description = "Listing title", example = "Foo")
  private String title;

  /**
   * A short description of the listing, shown in previews or summaries.
   */
  @Schema(description = "Listing short description", example = "Boo")
  private String shortDescription;

  /**
   * The price of the item in the listing, in whole currency units (e.g., NOK).
   */
  @Schema(description = "Listing price", example = "12345678")
  private int price;

  /**
   * The category the listing belongs to (e.g., ELECTRONICS).
   */
  @Schema(description = "Listing category", example = "ELECTRONICS")
  private String category;

  /**
   * The condition of the item in the listing (e.g., NEW, USED).
   */
  @Schema(description = "Listing condition", example = "NEW")
  private String condition;

  /**
   * The sale status of the listing (e.g., 1 for active, 0 for inactive).
   */
  @Schema(description = "Listing status", example = "1")
  private int status;

  /**
   * The full name and ID of the user who created the listing.
   */
  @Schema(description = "Listing creator name and id", example = "(1) Foo Boo")
  private String creatorName;

  /**
   * The timestamp representing when the listing was created.
   */
  @Schema(description = "created date", example = "2023-10-01 00:00:00")
  private String createdDate;

  /**
   * The timestamp representing when the listing was last updated.
   */
  @Schema(description = "updated date", example = "2023-10-01 00:00:00")
  private String updatedDate;

  /**
   * The longitude coordinate of the listing's location.
   */
  @Schema(description = "longitude of the adress", example = "10.123456")
  private double longitude;

  /**
   * The latitude coordinate of the listing's location.
   */
  @Schema(description = "latitude of the adress", example = "10.123456")
  private double latitude;

  /**
   * A detailed, long description of the listing, shown on the full listing page.
   */
  @Schema(description = "Listing long description", example = "Boo")
  private String longDescription;

  /**
   * Returns the listing ID of the listing.
   *
   * @return the listing ID
   */
  public int getId() { return id; }

  /**
   * Returns the title of the listing.
   *
   * @return the title
   */
  public String getTitle() { return title; }

  /**
   * Returns the short description of the listing.
   *
   * @return the short description
   */
  public String getShortDescription() { return shortDescription; }

  /**
   * Returns the price of the listing.
   *
   * @return the price
   */
  public int getPrice() { return price; }

  /**
   * Returns the category of the listing.
   *
   * @return the category
   */
  public String getCategory() { return category; }

  /**
   * Returns the condition of the listing.
   *
   * @return the condition
   */
  public String getCondition() { return condition; }

  /**
   * Returns the sale status of the listing.
   *
   * @return the sale status
   */
  public int getStatus() { return status; }

  /**
   * Returns the name of the owner of the listing.
   *
   * @return the name of the owner
   */
  public String getCreatorName() { return creatorName; }

  /**
   * Returns the created at date of the listing.
   *
   * @return the created at date
   */
  public String getCreatedDate() { return createdDate; }

  /**
   * Returns the updated at date of the listing.
   *
   * @return the updated at date
   */
  public String getUpdatedDate() { return updatedDate; }

  /**
   * Returns the longitude of the listing.
   *
   * @return the longitude
   */
  public double getLongitude() { return longitude; }

  /**
   * Returns the latitude of the listing.
   *
   * @return the latitude
   */
  public double getLatitude() { return latitude; }

  /**
   * Returns the long description of the listing.
   *
   * @return the long description
   */
  public String getLongDescription() { return longDescription; }

  /**
   * Constructs a new {@code ListingAdminDTO} with all fields explicitly set.
   *
   * @param id              the ID of the listing
   * @param title           the title of the listing
   * @param shortDescription a short description
   * @param price           the price of the listing
   * @param category        the category name
   * @param condition       the condition of the item
   * @param status          the listing's sale status
   * @param creatorName     full name (and ID) of the listing's creator
   * @param createdDate     creation date
   * @param updatedDate     last updated date
   * @param longitude       longitude of the listing location
   * @param latitude        latitude of the listing location
   * @param longDescription detailed description of the listing
   * @since 1.0
   */
  public ListingAdminDTO(int id, String title, String shortDescription, int price, String category,
      String condition, int status, String creatorName, String createdDate, String updatedDate,
      double longitude, double latitude, String longDescription) {
    this.id = id;
    this.title = title;
    this.shortDescription = shortDescription;
    this.price = price;
    this.category = category;
    this.condition = condition;
    this.status = status;
    this.creatorName = creatorName;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
    this.longitude = longitude;
    this.latitude = latitude;
    this.longDescription = longDescription;
  }

  /**
   * Constructs a new {@code ListingAdminDTO} based on a {@link Listing} entity.
   * This constructor extracts relevant information from the entity, including
   * creator name and coordinates.
   *
   * @param listing the listing entity
   * @since 1.0
   * @see Listing
   */
  public ListingAdminDTO(Listing listing) {
    this.id = listing.getId();
    this.title = listing.getTitle();
    this.shortDescription = listing.getBrief_description();
    this.price = listing.getPrice();
    this.category = listing.getCategory().toString();
    this.condition = listing.getCondition().toString();
    this.status = listing.getSale_status();
    this.creatorName = "(" + listing.getCreator().getId() + ") " + listing.getCreator().getFirstname() + " " + listing.getCreator().getSurname();
    this.createdDate = listing.getCreated_at().toString();
    this.updatedDate = listing.getUpdated_at().toString();
    this.longitude = listing.getLongitude();
    this.latitude = listing.getLatitude();
    this.longDescription = listing.getFull_description();
  }
}
