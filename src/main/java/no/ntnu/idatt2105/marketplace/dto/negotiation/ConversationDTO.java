package no.ntnu.idatt2105.marketplace.dto.negotiation;

import java.util.List;
import no.ntnu.idatt2105.marketplace.model.negotiation.Message;

public class ConversationDTO {

  private int id;
  private String other_user_picture;
  private String other_user_name;
  private boolean amISeller;
  private String listingTitle;
  private String last_message;
  private String last_update;
  private List<MessageDTO> messages;
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
