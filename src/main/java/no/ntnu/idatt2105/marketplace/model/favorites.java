package no.ntnu.idatt2105.marketplace.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorites")
public class favorites {

  @Id
  private int id;

  @OneToOne
  @JoinColumn(name = "users_id", nullable = false)
  private user user_id;

  @OneToOne
  @JoinColumn(name = "listing_id", nullable = false)
  private listing listing_id;

  // constructor
  public favorites() {}

  public favorites(int id, user user_id, listing listing_id) {
    this.id = id;
    this.user_id = user_id;
    this.listing_id = listing_id;
  }

  // getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public user getUser_id() {
    return user_id;
  }

  public void setUser_id(user user_id) {
    this.user_id = user_id;
  }

  public listing getListing_id() {
    return listing_id;
  }

  public void setListing_id(listing listing_id) {
    this.listing_id = listing_id;
  }
}
