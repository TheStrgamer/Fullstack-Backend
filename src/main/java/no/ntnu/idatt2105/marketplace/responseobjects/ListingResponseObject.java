package no.ntnu.idatt2105.marketplace.responseobjects;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.other.Images;

public class ListingResponseObject {
    private int id;

    // Creator details (minimal)
    private int creatorId;
    private String creatorName;
    private String creatorEmail;

    // Category details (minimal)
    private int categoryId;
    private String categoryName;

    // Condition details (minimal)
    private int conditionId;
    private String conditionName;

    // Main listing properties
    private String title;
    private int saleStatus;
    private int price;
    private String briefDescription;
    private String fullDescription;
    private String size;
    private Date createdAt;
    private Date updatedAt;
    private double latitude;
    private double longitude;

    // Image URLs instead of Image objects
    private List<String> imageUrls;

    // Default constructor
    public ListingResponseObject() {}

    // Constructor to convert from Entity to DTO
    public ListingResponseObject(Listing listing) {
        this.id = listing.getId();

        // Set creator minimal info
        if (listing.getCreator() != null) {
            this.creatorId = listing.getCreator().getId();
            this.creatorName = listing.getCreator().getFirstname() + " " + listing.getCreator().getSurname();
            this.creatorEmail = listing.getCreator().getEmail();
        }

        // Set category minimal info
        if (listing.getCategory() != null) {
            this.categoryId = listing.getCategory().getId();
            this.categoryName = listing.getCategory().getName();
        }

        // Set condition minimal info
        if (listing.getCondition() != null) {
            this.conditionId = listing.getCondition().getId();
            this.conditionName = listing.getCondition().getName();
        }

        // Set basic properties
        this.title = listing.getTitle();
        this.saleStatus = listing.getSale_status();
        this.price = listing.getPrice();
        this.briefDescription = listing.getBrief_description();
        this.fullDescription = listing.getFull_description();
        this.size = listing.getSize();
        this.createdAt = listing.getCreated_at();
        this.updatedAt = listing.getUpdated_at();
        this.latitude = listing.getLatitude();
        this.longitude = listing.getLongitude();
    }

    // Static method to convert a list of entities to DTOs
    public static List<ListingResponseObject> fromEntityList(List<Listing> listings) {
        return listings.stream()
                .map(ListingResponseObject::new)
                .collect(Collectors.toList());
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(int saleStatus) {
        this.saleStatus = saleStatus;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}