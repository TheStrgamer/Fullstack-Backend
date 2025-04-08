package no.ntnu.idatt2105.marketplace.dto.negotiation;

public class MessageSendDTO {
  private String message;
  private int conversationId;
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
