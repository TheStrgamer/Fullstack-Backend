package no.ntnu.idatt2105.marketplace.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) used by administrators to upload or create new categories.
 * Includes the category's name and description.
 *
 * This DTO is typically used in incoming API requests when a new category
 * is created or an existing one is updated.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 */
@Schema(description = "DTO used for uploading or creating a new category. Contains name and description.")
public class CategoriesUploadDTO {

    /**
     * The name of the category to be created.
     */
    @Schema(description = "The name of the category", example = "Electronics", required = true)
    private String name;

    /**
     * A short description of the category.
     */
    @Schema(description = "A short description of the category", example = "Devices, gadgets and more", required = true)
    private String description;

    /**
     * Default constructor for serialization/deserialization.
     *
     * @since 1.0
     */
    public CategoriesUploadDTO() {
    }

    /**
     * Constructs a new {@code CategoriesUploadDTO} with the specified name and description.
     *
     * @param name        the name of the category
     * @param description a brief description of the category
     * @since 1.0
     */
    public CategoriesUploadDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the name of the category.
     *
     * @return the category name
     * @since 1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category.
     *
     * @param name the category name
     * @since 1.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the category.
     *
     * @return the category description
     * @since 1.0
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the category.
     *
     * @param description the category description
     * @since 1.0
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
