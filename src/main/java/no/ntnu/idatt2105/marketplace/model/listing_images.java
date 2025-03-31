package no.ntnu.idatt2105.marketplace.model;

import jakarta.persistence.*;

@Entity
@Table(name = "listing_images")
public class listing_images {

  @Id
  private int id;

  @OneToOne
  @JoinColumn(name = "images_id")
  private images image_id;

  @OneToOne
  @JoinColumn(name = "listing_id")
  private listing listing_id;

  // constructor
  public listing_images() {}

  public listing_images(int id, images image_id, listing listing_id) {
    this.id = id;
    this.image_id = image_id;
    this.listing_id = listing_id;
  }

  // getters and setters

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public images getImage_id() {
    return image_id;
  }

  public void setImage_id(images image_id) {
    this.image_id = image_id;
  }

  public listing getListing_id() {
    return listing_id;
  }

  public void setListing_id(listing listing_id) {
    this.listing_id = listing_id;
  }
}
