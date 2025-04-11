package no.ntnu.idatt2105.marketplace.model.listing;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "condition")
public class Condition {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column (nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "condition")
  private List<Listing> listings;



  // constructor
  public Condition() {}

  public Condition(int id, String name) {
    this.id = id;
    this.name = name;
  }

  // getters and setters


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

  @Override
  public String toString() {
    return this.name;
  }
}
