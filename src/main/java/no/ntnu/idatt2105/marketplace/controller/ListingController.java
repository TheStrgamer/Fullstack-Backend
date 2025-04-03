package no.ntnu.idatt2105.marketplace.controller;

import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {
    @Autowired
    private ListingRepo listingRepo;

    @GetMapping("/all")
    public List<Listing> getAllListings() {
        return listingRepo.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getListingById(@PathVariable String id) {
        try {
            Listing listing = listingRepo.findById(Integer.valueOf(id)).orElse(null);
            if (listing == null) {
                return new ResponseEntity<>("Listing not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(listing, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid ID format", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/categories")
    public List<Listing> getListingCategories() {
        return listingRepo.findAll();
    }
}