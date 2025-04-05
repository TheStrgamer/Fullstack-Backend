package no.ntnu.idatt2105.marketplace.dto.negotiation;

import java.util.Date;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.service.images.ImagesService;

public class NegotiationChatsDTO {
  private int id;
  private Images other_user_picture;
  private String other_user_name;
  private String last_message;
  private Date last_update;

  public NegotiationChatsDTO(int id, Images other_user_picture, String other_user_name, String last_message,
      Date last_update) {
    this.id = id;
    this.other_user_picture = other_user_picture;
    this.other_user_name = other_user_name;
    this.last_message = last_message;
    this.last_update = last_update;
  }
  public NegotiationChatsDTO(int id, User other_user, String last_message,
      Date last_update) {
    this.id = id;
    ImagesService imagesService = new ImagesService();
    this.other_user_picture = other_user.getProfile_picture() != null ? imagesService.getDefaultUserImage() : other_user.getProfile_picture();
    this.other_user_name = other_user.getFirstname() + " " + other_user.getSurname();
    this.last_message = last_message;
    this.last_update = last_update;
  }
  public int getId() {return id;}
  public Images getOther_user_picture() {return other_user_picture;}
  public String getOther_user_name() {return other_user_name;}
  public String getLast_message() {return last_message;}
  public Date getLast_update() {return last_update;}
  public void setId(int id) {this.id = id;}
  public void setOther_user_picture(Images other_user_picture) {this.other_user_picture = other_user_picture;}
  public void setOther_user_name(String other_user_name) {this.other_user_name = other_user_name;}
  public void setLast_message(String last_message) {this.last_message = last_message;}
  public void setLast_update(Date last_update) {this.last_update = last_update;}







}
