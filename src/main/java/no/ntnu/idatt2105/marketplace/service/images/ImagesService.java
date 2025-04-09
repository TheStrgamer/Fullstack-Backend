package no.ntnu.idatt2105.marketplace.service.images;

import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.repo.ImagesRepo;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImagesService {

  @Value("${app.upload.dir}")
  private String uploadDir;

  @Autowired
  private ImagesRepo imagesRepo;

  @Autowired
  private ListingRepo listingRepo;


  public List<Images> saveListingImages(List<MultipartFile> images, int listingId) throws Exception {
    List<Images> createdImages = new ArrayList<>();
    if (images.isEmpty()) return null;

    Optional<Listing> listing = listingRepo.findById(listingId);
    if (listing.isEmpty()) {
      throw new Exception("Listing with id " + listingId + " not found");
    }

    for (MultipartFile file : images) {
      Images image = createDBImageFromRequest(file, "listingImages");
      image.setListing(listing.get());
      Images saved = imagesRepo.save(image);
      createdImages.add(saved);
    }

    return createdImages;
  }

  public void deleteImage(int image_id) {
    Optional<Images> img = imagesRepo.findById(image_id);
    imagesRepo.deleteById(image_id);
  }


  public Images saveUserProfilePicture(MultipartFile file) throws Exception {
    Images image = createDBImageFromRequest(file, "profilePictures");
    return imagesRepo.save(image);
  }

  public Images createDBImageFromRequest(MultipartFile file, String subDirectory) throws Exception {
    
    Images img = new Images();
    if (file.isEmpty()) {
      throw new Exception("Provided file is empty");
    }
    try {
      String finalDirectory = uploadDir + subDirectory + "/";
      // Ensure the directory exists
      Files.createDirectories(Paths.get(finalDirectory));

      // Generate unique filename
      String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      // Save the file
      Path path = Paths.get(finalDirectory + filename);
      System.out.println("Saving the file: " + filename + "in: \n" + path);
      saveImageInFiles(path, file);

      // set the filepath in the model
      img.setFilepath_to_image("/images/" + subDirectory + "/" + filename);


      return img;
    } catch (IOException e) {
      throw new Exception("Error Creating and saving file");
    }
  }

  public void saveImageInFiles(Path path, MultipartFile file) throws IOException {
    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
  }

  public String getImageURLFromId(int id) {
    Optional<Images> img = imagesRepo.findById(id);
    if (img.isEmpty()) return "";

    return img.get().getFilepath_to_image();
  }
  
  public Images getDefaultUserImage() {
    return new Images(0, "");
  }

  public void deleteImageFromFile(int imageId) {
    Optional<Images> imageOpt = imagesRepo.findById(imageId);
    if (imageOpt.isPresent()) {
      String filepath = imageOpt.get().getFilepath_to_image();
      try {
        Files.deleteIfExists(Paths.get(filepath));
        System.out.println("Deleted file: " + filepath);
      } catch (IOException e) {
        System.err.println("Failed to delete file: " + filepath);
        e.printStackTrace();
      }
    }
  }
}
