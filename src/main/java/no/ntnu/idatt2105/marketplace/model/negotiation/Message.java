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
@Table(name = "message")
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User sender;

  @ManyToOne
  @JoinColumn(name = "conversation_id", nullable = false)
  private Conversation conversation;

  @Column(nullable = false)
  private String text;

  @Column(nullable = false)
  private Date sendt_at;

  // constructor
  public Message() {}

  public Message(int id, User sender_id, Conversation conversation_id, String text) {
    this.id = id;
    this.sender = sender_id;
    this.conversation = conversation_id;
    this.text = text;
    this.sendt_at = new Date();
  }

  // getters and setters
  public int getId() {
    return id;
  }

  public User getSender() {
    return sender;
  }

  public Conversation getConversation() {
    return conversation;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Date getCreatedAt() {
    return sendt_at;
  }

  @Override
  public String toString() {
    return "Message{" +
        ", sender=" + sender +
        ", conversation=" + conversation +
        ", text='" + text + '\'' +
        ", sendt_at=" + sendt_at +
        '}';
  }

}
