package no.ntnu.idatt2105.marketplace.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;

/**
 * Data Transfer Object (DTO) used for transferring complete listing information
 * relevant for administrative operations such as viewing, updating, or deleting listings.
 */
@Schema(description = "Gives all information about a listing relevant for admin operations")
public class ListingAdminDTO {

  @Schema(description = "Listing id", example = "123456789")
  private int id;

  @Schema(description = "Listing title", example = "Foo")
  private String title;

  @Schema(description = "Listing short description", example = "Boo")
  private String shortDescription;

  @Schema(description = "Listing price", example = "12345678")
  private int price;

  @Schema(description = "Listing category", example = "ELECTRONICS")
  private String category;

  @Schema(description = "Listing condition", example = "NEW")
  private String condition;

  @Schema(description = "Listing status", example = "1")
  private int status;

  @Schema(description = "Listing creator name and id", example = "(1) Foo Boo")
  private String creatorName;

  @Schema(description = "Created date", example = "2023-10-01 00:00:00")
  private String createdDate;

  @Schema(description = "Updated date", example = "2023-10-01 00:00:00")
  private String updatedDate;

  @Schema(description = "Longitude of the address", example = "10.123456")
  private double longitude;

  @Schema(description = "Latitude of the address", example = "10.123456")
  private double latitude;

  @Schema(description = "Listing long description", example = "Boo")
  private String longDescription;

  /**
   * Constructs a {@code ListingAdminDTO} with full details.
   *
   * @param id             The listing ID
   * @param title          The title of the listing
   * @param shortDescription A brief description of the listing
   * @param price          Price of the listing
   * @param category       Name of the category
   * @param condition      Condition label of the item
   * @param status         Status code (e.g., 0 = available, 1 = sold)
   * @param creatorName    Formatted name and ID of the creator
   * @param createdDate    String representation of creation date
   * @param updatedDate    String representation of update date
   * @param longitude      Longitude of the location
   * @param latitude       Latitude of the location
   * @param longDescription Full detailed description
   */
  public ListingAdminDTO(int id, String title, String shortDescription, int price, String category,
                         String condition, int status, String creatorName, String createdDate,
                         String updatedDate, double longitude, double latitude, String longDescription) {
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
   * Constructs a {@code ListingAdminDTO} from a {@link Listing} entity.
   *
   * @param listing The {@code Listing} object to extract data from
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

  // Getters

  /** @return the listing ID */
  public int getId() { return id; }

  /** @return the listing title */
  public String getTitle() { return title; }

  /** @return the short description */
  public String getShortDescription() { return shortDescription; }

  /** @return the listing price */
  public int getPrice() { return price; }

  /** @return the category name */
  public String getCategory() { return category; }

  /** @return the condition name */
  public String getCondition() { return condition; }

  /** @return the listing status */
  public int getStatus() { return status; }

  /** @return the formatted creator name */
  public String getCreatorName() { return creatorName; }

  /** @return the created date string */
  public String getCreatedDate() { return createdDate; }

  /** @return the updated date string */
  public String getUpdatedDate() { return updatedDate; }

  /** @return the longitude of the location */
  public double getLongitude() { return longitude; }

  /** @return the latitude of the location */
  public double getLatitude() { return latitude; }

  /** @return the full description of the listing */
  public String getLongDescription() { return longDescription; }
}