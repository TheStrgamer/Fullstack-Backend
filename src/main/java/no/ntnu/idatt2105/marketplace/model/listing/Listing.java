package no.ntnu.idatt2105.marketplace.model.listing;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import no.ntnu.idatt2105.marketplace.model.negotiation.Conversation;
import no.ntnu.idatt2105.marketplace.model.other.Images;
import no.ntnu.idatt2105.marketplace.model.negotiation.Offer;
import no.ntnu.idatt2105.marketplace.model.user.User;

@Entity
@Table(name = "listing")
public class Listing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "creator", nullable = false)
  private User creator;

  @ManyToOne
  @JoinColumn(name = "category", nullable = false)
  private Categories category;

  @ManyToOne
  @JoinColumn(name = "condition", nullable = false)
  private Condition condition;

  @Column (nullable = false)
  private String title;

  @Column (nullable = false)
  private int sale_status;

  @Column (nullable = false)
  private int price;

  @Column (nullable = false)
  private String brief_description;

  @Column (nullable = false)
  private String full_description;

  @Column
  private String size;

  @Column (nullable = false)
  private Date created_at;

  @Column (nullable = false)
  private Date updated_at;

  @Column (nullable = false)
  private double latitude;

  @Column (nullable = false)
  private double longitude;

  @ManyToMany(mappedBy = "favorites")
  //private List<User> user_favorites;
  private List<User> user_favorites = new ArrayList<>(); //unngå nullpointer

  @ManyToMany(mappedBy = "history")
  //private List<User> user_history;
  private List<User> user_history = new ArrayList<>(); //unngå nullpointer

  @OneToMany(mappedBy = "listing")
  //private List<Offer> offers;
  private List<Offer> offers = new ArrayList<>(); //unngå nullpointer

  @OneToMany(mappedBy = "listing")
  //private List<Images> images;
  private List<Images> images = new ArrayList<>(); //unngå nullpointer

  @OneToMany(mappedBy = "listing")
  private List<Conversation> conversations = new ArrayList<>();

  // constructor
  public Listing() {}

  public Listing(int id, User user_id, Categories category, Condition condition, String title, int sale_status, int price, String brief_description, String full_description, String size, Date created_at, Date updated_at, double latitude, double longitude) {
    this.id = id;
    this.creator = user_id;
    this.category = category;
    this.condition = condition;
    this.title = title;
    this.sale_status = sale_status;
    this.price = price;
    this.brief_description = brief_description;
    this.full_description = full_description;
    this.size = size;
    this.created_at = created_at;
    this.updated_at = updated_at;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  // constructor


  // getters and setters


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getCreator() {
    return creator;
  }

    public void setCreator(User creator) {
        this.creator = creator;
    }

  public Categories getCategory() {
    return category;
  }

  public void setCategory(Categories category) {
    this.category = category;
  }

  public Condition getCondition() {
    return condition;
  }

  public void setCondition(Condition condition) {
    this.condition = condition;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getSale_status() {
    return sale_status;
  }

  public void setSale_status(int sale_status) {
    this.sale_status = sale_status;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getFull_description() {
    return full_description;
  }

  public void setFull_description(String full_description) {
    this.full_description = full_description;
  }

  public String getBrief_description() {
    return brief_description;
  }

  public void setBrief_description(String brief_description) {
    this.brief_description = brief_description;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
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

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public List<Images> getImages() {
    return images;
  }

  public void addImage(Images image) {
    images.add(image);
  }

  public void removeImage(Images image) {
    images.remove(image);
  }

  public void setImages(List<Images> images) {
    this.images = images;
  }

  public List<Conversation> getConversations() {
    return conversations;
  }
  public void addConversation(Conversation conversation) {
    conversations.add(conversation);
  }
  public void closeConversation(Conversation conversation) {
    conversation.setStatus(1);
  }
  public void closeAllConversations() {
    for (Conversation conversation : conversations) {
      conversation.setStatus(1);
    }
  }

}