package no.ntnu.idatt2105.marketplace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idatt2105.marketplace.dto.listing.ListingImagesUpload;
import no.ntnu.idatt2105.marketplace.dto.other.ProfileImageUpload;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.repo.ImagesRepo;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import no.ntnu.idatt2105.marketplace.service.images.ImagesService;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@Tag(name = "Image API", description = "Operations related to Images")
public class ImageController {

  @Autowired
  private ImagesRepo imagesRepo;


  @Autowired
  private ImagesService imagesService;

  JWT_token jwt;

  private static final Logger LOGGER = LogManager.getLogger(ListingController.class);

  @PostMapping("/upload/profile/image")
  @Operation(
          summary = "Upload a profile Picture",
          description = "Uploads and stores a profile picture for a user"
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully uploaded image",
                  content = @Content(schema = @Schema(implementation = String.class))),
          @ApiResponse(responseCode = "500", description = "Failed to upload image")
  })
  public ResponseEntity<?> uploadProfileImage(
          @RequestHeader("Authorization") String authorizationHeader,
          @ModelAttribute ProfileImageUpload images) {
    try {
      if (!authorizationHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
      System.out.println("TOKEN NOT NULL");

      if (images.getImages() == null || images.getImages().isEmpty()) {
        System.out.println("NO IMAGE GIVEN");
        return ResponseEntity.badRequest().body("No image file provided");
      }


      System.out.println("SAVE USER PROFILE");

      Images profilePicture = imagesService.saveUserProfilePicture(images.getImages().getFirst(), images.getEmail());
      System.out.println("USER PROFILE SAVED");

      return ResponseEntity.ok(profilePicture.getFilepath_to_image());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Failed to upload image: " + e.getMessage());
    }
  }


  @GetMapping("/get{id}")
  @Operation(
          summary = "Get image url",
          description = "Returns the image url from a provided image id"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "404",
                  description = "Image not found"
          ),
          @ApiResponse(
                  responseCode = "200",
                  description = "Image found",
                  content = @Content(
                          schema = @Schema(implementation = String.class)
                  )
          )
  })
  public ResponseEntity<?> getImageFromId(@PathVariable int id) {
    String url = imagesService.getImageURLFromId(id);
    if (url.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(url);
  }


  @PostMapping("/uploadListing")
  @Operation(
          summary = "Upload images for a listing",
          description = "Allows an authenticated user to upload one or more images for a listing using a JWT token."
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "Images successfully uploaded",
                  content = @Content(
                          schema = @Schema(implementation = String.class)
                  )
          ),
          @ApiResponse(
                  responseCode = "401",
                  description = "Unauthorized — invalid or missing token"
          ),
          @ApiResponse(
                  responseCode = "500",
                  description = "Internal server error during upload"
          )
  })
  public ResponseEntity<?> uploadImages(
          @Parameter(
                  name = "Authorization",
                  description = "Bearer token in the format `Bearer <JWT>`",
                  required = true,
                  example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
          )
          @RequestHeader("Authorization") String authorizationHeader,
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                  description = "Object containing the listing ID and images to upload",
                  required = true,
                  content = @Content(
                          mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                          schema = @Schema(implementation = ListingImagesUpload.class)
                  )
          )
          @ModelAttribute ListingImagesUpload images
  ) {
    try {
      if (!authorizationHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      imagesService.saveListingImages(images.getImages(), images.getId());
      return ResponseEntity.ok("Successfully uploaded images");
    } catch (Exception e) {
      LOGGER.error("Error: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}
