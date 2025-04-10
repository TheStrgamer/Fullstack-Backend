package no.ntnu.idatt2105.marketplace.controller;

import no.ntnu.idatt2105.marketplace.dto.listing.ListingDTO;
import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import no.ntnu.idatt2105.marketplace.repo.CategoriesRepo;
import no.ntnu.idatt2105.marketplace.service.ListingService;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoriesRepo categoriesRepo;

    @Autowired
    private JWT_token jwtTokenService;

    @Autowired
    private ListingService listingService;


    @GetMapping
    @Operation(
            summary = "Get all category names",
            description = "Returns a list of all category names stored in the system"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of category names returned",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = String.class))
            )
    ) public ResponseEntity<List<String>> getAllCategoryNames() {
        List<String> categoryNames = categoriesRepo.findAll()
                .stream()
                .map(Categories::getName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryNames);
    }

    @GetMapping("/category/{categoryName}")
    @Operation(
            summary = "Get listings by category name",
            description = "Returns all listings that belong to a given category. If a user is logged in, the response will exclude listings created by that user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of listings in the specified category",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ListingDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid category name"
            )
    }) public ResponseEntity<List<ListingDTO>> getListingsByCategory(
            @Parameter(
                    name = "categoryName",
                    description = "Name of the category to fetch listings for",
                    required = true,
                    example = "Clothing"
            ) @PathVariable String categoryName,
            @Parameter(
                    name = "Authorization",
                    description = "Optional Bearer token in the format `Bearer <JWT>` to identify logged-in user",
                    required = false,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR..."
            )
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        Integer userId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            userId = Integer.parseInt(jwtTokenService.extractIdFromJwt(token));
        }

        List<ListingDTO> listings = listingService.getListingsByCategory(categoryName, userId);
        return ResponseEntity.ok(listings);
    }

}