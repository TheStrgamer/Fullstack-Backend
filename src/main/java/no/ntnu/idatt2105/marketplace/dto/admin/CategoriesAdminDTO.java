package no.ntnu.idatt2105.marketplace.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.listing.Categories;

/**
 * Data Transfer Object (DTO) used by administrators to view category information.
 * This includes the category's ID, name, description, and the number of listings
 * that belong to the category.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 * @see Categories
 */
@Schema(description = "Gives all information about a user relevant for admin operations")
public class CategoriesAdminDTO {

  /**
   * The unique identifier of the category.
   */
  @Schema(description = "Category id", example = "123456789")
  private int id;

  /**
   * The name of the category.
   */
  @Schema(description = "Category name", example = "Foo")
  private String name;

  /**
   * A short description of the category.
   */
  @Schema(description = "Category description", example = "Boo")
  private String description;

  /**
   * Number of listings that currently exist in this category.
   */
  @Schema(description = "Listings within this category", example = "5")
  private int listings;

  /**
   * Returns the ID of the category.
   *
   * @return the category ID
   * @since 1.0
   */
  public int getId() { return id; }

  /**
   * Returns the name of the category.
   *
   * @return the category name
   * @since 1.0
   */
  public String getName() { return name; }

  /**
   * Returns the description of the category.
   *
   * @return the category description
   * @since 1.0
   */
  public String getDescription() { return description; }

  /**
   * Returns the number of listings in this category.
   *
   * @return the number of listings
   * @since 1.0
   */
  public int getListings() { return listings; }

  /**
   * Constructs a new {@code CategoriesAdminDTO} with all fields explicitly set.
   *
   * @param id          the ID of the category
   * @param name        the name of the category
   * @param description the description of the category
   * @param listings    the number of listings in this category
   * @since 1.0
   */
  public CategoriesAdminDTO(int id, String name, String description, int listings) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.listings = listings;
  }

  /**
   * Constructs a new {@code CategoriesAdminDTO} using a {@link Categories} object
   * and the number of listings in that category.
   *
   * @param category the category object
   * @param listings the number of listings in the category
   * @since 1.0
   * @see Categories
   */
  public CategoriesAdminDTO(Categories category, int listings) {
    this.id = category.getId();
    this.name = category.getName();
    this.description = category.getDescription();
    this.listings = listings;
  }
}
