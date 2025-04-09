package no.ntnu.idatt2105.marketplace.dto.listing;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.idatt2105.marketplace.model.listing.Categories;

@Schema(description = "DTO for transferring a categories entry")
public class CategoriesDTO {

  @Schema(description = "The id of the category")
  private int id;

  @Schema(description = "The name of the category")
  private String name;

  @Schema(description = "The description of the category")
  private String description;

  @Schema(description = "The id of the parent category, is null if the category is the root category")
  private int parent_category;


  // getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getParent_category() {
    return parent_category;
  }

  public void setParent_category(int parent_category) {
    this.parent_category = parent_category;
  }
}
