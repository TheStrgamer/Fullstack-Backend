package no.ntnu.idatt2105.marketplace.dto.listing;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Data Transfer Object used for uploading image files associated with a listing.
 * This class supports binding of form data using {@code @ModelAttribute}.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 */
@Schema(description = "")
public class ListingImagesUpload {

  /**
   * The ID of the listing for which images are being uploaded.
   */
  @Schema(description = "The id of the listing", example = "123")
  private int id;

  /**
   * The list of image files to be uploaded.
   */
  @Schema(
          description = "The list of image files to upload",
          type = "array",
          implementation = MultipartFile.class
  )
  private List<MultipartFile> images;

  /**
   * Gets the ID of the listing.
   *
   * @return the listing ID
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the listing.
   *
   * @param id the listing ID
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the list of uploaded image files.
   *
   * @return the list of {@link MultipartFile} representing the images
   */
  public List<MultipartFile> getImages() {
    return images;
  }

  /**
   * Sets the list of image files to upload.
   *
   * @param images the list of {@link MultipartFile} images
   */
  public void setImages(List<MultipartFile> images) {
    this.images = images;
  }
}