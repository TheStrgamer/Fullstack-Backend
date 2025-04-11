package no.ntnu.idatt2105.marketplace.dto.listing;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.listing.Categories;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;

/**
 * Data Transfer Object (DTO) used for transferring category data between client and server.
 * This class represents a category entry in the marketplace, including optional reference
 * to a parent category for hierarchical organization.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 * @see Categories
 */
@Schema(description = "DTO for transferring a category entry")
public class CategoriesDTO {

  /** Unique identifier of the category */
  @Schema(description = "The ID of the category", example = "12")
  private int id;

  /** Name/title of the category (e.g., Electronics, Furniture) */
  @Schema(description = "The name of the category", example = "Electronics")
  private String name;

  /** Description providing additional details about the category */
  @Schema(description = "The description of the category", example = "Devices such as phones, laptops, etc.")
  private String description;

  /** ID of the parent category. If the category is a root category, this may be null or zero */
  @Schema(description = "The ID of the parent category. Is 0 or null if the category is the root category", example = "0")
  private int parent_category;

  /**
   * Returns the ID of the category.
   *
   * @return the category ID
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the category.
   *
   * @param id the unique identifier for this category
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Returns the name of the category.
   *
   * @return the name of the category
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the category.
   *
   * @param name the name/title of the category
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the description of the category.
   *
   * @return the description of the category
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the category.
   *
   * @param description a textual explanation of what the category includes
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Returns the ID of the parent category.
   *
   * @return the parent category ID
   */
  public int getParent_category() {
    return parent_category;
  }

  /**
   * Sets the ID of the parent category.
   *
   * @param parent_category ID of the parent category (if any)
   */
  public void setParent_category(int parent_category) {
    this.parent_category = parent_category;
  }
}