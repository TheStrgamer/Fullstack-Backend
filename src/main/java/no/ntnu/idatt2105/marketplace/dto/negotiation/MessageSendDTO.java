package no.ntnu.idatt2105.marketplace.dto.negotiation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data transfer object representing a message to be sent in a negotiation")
public class MessageSendDTO {
  @Schema(description = "The message content", example = "Hello, how are you?")
  private String message;
  @Schema(description = "Unique ID of the conversation", example = "1")
  private int conversationId;
  @Schema(description = "JWT token for authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private String token;

  public MessageSendDTO() {}
  public MessageSendDTO(String message, int conversationId, String token) {
    this.message = message;
    this.conversationId = conversationId;
    this.token = token;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public int getConversationId() {
    return conversationId;
  }
  public void setConversationId(int conversationId) {
    this.conversationId = conversationId;
  }
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "MessageSendDTO{" +
        "message='" + message + '\'' +
        ", conversationId=" + conversationId +

        '}';
  }

}
