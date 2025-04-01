package no.ntnu.idatt2105.marketplace.model.user;

import jakarta.persistence.*;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;

@Entity
@Table(name = "favorites")
public class Favorites {

  @Id
  private int id;

  @OneToOne
  @JoinColumn(name = "users_id", nullable = false)
  private User user_id;

  @OneToOne
  @JoinColumn(name = "listing_id", nullable = false)
  private Listing listing_id;

  // constructor
  public Favorites() {}

  public Favorites(int id, User user_id, Listing listing_id) {
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

  public User getUser_id() {
    return user_id;
  }

  public void setUser_id(User user_id) {
    this.user_id = user_id;
  }

  public Listing getListing_id() {
    return listing_id;
  }

  public void setListing_id(Listing listing_id) {
    this.listing_id = listing_id;
  }
}
