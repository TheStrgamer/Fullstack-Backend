package no.ntnu.idatt2105.marketplace.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2105.marketplace.model.listing.*;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.user.Role;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.*;
import no.ntnu.idatt2105.marketplace.service.security.BCryptHasher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoleRepo roleRepo;
    private final CategoriesRepo categoriesRepo;
    private final ConditionRepo conditionRepo;
    private final UserRepo userRepo;
    private final ListingRepo listingRepo;
    private final ImagesRepo imagesRepo;
    private final BCryptHasher hasher;

    public DataSeeder(RoleRepo roleRepo, CategoriesRepo categoriesRepo,
                      ConditionRepo conditionRepo, UserRepo userRepo,
                      ListingRepo listingRepo, ImagesRepo imagesRepo,
                      BCryptHasher hasher) {
        this.roleRepo = roleRepo;
        this.categoriesRepo = categoriesRepo;
        this.conditionRepo = conditionRepo;
        this.userRepo = userRepo;
        this.listingRepo = listingRepo;
        this.imagesRepo = imagesRepo;
        this.hasher = hasher;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        seedRoles();
        seedConditions();
        seedCategories();
        seedImages();
        seedUserWithListing();
        seedAdditionalUserAndListings();
        seedMoreCategoriesAndUsersWithListings();
        assignAlbertFavoritesAndHistory();
    }

    private void seedRoles() {
        if (roleRepo.findByName("USER").isEmpty()) {
            roleRepo.save(new Role(0, "USER"));
        }
    }

    private void seedConditions() {
        if (conditionRepo.findByName("New").isEmpty()) {
            conditionRepo.save(new Condition(0, "New"));
        }
        if (conditionRepo.findByName("Used").isEmpty()) {
            conditionRepo.save(new Condition(0, "Used"));
        }
    }

    private void seedCategories() {
        List<String> categories = List.of("Electronics", "Clothing", "Books", "Sports", "Toys", "Furniture");
        for (String cat : categories) {
            if (categoriesRepo.findByName(cat).isEmpty()) {
                categoriesRepo.save(new Categories(0, cat, cat + " description", null));
            }
        }
    }

    private void seedImages() {
        if (imagesRepo.findByFilepathToImage("/images/default-profile.png").isEmpty()) {
            imagesRepo.save(new Images(0, "/images/default-profile.png"));
        }
        if (imagesRepo.findByFilepathToImage("/images/Albert.jpeg").isEmpty()) {
            imagesRepo.save(new Images(0, "/images/Albert.jpeg"));
        }
    }

    private void seedUserWithListing() {
        if (userRepo.findByEmail("test@example.com").isPresent()) return;

        Role role = roleRepo.findByName("USER").orElseThrow();
        Categories cat = categoriesRepo.findByName("Electronics").orElseThrow();
        Condition condition = conditionRepo.findByName("Used").orElseThrow();
        Images profileImg = imagesRepo.findByFilepathToImage("/images/default-profile.png").orElseThrow();

        User user = new User("test@example.com", hasher.hashPassword("test123"), "Test", "Bruker", "12345678", profileImg);
        user.setRole(role);
        userRepo.save(user);

        Listing listing = new Listing(0, user, cat, condition, "Brukt laptop", 0, 1500,
                "Fin brukt MacBook", "Lite brukt MacBook Pro 13'' fra 2020. Batteri i god stand.",
                "13''", new Date(), new Date(), 63.4305, 10.3951);
        listingRepo.save(listing);

        Images listingImg = new Images(0, "/images/listing-mac.png");
        listingImg.setListing(listing);
        imagesRepo.save(listingImg);
    }

    private void seedAdditionalUserAndListings() {
        if (userRepo.findByEmail("Albert@example.com").isPresent()) return;

        Role role = roleRepo.findByName("USER").orElseThrow();
        Categories clothing = categoriesRepo.findByName("Clothing").orElseThrow();
        Categories electronics = categoriesRepo.findByName("Electronics").orElseThrow();
        Condition used = conditionRepo.findByName("Used").orElseThrow();
        Images profileImg = imagesRepo.findByFilepathToImage("/images/Albert.jpeg").orElseThrow();

        User user = new User("Albert@example.com", hasher.hashPassword("password123"), "Albert", "Zindel", "98765432", profileImg);
        user.setRole(role);
        userRepo.save(user);

        Listing jacket = new Listing(0, user, clothing, used, "Pent brukt jakke", 0, 300,
                "Stilig vårjakke", "Lite brukt jakke i størrelse M, perfekt til våren.",
                "M", new Date(), new Date(), 59.9139, 10.7522);
        listingRepo.save(jacket);

        Images jacketImg = new Images(0, "/images/jacket.png");
        jacketImg.setListing(jacket);
        imagesRepo.save(jacketImg);

        Listing iphone = new Listing(0, user, electronics, used, "iPhone 12 til salgs", 0, 4500,
                "Fin iPhone 12", "iPhone 12, 128 GB. Ingen riper. Følger med lader og deksel.",
                null, new Date(), new Date(), 60.3913, 5.3221);
        listingRepo.save(iphone);

        Images iphoneImg = new Images(0, "/images/iphone.png");
        Images iphone2Img = new Images(0, "/images/iphone12-back.png");
        iphoneImg.setListing(iphone);
        imagesRepo.save(iphoneImg);
        iphone2Img.setListing(iphone);
        imagesRepo.save(iphone2Img);
    }

    private void seedMoreCategoriesAndUsersWithListings() {
        Role role = roleRepo.findByName("USER").orElseThrow();
        Condition used = conditionRepo.findByName("Used").orElseThrow();
        List<Categories> allCategories = categoriesRepo.findAll();

        for (int i = 1; i <= 5; i++) {
            String email = "user" + i + "@example.com";
            if (userRepo.findByEmail(email).isPresent()) continue;

            User user = new User(email, hasher.hashPassword("password" + i), "User", String.valueOf(i), "9990000" + i, null);
            user.setRole(role);
            userRepo.save(user);

            for (int j = 1; j <= 6; j++) {
                Categories cat = allCategories.get((i + j) % allCategories.size());
                Listing listing = new Listing(0, user, cat, used, "Ting " + j + " fra bruker" + i,
                        0, 100 * j, "Kort beskrivelse", "Lang beskrivelse", null,
                        new Date(), new Date(), 60.0 + i, 10.0 + j);
                listingRepo.save(listing);
            }
        }
    }

    private void assignAlbertFavoritesAndHistory() {
        Optional<User> albertOpt = userRepo.findByEmail("Albert@example.com");
        if (albertOpt.isEmpty()) return;

        User albert = albertOpt.get();
        List<Listing> all = listingRepo.findAll();
        List<Listing> notOwn = all.stream().filter(l -> l.getCreator().getId() != albert.getId()).toList();

        for (int i = 0; i < 10 && i < notOwn.size(); i++) {
            albert.addFavorite(notOwn.get(i));
        }
        for (int i = 10; i < 20 && i < notOwn.size(); i++) {
            albert.addHistory(notOwn.get(i));
        }

        userRepo.save(albert);
    }
}


