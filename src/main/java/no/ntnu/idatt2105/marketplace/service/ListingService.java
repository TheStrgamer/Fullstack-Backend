package no.ntnu.idatt2105.marketplace.service;

import no.ntnu.idatt2105.marketplace.dto.listing.ListingDTO;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public List<ListingDTO> getRecommendedListingsForUser(User currentUser, int count) {
        Map<String, Integer> categoryScores = new HashMap<>();

        // 1. Poeng fra favoritter (vekt 2)
        for (Listing fav : currentUser.getFavorites()) {
            String cat = fav.getCategory().getName();
            categoryScores.put(cat, categoryScores.getOrDefault(cat, 0) + 2);
        }

        // 2. Poeng fra historikk (vekt 1)
        for (Listing viewed : currentUser.getHistory()) {
            String cat = viewed.getCategory().getName();
            categoryScores.put(cat, categoryScores.getOrDefault(cat, 0) + 1);
        }

        // 3. Fallback til tilfeldige hvis ingen data
        if (categoryScores.isEmpty()) {
            return getRandomListings(count);
        }

        // 4. Filtrer ut brukerens egne annonser
        List<Listing> allListings = listingRepo.findAll().stream()
                .filter(listing -> listing.getCreator().getId() != currentUser.getId())
                .toList();

        List<Listing> filtered = allListings.stream()
                .filter(listing -> listing.getCreator().getId() != currentUser.getId())
                .collect(Collectors.toList());

        // 🎲 Randomiser først for å sikre ulik rekkefølge ved lik score
        Collections.shuffle(filtered);

        // 5. Sortér etter kategoripoeng
        List<Listing> sorted = filtered.stream()
                .sorted((l1, l2) -> {
                    int score1 = categoryScores.getOrDefault(l1.getCategory().getName(), 0);
                    int score2 = categoryScores.getOrDefault(l2.getCategory().getName(), 0);
                    return Integer.compare(score2, score1); // descending
                })
                .limit(count)
                .toList();

        // 6. Bygg DTO-er og sett favoritt-status
        return sorted.stream()
                .map(listing -> {
                    ListingDTO dto = toDTO(listing);
                    dto.setFavorited(currentUser.getFavorites().contains(listing));
                    return dto;
                })
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

    public ListingDTO toDTO(Listing listing, User user) {
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
        if (user != null) {
            dto.setFavorited(user.getFavorites().contains(listing));
        }

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


    public ListingDTO toDTO(Listing listing) {
        return toDTO(listing, null);
    }

    public List<ListingDTO> getListingsByCategory(String category, Integer userId) {
        List<Listing> listings = listingRepo.findByCategory_Name(category);

        if (userId != null) {
            listings = listings.stream()
                    .filter(l -> l.getCreator().getId() != userId)
                    .toList();
        }

        return listings.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


}

