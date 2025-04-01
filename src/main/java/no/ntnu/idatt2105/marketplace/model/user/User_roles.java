package no.ntnu.idatt2105.marketplace.model.user;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class User_roles {

  @OneToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  @OneToOne
  @JoinColumn(name = "users_id", nullable = false)
  private User user;


  @Id
  private int id;

  // constructor
  public User_roles() {}

  public User_roles(int id, Role role, User user) {
    this.id = id;
    this.role = role;
    this.user = user;
  }

  // setters  and getters


  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}