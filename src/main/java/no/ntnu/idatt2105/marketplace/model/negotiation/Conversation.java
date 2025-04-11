package no.ntnu.idatt2105.marketplace.model.negotiation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import no.ntnu.idatt2105.marketplace.model.listing.Listing;
import no.ntnu.idatt2105.marketplace.model.user.User;

@Entity
@Table(name = "conversation")
public class Conversation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "buyer_id", nullable = false)
  private User buyer;

  @ManyToOne
  @JoinColumn(name = "listing_id", nullable = false)
  private Listing listing;


  @OneToMany(mappedBy = "conversation")
  private List<Message> messages;

  @Column(nullable = false)
  private int status;

  @Column(nullable = false)
  private Date created_at;

  @Column(nullable = false)
  private Date updated_at;

  // constructor
  public Conversation() {}

  public Conversation(User buyer, Listing listing) {
    if (buyer == listing.getCreator()) {
      throw new IllegalArgumentException("Buyer cannot be the same as the seller");
    }
    this.buyer = buyer;
    this.listing = listing;
    this.status = 0; // 0 = open, 1 = closed
    this.created_at = new Date();
    this.updated_at = this.created_at;
  }
  // getters and setters
  public int getId() {
    return id;
  }

  public User getBuyer() {
    return buyer;
  }
  public User getSeller() {
    return listing.getCreator();
  }
  public Listing getListing() {
    return listing;
  }
  public List<Message> getMessages() {
    return messages;
  }

  public String getLatestMessage() {
    if (messages == null || messages.isEmpty()) {
      return null;
    }
    return messages.get(messages.size() - 1).getText();
  }
  public void addMessage(Message message) {
    this.messages.add(message);
  }

  public int getStatus() {
    return status;
  }
  public void setStatus(int status) {
    this.status = status;
  }
  public void updateDate() {
    this.updated_at = new Date();
  }
  public Date getCreatedAt() {
    return created_at;
  }
  public Date getUpdatedAt() {
    return updated_at;
  }
  public void setUpdatedAt(Date updated_at) {
    this.updated_at = updated_at;
  }

}
