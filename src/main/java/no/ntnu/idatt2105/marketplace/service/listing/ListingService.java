package no.ntnu.idatt2105.marketplace.service.listing;

import no.ntnu.idatt2105.marketplace.dto.listing.CategoriesDTO;
import no.ntnu.idatt2105.marketplace.dto.listing.ConditionsDTO;
import no.ntnu.idatt2105.marketplace.dto.listing.ListingDTO;
import no.ntnu.idatt2105.marketplace.dto.listing.ListingMiniDTO;
import no.ntnu.idatt2105.marketplace.dto.listing.ListingUpdate;
import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import no.ntnu.idatt2105.marketplace.model.listing.Condition;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.CategoriesRepo;
import no.ntnu.idatt2105.marketplace.repo.ConditionRepo;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.images.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        Optional<User> userOpt = userRepo.findById(user_id);
        Optional<Listing> listingOpt = listingRepo.findById(listing_id);

        if (userOpt.isEmpty() || listingOpt.isEmpty()) {
            System.out.println("User or listing not found");
            return false;
        }

        User user = userOpt.get();
        Listing listing = listingOpt.get();

        boolean isAdmin = user.getRole() != null && "ADMIN".equalsIgnoreCase(user.getRole().getName());
        boolean isCreator = listing.getCreator() != null && listing.getCreator().getId() == user_id;

        System.out.println("Is Admin: " + isAdmin);
        System.out.println("Is Creator: " + isCreator);

        return isAdmin || isCreator;
    }




    @Transactional
    public void updateListing(int listing_id, ListingUpdate updatedListingData) throws Exception {
        System.out.println("Updating listing with id: " + listing_id + "\n" + updatedListingData);

        Listing listing = listingRepo.findById(listing_id)
                .orElseThrow(() -> new Exception("No listing with id " + listing_id));

        listing.setTitle(updatedListingData.getTitle());

        Categories category = categoriesRepo.findById(updatedListingData.getCategory_id())
                .orElseThrow(() -> new Exception("No category found"));
        Condition condition = conditionRepo.findById(updatedListingData.getCondition_id())
                .orElseThrow(() -> new Exception("No condition found"));

        listing.setCategory(category);
        listing.setCondition(condition);

        // ✅ Handle image update & orphan removal
        List<String> updatedImageUrls = Optional.ofNullable(updatedListingData.getImages()).orElse(List.of());
        List<Images> imagesToKeep = new ArrayList<>();

        List<Images> existingImages = new ArrayList<>(listing.getImages());
        for (Images image : existingImages) {
            String fullURL = "http://localhost:8080" + image.getFilepath_to_image();

            if (updatedImageUrls.contains(fullURL)) {
                imagesToKeep.add(image); // don't worry about setListing — handled in setImages()
                System.out.println("Keeping: " + image.getFilepath_to_image());
            } else {
                image.setListing(null);
                listing.removeImage(image);
                imagesService.deleteImageFromFile(image.getId());
            }
        }

//        listing.setImages(imagesToKeep); // 🔄 will auto-set listing ref inside

        // ✅ Update other fields
        listing.setSale_status(updatedListingData.getSale_status());
        listing.setPrice(updatedListingData.getPrice());
        listing.setBrief_description(updatedListingData.getBrief_description());
        listing.setFull_description(updatedListingData.getFull_description());
        listing.setSize(updatedListingData.getSize());
        listing.setLatitude(updatedListingData.getLatitude());
        listing.setLongitude(updatedListingData.getLongitude());

        // ✅ Save
        listingRepo.saveAndFlush(listing);

        // Optional: log final result
        System.out.println("Updated listing: " + listingRepo.findById(listing_id).get());
    }




    public List<CategoriesDTO> getAllCategories() {
        return categoriesRepo.findAll().stream()
                .map(this::toCategoriesDTO)
                .collect(Collectors.toList());
    }

    public List<ConditionsDTO> getAllConditions() {
        return conditionRepo.findAll().stream()
                .map(this::toConditionsDTO)
                .collect(Collectors.toList());
    }

    private CategoriesDTO toCategoriesDTO(Categories category) {
        CategoriesDTO dto = new CategoriesDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        if (category.getParent_category() != null) {
            dto.setParent_category(category.getParent_category().getId());
        }

        return dto;
    }

    private ConditionsDTO toConditionsDTO(Condition condition) {
        ConditionsDTO dto = new ConditionsDTO();
        dto.setId(condition.getId());
        dto.setName(condition.getName());

        return dto;
    }

    public void deleteListingImages(int listing_id) {
        Optional<Listing> listing = listingRepo.findById(listing_id);
        listing.ifPresent(value -> value.setImages(null));
    }

    @Transactional
    public void delete(int listing_id) {
        System.out.println("Deleting");
        // Delete all images related to the listing from the files
        Optional<Listing> listing = listingRepo.findById(listing_id);
        if (listing.isEmpty()) return;

        // get the images
        List<Images> listingImages = listing.get().getImages();
        System.out.println("Deleting images from files");
        for (Images image : listingImages) {
            imagesService.deleteImageFromFile(image.getId());
        }
        // Delete the listings
        System.out.println("Deleting the listing from the database");
        listingRepo.deleteById(listing_id);
    }

    public ListingService(ListingRepo listingRepo, UserRepo userRepo) {
        this.listingRepo = listingRepo;
        this.userRepo = userRepo;
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

    public ListingDTO toDTO(Listing listing, User user) {
        String imagePath = listing.getImages().isEmpty()
                ? null
                : listing.getImages().get(0).getFilepath_to_image();

        List<String> imageUrls = listing.getImages().stream()
                .map(Images::getFilepath_to_image)
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

    public ListingMiniDTO toMiniDTO(Listing listing) {
        String imagePath = listing.getImages().isEmpty()
                ? null
                : listing.getImages().get(0).getFilepath_to_image();

        return new ListingMiniDTO(
                listing.getTitle(),
                listing.getPrice(),
                imagePath,
                listing.getCreator().getFirstname() + " " + listing.getCreator().getSurname()
        );
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

