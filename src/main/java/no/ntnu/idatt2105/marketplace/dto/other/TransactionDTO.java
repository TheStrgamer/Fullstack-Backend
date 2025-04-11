package no.ntnu.idatt2105.marketplace.dto.other;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data transfer object representing a transaction")
public class TransactionDTO {
  @Schema(description = "The name of the listing", example = "Laptop for sale")
  private String listingName;
  @Schema(description = "The name of the other user", example = "1")
  private String otherUserName;
  @Schema(description = "The price of the listing", example = "1000")
  private int price;
  @Schema(description = "The status of the transaction", example = "COMPLETED")
  private String status;
  @Schema(description = "The date of the transaction", example = "2023-10-01")
  private String date;

  public TransactionDTO(String listingName, String otherUserName, int price, String status,
      String date) {
    this.listingName = listingName;
    this.otherUserName = otherUserName;
    this.price = price;
    this.status = status;
    this.date = date;
  }
  public String getListingName() {
    return listingName;
  }
  public void setListingName(String listingName) {
    this.listingName = listingName;
  }
  public String getOtherUserName() {
    return otherUserName;
  }
  public void setOtherUserName(String otherUserName) {
    this.otherUserName = otherUserName;
  }
  public int getPrice() {
    return price;
  }
  public void setPrice(int price) {
    this.price = price;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getDate() {
    return date;
  }
  public void setDate(String date) {
    this.date = date;
  }



}
