package no.ntnu.idatt2105.marketplace.dto.negotiation;

public class MessageDTO {
  private int id;
  private String text;
  private boolean sendtBySelf;
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
