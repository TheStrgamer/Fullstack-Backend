package no.ntnu.idatt2105.marketplace.model.listing;

import jakarta.persistence.*;

@Entity(name = "categories")
public class Categories {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @ManyToOne
  @JoinColumn(name = "categories_id")
  private Categories parent_category;

  // constructor
  public Categories() {}

  public Categories(int id, String name, String description, Categories parent_category){
    this.id = id;
    this.name = name;
    this.description = description;
    this.parent_category = parent_category;
  }

  // gettes and setters


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Categories getParent_category() {
    return parent_category;
  }

  public void setParent_category(Categories parent_category) {
    this.parent_category = parent_category;
  }
}
