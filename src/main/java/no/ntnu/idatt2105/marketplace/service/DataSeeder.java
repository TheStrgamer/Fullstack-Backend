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

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        if (categoriesRepo.findByName("Electronics").isEmpty()) {
            categoriesRepo.save(new Categories(0, "Electronics", "Devices and gadgets", null));
        }
        if (categoriesRepo.findByName("Clothing").isEmpty()) {
            categoriesRepo.save(new Categories(0, "Clothing", "Apparel and accessories", null));
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
    //imagesRepo.save(new Images(0, "/images/listing-mac.png"));


    private void seedUserWithListing() {
        if (userRepo.findByEmail("test@example.com").isPresent()) return;

        // Hent roller og kategori/tilstand
        Role role = roleRepo.findByName("USER").orElseThrow();
        Categories cat = categoriesRepo.findByName("Electronics").orElseThrow();
        Condition condition = conditionRepo.findByName("Used").orElseThrow();

        // Hent bilde for profil
        Images profileImg = imagesRepo.findByFilepathToImage("/images/default-profile.png").orElseThrow();

        // Opprett bruker
        User user = new User(
                "test@example.com",
                hasher.hashPassword("test123"),
                "Test",
                "Bruker",
                "12345678",
                profileImg
        );
        user.setRole(role);
        userRepo.save(user);

        // Opprett listing
        Listing listing = new Listing(
                0,
                user,
                cat,
                condition,
                "Brukt laptop",
                0, // tilgjengelig
                1500,
                "Fin brukt MacBook",
                "Lite brukt MacBook Pro 13'' fra 2020. Batteri i god stand.",
                "13''",
                new Date(),
                new Date(),
                63.4305,
                10.3951
        );
        listingRepo.save(listing);

        // Ikke bruk et bilde som allerede er i databasen
        // Opprett nytt bilde direkte med riktig relasjon
        Images listingImg = new Images(0, "/images/listing-mac.png");
        listingImg.setListing(listing);      // Knyt til listing
        imagesRepo.save(listingImg);         // Hibernate setter listing_id korrekt
    }
    private void seedAdditionalUserAndListings() {
        if (userRepo.findByEmail("Albert@example.com").isPresent()) return;

        // Hent felles data
        Role role = roleRepo.findByName("USER").orElseThrow();
        Categories clothing = categoriesRepo.findByName("Clothing").orElseThrow();
        Categories electronics = categoriesRepo.findByName("Electronics").orElseThrow();
        Condition used = conditionRepo.findByName("Used").orElseThrow();

        // Opprett ny bruker
        Images profileImg = imagesRepo.findByFilepathToImage("/images/Albert.jpeg").orElseThrow();
        User user = new User(
                "Albert@example.com",
                hasher.hashPassword("password123"),
                "Albert",
                "Zindel",
                "98765432",
                profileImg
        );
        user.setRole(role);
        userRepo.save(user);

        // Opprett listing 1 – Jakke
        Listing jacket = new Listing(
                0, user, clothing, used,
                "Pent brukt jakke", 0, 300,
                "Stilig vårjakke", "Lite brukt jakke i størrelse M, perfekt til våren.",
                "M", new Date(), new Date(), 59.9139, 10.7522 // Oslo
        );
        listingRepo.save(jacket);

        Images jacketImg = new Images(0, "/images/jacket.png");
        jacketImg.setListing(jacket);
        imagesRepo.save(jacketImg);

        // Opprett listing 2 – iPhone
        Listing iphone = new Listing(
                0, user, electronics, used,
                "iPhone 12 til salgs", 0, 4500,
                "Fin iPhone 12", "iPhone 12, 128 GB. Ingen riper. Følger med lader og deksel.",
                null, new Date(), new Date(), 60.3913, 5.3221 // Bergen
        );
        listingRepo.save(iphone);

        Images iphoneImg = new Images(0, "/images/iphone.png");
        iphoneImg.setListing(iphone);
        imagesRepo.save(iphoneImg);

        // Add listing to favorites
        Optional<User> testUserOpt = userRepo.findByEmail("Albert@example.com");
        if (testUserOpt.isPresent()) {
            Listing laptop = listingRepo.findAllByTitle("Brukt laptop").stream().findFirst().orElseThrow();
            user.addFavorite(laptop);
            userRepo.save(user);
        }
    }

}


