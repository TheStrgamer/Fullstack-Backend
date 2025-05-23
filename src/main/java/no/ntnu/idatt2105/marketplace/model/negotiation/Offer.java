package no.ntnu.idatt2105.marketplace.model.negotiation;

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
  private int current_offer;

  @Column(nullable = false)
  private int status;

  @Column(nullable = false)
  private Date created_at;

  @Column(nullable = false)
  private Date updated_at;

  @ManyToOne()
  @JoinColumn(name = "created_by", nullable = false)
  private User creator;

  // constructor
  public Offer() {}

  public Offer(User buyer_id, Listing listing_id, int current_offer, int status, Date created_at, Date updated_at, User created_by) {
    this.buyer = buyer_id;
    this.listing = listing_id;
    this.current_offer = current_offer;
    this.status = status;
    this.created_at = created_at;
    this.updated_at = updated_at;
    this.creator = created_by;
  }

  // getters and setters


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getBuyer() {
    return buyer;
  }
  public Listing getListing() {
    return listing;
  }

  public void setListing_id(Listing listing_id) {
    this.listing = listing_id;
  }

  public int getCurrent_offer() {
    return current_offer;
  }

  public void setCurrent_offer(int current_offer) {
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

  public User getCreator() {
    return creator;
  }
}