package no.ntnu.idatt2105.marketplace.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "offer")
public class offer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @OneToOne
  @JoinColumn(name = "users_id", nullable = false)
  private user buyer_id;

  @OneToOne
  @JoinColumn(name = "listing_id", nullable = false)
  private listing listing_id;


  @Column(nullable = false)
  private float current_offer;

  @Column(nullable = false)
  private int status;

  @Column(nullable = false)
  private Date created_at;

  @Column(nullable = false)
  private Date updated_at;

  // constructor
  public offer() {}

  public offer(int id, user buyer_id, listing listing_id, float current_offer, int status, Date created_at, Date updated_at) {
    this.id = id;
    this.buyer_id = buyer_id;
    this.listing_id = listing_id;
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

  public user getBuyer_id() {
    return buyer_id;
  }

  public void setBuyer_id(user buyer_id) {
    this.buyer_id = buyer_id;
  }

  public listing getListing_id() {
    return listing_id;
  }

  public void setListing_id(listing listing_id) {
    this.listing_id = listing_id;
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

  public void setCreated_at(Date created_at) {
    this.created_at = created_at;
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
