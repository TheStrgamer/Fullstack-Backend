package no.ntnu.idatt2105.marketplace.service.images;

import no.ntnu.idatt2105.marketplace.controller.ListingController;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.repo.ImagesRepo;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

  private static final Logger LOGGER = LogManager.getLogger(ListingController.class);



  /**
   * Saves images for a listing in the upload directory and in the database
   * @param images {@link List} of {@link MultipartFile} containing the images uploaded
   * @param listingId the id of the listing
   * @return Returns the list of saved images as {@link Images}
   * @throws Exception if no listing with the given id is found
   */
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



  /**
   * Deletes an image from the database
   * @param image_id the is of the image to be deleted
   */
  public void deleteImage(int image_id) {
    imagesRepo.deleteById(image_id);
  }



  /**
   * Deletes an image from the database
   * @param image the {@link Images} instance of the image to be deleted
   */
  public void deleteImage(Images image) {
    imagesRepo.deleteById(image.getId());
  }



  /**
   * Saves an image file in the database
   * @param file the {@link MultipartFile} file to be saved
   * @return the created image as a {@link Images}
   * @throws Exception if provided {@link MultipartFile} is empty
   */
  public Images saveUserProfilePicture(MultipartFile file) throws Exception {
    Images image = createDBImageFromRequest(file, "profilePictures");
    return imagesRepo.save(image);
  }



  /**
   * Creates {@link Images} from a {@link MultipartFile} containing the uploaded image
   * @param file the images as a {@link MultipartFile}
   * @param subDirectory the subdirectory that the images is uploaded to
   * @return the created image as {@link Images}
   * @throws Exception if provided {@link MultipartFile} is empty
   */
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
      LOGGER.info("Saving the file: " + filename + "in: \n" + path);
      saveImageInFiles(path, file);

      // set the filepath in the model
      img.setFilepath_to_image("/images/" + subDirectory + "/" + filename);


      return img;
    } catch (IOException e) {
      throw new Exception("Error Creating and saving file");
    }
  }



  /**
   * Saves an{@link MultipartFile} image in the upload directory in the local filesystem
   * @param path the path to the upload directory
   * @param file the image as an {@link MultipartFile}
   * @throws IOException if there occurs an error while saving the image to the filesystem
   */
  public void saveImageInFiles(Path path, MultipartFile file) throws IOException {
    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
  }



  /**
   * Gets the url of the image with the given id
   * @param id the id of the image
   * @return the url of the image without the base-part of the backend server url
   */
  public String getImageURLFromId(int id) {
    Optional<Images> img = imagesRepo.findById(id);
    if (img.isEmpty()) return "";

    return img.get().getFilepath_to_image();
  }



  /**
   * gets the {@link Images} representing the default user image
   * @return {@link Images} the default user image
   */
  public Images getDefaultUserImage() {
    return new Images(0, "");
  }



  /**
   * Deletes an image from the upload directory where the images are stored
   * @param imageId the id of the image
   */
  public void deleteImageFromFile(int imageId) {
    Optional<Images> imageOpt = imagesRepo.findById(imageId);
    if (imageOpt.isPresent()) {
      String filepath = uploadDir + imageOpt.get().getFilepath_to_image().substring(8);
      try {
        Files.deleteIfExists(Paths.get(filepath));
        LOGGER.info("Deleted file: " + filepath);
      } catch (IOException e) {
        System.err.println("Failed to delete file: " + filepath);
        e.printStackTrace();
      }
    }
  }



  /**
   * Getter for the upload dir
   * @return the value of the upload dir variable
   */
  public String getUploadDir() {
    return uploadDir;
  }



  /**
   * Setter for the upload dir
   * @param uploadDir the new value of the upload dir
   */
  public void setUploadDir(String uploadDir) {
    this.uploadDir = uploadDir;
  }



//  @Transactional
//  public void removeImagesFromListing(int listingId, List<Integer> imageIdsToRemove) throws Exception {
//    Listing listing = listingRepo.findById(listingId)
//            .orElseThrow(() -> new Exception("Listing not found"));
//
//    List<Images> updatedImages = new ArrayList<>();
//
//    for (Images image : listing.getImages()) {
//      if (imageIdsToRemove.contains(image.getId())) {
//        deleteImageFromFile(image.getId()); // delete from file
//      } else {
//        updatedImages.add(image); // keep this one
//      }
//    }
//
//    listing.setImages(updatedImages); // orphanRemoval triggers delete
//    listingRepo.saveAndFlush(listing); // must persist to trigger orphan logic
//  }
}
