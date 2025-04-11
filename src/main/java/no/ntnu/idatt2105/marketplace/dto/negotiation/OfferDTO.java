package no.ntnu.idatt2105.marketplace.dto.negotiation;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Schema(description = "Data transfer object representing an offer in a negotiation")
public class OfferDTO {

  @Schema(description = "Unique ID of the offer", example = "1")
  private int id;

  @Schema(description = "Unique ID of the buyer", example = "1")
  private int buyerId;

  @Schema(description = "Unique ID of the listing", example = "1")
  private int listingId;

  @Schema(description = "Current offer amount", example = "1000")
  private int currentOffer;

  @Schema(description = "Status of the offer", example = "0")
  private int status;

  @Schema(description = "Timestamp when the offer was lats updated", example = "2024-03-15T10:23:00Z")
  private String updatedAt;

  @Schema(description = "Id of the one who made the offer", example = "1")
  private int createdById;

  @Schema(description = "If the offer was made by the requesting user", example = "true")
  private boolean isCreatedByUser;

  @Schema(description = "Name of the user who created the offer", example = "Poe Bidau Gustang")
  private String creatorName;

  public OfferDTO(int id, int buyerId, int listingId, int currentOffer, int status, String updatedAt, int createdById) {
    this.id = id;
    this.buyerId = buyerId;
    this.listingId = listingId;
    this.currentOffer = currentOffer;
    this.status = status;
    this.updatedAt = updatedAt;
    this.createdById = createdById;
    this.isCreatedByUser = false;
    this.creatorName = "";
  }
  public OfferDTO(int id, int buyerId, int listingId, int currentOffer, int status, String updatedAt, int createdById, boolean isCreatedByUser, String creatorName) {
    this.id = id;
    this.buyerId = buyerId;
    this.listingId = listingId;
    this.currentOffer = currentOffer;
    this.status = status;
    this.updatedAt = updatedAt;
    this.createdById = createdById;
    this.isCreatedByUser = isCreatedByUser;
    this.creatorName = creatorName;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getBuyerId() {
    return buyerId;
  }
  public void setBuyerId(int buyerId) {
    this.buyerId = buyerId;
  }
  public int getListingId() {
    return listingId;
  }
  public void setListingId(int listingId) {
    this.listingId = listingId;
  }
  public int getCurrentOffer() {
    return currentOffer;
  }
  public void setCurrentOffer(int currentOffer) {
    this.currentOffer = currentOffer;
  }
  public int getStatus() {
    return status;
  }
  public void setStatus(int status) {
    this.status = status;
  }
  public String getUpdatedAt() {
    return updatedAt;
  }
  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }
  public int getCreatedById() {
    return createdById;
  }
  public void setCreatedById(int createdById) {
    this.createdById = createdById;
  }
  public boolean isCreatedByUser() {
    return isCreatedByUser;
  }
  public void setCreatedByUser(boolean createdByUser) {
    isCreatedByUser = createdByUser;
  }
  public String getCreatorName() {
    return creatorName;
  }
  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  public String toJsonString(boolean isSender, String senderName) {
    DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    return "{\"offerId\": " + getId() + ", \"offeredByMe\": " + isSender + ", \"senderName\": \"" + senderName + "\", \"currentOffer\": " + getCurrentOffer() + ", \"status\": " + getStatus() + ", \"updatedAt\": \"" + LocalDateTime.now().format(formatter) + "\"}";
  }

}
