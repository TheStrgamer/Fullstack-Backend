package no.ntnu.idatt2105.marketplace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idatt2105.marketplace.dto.listing.ListingDTO;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/listings")
@Tag(name = "Listing API", description = "Operation related to Listings")
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }




    @GetMapping("/all")
    @Operation(
            summary = "Get all listings",
            description = "Returns a list of all the stored listings"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Returned all listings",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = ListingDTO.class))
            )
    )
    public ResponseEntity<List<ListingDTO>> getAllListings() {
        return ResponseEntity.ok(listingService.getAllListings());
    }




    @GetMapping("/id/{id}")
    @Operation(
            summary = "Get listing by id",
            description = "Returns a listing with the provided id"
    )
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "404",
                description = "Listing not found"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid id format"
        ),
        @ApiResponse(
                responseCode = "200",
                description = "Returned Listing with the given id"
        )
    })
    public ResponseEntity<?> getListingById(
            @Parameter(
                    name = "id",
                    description = "Integer value representing the listing id",
                    required = true,
                    example = "123"
            ) @PathVariable String id) {
        try {
            Listing listing = listingService.getListingById(Integer.parseInt(id));
            if (listing == null) {
                return new ResponseEntity<>("Listing not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(listing, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid ID format", HttpStatus.BAD_REQUEST);
        }
    }




    @GetMapping("/random")
    @Operation(
            summary = "Get n random listings",
            description = "Returns a list with a given amount 'n' of random listings"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of random listings",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = ListingDTO.class)
                    )
            )
    )
    public ResponseEntity<List<ListingDTO>> getRandomListings(
            @Parameter(
                    name = "count",
                    description = "Integer value representing the amount of listings that are to be returned",
                    example = "5"
            )
            @RequestParam(defaultValue = "10") int count) {
        return ResponseEntity.ok(listingService.getRandomListings(count));
    }

    @GetMapping("/recommended")
    @Operation(
            summary = "Get a list of n recommended listings",
            description = "Returns a list with the 'n' amount of recommended listings"
    )
    @ApiResponse(
         responseCode = "200",
         description = "List of recommended listings"
    )
    public ResponseEntity<List<ListingDTO>> getRecommendedListings(
            @Parameter(
                    name = "count",
                    description = "Integer value representing the amount of listings that are to be returned",
                    example = "5"
            ) @RequestParam(defaultValue = "10") int count,
            Principal principal) {
        return ResponseEntity.ok(listingService.getRecommendedListingsForUser(principal.getName(), count));
    }
}

