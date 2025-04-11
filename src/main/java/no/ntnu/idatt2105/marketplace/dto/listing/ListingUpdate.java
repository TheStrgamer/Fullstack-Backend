package no.ntnu.idatt2105.marketplace.dto.listing;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * Data Transfer Object for updating a listing in the marketplace.
 * Contains editable fields such as title, category, pricing, description, and image references.
 *
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 */
@Schema(description = "DTO for updating a listing")
public class ListingUpdate {

  /** The title of the listing */
  @Schema(description = "The title of the listing")
  private String title;

  /** The ID of the category the listing belongs to */
  @Schema(description = "The category of the listing")
  private int category_id;

  /** The ID of the condition associated with the listing */
  @Schema(description = "The condition of the listing")
  private int condition_id;

  /** Sale status of the listing (e.g., available, sold, reserved) */
  @Schema(description = "The sale status of the listing")
  private int sale_status;

  /** Price of the listing */
  @Schema(description = "The price of the listing")
  private int price;

  /** A short summary or teaser for the listing */
  @Schema(description = "The brief description of the listing")
  private String brief_description;

  /** A detailed explanation of the item being listed */
  @Schema(description = "The full description of the listing")
  private String full_description;

  /** The size or dimension of the item, if applicable */
  @Schema(description = "The size of the listing")
  private String size;

  /** Latitude coordinate for the item's location */
  @Schema(description = "The latitude of the listing")
  private double latitude;

  /** Longitude coordinate for the item's location */
  @Schema(description = "The longitude of the listing")
  private double longitude;

  /** The last updated timestamp as a formatted string */
  @Schema(description = "The last updated time of the listing")
  private String updatedAt;

  /** A list of image URLs that remain after the update */
  @Schema(description = "The remaining original image urls of the listing")
  private List<String> images;

  // Getters

  /** @return The listing's title */
  public String getTitle() {
    return title;
  }

  /** @return The category ID */
  public int getCategory_id() {
    return category_id;
  }

  /** @return The condition ID */
  public int getCondition_id() {
    return condition_id;
  }

  /** @return The listing's price */
  public int getPrice() {
    return price;
  }

  /** @return The sale status of the listing */
  public int getSale_status() {
    return sale_status;
  }

  /** @return The brief description */
  public String getBrief_description() {
    return brief_description;
  }

  /** @return The full description */
  public String getFull_description() {
    return full_description;
  }

  /** @return The latitude coordinate */
  public double getLatitude() {
    return latitude;
  }

  /** @return The longitude coordinate */
  public double getLongitude() {
    return longitude;
  }

  /** @return The size of the listing */
  public String getSize() {
    return size;
  }

  /** @return The update timestamp */
  public String getUpdatedAt() {
    return updatedAt;
  }

  /** @return The list of image URLs */
  public List<String> getImages() {
    return images;
  }

  // Setters

  /** @param title The new title to set */
  public void setTitle(String title) {
    this.title = title;
  }

  /** @param category_id The new category ID to set */
  public void setCategory_id(int category_id) {
    this.category_id = category_id;
  }

  /** @param condition_id The new condition ID to set */
  public void setCondition_id(int condition_id) {
    this.condition_id = condition_id;
  }

  /** @param price The new price to set */
  public void setPrice(int price) {
    this.price = price;
  }

  /** @param sale_status The new sale status to set */
  public void setSale_status(int sale_status) {
    this.sale_status = sale_status;
  }

  /** @param brief_description The new brief description */
  public void setBrief_description(String brief_description) {
    this.brief_description = brief_description;
  }

  /** @param full_description The new full description */
  public void setFull_description(String full_description) {
    this.full_description = full_description;
  }

  /** @param latitude The new latitude coordinate */
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  /** @param longitude The new longitude coordinate */
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  /** @param updatedAt The new last updated timestamp */
  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  /** @param size The new size value */
  public void setSize(String size) {
    this.size = size;
  }

  /** @param images The updated list of image URLs */
  public void setImages(List<String> images) {
    this.images = images;
  }

  /**
   * Returns a string representation of the listing update object.
   *
   * @return A formatted string with all field values.
   */
  @Override
  public String toString() {
    return "ListingUpdate {" +
            "\n  title='" + title + '\'' +
            ",\n  category_id=" + category_id +
            ",\n  condition_id=" + condition_id +
            ",\n  sale_status=" + sale_status +
            ",\n  price=" + price +
            ",\n  brief_description='" + brief_description + '\'' +
            ",\n  full_description='" + full_description + '\'' +
            ",\n  size='" + size + '\'' +
            ",\n  latitude=" + latitude +
            ",\n  longitude=" + longitude +
            ",\n  updatedAt='" + updatedAt + '\'' +
            ",\n  images=" + (images != null ? images.size() : "null") +
            "\n}";
  }
}