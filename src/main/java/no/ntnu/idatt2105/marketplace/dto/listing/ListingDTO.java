package no.ntnu.idatt2105.marketplace.dto.listing;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.List;

@Schema(description = "Data transfer object representing a marketplace listing")
public class ListingDTO {

    @Schema(description = "Unique ID of the listing", example = "101")
    private int id;

    @Schema(description = "Title of the listing", example = "Vintage bicycle for sale")
    private String title;

    @Schema(description = "Short summary of the listing", example = "Lightly used vintage bike")
    private String briefDescription;

    @Schema(description = "Full detailed description of the listing", example = "This vintage bicycle is in great condition...")
    private String fullDescription;

    @Schema(description = "Price of the listing in NOK", example = "1500")
    private int price;

    @Schema(description = "Sale status (e.g., 0 = available, 1 = sold)", example = "0")
    private int saleStatus;

    @Schema(description = "Size or dimensions of the item (if applicable)", example = "Medium")
    private String size;

    @Schema(description = "Latitude of the item location", example = "63.4305")
    private double latitude;

    @Schema(description = "Longitude of the item location", example = "10.3951")
    private double longitude;

    @Schema(description = "Name of the item's category", example = "Bicycles")
    private String categoryName;

    @Schema(description = "Condition label of the item", example = "Used - Good")
    private String conditionName;

    @Schema(description = "First name of the listing creator", example = "Alice")
    private String creatorFirstName;

    @Schema(description = "Surname of the listing creator", example = "Johansen")
    private String creatorSurname;

    @Schema(description = "Timestamp when the listing was created", example = "2024-03-15T10:23:00Z")
    private Date createdAt;

    @Schema(description = "Timestamp when the listing was last updated", example = "2024-03-17T14:10:00Z")
    private Date updatedAt;

    @Schema(description = "Relative or absolute path to the listing's main image", example = "/images/listings/101.jpg")
    private String imagePath;

    @Schema(description = "List of image URLs for the listing")
    private List<String> imageUrls;


    // Constructor
    public ListingDTO(int id, String title, String briefDescription, String fullDescription, int price, int saleStatus,
                      String size, double latitude, double longitude, String categoryName, String conditionName,
                      String creatorFirstName, String creatorSurname, Date createdAt, Date updatedAt, String imagePath) {
        this.id = id;
        this.title = title;
        this.briefDescription = briefDescription;
        this.fullDescription = fullDescription;
        this.price = price;
        this.saleStatus = saleStatus;
        this.size = size;
        this.latitude = latitude;
        this.longitude = longitude;
        this.categoryName = categoryName;
        this.conditionName = conditionName;
        this.creatorFirstName = creatorFirstName;
        this.creatorSurname = creatorSurname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imagePath = imagePath;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getBriefDescription() {
        return briefDescription;
    }
    public String getFullDescription() {
        return fullDescription;
    }
    public int getPrice() {
        return price;
    }
    public int getSaleStatus() {
        return saleStatus;
    }
    public String getSize() {
        return size;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public String getConditionName() {
        return conditionName;
    }
    public String getCreatorFirstName() {
        return creatorFirstName;
    }
    public String getCreatorSurname() {
        return creatorSurname;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public String getImagePath() {
        return imagePath;
    }
    public List<String> getImageUrls() {
        return imageUrls;
    }

    // Setters
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
