package no.ntnu.idatt2105.marketplace.service.listing;

import no.ntnu.idatt2105.marketplace.controller.ListingController;
import no.ntnu.idatt2105.marketplace.dto.listing.CategoriesDTO;
import no.ntnu.idatt2105.marketplace.dto.listing.ConditionsDTO;
import no.ntnu.idatt2105.marketplace.dto.listing.ListingDTO;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger(ListingController.class);



    /**
     * Gets a list if n amount of listings
     * @param count the amount of listings to be returned
     * @return List with {@code n} amount of {@link ListingDTO}
     */
    public List<ListingDTO> getRandomListings(int count) {
        List<Listing> all = new ArrayList<>(listingRepo.findAll());
        Collections.shuffle(all);
        return all.stream()
                .limit(count)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }



    /**
     * Gets all existing listings from the database
     * @return List of {@link ListingDTO} representing the stored listings in the database
     */
    public List<ListingDTO> getAllListings() {
        return listingRepo.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }



    /**
     * Gets all existing listings from the database where the creator id equals {@code user_id}
     * @param user_id the id of the user
     * @return List of {@link ListingDTO} where the creator id equals the provided {@code user_id}
     * @throws Exception
     */
    public List<ListingDTO> getAllListingsForUser(int user_id) throws Exception {
        Optional<User> creator = userRepo.findById(user_id);
        if (creator.isEmpty()) {
            throw new Exception("No user found");
        }
        return listingRepo.findAllByCreator(creator.get()).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }



    /**
     * Gets a listing from the database with matching ID
     * @param id the id if the listing
     * @return The {@link Listing} with the provided id if found, null otherwise
     */
    public Listing getListingById(int id) {
        return listingRepo.findById(id).orElse(null);
    }



    /**
     * Converts a {@link Listing} instance to an DTO instance used when sending data
     * @param listing the listing to be converted
     * @return an {@link ListingDTO} of the {@link Listing} provided
     */
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



    /**
     * <p>Validates whether a listing with the given {@code listing_id} exists in the repository.</p>
     *
     * @param listing_id The ID of the listing to validate.
     * @return {@code true} if the listing exists, {@code false} otherwise.
     */
    public boolean isListingValid(int listing_id) {
        return listingRepo.findById(listing_id).isPresent();
    }



    /**
     * <p>Validates whether a user with the given {@code user_id} has the required permission for a listing with the given {@code listing_id}.</p>
     * @param listing_id the integer value of the listing id
     * @param user_id the integer value fo the user id
     * @return {@code true} if the user has permission, {@code false} otherwise
     */
    public boolean userHasPermission(int listing_id, int user_id) {
        Optional<User> userOpt = userRepo.findById(user_id);
        Optional<Listing> listingOpt = listingRepo.findById(listing_id);

        if (userOpt.isEmpty() || listingOpt.isEmpty()) {
            LOGGER.info("User or listing not found");
            return false;
        }

        User user = userOpt.get();
        Listing listing = listingOpt.get();

        boolean isAdmin = user.getRole() != null && "ADMIN".equalsIgnoreCase(user.getRole().getName());
        boolean isCreator = listing.getCreator() != null && listing.getCreator().getId() == user_id;

        LOGGER.info("Is Admin: " + isAdmin);
        LOGGER.info("Is Creator: " + isCreator);

        return isAdmin || isCreator;
    }



    @Transactional
    public void updateListing(int listing_id, ListingUpdate updatedListingData) throws Exception {
        LOGGER.info("Updating listing with id: " + listing_id + "\n" + updatedListingData);

        Listing listing = listingRepo.findById(listing_id)
                .orElseThrow(() -> new Exception("No listing with id " + listing_id));

        listing.setTitle(updatedListingData.getTitle());

        Categories category = categoriesRepo.findById(updatedListingData.getCategory_id())
                .orElseThrow(() -> new Exception("No category found"));
        Condition condition = conditionRepo.findById(updatedListingData.getCondition_id())
                .orElseThrow(() -> new Exception("No condition found"));


        // Handle image update & orphan removal
        List<String> updatedImageUrls = Optional.ofNullable(updatedListingData.getImages()).orElse(List.of());
        List<Images> imagesToKeep = new ArrayList<>();

        List<Images> existingImages = new ArrayList<>(listing.getImages());
        for (Images image : existingImages) {
            String fullURL = "http://localhost:8080" + image.getFilepath_to_image();

            if (updatedImageUrls.contains(fullURL)) {
                imagesToKeep.add(image); // don't worry about setListing — handled in setImages()
                LOGGER.info("Keeping: " + image.getFilepath_to_image());
            } else {
                image.setListing(null);
                listing.removeImage(image);
                imagesService.deleteImageFromFile(image.getId());
            }
        }

        // Update fields
        listing.setCategory(category);
        listing.setCondition(condition);
        listing.setSale_status(updatedListingData.getSale_status());
        listing.setPrice(updatedListingData.getPrice());
        listing.setBrief_description(updatedListingData.getBrief_description());
        listing.setFull_description(updatedListingData.getFull_description());
        listing.setSize(updatedListingData.getSize());
        listing.setLatitude(updatedListingData.getLatitude());
        listing.setLongitude(updatedListingData.getLongitude());

        listingRepo.saveAndFlush(listing);
        LOGGER.info("Updated listing: " + listingRepo.findById(listing_id).get());
    }



    /**
     * Gets all {@link Categories} from the database
     * @return List of {@link CategoriesDTO} representing all the categories in the database
     */
    public List<CategoriesDTO> getAllCategories() {
        return categoriesRepo.findAll().stream()
                .map(this::toCategoriesDTO)
                .collect(Collectors.toList());
    }



    /**
     * Gets all {@link Condition}s from the database
     * @return List of {@link ConditionsDTO} representing all the categories in the database
     */
    public List<ConditionsDTO> getAllConditions() {
        return conditionRepo.findAll().stream()
                .map(this::toConditionsDTO)
                .collect(Collectors.toList());
    }



    /**
     * Converts a {@link Categories} instance to an DTO instance used when sending data
     * @param category the category to be converted
     * @return an {@link CategoriesDTO} of the {@link Categories} provided
     */
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



    /**
     * Converts a {@link Condition} instance to an DTO instance used when sending data
     * @param condition the category to be converted
     * @return an {@link ConditionsDTO} of the {@link Condition} provided
     */
    private ConditionsDTO toConditionsDTO(Condition condition) {
        ConditionsDTO dto = new ConditionsDTO();
        dto.setId(condition.getId());
        dto.setName(condition.getName());

        return dto;
    }



    /**
     * Deletes all images from a listing
     * @param listing_id the is of the listing
     */
    public void deleteListingImages(int listing_id) {
        Optional<Listing> listing = listingRepo.findById(listing_id);
        listing.ifPresent(value -> value.setImages(null));
    }



    /**
     * Deletes a listing with the given ID along with the images stored as files
     * @param listing_id the is og the listing
     */
    @Transactional
    public void delete(int listing_id) {
        LOGGER.info("Deleting");
        // Delete all images related to the listing from the files
        Listing listing = listingRepo.findById(listing_id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        // get the images and delete from files
        List<Images> listingImages = listing.getImages();
        LOGGER.info("Deleting images from files");
        for (Images image : listingImages) {
            imagesService.deleteImageFromFile(image.getId());
        }

        deleteListing(listing_id);
    }



    /**
     * Gets a list of n recommended listings based on browse history and favorites.
     * @param currentUser the id of the user
     * @param count the amount of listings to be returned
     * @return A list of {@link ListingDTO} containing the recommended listings
     */
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



    /**
     * Converts a {@link Listing} entity into a {@link ListingDTO}, optionally marking it as favorited
     * based on the provided {@link User}'s favorite listings.
     *
     * @param listing The {@link Listing} to convert.
     * @param user The {@link User} used to check if the listing is marked as favorite.
     *             If {@code null}, the favorited status will not be set.
     * @return A {@link ListingDTO} representing the provided {@link Listing}.
     */
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



    /**
     * Deletes a listing with the given ID
     * @param listingId the is og the listing
     */
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



    /**
     * Gets all existing listings with the given Category.
     * Does not include listings where the given userId matches the creator ID.
     * @param category The {@link Categories} that the listings are filtered by
     * @param userId The id of the user which listings are to be excluded from the result
     * @return List of {@link ListingDTO} representing filtered Listings from the database
     */
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

    /**
     * Searches for listings where the title contains the given query string (case-insensitive).
     *
     * @param query The search query to match in the title.
     * @return A list of {@link ListingDTO} matching the search query.
     */
    public List<ListingDTO> searchByTitle(String query) {
        return listingRepo.findByTitleContainingIgnoreCase(query).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}

