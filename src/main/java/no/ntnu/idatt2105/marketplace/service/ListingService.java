package no.ntnu.idatt2105.marketplace.service;

import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListingService {

    private final ListingRepo listingRepo;
    private final UserRepo userRepo;

    public ListingService(ListingRepo listingRepo, UserRepo userRepo) {
        this.listingRepo = listingRepo;
        this.userRepo = userRepo;
    }

    public List<Listing> getRandomListings(int count) {
        List<Listing> all = listingRepo.findAll();
        Collections.shuffle(all);
        return all.stream().limit(count).collect(Collectors.toList());
    }

    public List<Listing> getRecommendedListingsForUser(String email, int count) {
        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isEmpty()) return List.of();

        // TODO: Mer avansert anbefalingslogikk
        List<Listing> all = listingRepo.findAll();
        Collections.shuffle(all);
        return all.stream().limit(count).collect(Collectors.toList());
    }

    public List<Listing> getAllListings() {
        return listingRepo.findAll();
    }

    public Listing getListingById(int id) {
        return listingRepo.findById(id).orElse(null);
    }

}

