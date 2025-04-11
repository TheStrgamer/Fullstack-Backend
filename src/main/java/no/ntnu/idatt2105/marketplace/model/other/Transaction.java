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

  @ManyToOne
  @JoinColumn(name = "users_id", nullable = false)
  private User buyer;  // Change to ManyToOne for multiple transactions per user

  @ManyToOne
  @JoinColumn(name = "listing_id", nullable = false)
  private Listing listing;  // Change to ManyToOne for multiple transactions per listing

  @Column(nullable = false)
  private int final_price;

  @Column(nullable = false)
  private Date created_at;

  @Column(nullable = false)
  private Date updated_at;

  @Column(nullable = false)
  private String status;

  // constructor
  public Transaction() {}

  public Transaction(User buyer, Listing listing, int final_price, Date created_at, Date updated_at, String status) {
    this.buyer = buyer;
    this.listing = listing;
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

  public Listing getListing() {
    return listing;
  }

  public void setListing(Listing listing) {
    this.listing = listing;
  }

  public User getBuyer() {
    return buyer;
  }

  public void setBuyer(User buyer) {
    this.buyer = buyer;
  }

  public int getFinal_price() {
    return final_price;
  }

  public void setFinal_price(int final_price) {
    this.final_price = final_price;
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
