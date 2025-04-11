package no.ntnu.idatt2105.marketplace.dto.negotiation;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.service.images.ImagesService;

@Schema(description = "Data transfer object representing a negotiation chat")
public class NegotiationChatsDTO {
  @Schema(description = "Unique ID of the negotiation chat", example = "1")
  private int id;
  @Schema(description = "URL of the other user's profile picture", example = "/profile.jpg")
  private String other_user_picture;
  @Schema(description = "Name of the other user", example = "Urek Mazino")
  private String other_user_name;
  @Schema(description = "Last message in the negotiation chat", example = "Hello, how are you?")
  private String last_message;
  @Schema(description = "Date of the last update in the negotiation chat", example = "2023-10-01T12:00:00Z")
  private Date last_update;

  public NegotiationChatsDTO(int id, Images other_user_picture, String other_user_name, String last_message,
      Date last_update) {
    this.id = id;
    this.other_user_picture = other_user_picture.getFilepath_to_image() != null ? other_user_picture.getFilepath_to_image() : "";
    this.other_user_name = other_user_name;
    this.last_message = last_message;
    this.last_update = last_update;
  }
  public NegotiationChatsDTO(int id, User other_user, String last_message,
      Date last_update) {
    this.id = id;
    this.other_user_picture = other_user.getProfile_picture() != null ? other_user.getProfile_picture().getFilepath_to_image() : "";
    this.other_user_name = other_user.getFirstname() + " " + other_user.getSurname();
    this.last_message = last_message;
    this.last_update = last_update;
  }
  public int getId() {return id;}
  public String getOther_user_picture() {return other_user_picture;}
  public String getOther_user_name() {return other_user_name;}
  public String getLast_message() {return last_message;}
  public Date getLast_update() {return last_update;}
  public void setId(int id) {this.id = id;}
  public void setOther_user_picture(String other_user_picture) {this.other_user_picture = other_user_picture;}
  public void setOther_user_name(String other_user_name) {this.other_user_name = other_user_name;}
  public void setLast_message(String last_message) {this.last_message = last_message;}
  public void setLast_update(Date last_update) {this.last_update = last_update;}







}
