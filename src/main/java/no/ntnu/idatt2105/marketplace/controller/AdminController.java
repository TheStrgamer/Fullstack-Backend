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
import no.ntnu.idatt2105.marketplace.dto.admin.CategoriesUploadDTO;
import no.ntnu.idatt2105.marketplace.dto.admin.ListingAdminDTO;
import no.ntnu.idatt2105.marketplace.dto.admin.UserAdminDTO;
import no.ntnu.idatt2105.marketplace.dto.admin.UserUploadAdminDTO;

import no.ntnu.idatt2105.marketplace.exception.UserNotAdminException;
import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.Role;
import no.ntnu.idatt2105.marketplace.repo.CategoriesRepo;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import no.ntnu.idatt2105.marketplace.repo.RoleRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.ListingService;
import no.ntnu.idatt2105.marketplace.service.images.ImagesService;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import no.ntnu.idatt2105.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.service.user.AdminService;

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
  private RoleRepo roleRepo;

  @Autowired
  private UserService userService;

  @Autowired
  private ListingService listingService;

  @Autowired
  private ImagesService imagesService;

  @Autowired
  private JWT_token jwt;

  @Autowired
  private AdminService adminService;


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
    try {
      adminService.validateAdminPrivileges(authorizationHeader);
    } catch (UserNotAdminException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    try {
      adminService.validateAdminPrivileges(authorizationHeader);
    } catch (UserNotAdminException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

  @GetMapping("/users/{id}")
  @Operation(
      summary = "Get specific user",
      description = "Returns the user with the given ID including their listings count"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "User data received successfully",
          content = @Content(
              schema = @Schema(implementation = UserAdminDTO.class)
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid request"
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "User not found"
      )
  })
  public ResponseEntity<UserAdminDTO> getUserById(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      ) @RequestHeader("Authorization") String authorizationHeader,
      @Parameter(description = "ID of the user to retrieve", required = true)
      @PathVariable String id) {

    try {
      adminService.validateAdminPrivileges(authorizationHeader);
    } catch (UserNotAdminException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    Optional<User> user = userRepo.findById(Integer.parseInt(id));
    if (user.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    UserAdminDTO userDTO = new UserAdminDTO(
        user.get(),
        userService.getListingCount(user.get())
    );

    return ResponseEntity.ok(userDTO);
  }

  @DeleteMapping("/users/delete/{id}")
  @Operation(
      summary = "Delete user",
      description = "Deletes a user by ID if the request is authorized and user is not deleting themselves"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "User deleted successfully",
          content = @Content(schema = @Schema(implementation = String.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid request"
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized"
      ),
      @ApiResponse(
          responseCode = "403",
          description = "Forbidden"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "User not found"
      )
  })
  public ResponseEntity<String> deleteUser(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
      )
      @RequestHeader("Authorization") String authorizationHeader,

      @Parameter(description = "ID of the user to delete", required = true)
      @PathVariable String id
  ) {
    try {
      adminService.validateAdminPrivileges(authorizationHeader);
    } catch (UserNotAdminException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }

    Optional<User> userToDelete = userRepo.findById(Integer.parseInt(id));
    if (userToDelete.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found");
    }
    int requestingId = Integer.parseInt(jwt.extractIdFromJwt(authorizationHeader.substring(7)));
    Optional<User> requestingUser = userRepo.findById(requestingId);
    if (requestingUser.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requesting user not found");
    }
    if (userToDelete.get() == requestingUser.get()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admins cannot delete their own account");
    }
    List<Listing> listingsToDelete = listingRepo.findAllByCreator(userToDelete.get());
    listingRepo.deleteAll(listingsToDelete);
    userRepo.delete(userToDelete.get());
    return ResponseEntity.ok("User " + userToDelete.get().getEmail() + " deleted successfully");
  }

  @PutMapping("/users/update/{id}")
  @Operation(
      summary = "Update user details",
      description = "Allows an admin to update user information"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User updated successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  public ResponseEntity<String> updateUser(
      @PathVariable String id,
      @RequestBody UserUploadAdminDTO userUpdateDTO,
      @RequestHeader("Authorization") String authorizationHeader) {

    try {
      adminService.validateAdminPrivileges(authorizationHeader);
    } catch (UserNotAdminException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }

    Optional<User> optionalUser = userRepo.findById(Integer.parseInt(id));
    if (optionalUser.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    User user = optionalUser.get();
    user.setFirstname(userUpdateDTO.getFirstname());
    user.setSurname(userUpdateDTO.getSurname());
    user.setEmail(userUpdateDTO.getEmail());
    user.setPhonenumber(userUpdateDTO.getPhonenumber());
    Optional<Role> role = roleRepo.findByName(userUpdateDTO.getRole());
    if (role.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role not found");
    }
    user.setRole(role.get());

    userRepo.save(user);
    return ResponseEntity.ok("User updated successfully");
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
    try {
      adminService.validateAdminPrivileges(authorizationHeader);
    } catch (UserNotAdminException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    List<Listing> listings = listingRepo.findAll();
    if (listings.isEmpty()) {
      System.out.println("No listings found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    ArrayList<ListingAdminDTO> listingDTOList = new ArrayList<>();
    for (Listing l : listings) {
      ListingAdminDTO listingDTO = new ListingAdminDTO(l);
      listingDTOList.add(listingDTO);
    }
    return ResponseEntity.ok(listingDTOList);
  }

  @DeleteMapping("/listings/delete/{id}")
  @Operation(
      summary = "Delete a listing",
      description = "Allows an admin to delete a listing by its ID"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Listing deleted successfully"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "404", description = "Listing not found")
  })
  public ResponseEntity<String> deleteListing(
      @PathVariable String id,
      @RequestHeader("Authorization") String authorizationHeader) {

    try {
      adminService.validateAdminPrivileges(authorizationHeader);
    } catch (UserNotAdminException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }

    Optional<Listing> listingOpt = listingRepo.findById(Integer.parseInt(id));
    if (listingOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing not found");
    }
    Listing listing = listingOpt.get();
    listingService.deleteListing(listing.getId());
    return ResponseEntity.ok("Listing deleted successfully");
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

    try {
      adminService.validateAdminPrivileges(authorizationHeader);
    } catch (UserNotAdminException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    List<Categories> categories = categoriesRepo.findAll();
    if (categories.isEmpty()) {
      System.out.println("No category found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    ArrayList<CategoriesAdminDTO> categoryDTOList = new ArrayList<>();
    for (Categories c : categories) {
      CategoriesAdminDTO categoryDTO = new CategoriesAdminDTO(c, listingRepo.findAllByCategory(c).size());
      categoryDTOList.add(categoryDTO);
    }
    return ResponseEntity.ok(categoryDTOList);
  }


  @GetMapping("/categories/{id}")
  @Operation(
      summary = "Get specific category",
      description = "Returns the category with the given id"
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
  public ResponseEntity<CategoriesAdminDTO> getCategory(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      ) @PathVariable String id,
      @RequestHeader("Authorization") String authorizationHeader) {

    try {
      adminService.validateAdminPrivileges(authorizationHeader);
    } catch (UserNotAdminException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    Optional<Categories> category = categoriesRepo.findById(Integer.parseInt(id));
    if (category.isEmpty()) {
      System.out.println("No category found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    CategoriesAdminDTO categoryDTO = new CategoriesAdminDTO(category.get(), listingRepo.findAllByCategory(category.get()).size());

    return ResponseEntity.ok(categoryDTO);
  }

  @PostMapping("/categories/add")
  @Operation(
      summary = "Add categories",
      description = "Post request to add a category"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Category created successfully",
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
  public ResponseEntity<Boolean> addCategory(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      )
      @RequestBody CategoriesUploadDTO category,
      @RequestHeader("Authorization") String authorizationHeader) {

    try {
      adminService.validateAdminPrivileges(authorizationHeader);
    } catch (UserNotAdminException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    String name = category.getName();
    boolean nameExists = categoriesRepo.findByName(name).isPresent();
    if (nameExists) {
      System.out.println("Category with name " + name + " already exists");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    String description = category.getDescription();
    Categories newCategory = new Categories(name, description, null);
    categoriesRepo.save(newCategory);
    System.out.println("Category " + name + " created successfully");
    return ResponseEntity.ok(true);
  }


  @PutMapping("/categories/update/{id}")
  @Operation(
      summary = "Update a category",
      description = "Post request to update a category"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Category updated successfully",
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
  public ResponseEntity<Boolean> updateCategory(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      )
      @PathVariable String id,
      @RequestBody CategoriesUploadDTO category,
      @RequestHeader("Authorization") String authorizationHeader) {
    try {
      adminService.validateAdminPrivileges(authorizationHeader);
    } catch (UserNotAdminException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    Optional<Categories> foundCategory = categoriesRepo.findById(Integer.parseInt(id));
    if (foundCategory.isEmpty()) {
      System.out.println("No category with id " + id + " found");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    String newName = category.getName();
    if (categoriesRepo.findByName(newName).isPresent() && !foundCategory.get().getName().equals(newName)) {
      System.out.println("Category with name " + newName + " already exists");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    String newDescription = category.getDescription();
    Categories updatedCategory = foundCategory.get();
    if (newDescription != null) {
      updatedCategory.setDescription(newDescription);
    }
    if (newName != null) {
      updatedCategory.setName(newName);
    }
    categoriesRepo.save(updatedCategory);
    return ResponseEntity.ok(true);
  }

  @DeleteMapping("/categories/delete/{id}")
  @Operation(
      summary = "Delete categories",
      description = "Post request to delete a category"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Category deleted successfully",
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
  public ResponseEntity<String> deleteCategory(
      @Parameter(
          name = "Authorization",
          description = "Bearer token in the format `Bearer <JWT>`",
          required = true,
          example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
      )
      @PathVariable String id,
      @RequestHeader("Authorization") String authorizationHeader) {
    try {
      adminService.validateAdminPrivileges(authorizationHeader);
    } catch (UserNotAdminException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }
    Optional<Categories> category = categoriesRepo.findById(Integer.parseInt(id));
    if (category.isEmpty()) {
      System.out.println("Category with id " + id + " not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with id " + id + " not found");
    }
    Categories cat = category.get();
    if (cat.getName().equals("Other")) {
      System.out.println("Cannot delete category 'Other'");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Cannot delete category 'Other'");
    }
    List<Listing> listings = listingRepo.findAllByCategory(cat);
    Categories defaultCategory = categoriesRepo.findByName("Other").orElse(null);
    for (Listing l : listings) {
      l.setCategory(defaultCategory);
      listingRepo.save(l);
    }
    categoriesRepo.delete(cat);
    System.out.println("Category " + cat.getName() + " deleted successfully");
    return ResponseEntity.ok("Category " + cat.getName() + " deleted successfully");
  }

}
