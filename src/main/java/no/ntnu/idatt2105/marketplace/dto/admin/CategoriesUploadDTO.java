package no.ntnu.idatt2105.marketplace.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object used for uploading or updating a category from the admin interface.
 * This includes the category's name and description.
 */
@Schema(description="DTO for uploading a category entry")
public class CategoriesUploadDTO {

    /** The name of the category. */
    @Schema(description = "Name of the category", example = "Glasses")
    private String name;

    /** The description of the category. */
    @Schema(description = "Description of the category", example = "Sunglasses, etc.")
    private String description;

    /**
     * Default no-argument constructor for serialization/deserialization frameworks.
     */
    public CategoriesUploadDTO() {
    }

    /**
     * Constructs a new {@code CategoriesUploadDTO} with the specified name and description.
     *
     * @param name        The name of the category.
     * @param description The description of the category.
     */
    public CategoriesUploadDTO(String name, String description) {
        this.name = name;
        this.description = description;
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
     * Sets the name of the category.
     *
     * @param name The category name to set.
     */
    public void setName(String name) {
        this.name = name;
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
     * Sets the description of the category.
     *
     * @param description The category description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
