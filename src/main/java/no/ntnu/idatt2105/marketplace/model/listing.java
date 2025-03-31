package no.ntnu.idatt2105.marketplace.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "listing")
public class listing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @OneToOne
  @JoinColumn(name = "users_id", nullable = false)
  private user user_id;

  @OneToOne
  @JoinColumn(name = "categories_id")
  private categories category;

  @OneToOne
  @JoinColumn(name = "condition_id")
  private condition condition;

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

  // constructor
  public listing() {}

  public listing(int id, user user_id, categories category, condition condition, String title, int sale_status, int price, String brief_description, String full_description, String size, Date created_at, Date updated_at, double latitude, double longitude) {
    this.id = id;
    this.user_id = user_id;
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

  public user getUser_id() {
    return user_id;
  }

  public void setUser_id(user user_id) {
    this.user_id = user_id;
  }

  public categories getCategory() {
    return category;
  }

  public void setCategory(categories category) {
    this.category = category;
  }

  public no.ntnu.idatt2105.marketplace.model.condition getCondition() {
    return condition;
  }

  public void setCondition(no.ntnu.idatt2105.marketplace.model.condition condition) {
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
}