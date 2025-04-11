package no.ntnu.idatt2105.marketplace.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.listing.Categories;

/**
 * Data Transfer Object (DTO) for exposing detailed category information relevant to admin operations.
 * This includes ID, name, description, and the number of listings in the category.
 */
@Schema(description = "Gives all information about a category relevant for admin operations")
public class CategoriesAdminDTO {

  /** The unique identifier of the category. */
  @Schema(description = "Category id", example = "123456789")
  private int id;

  /** The name of the category. */
  @Schema(description = "Category name", example = "Foo")
  private String name;

  /** The description of the category. */
  @Schema(description = "Category description", example = "Boo")
  private String description;

  /** The number of listings associated with this category. */
  @Schema(description = "Listings within this category", example = "5")
  private int listings;

  /**
   * Gets the ID of the category.
   *
   * @return The category ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the name of the category.
   *
   * @return The category name.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the description of the category.
   *
   * @return The category description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the number of listings in this category.
   *
   * @return The number of listings.
   */
  public int getListings() {
    return listings;
  }

  /**
   * Constructs a new {@code CategoriesAdminDTO} with specified values.
   *
   * @param id          The ID of the category.
   * @param name        The name of the category.
   * @param description The description of the category.
   * @param listings    The number of listings in this category.
   */
  public CategoriesAdminDTO(int id, String name, String description, int listings) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.listings = listings;
  }

  /**
   * Constructs a new {@code CategoriesAdminDTO} from a {@link Categories} entity and listing count.
   *
   * @param category The {@link Categories} entity to convert.
   * @param listings The number of listings in this category.
   */
  public CategoriesAdminDTO(Categories category, int listings) {
    this.id = category.getId();
    this.name = category.getName();
    this.description = category.getDescription();
    this.listings = listings;
  }
}
