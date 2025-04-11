package no.ntnu.idatt2105.marketplace.service.images;

// model
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.other.Images;

// repo
import no.ntnu.idatt2105.marketplace.repo.ImagesRepo;
import no.ntnu.idatt2105.marketplace.repo.ListingRepo;

// JUint
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Mockito
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

// Java util
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.List;

public class ImagesServiceTest {

  @Mock
  private ImagesRepo imagesRepo;

  @Mock
  private ListingRepo listingRepo;

  @InjectMocks
  private ImagesService imagesService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    imagesService.setUploadDir("test/uploads/");
  }

  /**
   * Tests saving a user profile picture successfully.
   */
  @Test
  void saveUserProfilePicture_shouldSaveSuccessfully() throws Exception {
    MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "some-content".getBytes());
    Images image = new Images();
    image.setFilepath_to_image("/images/profilePictures/test.png");

    ImagesService spyService = spy(imagesService);
    doReturn(image).when(spyService).createDBImageFromRequest(any(), eq("profilePictures"));
    when(imagesRepo.save(any())).thenReturn(image);

    Images savedImage = spyService.saveUserProfilePicture(file);
    assertEquals("/images/profilePictures/test.png", savedImage.getFilepath_to_image());
  }

  /**
   * Tests saving listing images successfully.
   */
  @Test
  void saveListingImages_shouldSaveSuccessfully() throws Exception {
    MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "some-content".getBytes());
    Listing listing = new Listing();
    listing.setId(1);
    Images image = new Images();
    image.setFilepath_to_image("/images/listingImages/test.png");

    when(listingRepo.findById(1)).thenReturn(Optional.of(listing));
    when(imagesRepo.save(any())).thenReturn(image);

    ImagesService spyService = spy(imagesService);
    doReturn(image).when(spyService).createDBImageFromRequest(any(), eq("listingImages"));

    List<Images> images = spyService.saveListingImages(List.of(file), 1);
    assertEquals(1, images.size());
    assertEquals("/images/listingImages/test.png", images.get(0).getFilepath_to_image());
  }

  /**
   * Test that verifies image creation fails for an empty file.
   */
  @Test
  void createDBImageFromRequest_shouldThrowIfEmptyFile() {
    MockMultipartFile file = new MockMultipartFile("file", "", "image/png", new byte[0]);
    Exception exception = assertThrows(Exception.class, () ->
            imagesService.createDBImageFromRequest(file, "listingImages"));
    assertTrue(exception.getMessage().contains("Provided file is empty"));
  }

  /**
   * Test that verifies getDefaultUserImage returns an image with ID 0.
   */
  @Test
  void getDefaultUserImage_shouldReturnDefaultImage() {
    Images image = imagesService.getDefaultUserImage();
    assertEquals(0, image.getId());
    assertEquals("", image.getFilepath_to_image());
  }

  /**
   * Test that verifies image deletion from filesystem when the file exists.
   */
  @Test
  void deleteImageFromFile_shouldDeleteFileIfExists() throws IOException {
    Images image = new Images();
    image.setId(1);
    image.setFilepath_to_image("/images/listingImages/testfile.png");

    Path testPath = Path.of("test/uploads/listingImages/testfile.png");
    Files.createDirectories(testPath.getParent());
    Files.createFile(testPath);

    when(imagesRepo.findById(1)).thenReturn(Optional.of(image));

    imagesService.deleteImageFromFile(1);

    assertFalse(Files.exists(testPath));
  }

  /**
   * Test that verifies getImageURLFromId returns correct filepath.
   */
  @Test
  void getImageURLFromId_shouldReturnFilepath() {
    Images image = new Images();
    image.setId(1);
    image.setFilepath_to_image("/images/listingImages/test.png");

    when(imagesRepo.findById(1)).thenReturn(Optional.of(image));

    String url = imagesService.getImageURLFromId(1);
    assertEquals("/images/listingImages/test.png", url);
  }

  /**
   * Test that verifies getImageURLFromId returns empty string if image not found.
   */
  @Test
  void getImageURLFromId_shouldReturnEmptyIfNotFound() {
    when(imagesRepo.findById(999)).thenReturn(Optional.empty());
    String url = imagesService.getImageURLFromId(999);
    assertEquals("", url);
  }

  /**
   * Tests the setter, should set the upload dir
   */
  @Test
  void setUploadDir_shouldSetUploadDir() {
    String testUploadDir = "Test/dir";
    imagesService.setUploadDir(testUploadDir);
    assertEquals(testUploadDir, imagesService.getUploadDir());
  }

  /**
   * Tests the getter, should return the upload dir
   */
  @Test
  void getUploadDir_shouldReturnUploadDir() {
    String testUploadDir = "Test/dir";
    imagesService.setUploadDir(testUploadDir);

    String verifyDir = imagesService.getUploadDir();
    assertEquals(testUploadDir, verifyDir);
  }
}