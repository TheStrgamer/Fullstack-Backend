package no.ntnu.idatt2105.marketplace.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.listing.Categories;

@Schema(description = "Gives all information about a user relevant for admin operations")

public class CategoriesAdminDTO {

  @Schema(description = "Category id", example = "123456789")
  private int id;

  @Schema(description = "Category name", example = "Foo")
  private String name;

  @Schema(description = "Category description", example = "Boo")
  private String description;

  @Schema(description = "Listings within this category", example = "5")
  private int listings;

  // getters
  public int getId() { return id; }
  public String getName() { return name; }
  public String getDescription() { return description; }
  public int getListings() { return listings; }

  public CategoriesAdminDTO(int id, String name, String description, int listings) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.listings = listings;
  }

  public CategoriesAdminDTO(Categories category, int listings) {
    this.id = category.getId();
    this.name = category.getName();
    this.description = category.getDescription();
    this.listings = listings;
  }

}
