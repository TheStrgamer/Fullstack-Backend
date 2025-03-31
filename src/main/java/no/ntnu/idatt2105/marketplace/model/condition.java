package no.ntnu.idatt2105.marketplace.model;

import jakarta.persistence.*;

@Entity
@Table(name = "condition")
public class condition {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column (nullable = false, unique = true)
  private String name;


  // constructor
  public condition() {}

  public condition(int id, String name) {
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
}
