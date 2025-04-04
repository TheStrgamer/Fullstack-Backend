package no.ntnu.idatt2105.marketplace.model.other;

import jakarta.persistence.*;

import java.util.Date;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;

@Entity
@Table(name = "offer")
public class Offer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User buyer;

  @ManyToOne
  @JoinColumn(name = "listing_id", nullable = false)
  private Listing listing;


  @Column(nullable = false)
  private float current_offer;

  @Column(nullable = false)
  private int status;

  @Column(nullable = false)
  private Date created_at;

  @Column(nullable = false)
  private Date updated_at;

  // constructor
  public Offer() {}

  public Offer(int id, User buyer_id, Listing listing_id, float current_offer, int status, Date created_at, Date updated_at) {
    this.id = id;
    this.buyer = buyer_id;
    this.listing = listing_id;
    this.current_offer = current_offer;
    this.status = status;
    this.created_at = created_at;
    this.updated_at = updated_at;
  }

  // getters and setters


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getBuyer_id() {
    return buyer;
  }
  public Listing getListing_id() {
    return listing;
  }

  public void setListing_id(Listing listing_id) {
    this.listing = listing_id;
  }

  public float getCurrent_offer() {
    return current_offer;
  }

  public void setCurrent_offer(float current_offer) {
    this.current_offer = current_offer;
  }

  public Date getCreated_at() {
    return created_at;
  }

  public Date getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(Date updated_at) {
    this.updated_at = updated_at;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}