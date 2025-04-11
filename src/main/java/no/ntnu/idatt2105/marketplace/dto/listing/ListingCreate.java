package no.ntnu.idatt2105.marketplace.dto.listing;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;

import java.util.Date;
import java.util.List;

/**
 * Represents the data required to create a new listing on the marketplace platform.
 * This DTO is typically used during listing submission through a REST API endpoint.
 * It captures metadata such as title, description, location, pricing, categorization, and timestamps.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 * @see Listing
 */
@Schema(description = "Data transfer object representing a marketplace listing")
public class ListingCreate {

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

    @Schema(description = "Latitude of the item's physical location", example = "63.4305")
    private double latitude;

    @Schema(description = "Longitude of the item's physical location", example = "10.3951")
    private double longitude;

    @Schema(description = "ID of the item's category", example = "123")
    private int category;

    @Schema(description = "ID representing the item's condition (e.g., New, Used)", example = "2")
    private int condition;

    @Schema(description = "User ID of the listing creator", example = "221")
    private int creatorId;

    @Schema(description = "Timestamp when the listing was created", example = "2024-03-15T10:23:00Z")
    private Date createdAt;

    @Schema(description = "Timestamp when the listing was last updated", example = "2024-03-17T14:10:00Z")
    private Date updatedAt;

    @Schema(description = "Main image path of the listing, typically relative to static resources", example = "/images/listings/101.jpg")
    private String imagePath;

    @Schema(description = "List of additional image URLs for the listing")
    private List<String> imageUrls;

    /**
     * Constructs a new ListingCreate object using full parameters.
     *
     * @param title           Title of the listing
     * @param briefDescription A short description or summary
     * @param fullDescription A detailed description of the item
     * @param price           Price of the item in NOK
     * @param saleStatus      Current sale status (e.g., 0 = available)
     * @param size            Size or dimensions of the item
     * @param latitude        Geographic latitude of item location
     * @param longitude       Geographic longitude of item location
     * @param category        ID of the category this item belongs to
     * @param condition       ID representing the item's condition
     * @param creatorId       ID of the user creating the listing
     * @param createdAt       Timestamp when the listing was created
     * @param updatedAt       Timestamp when the listing was last updated
     * @param imagePath       Main image path (used as cover image)
     */
    public ListingCreate(String title, String briefDescription, String fullDescription, int price, int saleStatus,
                         String size, double latitude, double longitude, int category, int condition,
                         int creatorId, Date createdAt, Date updatedAt, String imagePath) {
        this.title = title;
        this.briefDescription = briefDescription;
        this.fullDescription = fullDescription;
        this.price = price;
        this.saleStatus = saleStatus;
        this.size = size;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.condition = condition;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imagePath = imagePath;
    }

    // ---------------- GETTERS ----------------

    /** @return Title of the listing */
    public String getTitle() {
        return title;
    }

    /** @return Short summary of the listing */
    public String getBriefDescription() {
        return briefDescription;
    }

    /** @return Detailed description of the listing */
    public String getFullDescription() {
        return fullDescription;
    }

    /** @return Price in NOK */
    public int getPrice() {
        return price;
    }

    /** @return Sale status (0 = available, 1 = sold, etc.) */
    public int getSaleStatus() {
        return saleStatus;
    }

    /** @return Size or dimensions of the item */
    public String getSize() {
        return size;
    }

    /** @return Latitude of the item’s location */
    public double getLatitude() {
        return latitude;
    }

    /** @return Longitude of the item’s location */
    public double getLongitude() {
        return longitude;
    }

    /** @return Category ID the item belongs to */
    public int getCategory() {
        return category;
    }

    /** @return Condition ID describing the item’s condition */
    public int getCondition() {
        return condition;
    }

    /** @return ID of the listing creator */
    public int getCreatorId() {
        return creatorId;
    }

    /** @return Creation timestamp */
    public Date getCreatedAt() {
        return createdAt;
    }

    /** @return Last updated timestamp */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /** @return Path to the main image for this listing */
    public String getImagePath() {
        return imagePath;
    }

    /** @return List of additional image URLs */
    public List<String> getImageUrls() {
        return imageUrls;
    }

    // ---------------- SETTERS ----------------

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSaleStatus(int saleStatus) {
        this.saleStatus = saleStatus;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public String toString() {
        return "ListingCreate{" +
                "\n  title='" + title + '\'' +
                ",\n  briefDescription='" + briefDescription + '\'' +
                ",\n  fullDescription='" + fullDescription + '\'' +
                ",\n  price=" + price +
                ",\n  saleStatus=" + saleStatus +
                ",\n  size='" + size + '\'' +
                ",\n  latitude=" + latitude +
                ",\n  longitude=" + longitude +
                ",\n  categoryId=" + category +
                ",\n  conditionId=" + condition +
                ",\n  creatorId=" + creatorId +
                ",\n  createdAt=" + createdAt +
                ",\n  updatedAt=" + updatedAt +
                ",\n  imagePath='" + imagePath + '\'' +
                ",\n  imageUrls=" + (imageUrls != null ? imageUrls.toString() : "null") +
                "\n}";
    }
}