package no.ntnu.idatt2105.marketplace.dto.listing;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

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

    @Schema(description = "Latitude of the item location", example = "63.4305")
    private double latitude;

    @Schema(description = "Longitude of the item location", example = "10.3951")
    private double longitude;

    @Schema(description = "Id of the item's category", example = "123")
    private int category;

    @Schema(description = "Id of the item' category", example = "123")
    private int condition;

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


    // Constructor
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

    // Getters
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
    public int getCategory() {
        return category;
    }
    public int getCondition() {
        return condition;
    }
    public int getCreatorId() {
        return creatorId;
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

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

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

    public void setSize(String size) {
        this.size = size;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setSaleStatus(int saleStatus) {
        this.saleStatus = saleStatus;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }



    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public String toString() {
        return "ListingCreate{" +
                ",\n  title='" + title + '\'' +
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
