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
import no.ntnu.idatt2105.marketplace.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/negotiation/offer")
@Tag(name = "Offer API", description = "Operations for managing offers on listings")
public class OfferController {

  @Autowired
  private OfferService offerService;

  @Autowired
  private ChatSocketController chatSocketController;

  @PostMapping("/create")
  @Operation(
      summary = "Create a new offer",
      description = "Creates a new offer for a listing. Only authorized users can create offers."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Offer created successfully, returns the offer ID",
          content = @Content(schema = @Schema(implementation = int.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid request or unauthorized",
          content = @Content(schema = @Schema(implementation = String.class))
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized",
          content = @Content(schema = @Schema(implementation = String.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(implementation = String.class))
      )
  })
  public ResponseEntity<?> createOffer(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      ) @RequestHeader("Authorization") String authorizationHeader,
      @Parameter(description = "Offer details", required = true)
      @RequestBody OfferSendDTO offerSendDTO) {

    try {
      String token = authorizationHeader.substring(7);
      OfferDTO offer = offerService.createOffer(token, offerSendDTO.getListingId(), offerSendDTO.getCurrentOffer(), offerSendDTO.getNegotiationId());
      chatSocketController.sendOfferCreateMessage(offerSendDTO.getNegotiationIdString(), offer);
      return ResponseEntity.ok(offer.getId());
    } catch (IllegalArgumentException e) {
      System.out.println("Error creating offer: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      System.out.println("Error creating offer: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error creating offer: " + e.getMessage());
    }
  }

  @DeleteMapping("/remove/{id}")
  @Operation(
      summary = "Remove an offer",
      description = "Removes an offer. Only the user who created the offer can remove it."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Offer removed successfully",
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
  public ResponseEntity<String> removeOffer(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      ) @RequestHeader("Authorization") String authorizationHeader,
      @Parameter(description = "ID of the offer to remove", required = true)
      @PathVariable int id) {

    try {
      String token = authorizationHeader.substring(7);
      offerService.removeOffer(token, id);
      String chatId = String.valueOf(offerService.getChatIdFromOffer(id));
      chatSocketController.sendOfferUpdateMessage(chatId, String.valueOf(id), 3);
      return ResponseEntity.ok("Offer removed successfully");
    } catch (IllegalArgumentException e) {
      System.out.println("Error removing offer: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      System.out.println("Error removing offer: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing offer");
    }
  }

  @PutMapping("/accept/{id}")
  @Operation(
      summary = "Accept an offer",
      description = "Accepts an offer. Only the listing owner can accept offers."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Offer accepted successfully",
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
  public ResponseEntity<String> acceptOffer(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      ) @RequestHeader("Authorization") String authorizationHeader,
      @Parameter(description = "ID of the offer to accept", required = true)
      @PathVariable int id) {

    try {
      String token = authorizationHeader.substring(7);
      System.out.println("Offer ID: " + id);
      offerService.acceptOffer(token, id);
      String chatId = String.valueOf(offerService.getChatIdFromOffer(id));
      chatSocketController.sendOfferUpdateMessage(chatId, String.valueOf(id), 1);
      return ResponseEntity.ok("Offer accepted successfully");
    } catch (IllegalArgumentException e) {
      System.out.println("Error accepting offer: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      System.out.println("Error accepting offer: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accepting offer");
    }
  }

  @PutMapping("/reject/{id}")
  @Operation(
      summary = "Reject an offer",
      description = "Rejects an offer. Only the listing owner can reject offers."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Offer rejected successfully",
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
  public ResponseEntity<String> rejectOffer(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      ) @RequestHeader("Authorization") String authorizationHeader,
      @Parameter(description = "ID of the offer to reject", required = true)
      @PathVariable int id) {

    try {
      String token = authorizationHeader.substring(7);
      offerService.rejectOffer(token, id);
      String chatId = String.valueOf(offerService.getChatIdFromOffer(id));
      chatSocketController.sendOfferUpdateMessage(chatId, String.valueOf(id), 2);
      return ResponseEntity.ok("Offer rejected successfully");
    } catch (IllegalArgumentException e) {
      System.out.println("Error rejecting offer: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      System.out.println("Error rejecting offer: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error rejecting offer");
    }
  }

  @GetMapping("/getOffers/{chatId}")
  @Operation(
      summary = "Get all offers for a chat",
      description = "Retrieves all offers for a specific chat."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Offers retrieved successfully",
          content = @Content(schema = @Schema(implementation = OfferDTO[].class))
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
          description = "Chat not found"
      )
  })
  public ResponseEntity<List<OfferDTO>> getOffers(@Parameter(
      name = "Authorization",
      description = "Bearer token in the format `Bearer <JWT>`",
      required = true,
      example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
  ) @RequestHeader("Authorization") String authorizationHeader,
      @Parameter(description = "ID of the offer", required = true)

      @PathVariable String chatId ) {
    try {
      String token = authorizationHeader.substring(7);
      List<OfferDTO> offers = offerService.getOffersFromConversation(token, Integer.parseInt(chatId));
      System.out.println("Offers: " + offers);
      System.out.println("OfferDTOs: " + offers);
      return ResponseEntity.ok(offers);
    } catch (IllegalArgumentException e) {
      System.out.println("Error getting offers: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    } catch (Exception e) {
      System.out.println("Error getting offers: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

  }


}