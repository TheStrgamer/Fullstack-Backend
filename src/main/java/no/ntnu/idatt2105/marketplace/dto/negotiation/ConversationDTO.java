package no.ntnu.idatt2105.marketplace.dto.negotiation;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Data Transfer Object representing a conversation between users,
 * including basic metadata and the list of messages exchanged.
 */
public class ConversationDTO {

  /** Unique identifier for the conversation */
  @Schema(description = "Id of the conversation", example = "123")
  private int id;

  /** URL or path to the profile picture of the other user */
  @Schema(description = "The profile image of the other user as the local url", example = "/image/profileImage/otherProfile.png")
  private String other_user_picture;

  /** Full name of the other user in the conversation */
  @Schema(description = "Name of the other user", example = "Bob")
  private String other_user_name;

  /** The most recent message text in the conversation */
  @Schema(description = "The last message sent", example = "Hello. My name is Bob")
  private String last_message;

  /** String representation of the last update timestamp */
  @Schema(description = "Last update timestamp", example = "12345678910112")
  private String last_update;

  /** List of all messages in this conversation */
  @Schema(description = "List of messages")
  private List<MessageDTO> messages;

  /** Status of the conversation (e.g., 0 = open, 1 = closed) */
  @Schema(description = "Status of the conversation", example = "0")
  private int status;

  /**
   * Constructor to initialize the conversation DTO with all fields.
   *
   * @param id The conversation ID
   * @param other_user_picture The profile picture URL of the other user
   * @param other_user_name The name of the other user
   * @param last_update The timestamp of the last update
   * @param messages List of messages in the conversation
   * @param status Status of the conversation (0 = open, 1 = closed)
   */
  public ConversationDTO(int id, String other_user_picture, String other_user_name,
                         String last_update, List<MessageDTO> messages, int status) {
    this.id = id;
    this.other_user_picture = other_user_picture;
    this.other_user_name = other_user_name;
    this.last_update = last_update;
    this.messages = messages;
    this.status = status;
  }

  /** @return The unique ID of the conversation */
  public int getId() {
    return id;
  }

  /** @return The profile picture URL of the other user */
  public String getOther_user_picture() {
    return other_user_picture;
  }

  /** @return The name of the other user */
  public String getOther_user_name() {
    return other_user_name;
  }

  /** @return The latest message text (if available) */
  public String getLast_message() {
    return last_message;
  }

  /** @return The string timestamp of the last update */
  public String getLast_update() {
    return last_update;
  }

  /** @return The list of messages in the conversation */
  public List<MessageDTO> getMessages() {
    return messages;
  }

  /** @return The status of the conversation (0 = open, 1 = closed) */
  public int getStatus() {
    return status;
  }

  /** @param id The ID to set */
  public void setId(int id) {
    this.id = id;
  }

  /** @param other_user_picture The profile picture path to set */
  public void setOther_user_picture(String other_user_picture) {
    this.other_user_picture = other_user_picture;
  }

  /** @param other_user_name The user name to set */
  public void setOther_user_name(String other_user_name) {
    this.other_user_name = other_user_name;
  }

  /** @param last_message The latest message to set */
  public void setLast_message(String last_message) {
    this.last_message = last_message;
  }

  /** @param last_update The update timestamp to set */
  public void setLast_update(String last_update) {
    this.last_update = last_update;
  }

  /** @param messages The list of messages to set */
  public void setMessages(List<MessageDTO> messages) {
    this.messages = messages;
  }

  /** @param status The conversation status to set */
  public void setStatus(int status) {
    this.status = status;
  }

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