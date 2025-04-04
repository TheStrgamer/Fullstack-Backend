package no.ntnu.idatt2105.marketplace.controller;

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
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ListingDTO>> getAllListings() {
        return ResponseEntity.ok(listingService.getAllListings());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getListingById(@PathVariable String id) {
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
    public ResponseEntity<List<ListingDTO>> getRandomListings(@RequestParam(defaultValue = "10") int count) {
        return ResponseEntity.ok(listingService.getRandomListings(count));
    }

    @GetMapping("/recommended")
    public ResponseEntity<List<ListingDTO>> getRecommendedListings(
            @RequestParam(defaultValue = "10") int count,
            Principal principal) {
        return ResponseEntity.ok(listingService.getRecommendedListingsForUser(principal.getName(), count));
    }
}

