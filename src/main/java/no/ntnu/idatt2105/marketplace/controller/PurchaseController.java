package no.ntnu.idatt2105.marketplace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import no.ntnu.idatt2105.marketplace.dto.negotiation.OfferDTO;
import no.ntnu.idatt2105.marketplace.dto.negotiation.OfferSendDTO;
import no.ntnu.idatt2105.marketplace.dto.other.TransactionDTO;
import no.ntnu.idatt2105.marketplace.model.other.Transaction;
import no.ntnu.idatt2105.marketplace.repo.TransactionRepo;
import no.ntnu.idatt2105.marketplace.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
@Tag(name = "Purchase API", description = "Operations for buying a listing")
public class PurchaseController {

  @Autowired
  private OfferService offerService;

  @Autowired
  private TransactionRepo transactionRepo;



  @PutMapping("/fromOffer/{offerId}")
  @Operation(
      summary = "Purchase listing through an offer",
      description = "Purchases a listing through an offer. Only accepted offers can be used to purchase a listing."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Offer purchased successfully",
          content = @Content(schema = @Schema(implementation = String.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid request or unauthorized"
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Offer not found"
      )
  })
  public ResponseEntity<String> purchaseListingWithOffer(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      ) @RequestHeader("Authorization") String authorizationHeader,
      @Parameter(description = "ID of the offer to accept", required = true)
      @PathVariable int offerId) {
    try {
      String token = authorizationHeader.substring(7);
      offerService.purchaseListingWithOffer(token, offerId);
      return ResponseEntity.ok("Listing purchased successfully");
    } catch (IllegalArgumentException e) {
      System.out.println("Error purchasing listing: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      System.out.println("Error purchasing: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error purchasing listing");
    }
  }
  @PutMapping("/fromListing/{listingId}")
  @Operation(
      summary = "Purchase listing without an offer",
      description = "Purchases a listing without an offer."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Offer purchased successfully",
          content = @Content(schema = @Schema(implementation = String.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid request or unauthorized"
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Offer not found"
      )
  })
  public ResponseEntity<String> purchaseListing(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      ) @RequestHeader("Authorization") String authorizationHeader,
      @Parameter(description = "ID of the offer to accept", required = true)
      @PathVariable int listingId) {
    try {
      String token = authorizationHeader.substring(7);
      offerService.purchaseListing(token, listingId);
      return ResponseEntity.ok("Listing purchased successfully");
    } catch (IllegalArgumentException e) {
      System.out.println("Error purchasing listing: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      System.out.println("Error purchasing: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error purchasing listing");
    }
  }




}