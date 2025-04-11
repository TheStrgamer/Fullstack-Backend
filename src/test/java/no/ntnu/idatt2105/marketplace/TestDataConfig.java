package no.ntnu.idatt2105.marketplace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import no.ntnu.idatt2105.marketplace.model.negotiation.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import no.ntnu.idatt2105.marketplace.repo.*;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.model.user.Role;
import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import no.ntnu.idatt2105.marketplace.model.listing.Condition;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.negotiation.Conversation;

@TestConfiguration
@Profile("test")
class TestDataConfig {

  @Bean
  public CommandLineRunner commandLineRunner(CategoriesRepo categoriesRepo,
      ConditionRepo conditionRepo, ConversationRepo conversationRepo, ImagesRepo imagesRepo,
      ListingRepo listingRepo, MessageRepo messageRepo, RoleRepo roleRepo, UserRepo userRepo) {

    return args -> {
      // Create test Images:
      Images images = new Images();
      images.setFilepath_to_image("test.jpg");
      images.setListing(null);
      imagesRepo.save(images);

      // Create test roles:
      Role role = new Role();
      role.setName("USER");
      roleRepo.save(role);

      // Create test users:
      User testUser = new User();
      testUser.setFirstname("Test");
      testUser.setSurname("User");
      testUser.setEmail("takenEmail@test.com");
      testUser.setPassword("taken");
      testUser.setPhonenumber("12345678");
      testUser.setProfile_picture(images);
      testUser.setRole(role);
      userRepo.save(testUser);

      // Create test categories:
      Categories category = new Categories();
      category.setName("Test Category");
      categoriesRepo.save(category);

      // Create test conditions:
      Condition condition = new Condition();
      condition.setName("Test Condition");
      conditionRepo.save(condition);

      // Create test listings:
      Date created_date;
      Date updated_date;
      try {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        created_date = simpleDateFormat.parse("2023-10-01");
        updated_date = simpleDateFormat.parse("2023-10-02");
      } catch (ParseException e) {
        throw new RuntimeException("Error parsing date", e);
      }

      Listing listing = new Listing();
      listing.setTitle("Test Listing");
      listing.setBrief_description("This is a test listing");
      listing.setFull_description("This is a full test listing");
      listing.setPrice(100);
      listing.setCondition(condition);
      listing.setCategory(category);
      listing.setCreator(testUser);
      listing.setSale_status(0);
      listing.setCreated_at(created_date);
      listing.setUpdated_at(updated_date);
      listing.setLatitude(100);
      listing.setLongitude(100);
      listing.setSize("test size");
      listingRepo.save(listing);

      // Create test conversations:
      Conversation conversation = new Conversation(testUser, listing);
      conversation.setStatus(0);
      conversation.updateDate();

      // Create test messages:
      Message message = new Message(testUser, conversation, "test message");
      messageRepo.save(message);

      // Add message to conversation and save:
      conversation.addMessage(message);
      conversationRepo.save(conversation);


    };
  }
}
