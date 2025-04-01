package no.ntnu.idatt2105.marketplace.model.other;

import jakarta.persistence.*;

import java.util.Date;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;

@Entity
@Table(name = "transaction")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @OneToOne
  @JoinColumn(name = "users_id", nullable = false)
  private User buyer_id;

  @OneToOne
  @JoinColumn(name = "listing_id", nullable = false)
  private Listing listing_id;

  @Column(nullable = false)
  private float final_price;

  @Column(nullable = false)
  private Date created_at;

  @Column(nullable = false)
  private Date updated_at;

  @Column(nullable = false)
  private String status;

  // constructor
  public Transaction() {}

  public Transaction(int id, User buyer_id, Listing listing_id, float final_price, Date created_at, Date updated_at, String status) {
    this.id = id;
    this.buyer_id = buyer_id;
    this.listing_id = listing_id;
    this.final_price = final_price;
    this.created_at = created_at;
    this.updated_at = updated_at;
    this.status = status;
  }

  // getters and setters


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Listing getListing_id() {
    return listing_id;
  }

  public void setListing_id(Listing listing_id) {
    this.listing_id = listing_id;
  }

  public User getBuyer_id() {
    return buyer_id;
  }

  public void setBuyer_id(User byer_id) {
    this.buyer_id = byer_id;
  }

  public float getFinal_price() {
    return final_price;
  }

  public void setFinal_price(float final_price) {
    this.final_price = final_price;
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
