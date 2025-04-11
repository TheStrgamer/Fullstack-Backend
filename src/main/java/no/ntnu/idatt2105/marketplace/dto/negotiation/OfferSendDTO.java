package no.ntnu.idatt2105.marketplace.dto.negotiation;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Data transfer object representing an offer in a negotiation")
public class OfferSendDTO {

    @Schema(description = "Unique ID of the negotiation", example = "1")
    private int negotiationId;

    @Schema(description = "Unique ID of the listing", example = "1")
    private int listingId;

    @Schema(description = "Current offer amount", example = "1000")
    private int currentOffer;

    public OfferSendDTO(int listingId, int currentOffer, int negotiationId) {
        this.listingId = listingId;
        this.currentOffer = currentOffer;
        this.negotiationId = negotiationId;
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
    public int getNegotiationId() { return negotiationId; }
    public String getNegotiationIdString() { return String.valueOf(negotiationId); }

}