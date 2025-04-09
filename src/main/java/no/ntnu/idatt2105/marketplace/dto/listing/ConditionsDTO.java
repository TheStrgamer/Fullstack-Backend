package no.ntnu.idatt2105.marketplace.dto.listing;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for transferring a categories entry")
public class ConditionsDTO {
    @Schema(description = "The id of the condition")
    private int id;

    @Schema(description = "The name of the condition")
    private String name;


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
}
