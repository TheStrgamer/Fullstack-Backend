package no.ntnu.idatt2105.marketplace.dto.listing;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) for transferring condition data related to listings.
 * Typically used when sending or receiving condition details in API responses or forms.
 */
@Schema(description = "DTO for transferring a condition entry associated with listings")
public class ConditionsDTO {

  /** Unique identifier for the condition (e.g., 1 = New, 2 = Used) */
  @Schema(description = "The ID of the condition", example = "1")
  private int id;

  /** Human-readable name of the condition */
  @Schema(description = "The name of the condition", example = "Used - Good")
  private String name;

  /**
   * Retrieves the ID of the condition.
   *
   * @return the condition ID
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the condition.
   *
   * @param id the unique condition ID to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Retrieves the name of the condition.
   *
   * @return the condition name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the condition.
   *
   * @param name the condition name to set (e.g., "New", "Used - Like New")
   */
  public void setName(String name) {
    this.name = name;
  }
}