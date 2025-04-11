package no.ntnu.idatt2105.marketplace.dto.other;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProfileImageUpload {

  private String email;
  private List<MultipartFile> images;

  public List<MultipartFile> getImages() {
    return images;
  }

  public void setImages(List<MultipartFile> images) {
    this.images = images;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}

