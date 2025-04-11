package no.ntnu.idatt2105.marketplace.dto.listing;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object representing a marketplace listing, used for API responses.
 */
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

    @Schema(description = "Id of the listing creator", example = "221")
    private int creatorId;

    @Schema(description = "Timestamp when the listing was created", example = "2024-03-15T10:23:00Z")
    private Date createdAt;

    @Schema(description = "Timestamp when the listing was last updated", example = "2024-03-17T14:10:00Z")
    private Date updatedAt;

    @Schema(description = "Relative or absolute path to the listing's main image", example = "/images/listings/101.jpg")
    private String imagePath;

    @Schema(description = "List of image URLs for the listing")
    private List<String> imageUrls;

    @Schema(description = "Boolean value to determine if a listing is favorited by the user or not", example = "true")
    @JsonProperty("isFavorited")
    private boolean isFavorited;

    /**
     * Constructor for creating a ListingDTO.
     *
     * @param id             ID of the listing
     * @param title          Title of the listing
     * @param briefDescription Short summary
     * @param fullDescription Detailed description
     * @param price          Price in NOK
     * @param saleStatus     Status (0 = available, 1 = sold)
     * @param size           Size or dimensions
     * @param latitude       Latitude of location
     * @param longitude      Longitude of location
     * @param categoryName   Name of the category
     * @param conditionName  Condition label
     * @param creatorId      ID of the creator
     * @param createdAt      Date created
     * @param updatedAt      Date last updated
     * @param imagePath      Path to the main image
     */
    public ListingDTO(int id, String title, String briefDescription, String fullDescription, int price, int saleStatus,
                      String size, double latitude, double longitude, String categoryName, String conditionName,
                      int creatorId, Date createdAt, Date updatedAt, String imagePath) {
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
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imagePath = imagePath;
    }

    // Getters

    /** @return Listing ID */
    public int getId() {
        return id;
    }

    /** @return Listing title */
    public String getTitle() {
        return title;
    }

    /** @return Brief description */
    public String getBriefDescription() {
        return briefDescription;
    }

    /** @return Full description */
    public String getFullDescription() {
        return fullDescription;
    }

    /** @return Listing price */
    public int getPrice() {
        return price;
    }

    /** @return Sale status (0 or 1) */
    public int getSaleStatus() {
        return saleStatus;
    }

    /** @return Item size or dimension */
    public String getSize() {
        return size;
    }

    /** @return Latitude of location */
    public double getLatitude() {
        return latitude;
    }

    /** @return Longitude of location */
    public double getLongitude() {
        return longitude;
    }

    /** @return Category name */
    public String getCategoryName() {
        return categoryName;
    }

    /** @return Condition name */
    public String getConditionName() {
        return conditionName;
    }

    /** @return Creator user ID */
    public int getCreatorId() {
        return creatorId;
    }

    /** @return Created timestamp */
    public Date getCreatedAt() {
        return createdAt;
    }

    /** @return Updated timestamp */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /** @return Path to the main image */
    public String getImagePath() {
        return imagePath;
    }

    /** @return List of additional image URLs */
    public List<String> getImageUrls() {
        return imageUrls;
    }

    /** @return Whether the listing is favorited */
    public boolean isFavorited() {
        return isFavorited;
    }

    // Setters

    /**
     * Sets image URLs associated with the listing.
     *
     * @param imageUrls List of image URLs
     */
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    /**
     * Sets whether the listing is favorited by the user.
     *
     * @param isFavorited true if favorited, false otherwise
     */
    public void setFavorited(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }
}