package no.ntnu.idatt2105.marketplace.dto.negotiation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data transfer object representing a message in a negotiation")
public class MessageDTO {
  @Schema(description = "Unique ID of the message", example = "1")
  private int id;
  @Schema(description = "The message content", example = "Hello, how are you?")
  private String text;
  @Schema(description = "Indicates if the message was sent by the user", example = "true")
  private boolean sendtBySelf;
  @Schema(description = "Timestamp of when the message was sent", example = "2023-10-01T12:00:00Z")
  private String sendtAt;

  public MessageDTO(int id, String text, boolean sendtBySelf, String sendtAt) {
    this.id = id;
    this.text = text;
    this.sendtBySelf = sendtBySelf;
    this.sendtAt = sendtAt;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isSendtBySelf() {
    return sendtBySelf;
  }

  public void setSendtBySelf(boolean sendtBySelf) {
    this.sendtBySelf = sendtBySelf;
  }

  public String getSendtAt() {
    return sendtAt;
  }

  public void setSendtAt(String sendtAt) {
    this.sendtAt = sendtAt;
  }

}
