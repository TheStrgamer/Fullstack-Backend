package no.ntnu.idatt2105.marketplace.dto.negotiation;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import no.ntnu.idatt2105.marketplace.model.negotiation.Message;

@Schema(description = "Data transfer object representing a conversation in a negotiation")
public class ConversationDTO {

  @Schema(description = "Unique ID of the conversation", example = "1")
  private int id;
  @Schema(description = "URL of the other user's profile picture", example = "/profile.jpg")
  private String other_user_picture;
  @Schema(description = "Name of the other user", example = "Urek Mazino")
  private String other_user_name;
  @Schema(description = "Indicates if the user is the seller", example = "true")
  private boolean amISeller;
  @Schema(description = "Title of the listing", example = "Used bicycle for sale")
  private String listingTitle;
  @Schema(description = "Last message in the conversation", example = "Hello, how are you?")
  private String last_message;
  @Schema(description = "Date of the last update in the conversation", example = "2023-10-01T12:00:00Z")
  private String last_update;
  @Schema(description = "List of messages in the conversation")
  private List<MessageDTO> messages;
  @Schema(description = "Status of the conversation", example = "1")
  private int status;

  public ConversationDTO(int id, String other_user_picture, String other_user_name,
      String last_update, List<MessageDTO> messages, int status, boolean amISeller,
      String listingTitle) {
    this.id = id;
    this.other_user_picture = other_user_picture;
    this.other_user_name = other_user_name;
    this.amISeller = amISeller;
    this.listingTitle = listingTitle;
    this.last_update = last_update;
    this.messages = messages;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public String getOther_user_picture() {
    return other_user_picture;
  }

  public String getOther_user_name() {
    return other_user_name;
  }

  public String getLast_message() {
    return last_message;
  }

  public String getLast_update() {
    return last_update;
  }

  public List<MessageDTO> getMessages() {
    return messages;
  }

  public int getStatus() {
    return status;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setOther_user_picture(String other_user_picture) {
    this.other_user_picture = other_user_picture;
  }

  public void setOther_user_name(String other_user_name) {
    this.other_user_name = other_user_name;
  }

  public void setLast_message(String last_message) {
    this.last_message = last_message;
  }

  public void setLast_update(String last_update) {
    this.last_update = last_update;
  }

  public void setMessages(List<MessageDTO> messages) {
    this.messages = messages;
  }

  public void setStatus(int status) {
    this.status = status;
  }
  public boolean isAmISeller() { return amISeller; }
  public void setAmISeller(boolean amISeller) { this.amISeller = amISeller; }
  public String getListingTitle() { return listingTitle; }
  public void setListingTitle(String listingTitle) { this.listingTitle = listingTitle; }

  @Override
  public String toString() {
    return "ConversationDTO{" +
        "id=" + id +
        ", other_user_picture='" + other_user_picture + '\'' +
        ", other_user_name='" + other_user_name + '\'' +
        ", last_message='" + last_message + '\'' +
        ", last_update='" + last_update + '\'' +
        ", messages=" + messages +
        ", status=" + status +
        '}';
  }
}
