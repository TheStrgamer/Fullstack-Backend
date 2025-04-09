package no.ntnu.idatt2105.marketplace.controller;

import no.ntnu.idatt2105.marketplace.dto.listing.*;
import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import no.ntnu.idatt2105.marketplace.model.listing.Condition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.CategoriesRepo;
import no.ntnu.idatt2105.marketplace.repo.ConditionRepo;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import no.ntnu.idatt2105.marketplace.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/listings")
@Tag(name = "Listing API", description = "Operation related to Listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @Autowired
    private ListingRepo listingRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoriesRepo CategoriesRepo;

    @Autowired
    private ConditionRepo ConditionRepo;

    @Autowired
    private JWT_token jwtTokenService;


    private static final Logger LOGGER = LogManager.getLogger(ListingController.class);

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
    public List<ListingDTO> getAllListings() {
        return listingService.getAllListings();
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
            return listingRepo.findById(Integer.valueOf(id))
                    .map(listing -> ResponseEntity.ok(listingService.toDTO(listing)))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/categories")
    @Operation(
            summary = "Get all categories",
            description = "Returns a list of DTOs containing information about a category"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully returned a list of all categories"
    )
    public ResponseEntity<?> getListingCategories() {
        return ResponseEntity.ok(listingService.getAllCategories());
    }

    @GetMapping("/conditions")
    @Operation(
            summary = "Get all conditions",
            description = "Returns a list of DTOs containing information about a condition"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully returned a list of all conditions"
    )
    public ResponseEntity<?> getListingConditions() {
        return ResponseEntity.ok(listingService.getAllConditions());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createListing(@RequestBody ListingCreate listing, @RequestHeader("Authorization") String authHeader) {
        try {
            LOGGER.info("Received listing data: {}", listing);

            if (listing.getTitle() == null || listing.getBriefDescription() == null ||
                    listing.getFullDescription() == null) {
                LOGGER.error("Missing required fields in listing data");
                return new ResponseEntity<>("Missing required fields", HttpStatus.BAD_REQUEST);
            }

            String token = authHeader.replace("Bearer ", "");
            LOGGER.info("Received token: {}", token);
            User user = jwtTokenService.getUserByToken(token);

            if (user == null) {
                return new ResponseEntity<>("Invalid token or user not found", HttpStatus.UNAUTHORIZED);
            }

            Categories category = CategoriesRepo.findById(listing.getCategory()).orElse(null);
            Condition condition = ConditionRepo.findById(listing.getCondition()).orElse(null);

            if (category == null || condition == null) {
                LOGGER.error("Category or condition not found");
                return new ResponseEntity<>("Category or condition not found", HttpStatus.BAD_REQUEST);
            }

            Optional<User> creator = userRepo.findById(user.getId());
            if (creator.isEmpty()) throw new Exception("No user found");

            Listing newListing = new Listing(
                    0,
                    creator.get(),
                    category,
                    condition,
                    listing.getTitle(),
                    listing.getSaleStatus(),
                    listing.getPrice(),
                    listing.getBriefDescription(),
                    listing.getFullDescription(),
                    listing.getSize(),
                    listing.getCreatedAt(),
                    listing.getUpdatedAt(),
                    listing.getLatitude(),
                    listing.getLongitude()
            );
            LOGGER.info("Saved listing");
            Listing createdListing = listingRepo.save(newListing);
            return new ResponseEntity<>(listingService.toDTO(createdListing), HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error creating listing: ", e);
            return new ResponseEntity<>("Error creating listing", HttpStatus.INTERNAL_SERVER_ERROR);
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




    @PutMapping("/update/{id}")
    @Operation(
            summary = "Update listing",
            description = "Updates a listing with the given id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updated listing"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid Authorization header"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error updating listings"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User does not have permission tpo update the requested listing"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Could not find requested listing"
            ),
    })
    public ResponseEntity<?> updateListing(
            @Parameter(
                    name = "Authorization",
                    description = "Bearer token in the format `Bearer <JWT>`",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
            ) @RequestHeader("Authorization") String authorizationHeader,
            @Parameter(
                    name = "id",
                    description = "The id of the listing that is being updated",
                    example = "111"
            ) @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated listing data",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ListingUpdate.class)
                    )
            ) @RequestBody ListingUpdate updatedListingData) {
        try {
//            LOGGER.info(updatedListingData.toString());
            // validate token
            if (!authorizationHeader.startsWith("Bearer ")) {
                LOGGER.info("Invalid Authorization header");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            LOGGER.info("Token is valid");

            // validate listing
            if (!listingService.isListingValid(id)) {
                LOGGER.info("There exists not listing with the id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            LOGGER.info("Listing is valid");

            // validate user permission
            String token = authorizationHeader.substring(7);
            int user_id = Integer.parseInt(jwtTokenService.extractIdFromJwt(token));

            if (!listingService.userHasPermission(id, user_id)) {
                LOGGER.info("User with id" + user_id
                                + " has no permission to update listing with id: " + id);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            LOGGER.info("User is valid \n Updating listing");


            listingService.updateListing(id, updatedListingData);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/getMyListings")
    @Operation(
            summary = "Get all listings by user id",
            description = "Returns a list of listingDTOs containing information about listings created by a user with the given user_id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully returned a list of listings where the creator id equals the given user id"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid Authorization header"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error returning list of listings"
            ),
    })
    public ResponseEntity<List<ListingDTO>> getListingsForUser(
            @Parameter(
                    name = "Authorization",
                    description = "Bearer token in the format `Bearer <JWT>`",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
            ) @RequestHeader("Authorization") String authorizationHeader) {
        try {
            // validate token
            if (!authorizationHeader.startsWith("Bearer ")) {
                LOGGER.info("Invalid Authorization header");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String token = authorizationHeader.substring(7);
            int user_id = Integer.parseInt(jwtTokenService.extractIdFromJwt(token));

            List<ListingDTO> response = listingService.getAllListingsForUser(user_id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @DeleteMapping("/{id}/delete")
    @Operation(
            summary = "Delete a listing",
            description = "Delete a listing with the provided Id, if requester has permission"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid Token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "The user has insufficient permission"
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully deleted the listing"
            )
    })
    public ResponseEntity<?> delete(
            @Parameter(
                    name = "id",
                    description = "The id of the listing that is being updated",
                    example = "111"
            ) @PathVariable int id,
            @Parameter(
            name = "Authorization",
            description = "Bearer token in the format `Bearer <JWT>`",
            required = true,
            example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
    ) @RequestHeader("Authorization") String authorizationHeader) {
        // validate token
        if (!authorizationHeader.startsWith("Bearer ")) {
            LOGGER.info("Invalid Authorization header");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(7);
        int user_id = Integer.parseInt(jwtTokenService.extractIdFromJwt(token));

        if (!listingService.userHasPermission(id, user_id)) {
            System.out.println("Request denied to delete listing, due to insufficient permission");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        listingService.delete(id);
        System.out.println("Successfully deleted listing with id: " + id);
        return ResponseEntity.ok().build();
    }
}