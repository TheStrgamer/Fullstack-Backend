package no.ntnu.idatt2105.marketplace.dto.negotiation;

/**
 * Data Transfer Object (DTO) used to send a message in a negotiation conversation.
 * Contains the message text, the ID of the conversation it belongs to, and the user's token for authentication.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 */
public class MessageSendDTO {

  /** The message text being sent */
  private String message;

  /** The ID of the conversation to which the message belongs */
  private int conversationId;

  /** The JWT token of the sender for authentication */
  private String token;

  /**
   * Default constructor.
   */
  public MessageSendDTO() {}

  /**
   * Parameterized constructor to initialize all fields.
   *
   * @param message        The message text.
   * @param conversationId The ID of the conversation.
   * @param token          The JWT token for authentication.
   */
  public MessageSendDTO(String message, int conversationId, String token) {
    this.message = message;
    this.conversationId = conversationId;
    this.token = token;
  }

  /**
   * Gets the message text.
   *
   * @return The message content.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the message text.
   *
   * @param message The message content to set.
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Gets the conversation ID.
   *
   * @return The ID of the conversation.
   */
  public int getConversationId() {
    return conversationId;
  }

  /**
   * Sets the conversation ID.
   *
   * @param conversationId The conversation ID to set.
   */
  public void setConversationId(int conversationId) {
    this.conversationId = conversationId;
  }

  /**
   * Gets the JWT token used for authentication.
   *
   * @return The JWT token.
   */
  public String getToken() {
    return token;
  }

  /**
   * Sets the JWT token for authentication.
   *
   * @param token The JWT token to set.
   */
  public void setToken(String token) {
    this.token = token;
  }

  /**
   * Provides a string representation of the object, useful for debugging and logging.
   *
   * @return A string with key fields.
   */
  @Override
  public String toString() {
    return "MessageSendDTO{" +
            "message='" + message + '\'' +
            ", conversationId=" + conversationId +
            '}';
  }
}