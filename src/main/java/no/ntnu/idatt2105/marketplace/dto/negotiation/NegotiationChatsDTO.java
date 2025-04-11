package no.ntnu.idatt2105.marketplace.dto.negotiation;

import java.util.Date;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.user.User;

/**
 * Data Transfer Object (DTO) representing an overview of a negotiation chat.
 * It includes information about the conversation ID, other user's profile picture,
 * their name, the last message sent, and the timestamp of the last update.
 */
public class NegotiationChatsDTO {
  private int id;
  private String other_user_picture;
  private String other_user_name;
  private String last_message;
  private Date last_update;

  /**
   * Constructor to initialize the chat DTO using a {@link User} object.
   *
   * @param id             The ID of the conversation.
   * @param other_user     The other user involved in the negotiation.
   * @param last_message   The last message exchanged in the conversation.
   * @param last_update    The timestamp of the last update in the conversation.
   */
  public NegotiationChatsDTO(int id, User other_user, String last_message, Date last_update) {
    this.id = id;
    this.other_user_picture = other_user.getProfile_picture() != null
            ? other_user.getProfile_picture().getFilepath_to_image()
            : "";
    this.other_user_name = other_user.getFirstname() + " " + other_user.getSurname();
    this.last_message = last_message;
    this.last_update = last_update;
  }

  /**
   * Constructor to initialize the chat DTO using an {@link Images} object for the profile picture.
   *
   * @param id                 The ID of the conversation.
   * @param other_user_picture The profile picture of the other user.
   * @param other_user_name    The full name of the other user.
   * @param last_message       The last message exchanged in the conversation.
   * @param last_update        The timestamp of the last update in the conversation.
   */
  public NegotiationChatsDTO(int id, Images other_user_picture, String other_user_name, String last_message,
                             Date last_update) {
    this.id = id;
    this.other_user_picture = (other_user_picture != null && other_user_picture.getFilepath_to_image() != null)
            ? other_user_picture.getFilepath_to_image()
            : "";
    this.other_user_name = other_user_name;
    this.last_message = last_message;
    this.last_update = last_update;
  }

  /** @return The ID of the conversation. */
  public int getId() {
    return id;
  }

  /** @return The profile picture URL of the other user. */
  public String getOther_user_picture() {
    return other_user_picture;
  }

  /** @return The full name of the other user. */
  public String getOther_user_name() {
    return other_user_name;
  }

  /** @return The last message sent in the conversation. */
  public String getLast_message() {
    return last_message;
  }

  /** @return The date of the last update to the conversation. */
  public Date getLast_update() {
    return last_update;
  }

  /** @param id The new ID to set. */
  public void setId(int id) {
    this.id = id;
  }

  /** @param other_user_picture The new profile picture URL to set. */
  public void setOther_user_picture(String other_user_picture) {
    this.other_user_picture = other_user_picture;
  }

  /** @param other_user_name The new user name to set. */
  public void setOther_user_name(String other_user_name) {
    this.other_user_name = other_user_name;
  }

  /** @param last_message The new last message to set. */
  public void setLast_message(String last_message) {
    this.last_message = last_message;
  }

  /** @param last_update The new last update timestamp to set. */
  public void setLast_update(Date last_update) {
    this.last_update = last_update;
  }
}