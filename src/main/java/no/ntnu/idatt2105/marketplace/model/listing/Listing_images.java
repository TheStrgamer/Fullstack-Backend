package no.ntnu.idatt2105.marketplace.model.listing;

import jakarta.persistence.*;
import no.ntnu.idatt2105.marketplace.model.other.Images;

@Entity
@Table(name = "listing_images")
public class Listing_images {

  @Id
  private int id;

  @OneToOne
  @JoinColumn(name = "images_id")
  private Images image_id;

  @OneToOne
  @JoinColumn(name = "listing_id")
  private Listing listing_id;

  // constructor
  public Listing_images() {}

  public Listing_images(int id, Images image_id, Listing listing_id) {
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

  public Images getImage_id() {
    return image_id;
  }

  public void setImage_id(Images image_id) {
    this.image_id = image_id;
  }

  public Listing getListing_id() {
    return listing_id;
  }

  public void setListing_id(Listing listing_id) {
    this.listing_id = listing_id;
  }
}
