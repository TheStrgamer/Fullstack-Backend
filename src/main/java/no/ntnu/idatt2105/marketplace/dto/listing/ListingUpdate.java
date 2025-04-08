package no.ntnu.idatt2105.marketplace.dto.listing;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "DTO for updating a listing")
public class ListingUpdate {

  @Schema(description = "The title of the listing")
  private String title;

  @Schema(description = "The category of the listing")
  private int category_id;

  @Schema(description = "The condition of the listing")
  private int condition_id;

  @Schema(description = "The sale status of the listing")
  private int sale_status;

  @Schema(description = "The price of the listing")
  private int price;

  @Schema(description = "The brief description of the listing")
  private String brief_description;

  @Schema(description = "The full description of the listing")
  private String full_description;

  @Schema(description = "The size of the listing")
  private String size;

  @Schema(description = "The latitude of the listing")
  private double latitude;

  @Schema(description = "The longitude of the listing")
  private double longitude;

  @Schema(description = "The images of the listing")
  private List<MultipartFile> images;

  // Getters and setters

  public String getTitle() {
    return title;
  }

  public int getCategory_id() {
    return category_id;
  }

  public int getCondition_id() {
    return condition_id;
  }

  public int getPrice() {
    return price;
  }

  public int getSale_status() {
    return sale_status;
  }

  public String getBrief_description() {
    return brief_description;
  }

  public String getFull_description() {
    return full_description;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getSize() {
    return size;
  }

  public List<MultipartFile> getImages() {
    return images;
  }
}