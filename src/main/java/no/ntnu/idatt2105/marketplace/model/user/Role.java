package no.ntnu.idatt2105.marketplace.model.user;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(unique = true, nullable = false)
  private String name;

  @ManyToMany(mappedBy = "roles")
  private List<User> users;

  // constructor
  public Role() {}
  public Role(int id, String name) {
    this.id = id;
    this.name = name;
  }

  //getters and setters


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

  public List<User> getUsers() {
    return users;
  }
}
