package no.ntnu.idatt2105.marketplace.dto.negotiation;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object representing a single message within a conversation.
 * Used to transfer non-sensitive message information between the backend and frontend.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 */

public class MessageDTO {

  /** Unique identifier for the message */
  @Schema(description = "Id of the message", example = "123")
  private int id;

  /** The textual content of the message */
  @Schema(description = "Message content text", example = "This is a message!")
  private String text;

  /** Flag indicating if the message was sent by the current user */
  @Schema(description = "Flag indicating if the message was send by the current user", example = "0")
  private boolean sendtBySelf;

  /** A string representation of the timestamp when the message was sent */
  @Schema(description = "The timestamp when the message was sent", example = "12345678910121314")
  private String sendtAt;

  /**
   * Constructor to initialize all fields of the MessageDTO.
   *
   * @param id           The unique ID of the message.
   * @param text         The message text content.
   * @param sendtBySelf  Whether the message was sent by the current user.
   * @param sendtAt      The timestamp string of when the message was sent.
   */
  public MessageDTO(int id, String text, boolean sendtBySelf, String sendtAt) {
    this.id = id;
    this.text = text;
    this.sendtBySelf = sendtBySelf;
    this.sendtAt = sendtAt;
  }

  /**
   * Gets the ID of the message.
   *
   * @return The message ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the message.
   *
   * @param id The ID to set.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the text content of the message.
   *
   * @return The message text.
   */
  public String getText() {
    return text;
  }

  /**
   * Sets the text content of the message.
   *
   * @param text The message text to set.
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Returns whether the message was sent by the current user.
   *
   * @return True if sent by self, false otherwise.
   */
  public boolean isSendtBySelf() {
    return sendtBySelf;
  }

  /**
   * Sets whether the message was sent by the current user.
   *
   * @param sendtBySelf Boolean indicating if it was sent by self.
   */
  public void setSendtBySelf(boolean sendtBySelf) {
    this.sendtBySelf = sendtBySelf;
  }

  /**
   * Gets the string representation of when the message was sent.
   *
   * @return Timestamp string.
   */
  public String getSendtAt() {
    return sendtAt;
  }

  /**
   * Sets the string representation of when the message was sent.
   *
   * @param sendtAt The timestamp to set.
   */
  public void setSendtAt(String sendtAt) {
    this.sendtAt = sendtAt;
  }
}