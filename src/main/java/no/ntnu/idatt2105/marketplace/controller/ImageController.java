package no.ntnu.idatt2105.marketplace.controller;

import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.repo.ImageRepo;
import no.ntnu.idatt2105.marketplace.service.images.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {

  @Autowired
  private ImageRepo imageRepo;

  @Autowired
  private ImagesService imagesService;

  @PostMapping("/addProfilePicture")
  public ResponseEntity<?> addProfilePicture(@RequestParam("image") MultipartFile file) {
    try {
      // create a db image entry from the image in the request
      Images profilePicture = imagesService.createDBImageFromRequest(file, "/profile_pictures");

      // Save the image entity in the database
      Images savedImage = imageRepo.save(profilePicture);

      return ResponseEntity.ok(savedImage);

    } catch (Exception e) {
      return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
    }
  }

// TODO: implement
//  @PostMapping("/addListingImage")
//  public ResponseEntity<?> addListingImage() {}
}
