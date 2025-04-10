package no.ntnu.idatt2105.marketplace.dto.listing;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ListingImagesUpload {

  @Schema(description = "The id of the listing", example = "123")
  private int id;

  @Schema(
          description = "The list of image files to upload",
          type = "array",
          implementation = MultipartFile.class
  )
  private List<MultipartFile> images;

  // Getters and setters (required for @ModelAttribute binding)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<MultipartFile> getImages() {
    return images;
  }

  public void setImages(List<MultipartFile> images) {
    this.images = images;
  }
}
