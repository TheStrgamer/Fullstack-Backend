package no.ntnu.idatt2105.marketplace.dto.listing;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.List;

@Schema(description = "Data transfer object representing a marketplace listing")
public class ListingMiniDTO {
  @Schema(description = "Title of the listing", example = "Vintage bicycle for sale")
  private String title;
  @Schema(description = "Price of the listing in NOK", example = "1500")
  private int price;

  @Schema(description = "Name of the listing creator", example = "Poe Bidau Gustang")
  private String creatorName;

  @Schema(description = "Relative or absolute path to the listing's main image", example = "/images/listings/101.jpg")
  private String imagePath;

  // Constructor
  public ListingMiniDTO(String title, int price, String imagePath, String creatorName) {
    this.title = title;
    this.price = price;
    this.imagePath = imagePath;
    this.creatorName = creatorName;
  }

  // Getters and Setters
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public int getPrice() { return price; }
  public void setPrice(int price) { this.price = price; }
  public String getImagePath() { return imagePath; }
  public void setImagePath(String imagePath) { this.imagePath = imagePath; }
  public String getCreatorName() { return creatorName; }
  public void setCreatorName(String creatorName) { this.creatorName = creatorName; }
}
