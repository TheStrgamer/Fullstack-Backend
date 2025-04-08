package no.ntnu.idatt2105.marketplace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.repo.ImagesRepo;
import no.ntnu.idatt2105.marketplace.service.images.ImagesService;
import no.ntnu.idatt2105.marketplace.service.security.JWT_token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
@Tag(name = "Image API", description = "Operations related to Images")
public class ImageController {

  @Autowired
  private ImagesRepo imagesRepo;


  @Autowired
  private ImagesService imagesService;

  JWT_token jwt;

  @PostMapping("/upload-profile-image")
  @Operation(
          summary = "Upload a profile Picture",
          description = "Uploads and stores a profile picture for a user"
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "Successfully uploaded image"
          ),
          @ApiResponse(
                  responseCode = "500",
                  description = "Failed to upload image"
          )
  })
  public ResponseEntity<?> uploadProfileImage(
          @Parameter(
                  name = "Authorization",
                  description = "Bearer token in the format `Bearer <JWT>`",
                  required = true,
                  example = "Bearer eyJhbGciOiJIUzI1N.iIsInR5cCI6IkpXVCJ9..."
          ) @RequestHeader("Authorization") String authorizationHeader,
          @Parameter(
                  description = "The profile picture file to upload",
                  required = true,
                  content = @Content(
                          mediaType = "multipart/form-data",
                          schema = @Schema(type = "string", format = "binary")
                  )
          ) @RequestParam("image") MultipartFile file) {
    try {
      if (!authorizationHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      String sessionToken = authorizationHeader.substring(7);
      jwt.validateJwtToken(sessionToken);

      String requesterId = jwt.extractIdFromJwt(sessionToken);
      // create a db image entry from the image in the request
      Images profilePicture = imagesService.createDBImageFromRequest(file, "/profile_pictures");

      // Save the image entity in the database
      Images savedImage = imagesRepo.save(profilePicture);

      return ResponseEntity.ok(savedImage);

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
    }
  }

  @GetMapping("/get{id}")
  public ResponseEntity<?> getImageFrmId(@RequestParam int id) {
    return ResponseEntity.ok(imagesService.getImageURLFromId(id));
  }

// TODO: implement
//  @PostMapping("/addListingImage")
//  public ResponseEntity<?> addListingImage() {}
}
