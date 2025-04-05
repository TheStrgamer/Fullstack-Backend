package no.ntnu.idatt2105.marketplace.service.images;

import no.ntnu.idatt2105.marketplace.model.other.Images;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImagesService {

  @Value("${app.upload.dir}")
  private String uploadDir;


  public Images createDBImageFromRequest(MultipartFile file, String subDirectory) throws Exception {
    
    Images img = new Images();
    if (file.isEmpty()) {
      throw new Exception("Provided file is empty");
    }
    try {
      String finalDirectory = uploadDir + subDirectory;
      // Ensure the directory exists
      Files.createDirectories(Paths.get(finalDirectory));

      // Generate unique filename
      String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();

      // Save the file
      Path path = Paths.get(finalDirectory + filename);
      saveImageInFiles(path, file);

      // set the filepath in the model
      img.setFilepath_to_image("images/" + filename);

      return img;
    } catch (IOException e) {
      throw new Exception("Error Creating and saving file");
    }
  }

  public void saveImageInFiles(Path path, MultipartFile file) throws IOException {
    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
  }

  public Images getDefaultUserImage() {
    return new Images(0, "images/default_user.png");
  }
}
