package no.ntnu.idatt2105.marketplace.service;

import no.ntnu.idatt2105.marketplace.dto.listing.ListingDTO;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListingService {

    private final ListingRepo listingRepo;
    private final UserRepo userRepo;

    public ListingService(ListingRepo listingRepo, UserRepo userRepo) {
        this.listingRepo = listingRepo;
        this.userRepo = userRepo;
    }

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

    public Listing getListingById(int id) {
        return listingRepo.findById(id).orElse(null);
    }

    public ListingDTO toDTO(Listing listing) {
        String imagePath = listing.getImages().isEmpty()
                ? null
                : listing.getImages().get(0).getFilepath_to_image();

        List<String> imageUrls = listing.getImages().stream()
                .map(img -> "http://localhost:8080/" + img.getFilepath_to_image())
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
    @Transactional
    public void deleteListing(int listingId) {
        Listing listing = listingRepo.findById(listingId)
            .orElseThrow(() -> new RuntimeException("Listing not found"));

        listing.removeUser_favorites();
        listing.removeUser_history();

        listing.removeOffers();
        listing.getConversations().clear();
        listing.getImages().clear();

        listingRepo.delete(listing);
    }



}

