package no.ntnu.idatt2105.marketplace.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;

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

  @Schema(description = "created date", example = "2023-10-01 00:00:00")
  private String createdDate;

  @Schema(description = "updated date", example = "2023-10-01 00:00:00")
  private String updatedDate;

  @Schema(description = "longitude of the adress", example = "10.123456")
  private double longitude;

  @Schema(description = "latitude of the adress", example = "10.123456")
  private double latitude;

  @Schema(description = "Listing long description", example = "Boo")
  private String longDescription;

  // getters
  public int getId() { return id; }
  public String getTitle() { return title; }
  public String getShortDescription() { return shortDescription; }
  public int getPrice() { return price; }
  public String getCategory() { return category; }
  public String getCondition() { return condition; }
  public int getStatus() { return status; }
  public String getCreatorName() { return creatorName; }
  public String getCreatedDate() { return createdDate; }
  public String getUpdatedDate() { return updatedDate; }
  public double getLongitude() { return longitude; }
  public double getLatitude() { return latitude; }
  public String getLongDescription() { return longDescription; }

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
