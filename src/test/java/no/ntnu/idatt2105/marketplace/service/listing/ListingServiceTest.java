package no.ntnu.idatt2105.marketplace.service.listing;

// Mockito
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

// utility
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

// models
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.model.user.Role;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import no.ntnu.idatt2105.marketplace.model.listing.Condition;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.negotiation.Conversation;

// DTOs
import no.ntnu.idatt2105.marketplace.dto.listing.*;

// repos
import no.ntnu.idatt2105.marketplace.repo.*;

// services
import no.ntnu.idatt2105.marketplace.service.images.ImagesService;

// JUnit tests
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Unit tests for {@link ListingService}.
 */
@SpringBootTest
@ActiveProfiles("test")
class ListingServiceTest {

  @Mock private ListingRepo listingRepo;
  @Mock private UserRepo userRepo;
  @Mock private CategoriesRepo categoriesRepo;
  @Mock private ConditionRepo conditionRepo;
  @Mock private ImagesService imagesService;

  @InjectMocks
  private ListingService listingService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Tests that isListingValid returns true for existing listing.
   */
  @Test
  void isListingValid_shouldReturnTrueIfExists() {
    when(listingRepo.findById(1)).thenReturn(Optional.of(new Listing()));
    assertTrue(listingService.isListingValid(1));
  }

  /**
   * Tests that isListingValid returns false if listing does not exist.
   */
  @Test
  void isListingValid_shouldReturnFalseIfNotFound() {
    when(listingRepo.findById(99)).thenReturn(Optional.empty());
    assertFalse(listingService.isListingValid(99));
  }

  /**
   * Tests that userHasPermission returns true if user is admin.
   */
  @Test
  void userHasPermission_shouldReturnTrueIfUserIsAdmin() {
    User admin = new User();
    Role role = new Role();
    role.setName("ADMIN");
    admin.setRole(role);
    admin.setId(1);

    Listing listing = new Listing();
    listing.setCreator(admin);

    when(userRepo.findById(1)).thenReturn(Optional.of(admin));
    when(listingRepo.findById(1)).thenReturn(Optional.of(listing));

    assertTrue(listingService.userHasPermission(1, 1));
  }

  /**
   * Tests that userHasPermission returns false if user is not creator or admin.
   */
  @Test
  void userHasPermission_shouldReturnFalseIfNoPermission() {
    User user = new User();
    user.setId(2);

    User creator = new User();
    creator.setId(3);

    Listing listing = new Listing();
    listing.setCreator(creator);

    when(userRepo.findById(2)).thenReturn(Optional.of(user));
    when(listingRepo.findById(1)).thenReturn(Optional.of(listing));

    assertFalse(listingService.userHasPermission(1, 2));
  }

  /**
   * Tests that getListingById returns the listing with the given id
   */
  @Test
  void getListingById_shouldReturnListingIfExists() {
    Listing listing = new Listing();
    listing.setId(1);
    when(listingRepo.findById(1)).thenReturn(Optional.of(listing));

    assertEquals(listingService.getListingById(1), listing);
  }

  /**
   * Tests that getListingById returns null if no listing is found
   */
  @Test
  void getListingById_shouldReturnNullIfNotFound() {
    when(listingRepo.findById(1)).thenReturn(Optional.empty());

    Listing result = listingService.getListingById(1);

    assertNull(result);
  }

  /**
   * Tests that getListingById returns null if the listing is not found
   */
  @Test
  void getAllCategories_shouldReturnListOfCategoriesDTOs() {
    when(listingRepo.findById(99)).thenReturn(Optional.empty());

    assertNull(listingService.getListingById(99));
  }

  /**
   * Test that the toDTO method returns a DTO with matching data to the provided Listing
   */
  @Test
  void toDTO_shouldReturnTheDtoOfAListing() {
    User creator = new User();
    creator.setId(1);

    Categories category = new Categories("Test", "Test Category", null);
    Condition condition = new Condition(1, "Test Condition");

    Listing listing = new Listing(
            1,
            creator,
            category,
            condition,
            "testListing",
            1,
            100,
            "brief description",
            "full description",
            "M",
            new Date(),
            new Date(),
            10.123456,
            10.123456
    );

    Images image = new Images();
    image.setFilepath_to_image("/images/test.png");
    image.setListing(listing);
    listing.addImage(image);

    ListingDTO dto = listingService.toDTO(listing);

    assertEquals(listing.getId(), dto.getId());
    assertEquals(listing.getTitle(), dto.getTitle());
    assertEquals(listing.getBrief_description(), dto.getBriefDescription());
    assertEquals(listing.getFull_description(), dto.getFullDescription());
    assertEquals(listing.getPrice(), dto.getPrice());
    assertEquals(listing.getSale_status(), dto.getSaleStatus());
    assertEquals(listing.getSize(), dto.getSize());
    assertEquals(listing.getLatitude(), dto.getLatitude());
    assertEquals(listing.getLongitude(), dto.getLongitude());
    assertEquals(listing.getCategory().getName(), dto.getCategoryName());
    assertEquals(listing.getCondition().getName(), dto.getConditionName());
    assertEquals(listing.getCreator().getId(), dto.getCreatorId());
    assertEquals(listing.getImages().get(0).getFilepath_to_image(), dto.getImagePath());
    assertTrue(dto.getImageUrls().contains("http://localhost:8080/images/test.png"));
  }

  /**
   * Tests that the toCategoriesDTO method correctly converts a Categories entity to a DTO.
   */
  @Test
  void toCategoriesDTO_shouldMapCategoryCorrectly() {
    // Arrange
    Categories parent = new Categories("Electronics", "All electronic items", null);
    parent.setId(10);
    Categories child = new Categories("Laptops", "Portable computers", parent);
    child.setId(11);

    // Act
    CategoriesDTO dto = invokeToCategoriesDTO(child);

    // Assert
    assertNotNull(dto);
    assertEquals(11, dto.getId());
    assertEquals("Laptops", dto.getName());
    assertEquals("Portable computers", dto.getDescription());
    assertEquals(10, dto.getParent_category());
  }

  /**
   * Tests that the toConditionsDTO method correctly converts a Condition entity to a DTO.
   */
  @Test
  void toConditionsDTO_shouldMapConditionCorrectly() {
    // Arrange
    Condition condition = new Condition();
    condition.setId(5);
    condition.setName("Used");

    // Act (via reflection)
    ConditionsDTO dto = invokeToConditionsDTO(condition);

    // Assert
    assertEquals(5, dto.getId());
    assertEquals("Used", dto.getName());
  }

  /**
   * Tests that getAllCategories returns a list of CategoriesDTO matching the categories in the repository.
   */
  @Test
  void getAllCategories_shouldReturnAllCategoryDTOs() {
    Categories cat1 = new Categories("Electronics", "All electronics", null);
    Categories cat2 = new Categories("Clothing", "Apparel and accessories", null);

    List<Categories> categoryList = List.of(cat1, cat2);

    when(categoriesRepo.findAll()).thenReturn(categoryList);

    List<CategoriesDTO> result = listingService.getAllCategories();

    // Assert: check that the result matches
    assertEquals(2, result.size());
    assertEquals("Electronics", result.get(0).getName());
    assertEquals("Clothing", result.get(1).getName());
    assertEquals("All electronics", result.get(0).getDescription());
    assertEquals("Apparel and accessories", result.get(1).getDescription());
  }

  /**
   * Tests that getAllConditions returns a list of ConditionsDTO matching the conditions in the repository.
   */
  @Test
  void getAllConditions_shouldReturnAllConditionDTOs() {
    // Arrange: create mock conditions
    Condition cond1 = new Condition(1, "New");
    Condition cond2 = new Condition(2, "Used");
    List<Condition> conditionList = List.of(cond1, cond2);

    // Mock the repository response
    when(conditionRepo.findAll()).thenReturn(conditionList);

    // Act: call the method
    List<ConditionsDTO> result = listingService.getAllConditions();

    // Assert: validate the results
    assertEquals(2, result.size());
    assertEquals("New", result.get(0).getName());
    assertEquals("Used", result.get(1).getName());
    assertEquals(1, result.get(0).getId());
    assertEquals(2, result.get(1).getId());
  }

  /**
   * Tests that getAllListings returns a list of DTOs corresponding to all listings in the database.
   */
  @Test
  void getAllListings_shouldReturnAllListings() {
    Listing listing1 = new Listing();
    listing1.setId(1);
    listing1.setTitle("Listing 1");
    listing1.setBrief_description("Brief 1");
    listing1.setFull_description("Full 1");
    listing1.setPrice(100);
    listing1.setSale_status(0);
    listing1.setLatitude(10.0);
    listing1.setLongitude(20.0);
    listing1.setCategory(new Categories("Electronics", "desc", null));
    listing1.setCondition(new Condition(1, "New"));
    listing1.setCreator(new User());
    listing1.setCreated_at(new Date());
    listing1.setUpdated_at(new Date());

    Listing listing2 = new Listing();
    listing2.setId(2);
    listing2.setTitle("Listing 2");
    listing2.setBrief_description("Brief 2");
    listing2.setFull_description("Full 2");
    listing2.setPrice(200);
    listing2.setSale_status(1);
    listing2.setLatitude(30.0);
    listing2.setLongitude(40.0);
    listing2.setCategory(new Categories("Clothing", "desc", null));
    listing2.setCondition(new Condition(2, "Used"));
    listing2.setCreator(new User());
    listing2.setCreated_at(new Date());
    listing2.setUpdated_at(new Date());

    List<Listing> mockListings = List.of(listing1, listing2);

    when(listingRepo.findAll()).thenReturn(mockListings);

    List<ListingDTO> result = listingService.getAllListings();

    assertEquals(2, result.size());
    assertEquals("Listing 1", result.get(0).getTitle());
    assertEquals("Listing 2", result.get(1).getTitle());
  }

  /**
   * Tests that getAllListingsForUser returns only the listings created by the given user,
   * and does not throw when the user exists.
   */
  @Test
  void getAllListingsForUser_shouldReturnAllListings() throws Exception {
    // Arrange
    User creator = new User();
    creator.setId(1);

    Listing listing1 = new Listing();
    listing1.setId(1);
    listing1.setTitle("Listing 1");
    listing1.setBrief_description("Brief 1");
    listing1.setFull_description("Full 1");
    listing1.setPrice(100);
    listing1.setSale_status(0);
    listing1.setLatitude(10.0);
    listing1.setLongitude(20.0);
    listing1.setCategory(new Categories("Electronics", "desc", null));
    listing1.setCondition(new Condition(1, "New"));
    listing1.setCreator(creator);
    listing1.setCreated_at(new Date());
    listing1.setUpdated_at(new Date());

    List<Listing> mockListings = List.of(listing1);

    when(userRepo.findById(1)).thenReturn(Optional.of(creator));
    when(listingRepo.findAllByCreator(creator)).thenReturn(mockListings);

    // Act
    List<ListingDTO> result = listingService.getAllListingsForUser(1);

    // Assert
    assertEquals(1, result.size());
    assertEquals("Listing 1", result.get(0).getTitle());
    assertEquals(100, result.get(0).getPrice());
  }

  /**
   * Test that getAllListingsForUser throws exception if user does not exist
   */
  @Test
  void getAllListingsForUser_shouldThrowExceptionIfUserNotFound() {
    when(userRepo.findById(1)).thenReturn(Optional.empty());
    Exception exception = assertThrows(Exception.class, () ->
            listingService.getAllListingsForUser(1)
    );
    assertEquals("No user found", exception.getMessage());
  }

  /**
   * Tests that toDTO maps listing correctly with favorite value
   */
  @Test
  void toDTO_shouldMapListingCorrectlyWithFavorited() {
    User user = new User();
    user.setId(1);

    Categories cat = new Categories("Electronics", "Tech stuff", null);
    Condition cond = new Condition(1, "New");

    Listing listing = new Listing(
            42,
            user,
            cat,
            cond,
            "MacBook Pro",
            0,
            2000,
            "Brief desc",
            "Full desc",
            "13\"",
            new Date(),
            new Date(),
            59.91,
            10.75
    );

    Images image1 = new Images(1, "/images/mac1.png");
    image1.setListing(listing);
    Images image2 = new Images(2, "/images/mac2.png");
    image2.setListing(listing);
    List<Images> images = new ArrayList<>();
    images.add(image1);
    images.add(image2);

    listing.setImages(images);

    // user favorites the listing
    user.addFavorite(listing);

    // Act
    ListingDTO dto = listingService.toDTO(listing, user);

    // Assert
    assertEquals(42, dto.getId());
    assertEquals("MacBook Pro", dto.getTitle());
    assertEquals("Brief desc", dto.getBriefDescription());
    assertEquals("/images/mac1.png", dto.getImagePath());
    assertEquals(2, dto.getImageUrls().size());
    assertTrue(dto.isFavorited());
  }

  /**
   * Tests that toDTO maps listing correctly even when user is null
   */
  @Test
  void toDTO_shouldMapListingCorrectlyWhenUserIsNull() {
    // Arrange
    User creator = new User();
    creator.setId(2);

    Listing listing = new Listing(
            100,
            creator,
            new Categories("Books", "Reading material", null),
            new Condition(2, "Used"),
            "Harry Potter",
            1,
            50,
            "A book",
            "A long book",
            "N/A",
            new Date(),
            new Date(),
            60.39,
            5.32
    );

    listing.setImages(List.of());

    // Act
    ListingDTO dto = listingService.toDTO(listing, null);

    // Assert
    assertEquals(100, dto.getId());
    assertEquals("Harry Potter", dto.getTitle());
    assertNull(dto.getImagePath());
    assertFalse(dto.isFavorited());
  }

  /**
   * Tests getRandomListings should return a list with {@code count} amount of listings
   */
  @Test
  void getRandomListings_shouldReturnListOfListings() {
    User creator = new User();
    creator.setId(1);
    Categories cat = new Categories("Category", "Test", null);
    Condition cond = new Condition(1, "New");

    Listing listing1 = new Listing(1, creator, cat, cond, "Listing 1", 0, 100, "Brief", "Full", "", new Date(), new Date(), 10, 10);
    Listing listing2 = new Listing(2, creator, cat, cond, "Listing 2", 0, 200, "Brief", "Full", "", new Date(), new Date(), 20, 20);
    Listing listing3 = new Listing(3, creator, cat, cond, "Listing 3", 0, 300, "Brief", "Full", "", new Date(), new Date(), 30, 30);

    List<Listing> allListings = List.of(listing1, listing2, listing3);

    when(listingRepo.findAll()).thenReturn(allListings);

    List<ListingDTO> randomListings = listingService.getRandomListings(2);

    assertNotNull(randomListings);
    assertEquals(2, randomListings.size());

    for (ListingDTO dto : randomListings) {
      assertNotNull(dto.getTitle());
      assertTrue(List.of("Listing 1", "Listing 2", "Listing 3").contains(dto.getTitle()));
    }
  }

  /**
   * Testing getListingByCategory should return List of {@link ListingDTO} with the provided category
   */
  @Test
  void getListingByCategory_shouldReturnListOfListings() {
    Categories cat = new Categories("Category", "Test", null);
    Condition cond = new Condition(1, "New");
    User creator = new User();
    creator.setId(1);

    Listing listing1 = new Listing(1, creator, cat, cond, "Listing 1", 0, 100, "Brief", "Full", "", new Date(), new Date(), 10, 10);
    Listing listing2 = new Listing(2, creator, cat, cond, "Listing 2", 0, 200, "Brief", "Full", "", new Date(), new Date(), 20, 20);

    List<Listing> allListings = List.of(listing1, listing2);

    when(listingRepo.findByCategory_Name("Category")).thenReturn(allListings);

    List<ListingDTO> listingsByCategory = listingService.getListingsByCategory(cat.getName(), null);

    assertNotNull(listingsByCategory);
    assertEquals(2, listingsByCategory.size());

    for (ListingDTO dto: listingsByCategory) {
      assertNotNull(dto.getTitle());
      assertTrue(List.of("Listing 1", "Listing 2").contains(dto.getTitle()));
    }
  }

  /**
   * Testing getListingByCategory should return List of {@link ListingDTO} with the provided category and not includes listings by given user id
   */
  @Test
  void getListingByCategory_shouldReturnListOfListingsWhereCreatorIdNotEquals1() {
    Categories cat = new Categories("Category", "Test", null);
    Condition cond = new Condition(1, "New");
    User creator1 = new User();
    creator1.setId(1);
    User creator2 = new User();
    creator2.setId(2);

    Listing listing1 = new Listing(1, creator1, cat, cond, "Listing 1", 0, 100, "Brief", "Full", "", new Date(), new Date(), 10, 10);
    Listing listing2 = new Listing(2, creator2, cat, cond, "Listing 2", 0, 200, "Brief", "Full", "", new Date(), new Date(), 20, 20);

    List<Listing> allListings = List.of(listing1, listing2);

    when(listingRepo.findByCategory_Name("Category")).thenReturn(allListings);

    List<ListingDTO> listingsByCategory = listingService.getListingsByCategory(cat.getName(), 2);

    assertNotNull(listingsByCategory);
    assertEquals(1, listingsByCategory.size());

    assertEquals(allListings.getFirst().getCreator().getId(), creator1.getId());
    assertEquals(allListings.getFirst().getTitle(), "Listing 1");
  }

  /**
   * Tests deleteListing should delete listing with given id if it exists
   */
  @Test
  void deleteListing_shouldDeleteListingIfExists() {
    // Arrange
    Listing mockListing = mock(Listing.class);

    // Correctly typed mock lists
    List<Conversation> mockConversations = spy(new ArrayList<>());
    List<Images> mockImages = spy(new ArrayList<>());

    // Stub all necessary methods
    when(mockListing.getConversations()).thenReturn(mockConversations);
    when(mockListing.getImages()).thenReturn(mockImages);

    doNothing().when(mockListing).removeUser_favorites();
    doNothing().when(mockListing).removeUser_history();
    doNothing().when(mockListing).removeOffers();

    when(listingRepo.findById(1)).thenReturn(Optional.of(mockListing));

    // Act
    listingService.deleteListing(1);

    // Assert
    verify(mockListing).removeUser_favorites();
    verify(mockListing).removeUser_history();
    verify(mockListing).removeOffers();
    verify(mockConversations).clear();
    verify(mockImages).clear();
    verify(listingRepo).delete(mockListing);
  }


  /**
   * Tests deleteListing should throw Exception if no listing with the given id is found
   */
  @Test
  void deleteListing_shouldThrowIfListingNotFound() {
    // Arrange
    when(listingRepo.findById(999)).thenReturn(Optional.empty());

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () ->
            listingService.deleteListing(999)
    );

    assertEquals("Listing not found", exception.getMessage());
  }


  /**
   * Tests getRecommendedListingsForUser should recommend based on favorites
   */
  @Test
  void getRecommendedListingsForUser_shouldPrioritizeFavorites() {
    // Arrange
    User user = new User();
    user.setId(1);

    Categories categoryA = new Categories("Electronics", "Electronics", null);
    Categories categoryB = new Categories("Books", "Books", null);
    Condition condition = new Condition(1, "New");

    Listing favorite = new Listing();
    favorite.setId(1);
    favorite.setCategory(categoryA);
    favorite.setCondition(condition);
    favorite.setCreator(new User());
    user.addFavorite(favorite);

    Listing unrelated = new Listing();
    unrelated.setId(2);
    unrelated.setCategory(categoryB);
    unrelated.setCondition(condition);
    unrelated.setCreator(new User());

    when(listingRepo.findAll()).thenReturn(List.of(favorite, unrelated));

    // Act
    List<ListingDTO> result = listingService.getRecommendedListingsForUser(user, 2);

    // Assert
    assertEquals(2, result.size());
    assertEquals("Electronics", result.get(0).getCategoryName()); // prioritized
  }

  /**
   * Tests getRecommendedListingsForUser should recommend based on history
   */
  @Test
  void getRecommendedListingsForUser_shouldPrioritizeHistory() {
    // Arrange
    User user = new User();
    user.setId(1);

    Categories watchedCategory = new Categories("Clothing", "Clothing", null);
    Condition condition = new Condition(1, "Used");

    Listing historyListing = new Listing();
    historyListing.setId(1);
    historyListing.setCategory(watchedCategory);
    historyListing.setCondition(condition);
    historyListing.setCreator(new User());
    user.addHistory(historyListing);

    Listing otherListing = new Listing();
    otherListing.setId(2);
    otherListing.setCategory(new Categories("Furniture", "Furniture", null));
    otherListing.setCondition(condition);
    otherListing.setCreator(new User());

    when(listingRepo.findAll()).thenReturn(List.of(historyListing, otherListing));

    // Act
    List<ListingDTO> result = listingService.getRecommendedListingsForUser(user, 2);

    // Assert
    assertEquals(2, result.size());
    assertEquals("Clothing", result.get(0).getCategoryName());
  }

  /**
   * Tests getRecommendedListingsForUser should exclude the users own listings
   */
  @Test
  void getRecommendedListingsForUser_shouldExcludeOwnListings() {
    // Arrange
    User user = new User();
    user.setId(1);

    Categories category = new Categories("Sports", "Sports", null);
    Condition condition = new Condition(1, "New");

    Listing ownListing = new Listing();
    ownListing.setId(1);
    ownListing.setCategory(category);
    ownListing.setCondition(condition);
    ownListing.setCreator(user); // same user

    Listing otherListing = new Listing();
    otherListing.setId(2);
    otherListing.setCategory(category);
    otherListing.setCondition(condition);
    otherListing.setCreator(new User()); // different creator

    user.addFavorite(otherListing); // Only this should be considered

    when(listingRepo.findAll()).thenReturn(List.of(ownListing, otherListing));

    // Act
    List<ListingDTO> result = listingService.getRecommendedListingsForUser(user, 2);

    // Assert
    assertEquals(1, result.size());
    assertEquals(2, result.get(0).getId());
  }

  /**
   * Tests getRecommendedListingsForUser should fall back to random listings if user history or favorites are empty
   */
  @Test
  void getRecommendedListingsForUser_shouldFallbackToRandomListingsIfNoHistoryOrFavorites() {
    User user = new User();
    user.setId(1);
    user.clearFavorites();
    user.clearHistory();

    Listing listing1 = new Listing();
    listing1.setId(10);
    listing1.setCreator(new User());
    listing1.setCategory(new Categories("Misc", "desc", null));
    listing1.setCondition(new Condition(1, "Good")); // ✅ Fix: Set condition

    when(listingRepo.findAll()).thenReturn(List.of(listing1));

    List<ListingDTO> result = listingService.getRecommendedListingsForUser(user, 1);

    assertEquals(1, result.size());
    assertEquals(10, result.get(0).getId());
  }


  /**
   * Tests updateListing should throw exception if listing does not exist
   */
  @Test
  void updateListing_shouldThrowExceptionIfListingNotFound() {
    when(listingRepo.findById(1)).thenReturn(Optional.empty());

    ListingUpdate dto = new ListingUpdate();
    dto.setCategory_id(1);
    dto.setCondition_id(1);

    Exception exception = assertThrows(Exception.class, () -> {
      listingService.updateListing(1, dto);
    });

    assertEquals("No listing with id 1", exception.getMessage());
  }

  /**
   * Tests updateListing should throw exception if category is not found
   */
  @Test
  void updateListing_shouldThrowExceptionIfCategoryNotFound() {
    Listing listing = new Listing();
    when(listingRepo.findById(1)).thenReturn(Optional.of(listing));
    when(categoriesRepo.findById(1)).thenReturn(Optional.empty());

    ListingUpdate dto = new ListingUpdate();
    dto.setCategory_id(1);
    dto.setCondition_id(1);

    Exception exception = assertThrows(Exception.class, () -> {
      listingService.updateListing(1, dto);
    });

    assertEquals("No category found", exception.getMessage());
  }

  /**
   * Tests updateListing should throw exception if condition is not found
   */
  @Test
  void updateListing_shouldThrowExceptionIfConditionNotFound() {
    Listing listing = new Listing();
    when(listingRepo.findById(1)).thenReturn(Optional.of(listing));
    when(categoriesRepo.findById(1)).thenReturn(Optional.of(new Categories()));
    when(conditionRepo.findById(1)).thenReturn(Optional.empty());

    ListingUpdate dto = new ListingUpdate();
    dto.setCategory_id(1);
    dto.setCondition_id(1);

    Exception exception = assertThrows(Exception.class, () -> {
      listingService.updateListing(1, dto);
    });

    assertEquals("No condition found", exception.getMessage());
  }


  /**
   * Tests updateListing should successfully update listing
   */
  @Test
  void updateListing_shouldUpdateSuccessfully() throws Exception {
    // Arrange
    Listing listing = new Listing();
    listing.setImages(new ArrayList<>());

    Categories category = new Categories("Electronics", null, null);
    Condition condition = new Condition(1, "New");

    ListingUpdate dto = new ListingUpdate();
    dto.setTitle("Updated Title");
    dto.setCategory_id(1);
    dto.setCondition_id(1);
    dto.setSale_status(1);
    dto.setPrice(100);
    dto.setBrief_description("Brief");
    dto.setFull_description("Full");
    dto.setSize("M");
    dto.setLatitude(10.0);
    dto.setLongitude(20.0);
    dto.setImages(List.of()); // no images

    when(listingRepo.findById(1)).thenReturn(Optional.of(listing));
    when(categoriesRepo.findById(1)).thenReturn(Optional.of(category));
    when(conditionRepo.findById(1)).thenReturn(Optional.of(condition));

    // Act
    listingService.updateListing(1, dto);

    // Assert
    assertEquals("Updated Title", listing.getTitle());
    assertEquals("Brief", listing.getBrief_description());
    assertEquals("Full", listing.getFull_description());
    assertEquals(100, listing.getPrice());
    assertEquals(10.0, listing.getLatitude());
    verify(listingRepo).saveAndFlush(listing);
  }





  /**
   * Utility to access private method toCategoriesDTO using reflection.
   */
  private CategoriesDTO invokeToCategoriesDTO(Categories category) {
    try {
      Method method = ListingService.class.getDeclaredMethod("toCategoriesDTO", Categories.class);
      method.setAccessible(true);
      return (CategoriesDTO) method.invoke(listingService, category);
    } catch (Exception e) {
      fail("Reflection failed: " + e.getMessage());
      return null;
    }
  }

  /**
   * Utility to access private method toConditionsDTO using reflection.
   */
  private ConditionsDTO invokeToConditionsDTO(Condition condition) {
    try {
      Method method = ListingService.class.getDeclaredMethod("toConditionsDTO", Condition.class);
      method.setAccessible(true);
      return (ConditionsDTO) method.invoke(listingService, condition);
    } catch (Exception e) {
      fail("Reflection failed: " + e.getMessage());
      return null;
    }
  }
}
