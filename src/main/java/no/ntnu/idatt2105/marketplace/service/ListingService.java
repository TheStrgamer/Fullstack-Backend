package no.ntnu.idatt2105.marketplace.service;

import no.ntnu.idatt2105.marketplace.dto.listing.ListingDTO;
import no.ntnu.idatt2105.marketplace.dto.listing.ListingUpdate;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.CategoriesRepo;
import no.ntnu.idatt2105.marketplace.repo.ConditionRepo;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.images.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListingService {

    @Autowired
    private ListingRepo listingRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoriesRepo categoriesRepo;

    @Autowired
    private ConditionRepo conditionRepo;

    @Autowired
    private ImagesService imagesService;

//    public ListingService(ListingRepo listingRepo, UserRepo userRepo) {
//        this.listingRepo = listingRepo;
//        this.userRepo = userRepo;
//    }

    public List<ListingDTO> getRandomListings(int count) {
        List<Listing> all = listingRepo.findAll();
        Collections.shuffle(all);
        return all.stream()
                .limit(count)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ListingDTO> getRecommendedListingsForUser(String email, int count) {
        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isEmpty()) return List.of(); // fallback

        // For nå: anbefal tilfeldig (senere: basert på historikk, kategorier, osv.)
        List<Listing> all = listingRepo.findAll();
        Collections.shuffle(all);
        return all.stream()
                .limit(count)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ListingDTO> getAllListings() {
        return listingRepo.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ListingDTO> getAllListingsForUser(int user_id) throws Exception {
        Optional<User> creator = userRepo.findById(user_id);
        if (creator.isEmpty()) {
            throw new Exception("No user found");
        }
        return listingRepo.findAllByCreator(creator.get()).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Listing getListingById(int id) {
        return listingRepo.findById(id).orElse(null);
    }

    public ListingDTO toDTO(Listing listing) {
        String imagePath = listing.getImages().isEmpty()
                ? null
                : listing.getImages().get(0).getFilepath_to_image();

        List<String> imageUrls = listing.getImages().stream()
                .map(img -> "http://localhost:8080" + img.getFilepath_to_image())
                .toList();

        ListingDTO dto = new ListingDTO(
                listing.getId(),
                listing.getTitle(),
                listing.getBrief_description(),
                listing.getFull_description(),
                listing.getPrice(),
                listing.getSale_status(),
                listing.getSize(),
                listing.getLatitude(),
                listing.getLongitude(),
                listing.getCategory().getName(),
                listing.getCondition().getName(),
                listing.getCreator().getId(),
                listing.getCreated_at(),
                listing.getUpdated_at(),
                imagePath
        );
        dto.setImageUrls(imageUrls);
        return dto;
    }


    public boolean isListingValid(int listing_id) {
        return listingRepo.findById(listing_id).isPresent();
    }

    public boolean userHasPermission(int listing_id, int user_id) {
        // if user role = ADMIN or user is creator return true
        return userRepo.findById(user_id).get().getRole().getName().equals("ADMIN") || listingRepo.findById(listing_id).get().getCreator().getId() == user_id;
    }


    public void updateListing(int listing_id, ListingUpdate updatedListingData) throws Exception {
        Listing listing = listingRepo.findById(listing_id).get();

        listing.setTitle(updatedListingData.getTitle());
        listing.setCategory(categoriesRepo.findById(updatedListingData.getCategory_id()).get());
        listing.setCondition(conditionRepo.findById(updatedListingData.getCondition_id()).get());
        listing.setSale_status(updatedListingData.getSale_status());
        listing.setPrice(updatedListingData.getPrice());
        listing.setBrief_description(updatedListingData.getBrief_description());
        listing.setFull_description(updatedListingData.getFull_description());
        listing.setSize(updatedListingData.getSize());
        listing.setLatitude(updatedListingData.getLatitude());
        listing.setLongitude(updatedListingData.getLongitude());
        listing.setImages(imagesService.saveListingImages(updatedListingData.getImages()));

        listingRepo.save(listing);
    }

}

