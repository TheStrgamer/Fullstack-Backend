package no.ntnu.idatt2105.marketplace.controller;

import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Listing getListingById(@PathVariable String id) {
        return listingRepo.findById(Integer.valueOf(id)).orElse(null);
    }
}
