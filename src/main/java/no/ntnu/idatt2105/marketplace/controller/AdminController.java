package no.ntnu.idatt2105.marketplace.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idatt2105.marketplace.dto.admin.CategoriesAdminDTO;
import no.ntnu.idatt2105.marketplace.dto.admin.ListingAdminDTO;
import no.ntnu.idatt2105.marketplace.dto.admin.UserAdminDTO;

import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.repo.CategoriesRepo;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.images.ImagesService;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import no.ntnu.idatt2105.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.idatt2105.marketplace.model.user.User;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin API", description = "Operations that require administrator privileges")
public class AdminController {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private ListingRepo listingRepo;

  @Autowired
  private CategoriesRepo categoriesRepo;

  @Autowired
  private UserService userService;

  @Autowired
  private ImagesService imagesService;

  @Autowired
  private JWT_token jwt;


  @GetMapping("/amIAdmin")
  @Operation(
      summary = "Check if user is admin",
      description = "Returns true if the user is an admin, false otherwise"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Data received successfully",
          content = @Content(
              schema = @Schema(implementation = String.class)
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid request"
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized"
      )
  })
  public ResponseEntity<Boolean> getIsUserAdmin(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      ) @RequestHeader("Authorization") String authorizationHeader) {
    if (!authorizationHeader.startsWith("Bearer ")) {
      System.out.println("Invalid Authorization header");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String token = authorizationHeader.substring(7);
    int user_id = Integer.parseInt(jwt.extractIdFromJwt(token));
    Optional<User> user = userRepo.findById(user_id);
    if (user.isEmpty()) {
      System.out.println("No user found with given id:" + user_id);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    boolean isAdmin = user.get().isAdmin();
    if(!isAdmin)
    {
      System.out.println("User is not an admin");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    System.out.println("User is an admin");
    return ResponseEntity.ok(true);
  }

  @GetMapping("/users")
  @Operation(
      summary = "Get users",
      description = "Returns a list of all users"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Data received successfully",
          content = @Content(
              schema = @Schema(implementation = String.class)
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid request"
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized"
      )
  })
  public ResponseEntity<List<UserAdminDTO>> getUsers(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      ) @RequestHeader("Authorization") String authorizationHeader) {

    if (!authorizationHeader.startsWith("Bearer ")) {
      System.out.println("Invalid Authorization header");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String token = authorizationHeader.substring(7);
    int user_id = Integer.parseInt(jwt.extractIdFromJwt(token));
    Optional<User> user = userRepo.findById(user_id);

    if (user.isEmpty()) {
      System.out.println("No user found with given id:" + user_id);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    User adminUser = user.get();
    if(!adminUser.isAdmin())
    {
      System.out.println("User is not an admin");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    List<User> users = userRepo.findAll();
    if (users.isEmpty()) {
      System.out.println("No users found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    ArrayList<UserAdminDTO> userDTOList = new ArrayList<>();
    for (User u : users) {
      UserAdminDTO userDTO = new UserAdminDTO(u, userService.getListingCount(u));
      userDTOList.add(userDTO);
    }
    return ResponseEntity.ok(userDTOList);
  }


  @GetMapping("/listings")
  @Operation(
      summary = "Get listings",
      description = "Returns a list of all listings"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Data received successfully",
          content = @Content(
              schema = @Schema(implementation = String.class)
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid request"
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized"
      )
  })
  public ResponseEntity<List<ListingAdminDTO>> getListings(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      ) @RequestHeader("Authorization") String authorizationHeader) {

    if (!authorizationHeader.startsWith("Bearer ")) {
      System.out.println("Invalid Authorization header");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String token = authorizationHeader.substring(7);
    int user_id = Integer.parseInt(jwt.extractIdFromJwt(token));
    Optional<User> user = userRepo.findById(user_id);

    if (user.isEmpty()) {
      System.out.println("No user found with given id:" + user_id);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    User adminUser = user.get();
    if(!adminUser.isAdmin())
    {
      System.out.println("User is not an admin");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    List<Listing> listings = listingRepo.findAll();
    if (listings.isEmpty()) {
      System.out.println("No users found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    ArrayList<ListingAdminDTO> listingDTOList = new ArrayList<>();
    for (Listing l : listings) {
      ListingAdminDTO listingDTO = new ListingAdminDTO(l);
      listingDTOList.add(listingDTO);
    }
    return ResponseEntity.ok(listingDTOList);
  }

  @GetMapping("/categories")
  @Operation(
      summary = "Get categories",
      description = "Returns a list of all categories"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Data received successfully",
          content = @Content(
              schema = @Schema(implementation = String.class)
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid request"
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized"
      )
  })
  public ResponseEntity<List<CategoriesAdminDTO>> getCategories(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      ) @RequestHeader("Authorization") String authorizationHeader) {

    if (!authorizationHeader.startsWith("Bearer ")) {
      System.out.println("Invalid Authorization header");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String token = authorizationHeader.substring(7);
    int user_id = Integer.parseInt(jwt.extractIdFromJwt(token));
    Optional<User> user = userRepo.findById(user_id);

    if (user.isEmpty()) {
      System.out.println("No user found with given id:" + user_id);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    User adminUser = user.get();
    if(!adminUser.isAdmin())
    {
      System.out.println("User is not an admin");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    List<Categories> categories = categoriesRepo.findAll();
    if (categories.isEmpty()) {
      System.out.println("No users found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    ArrayList<CategoriesAdminDTO> categoryDTOList = new ArrayList<>();
    for (Categories c : categories) {
      CategoriesAdminDTO categoryDTO = new CategoriesAdminDTO(c, listingRepo.findAllByCategory(c).size());
      categoryDTOList.add(categoryDTO);
    }
    return ResponseEntity.ok(categoryDTOList);
  }

}
