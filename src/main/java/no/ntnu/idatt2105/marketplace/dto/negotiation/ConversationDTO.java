package no.ntnu.idatt2105.marketplace.dto.negotiation;

import java.util.List;
import no.ntnu.idatt2105.marketplace.model.negotiation.Message;

public class ConversationDTO {

  private int id;
  private String other_user_picture;
  private String other_user_name;
  private String last_message;
  private String last_update;
  private List<Message> messages;
  private int status;

  public ConversationDTO(int id, String other_user_picture, String other_user_name,
      String last_message,
      String last_update, List<Message> messages, int status) {
    this.id = id;
    this.other_user_picture = other_user_picture;
    this.other_user_name = other_user_name;
    this.last_message = last_message;
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

  public List<Message> getMessages() {
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

  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
