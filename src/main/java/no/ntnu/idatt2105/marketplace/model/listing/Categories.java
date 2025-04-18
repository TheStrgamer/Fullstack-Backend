package no.ntnu.idatt2105.marketplace.model.listing;

import jakarta.persistence.*;
import java.util.List;

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

  @OneToMany(mappedBy = "category")
  private List<Listing> listings;

  // constructor
  public Categories() {}

  public Categories(String name, String description, Categories parent_category){
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

  public List<Listing> getListings() {
    return listings;
  }

  @Override
  public String toString() {
    return name;
  }
}
