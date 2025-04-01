package no.ntnu.idatt2105.marketplace.model.user;

import jakarta.persistence.*;

import java.util.Date;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;

@Entity
@Table(name = "browse_history")
public class Browse_history {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user_id;

  @OneToOne
  @JoinColumn(name = "listing_id", nullable = false)
  private Listing listing_id;

  @Column(nullable = false)
  private Date search_date;

  // constructor
  public Browse_history() {}

  public Browse_history(int id, User user_id, Listing listing_id, Date search_date) {
    this.id = id;
    this.user_id = user_id;
    this.listing_id = listing_id;
    this.search_date = search_date;
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

  public Date getSearch_date() {
    return search_date;
  }

  public void setSearch_date(Date search_date) {
    this.search_date = search_date;
  }
}
