package no.ntnu.idatt2105.marketplace.dto.admin;

public class CategoriesUploadDTO {
    private String name;
    private String description;

    public CategoriesUploadDTO() {
    }

    public CategoriesUploadDTO(String name, String description) {
        this.name = name;
        this.description = description;
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

}
