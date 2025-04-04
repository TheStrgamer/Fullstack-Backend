package no.ntnu.idatt2105.marketplace.dto.listing;

import java.util.Date;

public class ListingDTO {

    private int id;
    private String title;
    private String briefDescription;
    private String fullDescription;
    private int price;
    private int saleStatus;
    private String size;
    private double latitude;
    private double longitude;
    private String categoryName;
    private String conditionName;
    private String creatorFirstName;
    private String creatorSurname;
    private Date createdAt;
    private Date updatedAt;
    private String imagePath;

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
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getBriefDescription() { return briefDescription; }
    public String getFullDescription() { return fullDescription; }
    public int getPrice() { return price; }
    public int getSaleStatus() { return saleStatus; }
    public String getSize() { return size; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getCategoryName() { return categoryName; }
    public String getConditionName() { return conditionName; }
    public String getCreatorFirstName() { return creatorFirstName; }
    public String getCreatorSurname() { return creatorSurname; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public String getImagePath() { return imagePath; }
}
