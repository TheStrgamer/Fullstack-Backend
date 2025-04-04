package no.ntnu.idatt2105.marketplace.controller;

import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import no.ntnu.idatt2105.marketplace.model.listing.Condition;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.CategoriesRepo;
import no.ntnu.idatt2105.marketplace.repo.ConditionRepo;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.responseobjects.ListingResponseObject;
import no.ntnu.idatt2105.marketplace.security.JWTAuthorizationFilter;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {
    @Autowired
    private ListingRepo listingRepo;

    @Autowired
    private CategoriesRepo CategoriesRepo;

    @Autowired
    private ConditionRepo ConditionRepo;

    @Autowired
    private JWT_token jwtTokenService;

    private static final Logger LOGGER = LogManager.getLogger(ListingController.class);

    @GetMapping("/all")
    public List<ListingResponseObject> getAllListings() {
        List<Listing> listings = listingRepo.findAll();
        return ListingResponseObject.fromEntityList(listings);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getListingById(@PathVariable String id) {
        try {
            return listingRepo.findById(Integer.valueOf(id))
                    .map(listing -> ResponseEntity.ok(new ListingResponseObject(listing)))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ListingResponseObject()));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                    .body(new ListingResponseObject());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ListingResponseObject());
        }
    }

    @GetMapping("/categories")
    public List<Categories> getListingCategories() {
        return CategoriesRepo.findAll();
    }

    @GetMapping("/conditions")
    public List<Condition> getListingConditions() {
        return ConditionRepo.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createListing(@RequestBody Listing listing, @RequestHeader("Authorization") String authHeader) {
        try {
            LOGGER.info("Received listing data: {}", listing);

            if (listing.getTitle() == null || listing.getBrief_description() == null ||
                    listing.getFull_description() == null) {
                LOGGER.error("Missing required fields in listing data");
                return new ResponseEntity<>("Missing required fields", HttpStatus.BAD_REQUEST);
            }

            String token = authHeader.replace("Bearer ", "");
            LOGGER.info("Received token: {}", token);
            User user = jwtTokenService.getUserByToken(token);

            if (user == null) {
                return new ResponseEntity<>("Invalid token or user not found", HttpStatus.UNAUTHORIZED);
            }

            listing.setCreator(user);

            Categories category = CategoriesRepo.findById(listing.getCategory().getId()).orElse(null);
            Condition condition = ConditionRepo.findById(listing.getCondition().getId()).orElse(null);

            if (category == null || condition == null) {
                LOGGER.error("Category or condition not found");
                return new ResponseEntity<>("Category or condition not found", HttpStatus.BAD_REQUEST);
            }

            listing.setCategory(category);
            listing.setCondition(condition);
            listingRepo.save(listing);
            return new ResponseEntity<>(listing, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error creating listing: ", e);
            return new ResponseEntity<>("Error creating listing", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}